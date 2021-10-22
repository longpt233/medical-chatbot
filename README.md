## medical-chatbox
midterm information system integration


## how to build 

- build image spring

```bash
cd spring-service
docker build -t spring-service:v1 .
```

- build imagg-medical 
```bash
docker build -t medical-module:v1 .
```

- run service: 

```bash
docker-compose up 
```

## on dev env 

- chay spring boot app bthg, can them gi vao db thi lien he Long 
- viet dich vu truy nhap thong tin ca nhan, thong tin covid vao spring app nay 


## how to use mongodb 

- sử dụng cluster mongo atlas : có thể tìm hiểu ở link : ```https://www.mongodb.com/cloud/atlas/register```
- tải mongodb compass  (gui) 
- url connect ```mongodb+srv://<user>:<pass>@cluster-longpt.ocem8.mongodb.net/test```
- user, pass 

## module port
- spring : 8081
- image medical : 8083 : spring se call module nay


