from django.db import models

# Create your models here.
class Problem(models.Model):
    origin = models.TextField()
    problem = models.TextField()
    answer = models.CharField(max_length=100, null=True)
    similar1 = models.CharField(max_length=100, null=True)
    similar2 = models.CharField(max_length=100, null=True)
    similar3 = models.CharField(max_length=100, null=True)
    similar4 = models.CharField(max_length=100, null=True)