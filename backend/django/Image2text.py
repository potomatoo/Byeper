from google.cloud import vision
import os, cv2, io
import re

def image_processing(path):
    # /var/file/VIDEO_ID
    # root = 'C:\\Users\\pyoun\\Desktop\\s03p31b108\\backend\\django\\tmp\\tQHw2EovIOM'
    imgs = []
    for f in os.listdir(path):
        tmp = cv2.imread(os.path.join(path, f), 0)
        imgs.append(cv2.resize(tmp, (854, 480)))
    img = cv2.vconcat(imgs)
    target_dir = path[-11:]
    tmp_image = os.path.join(path, target_dir+'.png')

    cv2.imwrite(tmp_image, img)
    
    os.environ['GOOGLE_APPLICATION_CREDENTIALS'] = "C:\\Users\\pyoun\\Desktop\\pk.json"
    client = vision.ImageAnnotatorClient()

    with io.open(tmp_image, 'rb') as image_path:
        content = image_path.read()

    image = vision.Image(content=content)
    response = client.document_text_detection(image=image)
    texts = response.text_annotations

    sentence = ''
    for text in texts[0].description.split('\n'):
        text = re.sub(r"\((.*?)\)", '', text)
        if len(text.replace(' ', '')) > 20: 
            sentence += text
            if sentence[-1] not in '?!.': sentence += '.'
            sentence += ' '

    if response.error.message:
        raise Exception(
            '{}\nFor more info on error messages, check: '
            'https://cloud.google.com/apis/design/errors'.format(
                response.error.message))

    os.remove(tmp_image)
    return sentence

PATH = 'C:\\Users\\pyoun\\Desktop\\s03p31b108\\backend\\django\\tmp\\tQHw2EovIOM'
result = image_processing(PATH)

print(result)