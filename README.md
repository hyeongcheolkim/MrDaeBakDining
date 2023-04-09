# MrDaeBakDining 백엔드 RestAPI서버

## 📮 개요
- 학교 소프트웨어공학 수업 프로젝트
- 백엔드 1명, 프론트 2명 총 3명의 팀 프로젝트
- 백엔드 역할로 참여
  - 명세서 추상화
  - DB 설계
  - API 백엔드 서버 구현
## 📃 기술스택
- Spring Boot
- JPA
- H2
- MariaDB
- AWS EC2, RDS, S3
- Nginix 

## 🔍 기술적 구현 사항

- Spring MVC 동기 프로그래밍 구조
- Session을 통해 인증
- AOP로 Session이 있는지 없는지 확인
- RestConrollerAdvice를 통해 예외를 처리
- @Valid를 사용해 값 validate
- 아마존 웹 서비스를 이용한 실제 배포
- Nginx의 포트 리다이렉팅를 이용한 http 배포로부터 https 배포로의 변경
