from django.shortcuts import render, get_object_or_404

from rest_framework.response import Response
from rest_framework.decorators import api_view

from .models import Problem
from .serializers import ProblemSerializer
# from . import question_generatorTEST

@api_view(['GET'])
def create_problem_or_show_list(request):
    questions = Problem.objects.all()
    serializer = ProblemSerializer(questions, many=True)
    return Response(serializer.data)

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
