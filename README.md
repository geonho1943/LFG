## LFG

<H3>LFG(Looking For Group)</H3>
스팀 멀티 플레이 개임의 방만들기, 같이하기 등의 <br>
유저모집 기능이 없는 게임의 파티를 모집할수 있는 웹 사이트입니다<br>

### 파티 모집을 지원하지 않은 게임<br>
[PICO PARK](https://store.steampowered.com/app/461040/PICO_PARKClassic_Edition/)<br>
[RAFT](https://store.steampowered.com/app/648800/Raft/)<br>
[WE WERE HERE](https://store.steampowered.com/app/582500/We_Were_Here/)<br>
[ASTRONEER](https://store.steampowered.com/app/361420/ASTRONEER/)<br>
[스팀에세 멀티게임 더 찾아보기](https://store.steampowered.com/category/multiplayer/)<br>
---
## Period
2022/10/31 ~

## Environment

* BE: 
  * Java17
  * Spring Boot
  * JUnit, Gradle
  * GitHub Actions
* FE: 
  * HTML
  * CSS
  * JS
  * jQuery
  * Thymeleaf
* DB:
  * MariaDB
  * H2DB
* ETC:
  * AWS
  * Oracle Cloud


## workFlow
![LFG_workFlow_V9](https://github.com/geonho1943/LFG/assets/106109077/b84d7375-f8f2-4696-a403-09df791e632c)
<h6> gliffy </h6>

id: pushvalue42<br>
pw: aL!ce!n1865<br>

## ERD
![LFG_ERD](https://github.com/geonho1943/LFG/assets/106109077/e48350ac-b499-4b2b-840e-5859c16d9cb1)
<h6> MySQL Workbench </h6>

##  API

### Doc
| 이름         | 메서드    | 엔드 포인트   | 설명           | in     | out            |
|--------------|--------|---------------|----------------|--------|----------------|
| 메인 페이지 | GET    | /             | 메인 페이지   |        | doc/docList    |
| 게시물 검색 | GET    | /docDetail    | 게시물이 검색된 페이지 반환 | idx    | doc/docDetail  |
| 글 쓰기 기능 | POST   | /docPost      | 게시물 작성   | Doc    | redirect:/     |
| 글 수정 페이지 | GET    | /docUpdate    | 게시물 수정 페이지 | doc_idx | doc/docUpdate |
| 글 수정     | PUT    | /docUpdate    | 게시물 수정   | Doc    | redirect:/     |
| 글 검색     | GET    | /docSearch    | 이름으로 게시물 조회 | name   | doc/docList    |
| 글 삭제     | DELETE | /docDelete    | 게시물 삭제   | Doc    | redirect:/     |

### App
| 이름            | 메서드 | 엔드 포인트 | 설명                | in   | out                |
|-----------------|--------|-------------|-------------------|------|--------------------|
| 앱 리스트 갱신 | PUT    | /appList    | 최신 스팀 게임 리스트로 동기화 |      | redirect:/         |
| 앱 검색         | POST   | /searchApp  | 앱(게임) 조회          | name | appService.searchAppName(name) |

### User

| 이름              | 메서드 | 엔드 포인트  | 설명                          | in       | out                          |
|------------------|--------|--------------|-------------------------------|----------|------------------------------|
| 회원가입 페이지 | GET    | /userJoin    | 유저 회원가입 페이지         |          | user/userJoin                |
| 회원가입         | POST   | /userJoin    | 유저 회원가입                 | User     | redirect:/                   |
| 로그인 페이지    | GET    | /userLogin   | 유저 로그인 페이지           |          | user/userLogin               |
| 로그인            | POST   | /userLogin   | 유저 로그인                   | User     | redirect:/                   |
| 로그아웃         | GET    | /logout      | 유저 로그아웃                 |          | redirect:/                   |
| 회원정보 수정 페이지 | GET    | /userModify  | 유저 회원정보 수정 페이지   |          | user/userModify              |
| 회원정보 수정     | PUT    | /userModify  | 유저 회원정보 수정           | User     | redirect:/                   |
| 회원 프로필       | GET    | /myProfile   | 유저 회원정보 조회           |          | user/userProfile             |
| id 중복체크       | POST   | /idCheck     | 회원가입시 id 중복 검증 (비동기) | id       | userService.check(id)        |
| 유저오류 페이지  | GET    | /userError   | 유저정보에 의한 에러 페이지 |          | user/userError               |

## DTO

### App

| 데이터 타입 | 변수명      |
|-------------|----------|
| int         | app_id   |
| String      | app_name |


### Doc

| 데이터 타입 | 변수명          |
|-------------|--------------|
| int         | doc_idx      |
| String      | doc_sub      |
| String      | doc_writ     |
| String      | doc_cont     |
| String      | doc_reg      |
| String      | doc_app_name |
| int         | doc_app_id   |


### LoginInfo

| 데이터 타입 | 변수명       |
|-------------|-----------|
| int         | user_idx  |
| String      | user_id   |
| String      | user_name |
| String      | user_reg  |
| int         | user_role |


### User

| 데이터 타입 | 변수명       |
|-------------|-----------|
| int         | user_idx  |
| String      | user_id   |
| String      | user_name |
| String      | user_pw   |
| String      | user_reg  |
| int         | user_role |

