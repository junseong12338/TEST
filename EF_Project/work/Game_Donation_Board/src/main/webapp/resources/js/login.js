

const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

signUpButton.addEventListener('click', () => {
  container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
  container.classList.remove("right-panel-active");
});

// 아이디 중복 여부 변수
let email_check = false;

// 이름 유효성 검사 함수
function checkName(f) {
    let user_name = f.user_name.value.trim();
    // 이름이 공백인 경우
    if (user_name === '') {
        let span = document.getElementById("check_name");
        span.innerHTML = "이름을 입력해주세요.";
        email_check = false; // 이메일 유효성 검사 결과를 false로 설정
        return; // 함수 종료
    } else {
        let span = document.getElementById("check_name");
        span.innerHTML = "";
        email_check = true;
    }
}

// 비밀번호 유효성 검사 함수
function checkPw(f) {
    let user_pw = f.user_pw.value.trim();
    // 비밀번호가 공백인 경우
    if (user_pw === '') {
        let span = document.getElementById("check_pw");
        span.innerHTML = "비밀번호를 입력해주세요.<br><br>";
        email_check = false; // 이메일 유효성 검사 결과를 false로 설정
        return; // 함수 종료
    } else {
        let span = document.getElementById("check_pw");
        span.innerHTML = "";
        email_check = true;
    }
}

// 이메일 유효성 검사 함수
function checkEmail(f) {
    let user_email = f.user_email.value.trim();
    // 이메일이 공백인 경우
    if (user_email.trim() === '') {
        let span = document.getElementById("check_email");
        span.style.color = "red";
        
        span.innerHTML = "이메일을 입력해주세요.";
        email_check = false; // 이메일 유효성 검사 결과를 false로 설정
        return; // 함수 종료
    }
    // 이메일 유효성 검사
    let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    let isValidEmail = emailPattern.test(user_email);
    // 이메일 형식이 올바르지 않은 경우
    if (!isValidEmail) {
        let span = document.getElementById("check_email");
        span.style.color = "red";
        span.innerHTML = "이메일 형식이 올바르지 않습니다.";
        email_check = false; // 아이디 중복 여부 초기화
        return; // 함수 종료
    }
    // 이메일 유효성 검사 통과한 경우 아이디 중복 검사 진행
    let url = "check_email"; // 이메일 중복 확인을 위한 서버 경로
    let param = "user_email=" + encodeURIComponent(user_email);
    sendRequest(url, param, resultFn, "POST");
}

// 이메일 중복 확인 결과 처리 콜백 함수
function resultFn(f) {
    if (xhr.readyState == 4 && xhr.status == 200) {
        let data = xhr.responseText;
        let json = JSON.parse(data);
        if (json[0].res == 'no') {
      
            let span = document.getElementById("check_email");
            span.style.color = "red";
            span.innerHTML = "이미 있는 아이디 입니다.";
            email_check = false;
            
        } else if (json[0].res == 'yes') {
            let span = document.getElementById("check_email");
            span.style.color = "green";
            span.innerHTML = "사용가능한 아이디 입니다.";
            email_check = true;
            
        }
    }
}

// 회원가입 데이터 전송 함수
function send(f) {
    let user_name = f.user_name.value.trim();
    let user_email = f.user_email.value.trim();
    let user_pw = f.user_pw.value.trim();

    // 이메일 유효성 및 아이디 중복 검사 수행
    checkEmail(f);
    checkName(f);
    checkPw(f);

    // 아이디 중복 여부에 따라 폼 제출 여부 결정
    if (email_check) {
        // 폼 데이터 설정
        f.user_name.value = user_name;
        f.user_email.value = user_email;
        f.user_pw.value = user_pw;

        // 폼 제출
        f.action = "user_insert";
        f.method = "POST";
        f.submit();
    }
}

// 로그인 아이디 공백
function LoginCheckEmail(f){
	let user_email = f.user_email.value.trim();
	if (user_email.trim() === '') {
		let span = document.getElementById("checklog_email");
		span.innerHTML = "이메일을 입력해주세요.";
		return; // 함수 종료
    }
    else {
        let span = document.getElementById("checklog_email");
        span.innerHTML = "";
    }
 	
 	
}

// 로그인 비번 공백
function LoginCheckPw(f){
    let user_pw = f.user_pw.value.trim();
    if (user_pw == '') {
        let span = document.getElementById("checklog_pw");
 		span.innerHTML = "비밀번호를 입력해주세요";
    	return; // 함수 종료
    }
    else {
        let span = document.getElementById("checklog_pw");
        span.innerHTML = "";
    }
}

// 로그인 함수
function login(f) {
    let user_email = f.user_email.value.trim();
    let user_pw = f.user_pw.value.trim();
    
    LoginCheckEmail(f);
    LoginCheckPw(f);

    let url = "login";
    let param = "user_email=" + encodeURIComponent(user_email) + "&user_pw=" + encodeURIComponent(user_pw);
    sendRequest(url, param, myCheck, "POST");
}



// 로그인 결과 확인 함수
function myCheck() {
    if (xhr.readyState == 4 && xhr.status == 200) {
        let data = xhr.responseText;
        let json = JSON.parse(data);
        if (json[0].param == 'no_email') {
        let span = document.getElementById("checklog_email");
        	  span.innerHTML = "아이디가 존재하지 않습니다.";
        }
        else if (json[0].param == 'no_pw'){
           let span = document.getElementById("checklog_pw");
        	  span.innerHTML = "비밀번호가 틀렸습니다.";

        } 
        else location.href = "board_list"; // 화면 전환
    }
}
