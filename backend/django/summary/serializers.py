from .models import Video, Problem
from rest_framework import serializers

class VideoSerializer(serializers.ModelSerializer):
    class Meta:
        model = Video
        fields= '__all__'

class ProblemSerializer(serializers.ModelSerializer):
    class Meta:
        model = Problem
        fields= '__all__'