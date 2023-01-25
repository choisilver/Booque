/**
 * (지혜) 위시리스트 버튼 클릭시 담기/취소
 */
 
 window.addEventListener('DOMContentLoaded', () => {
 
 // 하트(위시리스트) 버튼 찾기, 이벤트 리스너 등록
 const btnWish = document.querySelector('#btnWish');
    btnWish.addEventListener('click', clickBtnWish);
    
    function clickBtnWish () {
        
        // 찜(위시리스트) 누른 책 id 찾기
        const bookId = document.querySelector('#id').value;
        // 하트 버튼 옆의 텍스트 찾기
        const wishText = document.querySelector('#wishText');
        
        console.log("clickBtnWish: bookId=" + bookId);
        
        //if (userId == undefined) {
            //alret("로그인 후 가능한 서비스입니다.");
            // 로그인 모달 열기?
            //return false;
       // }
        
        $.ajax ( {
            url: "/book/wishList",
            type: "Get",
            data: { 
                bookId:bookId
                },
            dataType: "text",
            
            success: function(wishSelectedOrNot) {
                let wishHtml = '';
                let wishText2 = '';
                
                if (wishSelectedOrNot == "selected") {
                    wishHtml += 
                '<img id="wishFull" style="width:25px; display:inline;" alt="취소" src=" /images/likeFull.png " />';
                    wishText2 += '위시리스트에 담은 책입니다.';
                
                } else {
                    wishHtml +=
                '<img id="wishEmpty" style="width:25px;" alt="추가" src=" /images/likeEmpty.png " />'
                + '<img id="wishFull" style="width:25px;" alt="취소" src=" /images/likeFull.png " />';
                    wishText2 += '위시리스트 담기';
                
                }
                
                btnWish.innerHTML = wishHtml;
                wishText.innerHTML = wishText2;
                
            },
            
            error: function() {
                alert("실패: 로그인 후 이용가능합니다!" );
            }
            
        })
        
        
     }
    
    });