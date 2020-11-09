from Image2text import image_processing
from textblob import TextBlob
# result = image_processing('C:\\Users\\multicampus\\Desktop\\s03p31b108\\backend\\django\\tmp\\tQHw2EovIOM')
# with open('foo.txt', 'w', encoding='utf-8') as f:
#     f.write(result)


with open('foo.txt', 'r', encoding="utf-8") as f:
    result = f.read()


origin = TextBlob(result)
eng = origin.translate('ko', 'en')


# for ori in origin.split('\n'):
#     en = ori.translate('ko', 'en')
#     # print(en, type(en))
print(set(eng.noun_phrases))
for texts in set(eng.noun_phrases):
    for text in texts.split():
        if len(text) < 3: break
    else:
        print(texts)