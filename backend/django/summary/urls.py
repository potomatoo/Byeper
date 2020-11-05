from django.urls import path
from django.urls.resolvers import URLPattern
from . import views

app_name = 'summary'

urlpatterns = [
    path('extract/', views.extract_image),
    path('qna/<int:video_pk>/', views.create_problem_or_show_list),
    # path('', views.extract_image),
    # path('qa/<int:video_pk>/', views.questions_detail_list),
]