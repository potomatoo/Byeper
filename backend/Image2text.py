import pytesseract
import cv2
pytesseract.pytesseract.tesseract_cmd = 'C:/Program Files/Tesseract-OCR/tesseract'
img = cv2.imread("example_img/img4.png",0)
sentances = pytesseract.image_to_string(
    img, lang='kor(2)+eng(2)', 
    config='--psm 6 --oem 1')
# print(sentances.replace('ㅅ', 'A').replace('^','A'))
print(sentances)

from langdetect import detect_langs
print(detect_langs(sentances))