from django.shortcuts import render, get_object_or_404

from rest_framework.response import Response
from rest_framework.decorators import api_view

from .models import Video
from .serializers import VideoSerializer
from .models import Problem
from .serializers import ProblemSerializer

import json
# from . import question_generatorTEST

@api_view(['get', 'POST'])
def extract_image(request):
    if request.method == 'GET':
        video = Video.objects.all()
        serializer = VideoSerializer(video, many=True)
        return Response(serializer.data)

    elif request.method == 'POST':
        serializer = VideoSerializer(data=request.data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()
        return Response(serializer.data)

# @api_view(['GET'])
# def extract_image(request):
#     problem = Problem.objects.all()
#     serializer = ProblemSerializer(problem, many=True)
#     return Response(serializer.data)
