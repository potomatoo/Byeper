from django.db import models

# Create your models here.
class Video(models.Model):
    video_id = models.CharField(max_length=20)
    video_title = models.CharField(max_length=80)
    video_description = models.TextField()
    video_img = models.IntegerField()
    video_max_img = models.IntegerField()

class Problem(models.Model):
    video = models.ForeignKey(Video, on_delete=models.CASCADE)
    origin = models.TextField()
    problem = models.TextField()
    answer = models.CharField(max_length=100, null=True)
    similar1 = models.CharField(max_length=100, null=True)
    similar2 = models.CharField(max_length=100, null=True)
    similar3 = models.CharField(max_length=100, null=True)
    similar4 = models.CharField(max_length=100, null=True)