FROM tensorflow/tensorflow:latest

WORKDIR /usr/src/app

RUN apt-get update -y
RUN apt-get install ffmpeg libsm6 libxext6  -y

COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

EXPOSE 8083

CMD ["python", "flask_service.py"]
