from django.urls import path
from django.urls.resolvers import URLPattern
from . import views

app_name = 'summary'

urlpatterns = [
    path('extract/', views.extract_image),
    path('qna/<int:video_pk>/', views.problem_create_list),
    path('extract_time/', views.extract_time)
    # path('', views.extract_image),
    # path('qa/<int:video_pk>/', views.questions_detail_list),
]
