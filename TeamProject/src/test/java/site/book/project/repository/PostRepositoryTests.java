package site.book.project.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import site.book.project.dto.PostReadDto;
import site.book.project.service.PostService;
import site.book.project.service.ReplyService;
import site.book.project.service.UserService;

@Slf4j
@SpringBootTest
public class PostRepositoryTests {
//
//   @Autowired
//   private PostRepository postRepository;
//   
   
   @Autowired
   private UserService userService;
   
   @Autowired
   private PostService postService;

   @Autowired
   private ReplyService replyService;
   private UserRepository userRepository;
   
   @Autowired
   private ReplyRepository replyRepository;

   public void testSave() {
   
//       User user1 = User.builder().username("user1").password("111").nickName("0").email("dd@n").phone("1").name("김").address("경기").build();
//       Post entity = Post.builder().user(user1).title("어린왕자").postContent("재밋다").postWriter("루피").myScore(5).build();
//       
//       log.info("save 전 {} | {} | {}", entity, entity.getCreatedTime(), entity.getModifiedTime());
//       
//       postRepository.save(entity);   // insert 문장
//       log.info("save 후 {} | {} | {}", entity, entity.getCreatedTime(), entity.getModifiedTime());
       
       
       Assertions.assertNotNull(userService);
       
       Assertions.assertNotNull(userRepository);
       log.info(" {}",userService.checkUsername("a"));
       

       
       
   }
   
   @Test
   public void testDelete() {
       List<PostReadDto> list =  postService.postRecomm("user", 10);
       
       for(PostReadDto p : list) {
           log.info("ㅈ[비리리ㅣ리릴 {}",list);
           
       }
   }
}
