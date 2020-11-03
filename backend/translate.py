import google.cloud.translate_v2 as translate
import os

def register_env():
    os.environ['GOOGLE_APPLICATION_CREDENTIALS'] = "C:\\Users\\pyoun\\Desktop\\pk.json"
    #print(os.environ['GOOGLE_APPLICATION_CREDENTIALS'])
    translate_client = translate.Client()
    
def kor2eng(text):
    RESULT = translate_client.translate(text, target_language='en')
    return RESULT['input'], RESULT['translatedText']

def eng2kor(text):
    RESULT = translate_client.translate(text, target_language='ko')
    return RESULT['input'], RESULT['translatedText']

text = """Intuitively, the whole process is very similar to the operation of _____ c elements into the priority queue and then removing them all     
putting"""

eng_origin, kor_result = eng2kor(text)
kor_origin, eng_result = kor2eng(text)
