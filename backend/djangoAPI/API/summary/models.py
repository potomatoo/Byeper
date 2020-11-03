from django.db import models

# Create your models here.
class Video(models.Model):
    video_id = models.CharField(max_length=20)
    video_title = models.CharField(max_length=80)
    video_description = models.TextField()
    video_img = models.IntegerField()
    video_max_img = models.IntegerField()