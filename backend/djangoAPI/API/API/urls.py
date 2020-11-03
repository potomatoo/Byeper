from django.contrib import admin
from django.urls import path, include

urlpatterns = [
    path('admin/', admin.site.urls),
    path('api/django/qna', include('qna.urls')),
    path('api/django/summary', include('summary.urls')),
]
