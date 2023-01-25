/**
 * 
 */
 
 // 정혁 
 const modalConfirm = (tar) => {
    let signupUsername = document.getElementById('signupUsername');
    let signupPassword = document.getElementById('signupPassword');
    let signupEmail = document.getElementById('signupEmail');
    let signupNickname = document.getElementById('signupNickname');
    let signupName = document.getElementById('signupName');
    let signupPhone = document.getElementById('signupPhone')
    let signinUsername = document.getElementById('signinUsername');
    let signinPassword = document.getElementById('signinPassword');
    
    console.log('modalConfirm');
    console.log(`data-type : ${tar.getAttribute('data-bs-toggle')}`);

    
    if(signupUsername.value === '' || signupUsername.value === ' ') return signupUsername.focus();
    if(signupPassword.value === '' || signupPassword.value === ' ') return signupPassword.focus();
    if(signupEmail.value === '' || signupEmail.value === ' ') return signupEmail.focus();
    if(signupNickname.value === '' || signupNickname.value === ' ') return signupNickname.focus();
    if(signupName.value === '' || signupName.value === ' ') return signupName.focus();
    if(signupPhone.value === '' || signupPhone.value === ' ') return signupPhone.focus();
    


    document.getElementById('username').value = signupUsername.value;
    document.getElementById('password').value = signupPassword.value;
    document.getElementById('email').value = signupEmail.value;
    document.getElementById('nickname').value = signupNickname.value;
    document.getElementById('name').value = signupName.value;
    document.getElementById('phone').value = signupPhone.value;

    
    
    return false;
    }

    //회원가입정보가 전부 들어갔는지 각각 체크하는 함수
    const checkValue = (tar) =>{
        if(signupUsername.value != '' && signupPassword.value != '' && signupEmail.value != '' &&
              signupNickname.value != '' && signupName.value != '' && signupPhone.value != '' ){ //모든 값이 반 값이 아닐때
            document.getElementById('registerConfirmBtn').setAttribute('data-bs-toggle','modal');
    
        }else{ //빈 값이 하나라도 있을 때
            document.getElementById('registerConfirmBtn').setAttribute('data-bs-toggle','');
  
        }
        return false;
    }

    const sendForm = (type) => {

        if(type === 'register'){
            const registerForm = document.getElementById('registerForm');
            registerForm.submit();
        }else if(type === 'login'){

            if(document.getElementById('signinUsername').value === '' || document.getElementById('signinUsername').value === ' ') return document.getElementById('signinUsername').focus();
            if(document.getElementById('signinPassword').value === '' || document.getElementById('signinPassword').value === ' ') return document.getElementById('signinPassword').focus();

            const loginForm = document.getElementById('loginForm');
            loginForm.submit();
        }

        
        
        return false;
       
    }
    window.addEventListener('DOMContentLoaded', function() {
       
       const usernameInput = document.querySelector('#signupUsername');
       const emailInput = document.querySelector('#signupEmail');
       const nicknameInput = document.querySelector('#signupNickname');
       
       const nameokDiv = document.querySelector('#nameok');
       const emailokDiv = document.querySelector('#emailok');
       const nickokDiv = document.querySelector('#nickok');
       const namenokIdv = document.querySelector('#namenok');
       const emailnokDiv = document.querySelector('#emailnok');
       const nicknokDiv = document.querySelector('#nicknok');
       
       const btnSubmit = document.querySelector('.btnSubmit');
       
        let nameok = false;
        let emailok = false;
        let nickok = false;
    
       usernameInput.addEventListener('change', function() {
            
            const username = usernameInput.value;
            axios
            .get('/user/checkid?username=' + username) // GET Ajax 요청 보냄.
            .then(response => { displayNameCheckResult(response.data) }) // 성공(HTTP 200) 응답 콜백
            .catch(err => { console.log(err); }); // 실패 응답 콜백
            
            
        });
       
       function displayNameCheckResult(data) {
            if (data == 'nameok') { // 사용할 수 있는 아이디
                nameokDiv.className = 'my-2';
                namenokDiv.className = 'my-2 d-none';
                nameok = true;
            } else {
                nameokDiv.className = 'my-2 d-none';
                namenokDiv.className = 'my-2';
            }
        }
   
       
       nicknameInput.addEventListener('change', function() {
          
          const nickname = nicknameInput.value;
            axios
            .get('/user/checknick?nickname=' + nickname) // GET Ajax 요청 보냄.
            .then(response => { displayNickCheckResult(response.data) }) // 성공(HTTP 200) 응답 콜백
            .catch(err => { console.log(err); }); // 실패 응답 콜백
          
           
            
       });
       
        function displayNickCheckResult(data) {
             if (data == 'nickok') { // 사용할 수 있는 아이디
                 nickokDiv.className = 'my-2';
                 nicknokDiv.className = 'my-2 d-none';
                 nickok = true;
             } else {
                 nickokDiv.className = 'my-2 d-none';
                 nicknokDiv.className = 'my-2';
             }
         }
       
       emailInput.addEventListener('change', function () {
          
          const email = emailInput.value;
            axios
            .get('/user/checkemail?email=' + email) // GET Ajax 요청 보냄.
            .then(response => { displayEmailCheckResult(response.data) }) // 성공(HTTP 200) 응답 콜백
            .catch(err => { console.log(err); }); // 실패 응답 콜백
            
            
       });
       
       function displayEmailCheckResult(data) {
            if (data == 'emailok') { // 사용할 수 있는 아이디
                emailokDiv.className = 'my-2';
                emailnokDiv.className = 'my-2 d-none';
                emailok = true;
            } else {
                emailokDiv.className = 'my-2 d-none';
                emailnokDiv.className = 'my-2';
            }
        }


       
    });
    
    function displayBtnSubmit(data) {
        if (nameok == nickok == emailok == true) {
            btnSubmit.className.remove('disabled');
        } else {
            return;
        }
    }
    
    function alertNewUser() {
       alert("BOOQUE의 가족이 되신걸 환영합니다!")
    };
       
       
       
       
       
       
       
       
 
 
 // (지혜) 사이드바 열고 닫기
function w3_open() {
  document.getElementById("sideBar").style.display = "block";
  document.getElementById("overlay").style.display = "block";
}
 
function w3_close() {
  document.getElementById("sideBar").style.display = "none";
  document.getElementById("overlay").style.display = "none";
}

 
 
 // (지혜) 사이드바 Accordion 메뉴
 
function open_group0() {
    let x = document.querySelector('#group0');
    let y = document.querySelector('#group1');
    let z = document.querySelector('#group2');
    
    if (x.className.indexOf("w3-show") == -1) {   // 해당 메뉴가 닫혀 있으면(class에 w3-show 속성이 없으면?)
        x.className += " w3-show";   // w3-show 속성을 추가해 열어 주기
        y.className = y.className.replace(" w3-show", "");   // 다른 메뉴는 닫기
        z.className = z.className.replace(" w3-show", "");   // 다른 메뉴는 닫기
    } else {
        x.className = x.className.replace(" w3-show", "");   // 열려 있다면 닫아 주기(w3-show 속성 지우기)
    }
}
 
function open_group1() {
  let x = document.querySelector('#group1');
  let y = document.querySelector('#group0');
  let z = document.querySelector('#group2');
    
  if (x.className.indexOf("w3-show") == -1) {
    x.className += " w3-show";
    y.className = y.className.replace(" w3-show", "");
    z.className = z.className.replace(" w3-show", "");
  } else {
    x.className = x.className.replace(" w3-show", "");
  }
}

function open_group2() {
  let x = document.querySelector('#group2');
  let y = document.querySelector('#group0');
  let z = document.querySelector('#group1');
  
  if (x.className.indexOf("w3-show") == -1) {
    x.className += " w3-show";
    y.className = y.className.replace(" w3-show", "");
    z.className = z.className.replace(" w3-show", "");
  } else {
    x.className = x.className.replace(" w3-show", "");
  }
}


// (지혜) 사이드바 Accordian 하위 메뉴 선택시 효과

window.addEventListener('DOMContentLoaded', function () {
    const buttons = document.querySelectorAll('.btnCategory');   // 모든 하위카테 버튼 찾기
    const cateNows = document.querySelectorAll('.cateNow');   // 모든 하위카테 앞의 ▶ 표시 찾기
    
    buttons.forEach(btn => {
        btn.addEventListener('click', e => {
            
            buttons.forEach(others => {
                others.className = others.className.replace(" w3-light-grey", "");  // 선택한 하위카테 외의 다른 카테는 모두 회색배경 지우기
            });
            
            cateNows.forEach(others2 => {
               others2.style.display = "none";  // 선택한 하위카테 외의 다른 카테는 모두 ▶ 표시 지우기 
            });
            
            const div = btn.closest('div');
            const a = div.querySelector('a');
            const i = div.querySelector('i');
            a.className += " w3-light-grey";    // 선택한 하위카테에만 회색배경 넣기
            i.style.display = "inline";  // 선택한 하위카테 앞에만 ▶ 표시 붙이기
        });
    });
});



// (지혜) 책 카테고리별 페이지 이동시 사이드바에 표시 효과(현재 보고 있는 카테고리 강조)


window.addEventListener('DOMContentLoaded', function () {
    
    const pathname = window.location.pathname;   // 주소창 '?' 앞의 pathname 찾기 (/category)
    const url = window.location.search;  // 주소창 '/category' 뒤에 오는 텍스트 찾기(예: ?group=외국도서&category=경제)
    const buttons = document.querySelectorAll('.btnCategory');   // 모든 하위카테 버튼 찾기
    const cateNows = document.querySelectorAll('.cateNow');   // 모든 하위카테 앞의 ▶ 표시 찾기
    const btnGroup = document.querySelectorAll('.btnGroup');  // 모든 아코디언메뉴 조상 찾기(분야별도서/국내도서/외국도서)
    
    buttons.forEach(others => {
        others.className = others.className.replace(" w3-light-grey", "");   // 다른 버튼들 배경색 제거
    });
   
    cateNows.forEach(others2 => {
        others2.style.display = "none";  // 다른 버튼들 앞의 ▶ 표시 제거
    });
    
    buttons.forEach(btn => {
       const div = btn.closest('div');
       const groupdiv = div.closest('.btnGroup');
       const a = div.querySelector('a');
       const i = div.querySelector('i');
       const href = a.getAttribute('href');  // 선택된 버튼의 href값 찾기
       
       if ((pathname+url) == href) {      // 주소창의 주소와 동일한 href 경로명을 가진 버튼 찾아서(=현재 선택된 버튼 찾아서)
       
       btnGroup.forEach(others3 => {
       others3.className = others3.className.replace(" w3-show", "");   // 다른 열려 있는 아코디언메뉴들 접기
        });    // 여기에 넣은 이유는, 카테가 선택되지 않는 이상 '분야별도서' 아코디언메뉴는 늘 열어놓을 것이기 때문
       
         a.className += " w3-light-grey";  // 배경색 강조
         i.style.display = "inline";  // 버튼 앞에 ▶ 붙이기
         groupdiv.className += " w3-show";  // 현재 선택된 하위카테의 아코디언 조상(분야별도서/국내도서/외국도서) 펼치기
         document.getElementById("sideBar").style.display = "block";   // 사이드바 열어 두기(연속적으로 다른 카테 들어가기 편하도록)
         document.getElementById("overlay").style.display = "block";
        };
    });
    
    
    /*
    $('div').find('.btnCategory').each(function() {
        const this = document.querySelector($(this)).getAttribute('href');
        alert();
        
        buttons.forEach(others => {
           others.className = others.className.replace(" w3-light-grey", ""); 
           
           
        });
        
        $(this).className += " w3-light-grey";
        console.log(this.className);
    });*/
    
});



// (지혜) 검색창 내려오기/닫기
function search_open() {
  document.getElementById("searchBar").style.display = "block";
  document.getElementById("overlay2").style.display = "block";
}
 
function search_close() {
  document.getElementById("searchBar").style.display = "none";
  document.getElementById("overlay2").style.display = "none";
}





