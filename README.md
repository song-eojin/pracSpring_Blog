# pracSpring_Blog
<h3>내일배움캠프 Spring 개인과제 (lv1 ~ 플러스 주차)</h3>
나만의 항해 블로그 백엔드 서버 만들기

<br>

<h3>튜터님께 확인받고 싶은 내용</h3>

<br>

<h3>요구사항과 다르게 적용한 내용</h3>
- 회원가입 API 요구사항 : 데이터베이스에 존재하는 닉네임을 입력한 채 회원가입 버튼을 누른 경우 "중복된 닉네임입니다." 라는 에러메세지를 response에 포함하기

-> MsgResponse로 반환하는 것을 요구하는 것으로 이해했으며, 제 생각에는 요청을 할 때 중복된 사례를 예리처리해주는 것이 사용자 입장에서 올바를 것 같아 userService에서 처리했습니다.

<br>

<h3>요구사항</h3>
- 7/25 - 회원가입 API
    - 닉네임, 비밀번호, 비밀번호 확인을 request에서 전달받기
    - 닉네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성하기
    - 비밀번호는 최소 4자 이상이며, 닉네임과 같은 값이 포함된 경우 회원가입에 실패로 만들기
    - 비밀번호 확인은 비밀번호와 정확하게 일치하기
    - 데이터베이스에 존재하는 닉네임을 입력한 채 회원가입 버튼을 누른 경우 "중복된 닉네임입니다." 라는 에러메세지를 response에 포함하기

<br>

<h3>설계 01. 유즈케이스</h3>

<br>

<h3>설계 02. API 명세서</h3>

<br>

<h3>설계 03. ERD</h3>

<br>



