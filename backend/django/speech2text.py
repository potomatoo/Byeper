from collections import Counter
import os, re
from pytube import YouTube
from textblob import TextBlob

check_kor = re.compile('[가-힣]+')

def make_answer(url):
    # video_url = 'https://www.youtube.com/watch?v=P8C8VNiM33A'
    # video_url = 'https://www.youtube.com/watch?v=tDeN1SdSqe8'
    video_url = 'https://www.youtube.com/watch?v='+url
    yt = YouTube(video_url)

    caption = yt.captions.get_by_language_code('ko')
    if caption == None: 
        caption = yt.captions.get_by_language_code('a.ko')
    
    caption = caption.generate_srt_captions()
        
    result = ''
    for sen in caption.split('\n'):
        if not check_kor.search(sen): continue
        result += sen
        if sen[-1] in ('다', '?', '요', '죠', '.'):
            result += '\n'
        else:
            result += ' '

    origin = TextBlob(result)
    eng = origin.translate('ko', 'en')

    answers = []
    for k in Counter(eng.noun_phrases).most_common():
        for word in k[0].split():
            if len(word) < 3: break
        else:
            answers.append(k[0])
        if len(answers) == 10: break
    return eng, answers

