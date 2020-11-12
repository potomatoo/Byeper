from django.db import models

# Create your models here.
class Video(models.Model):
    video_id = models.CharField(max_length=20)
    video_title = models.CharField(max_length=80)
    video_description = models.TextField()
    video_img = models.TextField()
    video_max_img = models.IntegerField()

class Problem(models.Model):
    video = models.ForeignKey(Video, on_delete=models.CASCADE)
    origin = models.TextField()
    problem = models.TextField()
    answer = models.CharField(max_length=100, null=True)
