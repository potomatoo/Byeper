from google.cloud import vision
import os, cv2, io, re
check_kor = re.compile('[가-힣]+')
check_eng = re.compile('[a-z|A-Z]+')

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
        if not check_kor.search(text): continue
        
        if text[-1] in ('은', '는', '이', '가', '을', '를'):
            sentence += text + ' '
        else:
            sentence += text + '\n'

    if response.error.message:
        raise Exception(
            '{}\nFor more info on error messages, check: '
            'https://cloud.google.com/apis/design/errors'.format(
                response.error.message))

    os.remove(tmp_image)
    return sentence
