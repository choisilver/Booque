package site.book.project.service;

import java.util.ArrayList;
import java.util.List;

// import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.Post;
import site.book.project.domain.User;
import site.book.project.dto.PostCreateDto;
import site.book.project.dto.PostListDto;
import site.book.project.dto.PostReadDto;
import site.book.project.dto.PostUpdateDto;
import site.book.project.dto.ReplyReadDto;
import site.book.project.repository.BookRepository;
import site.book.project.repository.PostRepository;
import site.book.project.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookService bookService;
    private final ReplyService replyService;
    private final UserService userService;
    
    
    // Post 리스트 전체  TODO 유저별 전체리스트 ? 
    @Transactional(readOnly = true)
    public List<Post> read(){
        log.info("read()");
        
        return postRepository.findByOrderByPostIdDesc();
    }
   
    @Transactional(readOnly = true)
    public List<PostListDto> postDtoList(Integer userId) {
        List<Post> list = postRepository.findByUserIdOrderByCreatedTimeDesc(userId);
         
        List<PostListDto> dtoList = new ArrayList<>();
        
        PostListDto dto = null;
        
        for (Post post : list) {
            Post p = post;
            
            List<ReplyReadDto> rpiList = replyService.readReplies(p.getPostId());
             
           dto = PostListDto.builder()
            .userId(p.getUser().getId())
            .postId(p.getPostId())
            .title(p.getTitle())
            .postWriter(p.getPostWriter())
            .postContent(p.getPostContent())
            .bookId(p.getBook().getBookId())
            .bookImage(p.getBook().getBookImage()).modifiedTime(p.getModifiedTime())
            .createdTime(p.getCreatedTime())
            .replyCount(rpiList.size())
            .hit(p.getHit())
            .build();
            
        
             dtoList.add(dto);           
        }
        
        
         return dtoList;
    }
    
  
    @Transactional
    public Post create(PostCreateDto dto) {
        Book book = bookRepository.findById(dto.getBookId()).get();
        User user = userRepository.findById(dto.getUserId()).get();

        if( book.getBookScore() == null) {
        	book.update(25);
        } else {
        	Integer score = book.getBookScore() + dto.getMyScore()*10;
        	book.update(score/2);
        	
        }
        
        
        Post entity = postRepository.save(dto.toEntity(book,user));
        return entity;
    }

    @Transactional(readOnly = true)
    public Post read(Integer postId) {
        log.info("read(postId = {})", postId);
        
        return postRepository.findById(postId).get();
    }

    public void delete(Integer postId) {
        log.info("delete(postId={})",postId);
       
        postRepository.deleteById(postId);
       
    }
   
    @Transactional // readOnly = false(기본값)
    public void update(PostUpdateDto dto) {
        log.info("update(dto={})", dto);
       
        // 메서드에 @Transactional 애너테이션을 사용하고,
        // (1) 수정하기 전의 엔터티 객체를 검색한 후에
        // (2) 검색된 엔터티 객체를 수정하면
        // 메서드가 종료될 때 데이터베이스 테이블에 자동으로 update SQL이 실행됨.
        // PostRepository의 save() 메서드를 명시적으로 호출하지 않아도 됨.
        Post entity = postRepository.findById(dto.getPostId()).get(); // (1)
        entity.update(dto.getTitle(), dto.getPostContent()); // (2)
       
        
    }

    @Transactional(readOnly = true)
    public List<Post> search(String type, String keyword) {
        log.info("search(type= {} keyword={})", type, keyword);
        
        List<Post> list = new ArrayList<>();
        
        switch(type) {
        case "pt":
            list = postRepository.findByTitleIgnoreCaseContainingOrderByPostIdDesc(keyword);
            break;
        case "pc":
            list = postRepository.findByPostContentIgnoreCaseContainingOrderByPostIdDesc(keyword);
            break;
        
        }
        
        return list;
    }


    
    // choi 1207 책 상세 post 최신순, 별점 높은순, 별점 낮은순 => Ajax로 할 예정
    
	public List<Post> findBybookId(Integer bookId) {
	    
	    // 오래된 순
	    return postRepository.findByBookBookId(bookId);
	}

	// 최신순
	public List<PostReadDto> findDesc(Integer bookId){
	    List<Post> list = postRepository.findByBookBookIdOrderByCreatedTimeDesc(bookId); 
	    return list.stream().map(PostReadDto:: fromEntity).toList();
	}
	
	// 별점 높은순
	public List<PostReadDto> findScoreDesc(Integer bookId){
	    List<Post> list = postRepository.findByBookBookIdOrderByMyScoreDesc(bookId);
	    
	    return list.stream().map(PostReadDto:: fromEntity).toList();
	}
	
	// 별점 낮은순
	public List<PostReadDto> findScore(Integer bookId){
	    List<Post> list = postRepository.findByBookBookIdOrderByMyScore(bookId);
	    return list.stream().map(PostReadDto:: fromEntity).toList();
	}

	// (홍찬) 검색 화면에서 BookId로 Post 글이 몇 개 달려있는지 select하기
	@Transactional(readOnly = true)
	public Integer countPostByBookId(Integer bookId) {
	    Integer count = 0;
	    List<Post> list = postRepository.findByBookBookId(bookId);
	    count = list.size();
	    return count;
	}

	// (홍찬) 리뷰순에서 사용할 것 - 책 ID에 해당하는 포스트 글이 1 증가시켜주기
	@Transactional
	public void countUpPostByBookId(Integer bookId) {
	    Book entity = bookRepository.findById(bookId).get();
	    entity.updatePostCount(entity.getPostCount()+1);
	}
	
	
	
	// (은정)  writer 쓴 유저는 제외 하고 꺼낼예정
	public List<PostReadDto> postRecomm(String writer, Integer bookId ){
	    List<PostReadDto> list = findScoreDesc(bookId);
	    List<PostReadDto> postC =  new ArrayList<>();
	    
	    
	     
	    for(PostReadDto post : list) {
	        
	       if(!post.getWriter().equals(writer)) {
	           
	           User user = userService.read(post.getWriter());
	           
	           String userImage = user.getUserImage();
	           post.setUserImage(userImage);
	           
	           postC.add(post);
	           
	       }
	        
	        
	    }
	    
	    
	    
	    return postC;
	}
	
	
	
}
