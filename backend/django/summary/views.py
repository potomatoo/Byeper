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

from Image2text import image_processing
from konlpy.tag import Komoran
os.environ['GOOGLE_APPLICATION_CREDENTIALS'] = "/home/ubuntu/pk.json"
os.environ['JAVA_HOME'] = "/usr/lib/jvm/java-11-openjdk-amd64/bin"
komoran = Komoran()

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
        print(e) 
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
    print(111)
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

        result = image_processing(path)  # image --> text

        # if not result:
        #     return Response(200)
            
        # origin = TextBlob(result)
        # eng = origin.translate('ko', 'en')
        # answers = eng.noun_phrases

        answer_list = []

        answers = set([word for word in komoran.nouns(result) \
            if len(word) > 2 and word[-1] not in ('은', '는', '이', '을', '를', '요', '다', '까')])

        QnA = []
        for sentence in result.split('\n'):
            for answer in answers:
                if answer in sentence:
                    problem = sentence.replace(answer, '______')
                    DATA = {'problem':problem, 'answer': answer, 'video':video_pk, 'origin':sentence}
                    QnA.append(DATA)


        return Response({'data':QnA, 'OCR':result})
