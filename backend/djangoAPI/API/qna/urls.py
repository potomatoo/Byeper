from django.urls import path
from django.urls.resolvers import URLPattern
from . import views

app_name = 'qna'

urlpatterns = [
    path('', views.questions_list),
]