from google.cloud import vision
import os, cv2

def detect_text(path):
    import io
    os.environ['GOOGLE_APPLICATION_CREDENTIALS'] = "C:\\Users\\pyoun\\Desktop\\pk.json"
    client = vision.ImageAnnotatorClient()

    with io.open(path, 'rb') as image_file:
        content = image_file.read()

    image = vision.Image(content=content)
    response = client.document_text_detection(image=image)

    texts = response.text_annotations
    sentence = ''
    for text in texts[0].description.split('\n'):
        # if len(text.replace(' ', '')) < 25: continue
        print(len(text.replace(' ', '')), text)
        sentence += text + ' '
    # print(sentence)
    if response.error.message:
        raise Exception(
            '{}\nFor more info on error messages, check: '
            'https://cloud.google.com/apis/design/errors'.format(
                response.error.message))

def combine_image(path):
    # /var/file/VIDEO_ID
    # root = 'C:\\Users\\pyoun\\Desktop\\s03p31b108\\backend\\django\\tmp\\tQHw2EovIOM'
    target_dir = path[:-11]
    imgs = []
    for f in os.listdir(path):
        tmp = cv2.imread(os.path.join(path, f), 0)
        imgs.append(cv2.resize(tmp, (854, 480)))
    img = cv2.vconcat(imgs)
    tmp_image = os.path.join(path, target_dir+'.png')
    cv2.imwrite(tmp_image, img)
    detect_text(tmp_image)
    os.remove(tmp_image)
    return path

combine_image('C:\\Users\\pyoun\\Desktop\\s03p31b108\\backend\\django\\tmp\\tQHw2EovIOM')