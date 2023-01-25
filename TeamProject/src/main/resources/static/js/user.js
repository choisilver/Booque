/**
 * member.js
 * /member/signup.html에서 이용하는 파일
 */
 
 window.addEventListener('DOMContentLoaded', function() {
    
    // 아이디 중복 체크
    const usernameInput = document.querySelector('#signupUsername');
    const okDiv = document.querySelector('#ok');
    const nokDiv = document.querySelector('#nok');
    const btnSubmit = document.querySelector('#btnSubmit');
    
    usernameInput.addEventListener('change', function() {
        
        const username = usernameInput.value;
        axios
        .get('/user/checkid?username=' + username) // GET Ajax 요청 보냄.
        .then(response => { displayCheckResult(response.data) }) // 성공(HTTP 200) 응답 콜백
        .catch(err => { console.log(err); }); // 실패 응답 콜백
    });
        
    
    
    function displayCheckResult(data) {
        if (data == 'ok') { // 사용할 수 있는 아이디
            okDiv.className = 'my-2';
            nokDiv.className = 'my-2 d-none';
            btnSubmit.classList.remove('disabled');
        } else {
            okDiv.className = 'my-2 d-none';
            nokDiv.className = 'my-2';
            btnSubmit.classList.add('disabled')
        }
    }
    
});