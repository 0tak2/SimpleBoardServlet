# SimpleBoardServlet

기본 서블릿과 Vue.js를 이용하여 작성한 게시판 프로젝트입니다.
백엔드에 CORS 처리를 위한 필터가 있어 프론트엔드와 백엔드를
별도의 서버에서 기동할 수 있습니다.  
프론트엔드를 별도로 구성하지 않고, JSP 기반의 뷰를 구성한 이전 프로젝트는 [여기](https://github.com/0tak2/KOSAjava/tree/main/Projects-With-Servlet/BoardProject0209)서 볼 수 있습니다.

## 테스트 인스트럭션

- 아래는 이 프로젝트의 데모를 간단하게 테스트하기 위한 인스트력션입니다.
  아래의 개발 환경이 이미 구축되어 있음을 가정합니다.
- 권장 테스트 환경
    - Git
    - Oracle JDK 1.8
    - Tomcat 9.x
    - Apach Maven 3.x
    - 위의 개발 도구가 연동되어 있는 이클립스 EE 2022-12
    - MySQL 8.x

### 1. Git 저장소 클론

```bash
$ git clone https://github.com/0tak2/SimpleBoardServlet
$ cd SimpleBoardServlet
```

### 2. MySQL 스크립트 실행

```bash
$ mysql -u유저 -p비밀번호

mysql> source ./board-backend/base.sql

# 오류가 없는 것을 확인

mysql> exit
```

### 3. 백엔드 세팅

- 이클립스의 Git Perspective에서 클론한 저장소의 디렉토리를 선택해
불러온 후, 디렉토리 내의 board-backend에 대해 Import Project

- '프로젝트 루트/resources/driver.properties'를 열어 자신의 MySQL 서버에 맞게 수정

- 톰캣 서버에 해당 프로젝트 Configure 및 서버 실행

### 4. 프론트엔드 세팅

```bash
$ cd board-frontend
$ npm install
$ npm run serve
```

이후 브라우저를 실행하여 http://localhost:8090 접속

&nbsp;

&nbsp;

2023 by 0tak