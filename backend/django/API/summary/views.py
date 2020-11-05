from django.shortcuts import render, get_object_or_404

from rest_framework.response import Response
from rest_framework.decorators import api_view

from .models import Video
from .serializers import VideoSerializer
from .models import Problem
from .serializers import ProblemSerializer

# from . import question_generatorTEST

@api_view(['POST'])
def extract_video(request):
    if request.method == 'POST':
        serializer = VideoSerializer(data=request.data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()
        return Response(serializer.data)

# from . import question_generatorTEST

@api_view(['GET', 'POST'])
def extract_image(request):
    video = get_object_or_404(Video, pk=video_pk)
    if request.mothod == 'GET':
        problems = Problem.objects.all()
        serializer = ProblemSerializer(problems, many=True)
        return Response(serializer.data)
    elif request.method == 'POST':
        serializer = ProblemSerializer(data=request.data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()
            res = serializer.data['success'] = True
        return Response(res.data)


# @api_view(['GET', 'POST'])
# def create_problem_or_show_list(request, video_pk):
#     question = get_object_or_404(Questionanswer, pk=video_pk)

#     if request.method == 'GET':
#         musics = Music.objects.all()
#         serializer = MusicSerializer(musics, many=True)
#         return Response(serializer.data)
#     elif request.method == 'POST':
#         # MusicSerializer fields => title
#         serializer = MusicSerializer(data=request.data)
#         if serializer.is_valid(raise_exception=True):
#             serializer.save(artist_id=artist.id)
#         return Response(serializer.data)