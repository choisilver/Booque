package site.book.project.web;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.BookComment;
import site.book.project.domain.Order;
import site.book.project.domain.User;
import site.book.project.dto.BookCommentReadDto;
import site.book.project.dto.BookWishDto;
import site.book.project.dto.UserModifyDto;
import site.book.project.dto.UserSecurityDto;
import site.book.project.repository.UserRepository;
import site.book.project.service.BookCommentService;
import site.book.project.service.BookWishService;
import site.book.project.service.OrderService;
import site.book.project.service.UserService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MyPageController {
    
    private final UserRepository userRepository;
    private final OrderService orderService;
    private final UserService userService;
    private final BookWishService bookWishService;
    private final BookCommentService bookCommentService;
    
    // (하은) 마이페이지 연결
    @GetMapping("/myPage")
    public String myPage(@AuthenticationPrincipal UserSecurityDto u, Model model) {
        
        User user = userRepository.findById(u.getId()).get();
        
        // 주문내역 확인 리스트로 가져옴. 날짜별로  최근순 
        List<Order> orderList = orderService.readByUserId(user.getId());
        
        List<BookWishDto> wishBookInfo = bookWishService.searchWishList(user.getId());
        List<BookCommentReadDto> commentList = bookCommentService.readByUserId(user.getId());
        
        
        
        
        model.addAttribute("commentList", commentList);
        model.addAttribute("wishBookInfo", wishBookInfo);
        model.addAttribute("orderList", orderList);
        model.addAttribute("user", user);
        
        return "/book/myPage";
    }
    
    
    // (은정)
    @PostMapping("/myPage/modify")
    public String modify(@AuthenticationPrincipal UserSecurityDto u,
                      UserModifyDto userModifyDto) {
        // 중복검사는 ajax로 해야함..
         userService.modify(userModifyDto, u);
        
        return "redirect:/myPage";
    }
    
    // 못함
    @PostMapping("/myPage/file")
    public String filemodify(@AuthenticationPrincipal UserSecurityDto  userSecurityDto,
                            @RequestParam("filePath") MultipartFile file) throws IllegalStateException, IOException {

        userService.modifyUserImage(userSecurityDto.getId(), file);
        
        
        
        
        return "redirect:/myPage";
    }
    
    // (은정) wish 삭제
    @PostMapping("/myPage/delete")
    public String deleteWish( Integer bookWishId) {
        
        bookWishService.deleteWish(bookWishId);
        
        
        return "redirect:/myPage";
        
    }
    
    
    
    
}
