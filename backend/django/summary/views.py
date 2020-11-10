from django.shortcuts import render, get_object_or_404
# from django.views.decorators.http import require_GET
# from django.http.response import JsonResponse, HttpResponse

from rest_framework.response import Response
from rest_framework.decorators import api_view

from .models import Video
from .serializers import VideoSerializer
# Create your views here.

# from . import question_generatorTEST
import cv2
import numpy as np
import os
import pafy
import re
import json
import datetime
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
        return False
    try:
        video = pafy.new(youtube_url)
    except:
        return extract_from_youtube_url(youtube_url, n+1)
    best = video.getbest()
    id = youtube_url[32:]
    
    if not(os.path.isdir('/var/file/{}'.format(id))):
        os.makedirs(os.path.join('/var/file/{}'.format(id)))
    else:
        return -1
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
        save(i, image, id)
        info_dict[int(i)] = {'time': int(frame // 30), 
                        'diff': int(diff)}
    with open("/var/file/{}/{}.json".format(id, id), "w") as json_file:
        json.dump(info_dict, json_file)
    return len(save_frames)

@api_view(['POST'])
def extract_image(request):
    if request.method == 'POST':
        data = request.data
        start = datetime.datetime.now()
        video_max_img = extract_from_videoid(data['video_id'])
        end = datetime.datetime.now()
        print(end - start)
        if video_max_img == -1:
            return Response(200)
        elif video_max_img == False:
            return Response(500)
        data['video_max_img'] = video_max_img
        serializer = VideoSerializer(data=data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()
        return Response(data)

@api_view(['POST'])
def extract_time(request):
    id = request.data['id']
    url = 'https://www.youtube.com/watch?v=' + id
    time = request.data['time']
    frame = int(time * 30)
    video = pafy.new(url)
    best = video.getbest()
    vidcap = cv2.VideoCapture(best.url)
    vidcap.set(cv2.CAP_PROP_POS_FRAMES, frame)
    _, image = vidcap.read()
    imwrite("/var/file/{}/{}.jpg".format(id, id + str(frame)), image)
    return Response("/var/file/{}/{}.jpg".format(id, id + str(frame)))

@api_view(['GET', 'POST'])
def create_problem_or_show_list(request, video_pk):
    video = get_object_or_404(request, pk = video_pk)
    if request.method == 'GET':
        pass
    elif request.method == 'POST':
        pass