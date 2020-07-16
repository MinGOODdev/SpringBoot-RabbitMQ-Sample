# SpringBoot RabbitMQ Sample
* 2대의 RabbitMQ를 Docker에 띄워서 테스트한다.

### 테스트 환경 세팅 - Docker 및 RabbitMQ 설치 방법
1. 로컬에 DockerHub 설치
2. 터미널에서 docker --version 명령어로 docker가 잘 설치되었는지 확인
3. 설치되었다면
```
docker run -d --hostname listen-rabbit --name listen-rabbit -p 8201:5672 -p 18201:15672 rabbitmq:3-management
docker run -d --hostname userevent-rabbit --name userevent-rabbit -p 8202:5672 -p 18202:15672 rabbitmq:3-management
```
* listen-rabbit, userevent-rabbit 이라는 이름으로 2대의 RabbitMQ를 띄운다.
* listen-rabbit은 8201(mq) 포트 / 18201(management) 포트
* user-event는 8202(mq) 포트 / 18202(management) 포트

4. docker ps로 각 프로세스가 잘 떴는지 확인할 수 있다. 