FROM python:3.8
WORKDIR /src/app/covid-module

COPY requirements.txt .
RUN pip install -r requirements.txt

COPY . .

EXPOSE 5006

CMD python app.py