from Image2text import image_processing
from textblob import TextBlob
from collections import Counter
# result = image_processing('C:\\Users\\multicampus\\Desktop\\s03p31b108\\backend\\django\\tmp\\tQHw2EovIOM')
# with open('foo.txt', 'w', encoding='utf-8') as f:
#     f.write(result)
with open('foo.txt', 'r', encoding="utf-8") as f:
    result = f.read()

cnt = Counter()
origin = TextBlob(result)
eng = origin.translate(to='en')
answer_list = []
for answers in set(eng.noun_phrases):
    for answer in answers.split():
        if len(answer) < 3: break
    else:
        answer_list.append(answers)

print(type(eng))
for sen in eng.split('\n'):
    sen = TextBlob(sen)
    candy = ''
    for words, tag in sen.tags:
        print(words, tag)
        for 
    print('=============================')