# pracSpring_Blog
<h3>내일배움캠프 Spring 개인과제 (lv1 ~ 플러스 주차)</h3>
나만의 항해 블로그 백엔드 서버 만들기

<br><br>

<h3>튜터님께 확인받고 싶은 내용</h3>

<br><br>

<h3>요구사항과 다르게 적용한 내용</h3>

<ul>
    <li>회원가입 API 요구사항 : 데이터베이스에 존재하는 닉네임을 입력한 채 회원가입 버튼을 누른 경우 "중복된 닉네임입니다." 라는 에러메세지를 response에 포함하기</li>
    <br>
    <li>회원가입 API 요구시힝 변경 : MsgResponse로 반환하는 것을 요구하는 것으로 이해했으며, 제 생각에는 요청을 할 때 중복된 사례를 예리처리해주는 것이 사용자 입장에서 올바를 것 같아 userService에서 처리했습니다.</li>
</ul>

<br><br>

<h3>요구사항</h3>
<ul>
    <li>7/25 회원가입 API 
        <ul>
            <li>닉네임, 비밀번호, 비밀번호 확인을 request에서 전달받기</li>
            <li>username(아이디 역할)은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성하기</li>
            <li>비밀번호는 최소 4자 이상이며, username과 같은 값이 포함된 경우 회원가입에 실패로 만들기</li>
            <li>비밀번호 확인은 비밀번호와 정확하게 일치하기</li>
            <li>데이터베이스에 존재하는 닉네임을 입력한 채 회원가입 버튼을 누른 경우 "중복된 닉네임입니다." 라는 에러메세지를 response에 포함하기</li>
        </ul>        
    </li>
    <li>7/26 - 로그인 API
        <ul>
            <li>닉네임, 비밀번호를 request에서 전달받기</li>
            <li>로그인 버튼을 누른 경우 닉네임과 비밀번호가 데이터베이스에 등록됐는지 확인한 뒤, 하나라도 맞지 않는 정보가 있다면 "닉네임 또는 패스워드를 확인해주세요."라는 에러 메세지를 response에 포함하기</li>
            <li>로그인 성공 시, 로그인에 성공한 유저의 정보를 JWT를 활용하여 클라이언트에게 Cookie로 전달하기</li>
        </ul>
    </li>
    
    <li>7/27 - 전체 게시글 목록 조회 API
        <ul>
            <li>제목, 작성자명(nickname), 작성 날짜를 조회하기</li>
            <li>작성 날짜 기준으로 내림차순 정렬하기</li>
        </ul>
    </li>
    
    <li>7/28 - 게시글 작성 API
        <ul>
            <li>토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능</li>
            <li>제목, 작성 내용을 입력하기</li>
        </ul>
    </li>

    <li>7/31 - 게시글 조회 API
        <ul><li>제목, 작성자명(nickname), 작성 날짜, 작성 내용을 조회하기 (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)</li></ul>
    </li>
    
    <li>8/1 - 게시글 수정 API
        <ul><li>토큰을 검사하여, 해당 사용자가 작성한 게시글만 수정 가능</li></ul>
    </li>
    
    <li> 8/2 - 게시글 삭제 API
        <ul><li>토큰을 검사하여, 해당 사용자가 작성한 게시글만 삭제 가능</li></ul>
    </li>
    
    <li>8/3 - 댓글 작성 API 
        <ul>
            <li>게시글과 연관관계를 가진 댓글 테이블 추가</li>
            <li>토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능</li>
            <li>작성 내용을 입력하기</li>
        </ul>
    </li>
    
    <li>8/4 - 게시글과 댓글조회 API, 댓글 수정/삭제 API
        <ul>
            <li>게시글 조회 API 호출시 해당 게시글의 댓글 목록도 응답</li>
            <li>토큰을 검사하여, 해당 사용자가 작성한 댓글만 수정/삭제 가능</li>
        </ul>
    </li>
</ul>

<br><br>

<h3>설계 01. API 명세서</h3>
<body><article id="0dda8fb3-09d0-42cd-bfb7-de0e990871a4" class="page sans"><header><h1 class="page-title">API 명세</h1><p class="page-description"></p></header><div class="page-body"><table class="collection-content"><thead><tr><th><span class="icon property-icon"><svg role="graphics-symbol" viewBox="0 0 16 16" style="width:14px;height:14px;display:block;fill:rgba(55, 53, 47, 0.45);flex-shrink:0" class="typesTitle"><path d="M0.637695 13.1914C1.0957 13.1914 1.32812 13 1.47852 12.5215L2.24414 10.3887H6.14746L6.90625 12.5215C7.05664 13 7.2959 13.1914 7.74707 13.1914C8.22559 13.1914 8.5332 12.9043 8.5332 12.4531C8.5332 12.2891 8.50586 12.1523 8.44434 11.9678L5.41602 3.79199C5.2041 3.21777 4.82129 2.9375 4.19922 2.9375C3.60449 2.9375 3.21484 3.21777 3.0166 3.78516L-0.0322266 12.002C-0.09375 12.1797 -0.121094 12.3232 -0.121094 12.4668C-0.121094 12.918 0.166016 13.1914 0.637695 13.1914ZM2.63379 9.12402L4.17871 4.68066H4.21973L5.76465 9.12402H2.63379ZM12.2793 13.2324C13.3115 13.2324 14.2891 12.6787 14.7129 11.8037H14.7402V12.5762C14.7471 12.9863 15.0273 13.2393 15.4238 13.2393C15.834 13.2393 16.1143 12.9795 16.1143 12.5215V8.00977C16.1143 6.49902 14.9658 5.52148 13.1543 5.52148C11.7666 5.52148 10.6592 6.08887 10.2695 6.99121C10.1943 7.15527 10.1533 7.3125 10.1533 7.46289C10.1533 7.81152 10.4062 8.04395 10.7686 8.04395C11.0215 8.04395 11.2129 7.94824 11.3496 7.73633C11.7529 6.99121 12.2861 6.65625 13.1064 6.65625C14.0977 6.65625 14.6992 7.20996 14.6992 8.1123V8.67285L12.5664 8.7959C10.7686 8.8916 9.77734 9.69824 9.77734 11.0107C9.77734 12.3369 10.8096 13.2324 12.2793 13.2324ZM12.6621 12.1387C11.8008 12.1387 11.2129 11.667 11.2129 10.9561C11.2129 10.2725 11.7598 9.82129 12.7578 9.75977L14.6992 9.62988V10.3203C14.6992 11.3457 13.7969 12.1387 12.6621 12.1387Z"></path></svg></span>Function</th><th><span class="icon property-icon"><svg role="graphics-symbol" viewBox="0 0 16 16" style="width:14px;height:14px;display:block;fill:rgba(55, 53, 47, 0.45);flex-shrink:0" class="typesMultipleSelect"><path d="M1.91602 4.83789C2.44238 4.83789 2.87305 4.40723 2.87305 3.87402C2.87305 3.34766 2.44238 2.91699 1.91602 2.91699C1.38281 2.91699 0.952148 3.34766 0.952148 3.87402C0.952148 4.40723 1.38281 4.83789 1.91602 4.83789ZM5.1084 4.52344H14.3984C14.7607 4.52344 15.0479 4.23633 15.0479 3.87402C15.0479 3.51172 14.7607 3.22461 14.3984 3.22461H5.1084C4.74609 3.22461 4.45898 3.51172 4.45898 3.87402C4.45898 4.23633 4.74609 4.52344 5.1084 4.52344ZM1.91602 9.03516C2.44238 9.03516 2.87305 8.60449 2.87305 8.07129C2.87305 7.54492 2.44238 7.11426 1.91602 7.11426C1.38281 7.11426 0.952148 7.54492 0.952148 8.07129C0.952148 8.60449 1.38281 9.03516 1.91602 9.03516ZM5.1084 8.7207H14.3984C14.7607 8.7207 15.0479 8.43359 15.0479 8.07129C15.0479 7.70898 14.7607 7.42188 14.3984 7.42188H5.1084C4.74609 7.42188 4.45898 7.70898 4.45898 8.07129C4.45898 8.43359 4.74609 8.7207 5.1084 8.7207ZM1.91602 13.2324C2.44238 13.2324 2.87305 12.8018 2.87305 12.2686C2.87305 11.7422 2.44238 11.3115 1.91602 11.3115C1.38281 11.3115 0.952148 11.7422 0.952148 12.2686C0.952148 12.8018 1.38281 13.2324 1.91602 13.2324ZM5.1084 12.918H14.3984C14.7607 12.918 15.0479 12.6309 15.0479 12.2686C15.0479 11.9062 14.7607 11.6191 14.3984 11.6191H5.1084C4.74609 11.6191 4.45898 11.9062 4.45898 12.2686C4.45898 12.6309 4.74609 12.918 5.1084 12.918Z"></path></svg></span>Method</th><th><span class="icon property-icon"><svg role="graphics-symbol" viewBox="0 0 16 16" style="width:14px;height:14px;display:block;fill:rgba(55, 53, 47, 0.45);flex-shrink:0" class="typesUrl"><path d="M7.69922 10.8945L8.73828 9.84863C7.91797 9.77344 7.34375 9.51367 6.91992 9.08984C5.76465 7.93457 5.76465 6.29395 6.91309 5.14551L9.18262 2.87598C10.3379 1.7207 11.9717 1.7207 13.127 2.87598C14.2891 4.04492 14.2822 5.67188 13.1338 6.82031L11.958 7.99609C12.1768 8.49512 12.2451 9.10352 12.1289 9.62988L14.0908 7.6748C15.7725 6 15.7793 3.62109 14.084 1.92578C12.3887 0.223633 10.0098 0.237305 8.33496 1.91211L5.95605 4.29785C4.28125 5.97266 4.26758 8.35156 5.96289 10.0469C6.36621 10.4434 6.90625 10.7441 7.69922 10.8945ZM8.30078 5.13184L7.26855 6.17773C8.08203 6.25293 8.66309 6.51953 9.08008 6.93652C10.2422 8.09863 10.2422 9.73242 9.08691 10.8809L6.81738 13.1504C5.66211 14.3057 4.03516 14.3057 2.87305 13.1504C1.71094 11.9883 1.71777 10.3545 2.87305 9.20605L4.04199 8.03027C3.83008 7.53125 3.75488 6.92969 3.87109 6.39648L1.91602 8.35156C0.234375 10.0264 0.227539 12.4121 1.92285 14.1074C3.61816 15.8027 5.99707 15.7891 7.67188 14.1143L10.0439 11.7354C11.7256 10.0537 11.7324 7.6748 10.0371 5.98633C9.64062 5.58301 9.10059 5.28223 8.30078 5.13184Z"></path></svg></span>URL</th><th><span class="icon property-icon"><svg role="graphics-symbol" viewBox="0 0 16 16" style="width:14px;height:14px;display:block;fill:rgba(55, 53, 47, 0.45);flex-shrink:0" class="typesText"><path d="M1.56738 3.25879H14.4258C14.7676 3.25879 15.0479 2.97852 15.0479 2.63672C15.0479 2.29492 14.7744 2.02148 14.4258 2.02148H1.56738C1.21875 2.02148 0.952148 2.29492 0.952148 2.63672C0.952148 2.97852 1.22559 3.25879 1.56738 3.25879ZM1.56738 6.84082H14.4258C14.7676 6.84082 15.0479 6.56055 15.0479 6.21875C15.0479 5.87695 14.7744 5.60352 14.4258 5.60352H1.56738C1.21875 5.60352 0.952148 5.87695 0.952148 6.21875C0.952148 6.56055 1.22559 6.84082 1.56738 6.84082ZM1.56738 10.4229H14.4258C14.7676 10.4229 15.0479 10.1426 15.0479 9.80078C15.0479 9.45898 14.7744 9.18555 14.4258 9.18555H1.56738C1.21875 9.18555 0.952148 9.45898 0.952148 9.80078C0.952148 10.1426 1.22559 10.4229 1.56738 10.4229ZM1.56738 14.0049H8.75879C9.10059 14.0049 9.38086 13.7246 9.38086 13.3828C9.38086 13.041 9.10742 12.7676 8.75879 12.7676H1.56738C1.21875 12.7676 0.952148 13.041 0.952148 13.3828C0.952148 13.7246 1.22559 14.0049 1.56738 14.0049Z"></path></svg></span>Request</th><th><span class="icon property-icon"><svg role="graphics-symbol" viewBox="0 0 16 16" style="width:14px;height:14px;display:block;fill:rgba(55, 53, 47, 0.45);flex-shrink:0" class="typesText"><path d="M1.56738 3.25879H14.4258C14.7676 3.25879 15.0479 2.97852 15.0479 2.63672C15.0479 2.29492 14.7744 2.02148 14.4258 2.02148H1.56738C1.21875 2.02148 0.952148 2.29492 0.952148 2.63672C0.952148 2.97852 1.22559 3.25879 1.56738 3.25879ZM1.56738 6.84082H14.4258C14.7676 6.84082 15.0479 6.56055 15.0479 6.21875C15.0479 5.87695 14.7744 5.60352 14.4258 5.60352H1.56738C1.21875 5.60352 0.952148 5.87695 0.952148 6.21875C0.952148 6.56055 1.22559 6.84082 1.56738 6.84082ZM1.56738 10.4229H14.4258C14.7676 10.4229 15.0479 10.1426 15.0479 9.80078C15.0479 9.45898 14.7744 9.18555 14.4258 9.18555H1.56738C1.21875 9.18555 0.952148 9.45898 0.952148 9.80078C0.952148 10.1426 1.22559 10.4229 1.56738 10.4229ZM1.56738 14.0049H8.75879C9.10059 14.0049 9.38086 13.7246 9.38086 13.3828C9.38086 13.041 9.10742 12.7676 8.75879 12.7676H1.56738C1.21875 12.7676 0.952148 13.041 0.952148 13.3828C0.952148 13.7246 1.22559 14.0049 1.56738 14.0049Z"></path></svg></span>Response</th><th><span class="icon property-icon"><svg role="graphics-symbol" viewBox="0 0 16 16" style="width:14px;height:14px;display:block;fill:rgba(55, 53, 47, 0.45);flex-shrink:0" class="typesText"><path d="M1.56738 3.25879H14.4258C14.7676 3.25879 15.0479 2.97852 15.0479 2.63672C15.0479 2.29492 14.7744 2.02148 14.4258 2.02148H1.56738C1.21875 2.02148 0.952148 2.29492 0.952148 2.63672C0.952148 2.97852 1.22559 3.25879 1.56738 3.25879ZM1.56738 6.84082H14.4258C14.7676 6.84082 15.0479 6.56055 15.0479 6.21875C15.0479 5.87695 14.7744 5.60352 14.4258 5.60352H1.56738C1.21875 5.60352 0.952148 5.87695 0.952148 6.21875C0.952148 6.56055 1.22559 6.84082 1.56738 6.84082ZM1.56738 10.4229H14.4258C14.7676 10.4229 15.0479 10.1426 15.0479 9.80078C15.0479 9.45898 14.7744 9.18555 14.4258 9.18555H1.56738C1.21875 9.18555 0.952148 9.45898 0.952148 9.80078C0.952148 10.1426 1.22559 10.4229 1.56738 10.4229ZM1.56738 14.0049H8.75879C9.10059 14.0049 9.38086 13.7246 9.38086 13.3828C9.38086 13.041 9.10742 12.7676 8.75879 12.7676H1.56738C1.21875 12.7676 0.952148 13.041 0.952148 13.3828C0.952148 13.7246 1.22559 14.0049 1.56738 14.0049Z"></path></svg></span>Request Header</th><th><span class="icon property-icon"><svg role="graphics-symbol" viewBox="0 0 16 16" style="width:14px;height:14px;display:block;fill:rgba(55, 53, 47, 0.45);flex-shrink:0" class="typesText"><path d="M1.56738 3.25879H14.4258C14.7676 3.25879 15.0479 2.97852 15.0479 2.63672C15.0479 2.29492 14.7744 2.02148 14.4258 2.02148H1.56738C1.21875 2.02148 0.952148 2.29492 0.952148 2.63672C0.952148 2.97852 1.22559 3.25879 1.56738 3.25879ZM1.56738 6.84082H14.4258C14.7676 6.84082 15.0479 6.56055 15.0479 6.21875C15.0479 5.87695 14.7744 5.60352 14.4258 5.60352H1.56738C1.21875 5.60352 0.952148 5.87695 0.952148 6.21875C0.952148 6.56055 1.22559 6.84082 1.56738 6.84082ZM1.56738 10.4229H14.4258C14.7676 10.4229 15.0479 10.1426 15.0479 9.80078C15.0479 9.45898 14.7744 9.18555 14.4258 9.18555H1.56738C1.21875 9.18555 0.952148 9.45898 0.952148 9.80078C0.952148 10.1426 1.22559 10.4229 1.56738 10.4229ZM1.56738 14.0049H8.75879C9.10059 14.0049 9.38086 13.7246 9.38086 13.3828C9.38086 13.041 9.10742 12.7676 8.75879 12.7676H1.56738C1.21875 12.7676 0.952148 13.041 0.952148 13.3828C0.952148 13.7246 1.22559 14.0049 1.56738 14.0049Z"></path></svg></span>Response Header</th></tr></thead><tbody><tr id="70471f4e-8964-4e92-b6de-ab5dfa446fd7"><td class="cell-title"><a href="https://www.notion.so/70471f4e89644e92b6deab5dfa446fd7?pvs=21">로그인</a></td><td class="cell-kiOw"><span class="selected-value select-value-color-blue">POST</span></td><td class="cell-BNF_"><a href="/api/users/login" class="url-value">/api/users/login</a></td><td class="cell-=LX}">{”username”:”eojin111”, “password”:“abcd1234”}</td><td class="cell-vr}k"></td><td class="cell-yBaz"></td><td class="cell-[BC~"></td></tr><tr id="049e4f7b-9e76-40a1-a59e-0ea8b2bee04e"><td class="cell-title"><a href="https://www.notion.so/049e4f7b9e7640a1a59e0ea8b2bee04e?pvs=21">회원가입</a></td><td class="cell-kiOw"><span class="selected-value select-value-color-blue">POST</span></td><td class="cell-BNF_"><a href="/api/users/signup" class="url-value">/api/users/signup</a></td><td class="cell-=LX}">{”username”:”eojin111”, “nickname”: “어진”, “password”:“abcd1234”, “pwConfirm”:”pwConfirm”, “role”:”USER”}</td><td class="cell-vr}k">{
”message”:”회원 가입 성공”,
”status_code”:200
}</td><td class="cell-yBaz"></td><td class="cell-[BC~"></td></tr><tr id="f6dcc85d-9417-4322-92aa-4a883714f955"><td class="cell-title"><a href="https://www.notion.so/f6dcc85d9417432292aa4a883714f955?pvs=21">게시글 작성</a></td><td class="cell-kiOw"><span class="selected-value select-value-color-blue">POST</span></td><td class="cell-BNF_"><a href="/api/posts" class="url-value">/api/posts</a></td><td class="cell-=LX}">{
”title”:”title”,
”content”:”content”,
”author”:”author”,
”password”:”password”
}</td><td class="cell-vr}k">{
{
&quot;createdAt&quot;: &quot;2022-07-25T12:43:01.226062”,
&quot;modifiedAt&quot;: &quot;2022-07-25T12:43:01.226062”
,&quot;id&quot;: 1
,&quot;title&quot;: &quot;title1&quot;
,&quot;content&quot;: &quot;content1&quot;
,&quot;author&quot;: &quot;author1&quot;
}
}</td><td class="cell-yBaz"></td><td class="cell-[BC~"></td></tr><tr id="8b0f0f8e-6804-425e-ac15-3a7522bdb6fe"><td class="cell-title"><a href="https://www.notion.so/8b0f0f8e6804425eac153a7522bdb6fe?pvs=21">전체 게시글 목록 조회</a></td><td class="cell-kiOw"><span class="selected-value select-value-color-green">GET</span></td><td class="cell-BNF_"><a href="/api/posts" class="url-value">/api/posts</a></td><td class="cell-=LX}">-</td><td class="cell-vr}k">{
{
&quot;createdAt&quot;: &quot;2022-07-25T12:43:01.226062”,
&quot;modifiedAt&quot;: &quot;2022-07-25T12:43:01.226062”
,&quot;id&quot;: 1
,&quot;title&quot;: &quot;title1&quot;
,&quot;content&quot;: &quot;content1&quot;
,&quot;author&quot;: &quot;author1&quot;
},

{
&quot;createdAt&quot;: &quot;2022-07-25T12:43:01.226062”
,&quot;modifiedAt&quot;: &quot;2022-07-25T12:43:01.226062”
,&quot;id&quot;: 2
,&quot;title&quot;: &quot;title2&quot;
,&quot;content&quot;: &quot;content2&quot;
,&quot;author&quot;: &quot;author2&quot;
}
, 
…
}</td><td class="cell-yBaz"></td><td class="cell-[BC~"></td></tr><tr id="8994f52f-3ed0-4a06-8bc8-e81ae4b240ca"><td class="cell-title"><a href="https://www.notion.so/8994f52f3ed04a068bc8e81ae4b240ca?pvs=21">선택한 게시글 조회</a></td><td class="cell-kiOw"><span class="selected-value select-value-color-green">GET</span></td><td class="cell-BNF_"><a href="/api/posts/{id}" class="url-value">/api/posts/{id}</a></td><td class="cell-=LX}">{
&quot;title&quot;: &quot;title&quot;
,&quot;content&quot;: &quot;content&quot;
,&quot;author&quot;: &quot;author&quot;
}</td><td class="cell-vr}k">{
&quot;createdAt&quot;: &quot;2022-07-25T12:43:01.226062”,
&quot;modifiedAt&quot;: &quot;2022-07-25T12:43:01.226062”
,&quot;id&quot;: 1
,&quot;title&quot;: &quot;title&quot;
,&quot;content&quot;: &quot;content&quot;
,&quot;author&quot;: &quot;author&quot;
}</td><td class="cell-yBaz"></td><td class="cell-[BC~"></td></tr><tr id="951f1dc0-466c-4317-b61c-970b0ded3443"><td class="cell-title"><a href="https://www.notion.so/951f1dc0466c4317b61c970b0ded3443?pvs=21">선택한 게시글 수정</a></td><td class="cell-kiOw"><span class="selected-value select-value-color-orange">PUT</span></td><td class="cell-BNF_"><a href="/api/posts/{id}" class="url-value">/api/posts/{id}</a></td><td class="cell-=LX}"></td><td class="cell-vr}k">{
{
&quot;createdAt&quot;: &quot;2022-07-25T12:43:01.226062”,
&quot;modifiedAt&quot;: &quot;2022-07-25T12:43:01.226062”
,&quot;id&quot;: 1
,&quot;title&quot;: &quot;title2&quot;
,&quot;content&quot;: &quot;content2&quot;
,&quot;author&quot;: &quot;author2&quot;
}
}</td><td class="cell-yBaz"></td><td class="cell-[BC~"></td></tr><tr id="0386cf32-c2a5-472d-b64d-803dc363e5f6"><td class="cell-title"><a href="https://www.notion.so/0386cf32c2a5472db64d803dc363e5f6?pvs=21">선택한 게시글 삭제</a></td><td class="cell-kiOw"><span class="selected-value select-value-color-gray">DELETE</span></td><td class="cell-BNF_"><a href="/api/posts/{id}" class="url-value">/api/posts/{id}</a></td><td class="cell-=LX}">{
”password”:”password”
}</td><td class="cell-vr}k">{
”message”:”회원 정보 삭제 성공”,
}</td><td class="cell-yBaz"></td><td class="cell-[BC~"></td></tr><tr id="ac09f2d0-53e8-493a-8225-e39c2d990321"><td class="cell-title"><a href="https://www.notion.so/ac09f2d053e8493a8225e39c2d990321?pvs=21">댓글 작성</a></td><td class="cell-kiOw"><span class="selected-value select-value-color-blue">POST</span></td><td class="cell-BNF_"><a href="/api/comments" class="url-value">/api/comments</a></td><td class="cell-=LX}"></td><td class="cell-vr}k"></td><td class="cell-yBaz"></td><td class="cell-[BC~"></td></tr><tr id="c9a91a1e-a8da-43c7-9b49-f203a332b804"><td class="cell-title"><a href="https://www.notion.so/c9a91a1ea8da43c79b49f203a332b804?pvs=21">댓글 삭제</a></td><td class="cell-kiOw"><span class="selected-value select-value-color-gray">DELETE</span></td><td class="cell-BNF_"><a href="/api/comments/{id}" class="url-value">/api/comments/{id}</a></td><td class="cell-=LX}"></td><td class="cell-vr}k"></td><td class="cell-yBaz"></td><td class="cell-[BC~"></td></tr><tr id="1af103fc-7f2b-4952-a62c-237df6ee8ed3"><td class="cell-title"><a href="https://www.notion.so/1af103fc7f2b4952a62c237df6ee8ed3?pvs=21">댓글 수정</a></td><td class="cell-kiOw"><span class="selected-value select-value-color-orange">PUT</span></td><td class="cell-BNF_"><a href="/api/comments/{id}" class="url-value">/api/comments/{id}</a></td><td class="cell-=LX}"></td><td class="cell-vr}k"></td><td class="cell-yBaz"></td><td class="cell-[BC~"></td></tr></tbody></table><br/><br/></div></article></body></html>

<br><br>

<h3>설계 02. ERD</h3>
![pracSpring_Blog](https://github.com/song-eojin/pracSpring_Blog/assets/122079064/eeff0c93-bcc5-49f1-ae9c-ce012df51e05)

<br><br>



