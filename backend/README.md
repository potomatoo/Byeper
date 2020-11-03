- Tesseract-OCR 설치 (`Image 기반 Text 추출`)

```bash
-> https://github.com/UB-Mannheim/tesseract/wiki
-> `tesseract-ocr-w64-setup-v5.0.0-alpha.20200328.exe` (64 bit) resp. # 설치

# 또는
-> https://github.com/tesseract-ocr/tesseract/wiki # 리눅스 기반
```

- ./djangoAPI/requirements.txt (python 모듈 설치)

```python
pip install -r requirments.txt
```

- Image2text.py

```bash
# tesseract를 사용하여 이미지에서 텍스트를 추출한다.
python Image2text.py
```

- question_generatorTEST.py

```bash
# input을 기준으로 질문생성을 수행
python question_generatorTEST.py
```

- spacy model 설치

```python
# en_core_web_md 파일이 설치되어야 하는데 pip로는 설치되지 않는다.
# pip로 설치하려면
https://github.com/explosion/spacy-models/releases/download/en_core_web_md-2.3.1/en_core_web_md-2.3.1.tar.gz
# 를 입력하면된다.
```

- django mysql 사용

```bash
mysql -u root -p # 명령을 통해 Mysql을 실행시킨 후 다음의 명령어로 데이터베이스를 만들어준다.
mysql> CREATE DATABASE pypy CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
mysql> use pypy;
mysql> show tables; # Empty set (0.01 sec) 아직 테이블을 생성하지 않았기 때문에 테이블이 없다는 메세지를 반환해준다.
```

