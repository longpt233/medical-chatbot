FROM python:3.8
WORKDIR /src/app/health-care-services

COPY requirements.txt .
RUN pip install -r requirements.txt

COPY . .

CMD python app.py