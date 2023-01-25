/**
 * 
 */
 
 // 홈(메인) 화면 컨텐츠에 쓰일 자바스크립트
 
 
 
// 카테고리별 BEST 탭 효과
function openTap(e, tapContent) {
    const content = document.querySelectorAll('.tapContent');  // 모든 탭 내용 찾기
    const button = document.querySelectorAll('.btnTap');  // 모든 탭 버튼 찾기  
    
    /*
    content.forEach(c => {
        c.style.display = "none";
    });
    */
    
    let i;
    for (i = 0; i < content.length; i++) {
        content[i].style.display = "none";    // 탭 내용들 숨기기
        button[i].className = button[i].className.replace(" w3-black", "");  // 탭 버튼들 강조효과 지우기
    }
      
      document.getElementById(tapContent).style.display = "block";
      e.currentTarget.className += " w3-black";
    }

    const btnDefault = document.querySelectorAll('.btnTap')[0];  // 첫번째 탭 열어 놓기
    btnDefault.click();
    
    
    