from django.shortcuts import render, get_object_or_404
# from django.views.decorators.http import require_GET
# from django.http.response import JsonResponse, HttpResponse

from rest_framework.response import Response
from rest_framework.decorators import api_view

from .models import Problem
from .serializers import ProblemSerializer
# Create your views here.

# from . import question_generatorTEST

@api_view(['GET'])
def questions_list(request):
    questions = Problem.objects.all()
    serializer = ProblemSerializer(questions, many=True)
    return Response(serializer.data)

# @api_view(['GET', 'POST'])
# def music_create_list(request, artist_pk):
#     question = get_object_or_404(Questionanswer, pk=artist_pk)

#     if request.method == 'GET':
#         musics = Music.objects.all()
#         serializer = MusicSerializer(musics, many=True)
#         return Response(serializer.data)
#     elif request.method == 'POST':
#         # MusicSerializer fields => title
#         serializer = MusicSerializer(data=request.data)
#         if serializer.is_valid(raise_exception=True): # artist_id 검증 안해서
#             serializer.save(artist_id=artist.id)
#             # music = forms.save(commit=False)
#             # music.artist = artist
#             # music.save()
#             # 3줄이 위의 하나로
#         return Response(serializer.data)
