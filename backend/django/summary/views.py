from django.shortcuts import render, get_object_or_404
from rest_framework.response import Response
from rest_framework.decorators import api_view

from .models import Video
from .serializers import VideoSerializer
from .models import Problem
from .serializers import ProblemSerializer

import cv2, os, io, re
import numpy as np
import pafy, json

from pytube import YouTube
from konlpy.tag import Komoran
os.environ['JAVA_HOME'] = '/usr/lib/jvm/java-11-openjdk-amd64/bin'
check_kor = re.compile('[가-힣]+')
check_eng = re.compile('[a-zA-Z]+')
check_num = re.compile('[0-9]+')
check_bracket = re.compile('\([^)]*\)')


save_frames = []
def imwrite(filename, img, params=None): 
    try: 
        ext = os.path.splitext(filename)[1] 
        result, n = cv2.imencode(ext, img, params) 
        if result: 
            with open(filename, mode='w+b') as f: 
                n.tofile(f) 
            return True 
        else: 
            return False 
    except Exception as e: 
        return False

def save(frame, image, title):
    imwrite("/var/file/{}/{}.jpg".format(title, frame), image)

def divide_and_conquer(vidcap, left, right, left_image, right_image):
    global save_frames
    if right - left < 20:
        #저장
        diff = np.subtract(left_image, right_image, dtype=np.int16)
        diff = np.abs(diff)
        diff = np.sum(diff>10)
        save_frames.append([right, diff])
        print('save ', right // 30, 's')
        return
    mid = (left + right) // 2
    vidcap.set(cv2.CAP_PROP_POS_FRAMES, mid)
    ret, mid_image = vidcap.read()
    mid_image = cv2.resize(mid_image, (400, 225))
    mid_image = cv2.cvtColor(mid_image, cv2.COLOR_BGR2GRAY)
    diff = np.subtract(left_image, mid_image, dtype=np.int16)
    diff = np.abs(diff)
    diff_sum_left = np.sum(diff>10)

    diff = np.subtract(mid_image, right_image, dtype=np.int16)
    diff = np.abs(diff)
    diff_sum_right = np.sum(diff>10)
    if diff_sum_left < 1000 and diff_sum_right < 1000:
        # 뭔가 이상한곳
        return
    if diff_sum_left > 1000 and diff_sum_right > 1000:
        if right - left < 600:
            divide_and_conquer(vidcap, mid, right, mid_image, right_image)
        else:
            divide_and_conquer(vidcap, left, mid, left_image, mid_image)
            divide_and_conquer(vidcap, mid, right, mid_image, right_image)
        return
    if diff_sum_left > 1000:
        divide_and_conquer(vidcap, left, mid, left_image, mid_image)
        return
    if diff_sum_right > 1000:
        divide_and_conquer(vidcap, mid, right, mid_image, right_image)
        return

def extract_from_videoid(id):
    return extract_from_youtube_url('https://www.youtube.com/watch?v=' + id, 0)

def extract_from_youtube_url(youtube_url, n):
    global save_frames
    if n == 8: 
        return 0
    try:
        video = pafy.new(youtube_url)
    except:
        return extract_from_youtube_url(youtube_url, n+1)
    best = video.getbest()
    id = youtube_url[32:]
    
    if not(os.path.isdir('/var/file/{}'.format(id))):
        os.makedirs(os.path.join('/var/file/{}'.format(id)))
    vidcap = cv2.VideoCapture(best.url)
    vidcap.set(3, 400)
    vidcap.set(4, 225)
    _, start_image = vidcap.read()
    end = int(video.length * 29.97) - 30
    vidcap.set(cv2.CAP_PROP_POS_FRAMES, end)
    _, end_image = vidcap.read()
    start_image = cv2.resize(start_image, (400, 225))
    start_image = cv2.cvtColor(start_image, cv2.COLOR_BGR2GRAY)
    end_image = cv2.resize(end_image, (400, 225))
    end_image = cv2.cvtColor(end_image, cv2.COLOR_BGR2GRAY)

    save_frames = [[0, 99999]]
    divide_and_conquer(vidcap, 0, end, start_image, end_image)
    
    info_dict = []
    for i, save_frame in enumerate(save_frames):
        frame, diff = save_frame
        vidcap.set(cv2.CAP_PROP_POS_FRAMES, frame)
        _, image = vidcap.read()
        save(i, image, id)
        info_dict.append({
                        'address': '/file/{}/{}.jpg'.format(id, i),
                        'time': int(frame // 30), 
                        'diff': int(diff)})
    with open("/var/file/{}/{}.json".format(id, id), "w") as json_file:
        json.dump(info_dict, json_file)
    return len(save_frames)

@api_view(['POST'])
def extract_image(request):
    if request.method == 'POST':
        # data = request.data
        # video_max_img = extract_from_videoid(data['video_id'])
        # if video_max_img == -1:
        #     return Response('already exist')
        # elif video_max_img == False:
        #     return Response('failed')
        # data['video_max_img'] = video_max_img
        # serializer = VideoSerializer(data=data)
        # if serializer.is_valid(raise_exception=True):
        #    serializer.save()
        video_ids = request.data
        for video_id in video_ids:
            video = Video.objects.get(video_id=video_id)
            video_max_img = extract_from_videoid(video_id)
            video.video_max_img = video_max_img
            video.save()
        return Response(200)

@api_view(['POST', 'OPTIONS'])
def extract_time(request):

    id = request.data['id']
    url = 'https://www.youtube.com/watch?v=' + id
    time = request.data['time']
    frame = int(time * 30)
    cnt = 0
    if not(os.path.isdir('/var/file/{}'.format(id))):
        os.makedirs(os.path.join('/var/file/{}'.format(id)))
    while cnt < 10:
        try:
            video = pafy.new(url)
            break
        except:
            cnt += 1
    best = video.getbest()
    vidcap = cv2.VideoCapture(best.url)
    vidcap.set(cv2.CAP_PROP_POS_FRAMES, frame)
    _, image = vidcap.read()
    imwrite("/var/file/{}/{}.jpg".format(id, id + str(frame)), image)
    return Response("/file/{}/{}.jpg".format(id, id + str(frame)))


@api_view(['POST'])
def problem_create_list(request):
    if request.method == 'POST':
        video_pk = request.data['video_id']

        path = os.path.join('/var/file', video_pk)
        is_json = os.path.join(path, video_pk+'.json')
        if not os.path.isdir(path) or not os.path.isfile(is_json):
            return -1  # --> video dir check

        sentence, answers = make_answer(video_pk)
        sentence = str(sentence)
        QnA = []
        for sen in sentence.split('\n'):
            for answer in answers:
                if len(sen.split()) >= 4 and answer in sen:
                    problem = sen.replace(answer, '______')
                    DATA = {'problem':problem, 'answer': answer, 'video':video_pk, 'origin':sen}
                    QnA.append(DATA)

        return Response({ 'data':QnA })


def make_answer(urls):
    video_url = 'https://www.youtube.com/watch?v=' + urls
    yt = YouTube(video_url)

    caption = yt.captions.get_by_language_code('ko')
    if caption == None: 
        caption = yt.captions.get_by_language_code('a.ko')

    caption = caption.generate_srt_captions()
    result = ''
    for sen in caption.split('\n'):
        if not check_kor.search(sen): continue
        result += sen
        if sen[-1] in ('다', '?', '요', '죠', '.'):
            result += '\n'
        else:
            result += ' '

    komoran = Komoran()
    stopwords = set(open('stopwords.txt', 'r', encoding='utf-8').read().split('\n'))
    answers = set()
    for sen in result.split('\n'):
        Nouns = set([ w for w, t in set(komoran.pos(sen)) if (t == 'NNP' or t == 'NNG') and len(w) > 1 and not set(w) & set('0123456789') and not {w} & stopwords])
        stack = []
        for word in sen.split():
            if check_eng.search(word):
                if check_kor.search(word) or check_bracket.search(word):
                    engs = re.sub('[가-힣]+', '', word)
                    # engs = re.sub('[-=+,#/\?:^$.@*\"※~&%ㆍ!』\\‘|\(\)\[\]\<\>`\'…》]', '', engs)
                    engs = re.sub('\([^)]*\)', '', engs)
                    if stack:
                        if check_eng.search(stack[-1]):
                            stack.append(engs)
                        else:
                            answers.add(' '.join(stack))
                            stack = [engs]
                    else:
                        stack = [engs]
                else:
                    stack.append(word)
            else:
                if stack and check_eng.search(stack[-1]):

                    answers.add(' '.join(stack))
                    stack = []
                
                if len(word) < 3:
                    if stack: answers.add(' '.join(stack)); stack = []
                    continue
                for target in komoran.nouns(word):
    
                    if target in Nouns:
                         
                        if target != word:
                            stack.append(target)
                            answers.add(' '.join(stack))
                            stack = []
                        else:
                            stack.append(target)
                    else:
                        if stack:
                            answers.add(' '.join(stack))
                            stack = []

    if stack: answers.add(' '.join(stack))

    return result, sorted([ w for w in answers if len(w) > 2], key=lambda x:len(x), reverse=True)[:12]
