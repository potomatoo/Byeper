from django.contrib import admin
from django.urls import path, include
from rest_framework_swagger.views import get_swagger_view
# from rest_framework.schemas.coreapi import AutoSchema

schema_view = get_swagger_view(title='Aang Gimoddi')

urlpatterns = [
    path('admin/', admin.site.urls),
    path('api/django/qna/', include('qna.urls')),
    path('api/django/summary/', include('summary.urls')),
    path('api/doc/', schema_view),
]
