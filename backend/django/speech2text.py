import os, re
from pytube import YouTube
from konlpy.tag import Komoran
os.environ['JAVA_HOME'] = '/usr/lib/jvm/java-11-openjdk-amd64/bin'

check_kor = re.compile('[가-힣]+')
check_eng = re.compile('[a-zA-Z]+')
check_num = re.compile('[0-9]+')
check_bracket = re.compile('\([^)]*\)')

def make_answer(urls):
    video_url = 'https://www.youtube.com/watch?v=' + urls
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

    komoran = Komoran()

    stopwords = set(open('stopwords.txt', 'r', encoding='utf-8').read().split('\n'))
    answers = set()
    for sen in result.split('\n'):
        Nouns = set([ w for w, t in set(komoran.pos(sen)) if (t == 'NNP' or t == 'NNG') and len(w) > 1 and not set(w) & set('0123456789') and not {w} & stopwords])
        stack = []
        for word in sen.split():
            if len(word) < 3:
                if stack:
                    answers.add(' '.join(stack))
                    stack = []
            elif check_eng.search(word):
                if check_kor.search(word) or check_bracket.search(word):
                    engs = re.sub('[^a-zA-Z]+', '', word)
                    engs = re.sub('\([^)]*\)', '', engs)
                    if stack and check_eng.search(stack[-1]):
                        stack.append(engs)
                        answers.add(' '.join(stack))
                        stack = []
                    else:
                        answers.add(' '.join(stack))
                        answers.add(engs)
                        stack = []
                else:
                    stack.append(word)
            else:
                for target in komoran.nouns(word):
                    if target in Nouns:
                        if len(target) < 3: continue
                        if target != word:
                            stack.append(target)
                            answers.add(' '.join(stack))
                            stack = []
                        else:
                            stack.append(target)
                    else:
                        if stack:
                            answers.add(' '.join(stack))
                            stack = []

    if stack: answers.add(' '.join(stack))

    return result, sorted(list(answers), key=lambda x:len(x), reverse=True)[:10]

print('-hdtrReY9Zw')
