package site.book.project.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.Post;
import site.book.project.domain.User;
import site.book.project.dto.PostCreateDto;
import site.book.project.dto.PostListDto;
import site.book.project.dto.PostReadDto;
import site.book.project.dto.PostUpdateDto;
import site.book.project.dto.UserSecurityDto;
import site.book.project.service.BookService;
import site.book.project.service.PostService;
import site.book.project.service.ReplyService;
import site.book.project.service.UserService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final BookService bookService;
    private final UserService userService;
    private final ReplyService replyService;
       
    
    @Transactional(readOnly = true)
    @GetMapping("/list")
    public String list(@AuthenticationPrincipal UserSecurityDto userSecurityDto, String postWriter, Model model) {
        log.info("list()");
//        bookService.readPostCountByAllBookId();
     

        
        
        User user = null; 
        List<PostListDto> postList = new ArrayList<>();
        
        if(userSecurityDto == null) {
             user = userService.read(postWriter);
            Integer userId = user.getId();
            
           postList = postService.postDtoList(userId);
            
               
        } else if (postWriter == null) {
            
            Integer userId = userSecurityDto.getId();
            user = userService.read(userId);
            log.info("id= {}",userId);
        
            postList = postService.postDtoList(userId);
        
              
        }  else if(userSecurityDto.getUsername().equals(postWriter)) {
            
            Integer userId = userSecurityDto.getId();
             user = userService.read(userId);
            log.info("id= {}",userId);
        
            postList = postService.postDtoList(userId);
        
        
        } else if(!userSecurityDto.getUsername().equals(postWriter)) {
            user = userService.read(postWriter);
            Integer userId = user.getId();
            
             postList = postService.postDtoList(userId);
      } 
        
       
       // ????????? create ????????? ?????? ????????? ????????? new ?????????
        LocalDate now = LocalDate.now();
        String day= now.toString().substring(8);
        
        // (??????) post??? ?????? bookId??? ??? ?????? ?????????
        List<Book> books = new ArrayList<>();
        
        for ( PostListDto p : postList) {
            
            Book book = bookService.read(p.getBookId());
            books.add(book);
        }
            
        
            model.addAttribute("day", day);
            model.addAttribute("user", user);      
            model.addAttribute("list", postList);
            model.addAttribute("books", books);
                
        return "/post/list";
    }


   
    
    @PostMapping("/create")
    public String create(PostCreateDto dto, RedirectAttributes attrs) {
        log.info("create(dto ={})", dto);   
      
        Post entity = postService.create(dto); 
        
        // (??????) ??????????????? ????????? ??? - ?????? ???????????? ??????
        // BookID??? ???????????? ????????? ?????? 1 ??????????????????
        postService.countUpPostByBookId(dto.getBookId());
        
        attrs.addFlashAttribute("createdPostId", entity.getPostId());
        attrs.addFlashAttribute("userId", dto.getUserId());
        return "redirect:/post/list";
    }
    
    @Transactional(readOnly = true)
    @GetMapping({ "/detail", "/modify" })
    public void detail(@AuthenticationPrincipal UserSecurityDto userDto,
            Integer postId, String username ,Integer bookId, Model model) {
        log.info("detail(postId= {}, bookId={}, postWriter={})", postId, bookId, username);
        
        List<PostReadDto> recomList = postService.postRecomm(username, bookId);  // 1)
        
        Post p = postService.read(postId);
        Book b = bookService.read(bookId);
        

        
        if (username == null || userDto == null) { // ??? ???????????? ????????? ?????? ??????
            User u = userService.read(p.getUser().getId());
            Post entity = postService.read(postId); // ??? ?????? ???????????? 1?????????.
            entity.update(postId, entity.getHit()+1);
            int hitCount = entity.getHit();
            model.addAttribute("hitCount", hitCount);
            model.addAttribute("user", u);
            
        } else { // ??? ???????????? ????????? ????????????
            User u = userService.read(username);
            int hitCount = postService.read(postId).getHit();
            model.addAttribute("hitCount", hitCount);
            model.addAttribute("user", u);
        }
        
         model.addAttribute("recomList",recomList );    // 2)
         model.addAttribute("post", p);
         model.addAttribute("book", b);
       
        
    }
   
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/delete")
    public String delete(Integer postId, RedirectAttributes attrs) {
        log.info("delete(postId={})",postId);
       
        replyService.deletePostIdWithAllReply(postId);
        postService.delete(postId);
        attrs.addFlashAttribute("deletedPostId", postId);
       
        // ?????? ?????? ????????? ?????? ???????????? ??????(redirect) - PRG ??????
        return "redirect:/post/list";
    }
   
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/update")
    public String update(PostUpdateDto dto) {
        log.info("update(dto={})", dto);
       
        postService.update(dto);
       
        // ????????? ?????? ?????? ????????? ?????? ???????????? ??????(redirect)
        return "redirect:/post/detail?postId=" + dto.getPostId()+"&bookId="+ dto.getBookId();
    }
   
    @GetMapping("/search")
    public String search(String type, String keyword, Model model) {
        log.info("search(type={}, keyword={})", type, keyword);
       
        List<Post> list = postService.search(type, keyword);
        model.addAttribute("list", list);
       
        return "/post/list"; // list.html ??????
    }

   
}
