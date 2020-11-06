from django.urls import path
from django.urls.resolvers import URLPattern
from . import views

app_name = 'summary'

urlpatterns = [
    path('extract/', views.extract_image),
    path('extract/<int:video_pk>/problem/', views.problem_create_list),
    # path('', views.extract_image),
    # path('qa/<int:video_pk>/', views.questions_detail_list),
]