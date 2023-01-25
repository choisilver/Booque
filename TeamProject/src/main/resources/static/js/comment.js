/**
 *  좋아요 해야함. 댓글 수정, 삭제도 해야함.
 *  최신순, 해야함.
 */
 
 window.addEventListener('DOMContentLoaded', () => {
    
    
    readAllComment();

    //readAllComments();
    // 사용할 버튼, bookId, commentWriter, commentText, btnComment
    
    const btnComment = document.querySelector('#btnComment');
    btnComment.addEventListener('click', newComment);

    const btnOrderLikes = document.querySelector('#btnOrderLikes');
    btnOrderLikes.addEventListener('click', readAllOrderLikeComment);
    
    const btnOrderDesc = document.querySelector('#btnOrderDesc');
    btnOrderDesc.addEventListener('click',readAllComment)
    
    // comment 작성 함수
    function newComment (){
        const bookId = document.querySelector('#id').value;
        const commentText = document.querySelector('#commentText').value;
        
        if( commentText==''){
            alert('한줄 평을 입력해 주세여.')
            return;
        }
        // BookCommentRegisterDto와 동일한 key값을 넣어야지 제대로 들어감
        const data = {
            bookId: bookId,
            commentText: commentText
        }
        
        axios.post('/api/comment' , data)
            .then(response => {
                console.log(response);
                alert('#'+response.data + '번째 한줄평 등록');
                clearInputs();
                readAllComment();   // 밑에 있는 함수사용
            })
            .catch(error => {
                console.log(error);
            })
        
    }
    
    
    
    function clearInputs() {
        document.querySelector('#commentText').value = '';
    }
    
    // 최신순
    function readAllComment(){
        const bookId = document.querySelector('#id').value; // 책 번호
        
         axios
        .get('/api/comment/all/' + bookId) // Ajax GET 요청 보냄.
        .then(response => { updateCommentList(response.data) }) // 어떤 데이터를 받아야함?
        .catch(err => { console.log(err) });
        
    }
    
    // 호감순
    function readAllOrderLikeComment(){
        const bookId = document.querySelector('#id').value; // 책 번호
        
         axios
        .get('/api/comment/allOrderLike/' + bookId) // Ajax GET 요청 보냄.
        .then(response => { updateCommentList(response.data) }) // 어떤 데이터를 받아야함?
        .catch(err => { console.log(err) });
        
    }
    
    
    
  // bookCommentReadDto에 있는 정보가 response.data로 전해짐 필드랑 동일하게 써야함
    function updateCommentList(data){
        const divComment = document.querySelector('#bookComment');
        
        let str = '';
        
        for(let r of data){
            
            str += '<div class="my-2">' 
                + '<div class="card my-2">'
                + '<div class="card-header">'
                + '<h6 style="float: right;">' + r.writer + '</h6>'
                + '</div>'
                + '<div> '
                + '<span>좋아요 </span>'
                + '<span  class="likes">'
                + '<img id="likeEmpty" style="width:15px;" alt="" src=" /images/likeEmpty.png " />'
                + '<img id="likeFull" style="width:15px; margin-bottom:0px;" alt="" src=" /images/likeFull.png " />'
                + '</span>'
                + ' <span>  </span>'
                + '<span id="likesCount">'+r.likes+'</span> </div>'
                + '<div class="card-body">'
                + '<p>' + r.commentText + '</p>'
                + '<p style="float: right;" id="commentDate" class="card-subtitle mb-2 text-muted">' + r.createdTime + '</p>'
                + '</div>';
            // 댓글 작성자 아이디와 로그인 사용자 아이디가 같을 때만 "수정"을 보여줌.
    //        if (r.writer==loginUser) {
      //          str += '<div class="card-footer">'
     //           + `<button type="button" class="btnModifies btn btn-outline-primary" data-rid="${r.replyId}">수정</button>`
     //           + '</div>'
      //      }
            str += '</div>';
        }
        
        divComment.innerHTML = str;
        
        
        const dates = document.querySelectorAll('#commentDate')
        dates.forEach( d => {
			d.innerText = d.innerText.substr(0,10);
		})
        
    }
    
    
    
    
    
    
})
 
 
 
 
 