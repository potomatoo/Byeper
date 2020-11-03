from .models import Questionanswer
from rest_framework import serializers

class ProblemSerializer(serializers.ModelSerializer):
    class Meta:
        model = Questionanswer
        fields= '__all__'