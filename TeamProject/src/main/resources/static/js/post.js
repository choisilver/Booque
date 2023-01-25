/**
 * 
 */
 window.addEventListener('DOMContentLoaded', () => {
    // (하은) 
    
    readPost();
    
    function readPost() {
        
        const userId = document.querySelector('#userId').value;
        const postId = document.querySelector('#postId').value;
        const postWriter = document.querySelector('#postWriter').value;
        
     
        axios
        .get('/api/post/content/' + postId)
        .then(response => { postContentShow(response.data) })
        .catch(err => { console.log(err) })
    };
    
    function postContentShow(data) {
        const divPost = document.querySelector('#postContent');
        
        let str = '';
        
            str += '<div class="w-100 px-5 py-5 mt-5 border-top border-dark">'
                + '<div>' + data.content + '</div>'
                + '</div>';
        divPost.innerHTML = str;
        
    };
    
});