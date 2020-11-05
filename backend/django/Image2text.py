import pytesseract
import cv2
import os, glob
import time
pytesseract.pytesseract.tesseract_cmd = 'C:/Program Files/Tesseract-OCR/tesseract'
t = time.time()
root = 'C:\\Users\\pyoun\\Desktop\\s03p31b108\\backend\\django\\tmp'
target_dir = 'tQHw2EovIOM'
files = os.path.join(root, target_dir)
for f in os.listdir(files):
    img = cv2.imread(os.path.join(files, f),0)
    img = cv2.resize(img, (0, 0), fx=0.5, fy=0.5)
    sentances = pytesseract.image_to_string(
        img, lang='kor(2)+eng(2)', 
        config='--psm 6 --oem 1')
    print(sentances.replace('ㅅ', 'A').replace('^','A'))
print(time.time()-t)
# print(sentances)
# from langdetect import detect_langs
# print(detect_langs(sentances))