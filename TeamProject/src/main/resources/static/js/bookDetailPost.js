/**
 *  책 상세페이지에서 post 순서 변경
 *  최신순, 별점 높은순, 별점 낮은순 (댓글 순 은 아직,,)
 */
 
 window.addEventListener('DOMContentLoaded', () => {
    
    // 최신순을 기본으로!
    readPostDesc();
    
    

    // orderDesc, orderScoreDesc, orderScore
    const orderScoreDesc = document.querySelector('#orderScoreDesc');
    orderScoreDesc.addEventListener('click', scoreDesc);

    const orderScore = document.querySelector('#orderScore');
    orderScore.addEventListener('click', score);
    
    const orderDesc = document.querySelector('#orderDesc');
    orderDesc.addEventListener('click',readPostDesc)


    function scoreDesc(){
        const bookId = document.querySelector('#id').value;
        
        axios
        .get('/api/post/scoreDesc/'+bookId)
        .then(respons => {updatePostList(respons.data)})
        .catch(err => { console.log(err) });
        
    }
    
    function score(){
        const bookId = document.querySelector('#id').value;
        
        axios
        .get('/api/post/score/'+bookId)
        .then(response => {updatePostList(response.data)})
        .catch(err => { console.log(err) });
        
    }

    

    function readPostDesc() {
        
    
        const bookId = document.querySelector('#id').value;
    
        axios
        .get('/api/post/all/' + bookId) // Ajax GET 요청 보냄.
        .then(response => { updatePostList(response.data) }) // 어떤 데이터를 받아야함?
        .catch(err => { console.log(err) });
    
        
    };  
    
    
    
    
  // bookCommentReadDto에 있는 정보가 response.data로 전해짐 필드랑 동일하게 써야함
    function updatePostList(data){
        const divPost = document.querySelector('#postOrder');
        const username = document.querySelector('#un').value;
        let str = '';
        
        for(let r of data){
            const scoreR = Math.round(r.myScore)
            let s ='';
            for(let i=0; i<scoreR; i++){
                   s += '★';
            }
            for(let i=0; i<5-scoreR; i++){
                   s += '☆';
            }
            
            let score = '  (별점 '+r.myScore+')'
            
            
            str += '<div class="my-2">' 
                + '<div class="card my-2">'
                + '<div class="card-header">'
           +  '<div class="my-2"> '
           +  `<a href="/post/detail?postId=${r.postId}&bookId=${r.bookId}&username=${r.writer}" onclick="postHitCountUp(${r.postId}, ${username});">` 
                + '<h5>' + r.writer + '</h5>'
           +   '   </a> '
           + '  </div> '
           
                
                + '<h5 class="card-title">' + r.title + '</h5>'
                + '<span>' + s+ '</span> <span class="card-subtitle mb-2 text-muted">'+score+'</span>'
                + '</div>'
                + '<div class="card-body">'
                + '<div class="box">'
                    +'<div style="display: block;" class="postcontent">'+r.content +'</div>'
                    +'<div style="display: none;" class="contentAll" >'+r.content+'</div>'
                + '</div>'
                + '<p id="postDate" style="float: right;" class="card-subtitle mb-2 text-muted" >' + r.createdTime + '</p>'
                + '</div>';
            // 댓글 작성자 아이디와 로그인 사용자 아이디가 같을 때만 "수정"을 보여줌.
    //        if (r.writer==loginUser) {
      //          str += '<div class="card-footer">'
     //           + `<button type="button" class="btnModifies btn btn-outline-primary" data-rid="${r.replyId}">수정</button>`
     //           + '</div>'
      //      }
            str += '</div>';
            
            
        }
        
        divPost.innerHTML = str;
        
        const postDates = document.querySelectorAll('#postDate');
        postDates.forEach(d =>{
			d.innerText = d.innerText.substr(0,10);
		})
        

        $('.box').each(function(){
            var content = $(this).children('.postcontent');
            console.log(content)
            var content_txt = content.text();
            var content_txt_short = content_txt.substring(0,100)+"   ...";
            var btn_more = $('<a href="javascript:void(0)" class="more"> <span> <br></span> 더보기</a>');
            var conAll = $(this).children('.contentAll');
            
            
            $(this).append(btn_more);
            
            if(content_txt.length >= 100){
                content.html(content_txt_short)
                
            }else{
                $('#contentAll').show();
                conAll.show();
                content.hide();                
                btn_more.hide()
            }
            
            btn_more.click(toggle_content);
            // 아래 bind가 안 되는 이유는??
            // btn_more.bind('click',toggle_content);

            function toggle_content(){
                if($(this).hasClass('short')){
                    conAll.hide();
                    content.show();
                    // 접기 상태
                    $(this).html('     더보기');
                    content.html(content_txt_short)
                    $(this).removeClass('short');
                }else{
                    conAll.show();
                    content.hide();
                    // 더보기 상태
                    $(this).html('접기');
                    content.html(content_txt)
                    $(this).addClass('short');

                }
            }
        });
        
        
        
    }
    
    
    
    
    
    
})
 
 
 
 
 