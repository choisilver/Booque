/**
 * 
 * 
 */
 
 // 아이디 중복 체크
 
 window.addEventListener('DOMContentLoaded', function() {
    
    
    const usernameInput = document.querySelector('#signupUsername');
    const okDiv = document.querySelector('#ok');
    const nokDiv = document.querySelector('#nok');
    const btnSubmit = document.querySelector('#btnSubmit');
    
    usernameInput.addEventListener('change', function() {
        
        const username = usernameInput.value;
        axios
        .get('/member/checkid?username=' + username) 
        .then(response => { displayCheckResult(response.data) }) 
        .catch(err => { console.log(err); }); 
    });
        
    
    
    function displayCheckResult(data) {
        if (data == 'ok') { 
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