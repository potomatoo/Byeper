from django.shortcuts import render, get_object_or_404
# from django.views.decorators.http import require_GET
# from django.http.response import JsonResponse, HttpResponse

from rest_framework.response import Response
from rest_framework.decorators import api_view

from .models import Video
from .serializers import VideoSerializer
from .models import Problem
from .serializers import ProblemSerializer
# Create your views here.

import google.cloud.vision
import google.cloud.translate_v2 as translate
import cv2, os, io, re
import numpy as np
import pafy, json

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
    sec = frame // 30
    imwrite("image/{}/{}.jpg".format(title, sec), image)

def divide_and_conquer(vidcap, left, right, left_image, right_image):
    global save_frames
    if right - left < 20:
        #저장
        diff = np.subtract(left_image, right_image, dtype=np.int16)
        diff = np.abs(diff)
        diff = np.sum(diff>10)
        save_frames.append([right, diff])
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
    if diff_sum_left < 2000 and diff_sum_right < 2000:
        # 뭔가 이상한곳
        return
    if diff_sum_left > 2000 and diff_sum_right > 2000:
        if right - left < 600:
            divide_and_conquer(vidcap, mid, right, mid_image, right_image)
        else:
            divide_and_conquer(vidcap, left, mid, left_image, mid_image)
            divide_and_conquer(vidcap, mid, right, mid_image, right_image)
        return
    if diff_sum_left > 2000:
        divide_and_conquer(vidcap, left, mid, left_image, mid_image)
        return
    if diff_sum_right > 2000:
        divide_and_conquer(vidcap, mid, right, mid_image, right_image)
        return

def get_time(sec):
    if sec < 0: 
        return 0, 0, 0
    hour, remain = sec // 3600, sec % 3600
    minute, sec = remain // 60, remain % 60
    return hour, minute, sec

def extract_from_videoid(id):
    return extract_from_youtube_url('https://www.youtube.com/watch?v=' + id, 0)

def extract_from_youtube_url(youtube_url, n):
    global save_frames
    if n == 5: 
        return False
    try:
        video = pafy.new(youtube_url)
    except:
        return extract_from_youtube_url(youtube_url, n+1)
    best = video.getbest()
    id = youtube_url[32:]
    
    if not(os.path.isdir('image/{}'.format(id))):
        os.makedirs(os.path.join('image/{}'.format(id)))

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
    
    info_dict = {}
    for i, save_frame in enumerate(save_frames):
        frame, diff = save_frame
        vidcap.set(cv2.CAP_PROP_POS_FRAMES, frame)
        _, image = vidcap.read()
        save(frame, image, id)
        info_dict[int(i)] = {'time': int(frame // 30), 
                        'diff': int(diff)}
    with open("{}.json".format(id), "w") as json_file:
        json.dump(info_dict, json_file)
    return len(save_frames)


@api_view(['POST'])
def extract_image(request):
    if request.method == 'POST':
        data = request.data
        video_max_img = extract_from_videoid(data['video_id'])
        data['video_max_img'] = video_max_img
        serializer = VideoSerializer(data=data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()
        return Response(200)


@api_view(['GET', 'POST'])
def problem_create_list(request, video_pk):
    video = get_object_or_404(Video, pk=video_pk)
    if request.method == 'GET':
        problems = Problem.objects.all()
        serializer = ProblemSerializer(problems, many=True)
        return Response(serializer.data)
    elif request.method == 'POST':
        
        serializer = ProblemSerializer(data = request.data)
        if serializer.is_valid(raise_exception=True):
            serializer.save(video_id=video.id)
        return Response(200)