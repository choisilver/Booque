package site.book.project.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.User;
import site.book.project.dto.BookWishDto;
import site.book.project.dto.BuyInfoDto;
import site.book.project.dto.CartDto;
import site.book.project.dto.UserSecurityDto;
import site.book.project.service.BookWishService;
import site.book.project.service.CartService;
import site.book.project.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {
// 유저 한명당 하나의 카트 페이지 존재함
// queryString을 유저 번호로 넘김
    
    private final UserService userService;
    private final CartService cartService;
    private final BookWishService bookWishService;

    // Principal 객체가 있군 user객체 안뜰때 사용
    @GetMapping("/cart")
    public String cart(@AuthenticationPrincipal UserSecurityDto userSecurityDto,  Model model ){
        log.info("하은 테스트 {}", userSecurityDto);
        
        Integer id = userSecurityDto.getId();
        
        
        User user = userService.read(id); 
        List<CartDto> cartList = cartService.cartDtoList(id);
        
        Integer total = cartService.total(cartList);
        
        // (하은) userId로 조건에 맞는 행 찾기 -> bookId로 book 정보 찾기        
        List<BookWishDto> wishBookInfo = bookWishService.searchWishList(id);
        
        model.addAttribute("wishBookInfo", wishBookInfo);
        model.addAttribute("user", user);
        model.addAttribute("total", total);
        model.addAttribute("cartList", cartList);

        return "book/cart";
        
    }
    
    // (하은수정) 장바구니로 넘어오는 코드 합치기
    @PostMapping("/cart")
    public String cart(@AuthenticationPrincipal UserSecurityDto userSecurityDto, BuyInfoDto dto, Model model) {
        
        Integer userId = userSecurityDto.getId(); // userId로 cart, wish 정보 찾기
        User user = userService.read(userId);
        List<CartDto> cartList = cartService.cartDtoList(userId); // 장바구니에 표시할 책 정보
        Integer total = cartService.total(cartList);
        
        // 책 수량을 선택하지 않고 장바구니에 추가할 경우, 책 수량 1로 고정
        if (dto.getCount() == null) {
            dto.setCount(1);
        }
        
        // 장바구니 데이터 cart table에 생성
        if (cartService.checkUser(userId, dto.getBookId()) == 1) { // 사용자 없으면 create
            cartService.addCart(userId, dto.getBookId(), dto.getCount());
        } else { // 사용자 있으면 update
            Integer afterCount = cartService.updateCount(userId, dto.getBookId(), dto.getCount());
            log.info("변경 수량={}", afterCount);
        }
        
        // userId로 user 위시리스트 불러오기(책 리스트 + 책 정보)   
        List<BookWishDto> wishBookInfo = bookWishService.searchWishList(userId);
        
        model.addAttribute("wishBookInfo", wishBookInfo);
        model.addAttribute("user", user);
        model.addAttribute("cartList", cartList);
        model.addAttribute("total", total);
        
        return "book/cart";
    }
    
    // (하은) 장바구니에 넣고 쇼핑 계속하기 버튼 눌렀을 때 사용
    @PostMapping("/cart/onlyAdd")
    public String onlyAddCart(BuyInfoDto dto, @AuthenticationPrincipal UserSecurityDto userSecurityDto) {
        Integer userId = userSecurityDto.getId();
        
        if (cartService.checkUser(userId, dto.getBookId()) == 1) { // 사용자 없으면 create
            cartService.addCart(userId, dto.getBookId(), dto.getCount());
        } else { // 사용자 있으면 update
            Integer afterCount = cartService.updateCount(userId, dto.getBookId(), dto.getCount());
            log.info("변경 수량={}", afterCount);
        }
        
         return "redirect:/detail?id=" + dto.getBookId();
    }    
    
}
