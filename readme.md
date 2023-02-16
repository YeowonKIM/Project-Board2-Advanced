### 회원가입, 로그인 기능이 들어간 게시판 만들기 
<br/> 

#### | 목표 |
  1. 회원가입, 로그인을 구현하기
  2. 인증/인가를 이해하고 JWT를 활용하여 게시글을 처리하기
  3. JPA 연관관계를 이해하고 회원과 게시글을 구현하기

<br/> <br/> 

## STEP 1 : 서비스 요구사항 분석


 #### 1. 회원 가입 API

    • username, password를 Client에서 전달받기

    • username은 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 한다.

    • password는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 한다.

    • DB에 중복된 username이 없다면 회원을 저장하고 Client 로 성공했다는 메시지, 상태코드 반환한다.

 

  #### 2. 로그인 API

    • username, password를 Client에서 전달받기

    • DB에서 username을 사용하여 저장된 회원의 유무를 확인하고 있다면 password 비교하기

    • 로그인 성공 시, 로그인에 성공한 유저의 정보와 JWT를 활용하여 토큰을 발급하고,

      발급한 토큰을 Header에 추가하고 성공했다는 메시지, 상태코드 와 함께 Client에 반환하기

 

  #### 3. 게시글 작성 API 

    • 토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능

    • 제목, 작성자명(username), 작성 내용을 저장하고

    • 저장된 게시글을 Client 로 반환하기

 

  #### 4. 선택한 게시글 조회 API

    : 선택한 게시글의 제목, 작성자명(username), 작성 날짜, 작성 내용을 조회하기

 

   #### 5. 선택한 게시글 수정 API 

      • 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능

      • 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기

 

   #### 6. 선택한 게시글 삭제 API

      • 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 삭제 가능

      • 선택한 게시글을 삭제하고 Client 로 성공했다는 메시지, 상태코드 반환하기
      
<br/> <br/> 

## STEP 2 :  유스케이스 작성

<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fdase29%2FbtrZqS36imb%2FhQVeYLW3RIOUIl94QZior0%2Fimg.jpg"  width="600" height="400">

<br/> <br/> 


## STEP 3 :  ERD 작성
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbjn7Lx%2FbtrZuDxBn0d%2FarPrZmDRfhsJ3hglN13C30%2Fimg.png"  width="600" height="300">

<br/><br/>


## STEP 4 :   API 명세서 작성
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F3BIAM%2FbtrZrUGQzoI%2FapZzTKE35ZuEZK4E9V1dV1%2Fimg.png"  width="800" height="600">
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FzIDWR%2FbtrZqFYhnou%2FJGqBmK60mjc5mF0mxBYvC0%2Fimg.png"  width="800" height="400">

<br/><br/>

## STEP 5 :  코드작성

<br/><br/>

 
