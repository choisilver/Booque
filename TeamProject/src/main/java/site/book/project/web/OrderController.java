package site.book.project.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.User;
import site.book.project.dto.UserSecurityDto;
import site.book.project.dto.BookOrderDto;
import site.book.project.dto.BuyInfoDto;
import site.book.project.service.BookService;
import site.book.project.service.CartService;
import site.book.project.service.OrderService;
import site.book.project.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;
    private final BookService bookService;
    
    // (하은수정) 카트에서 결제하기 버튼 눌렀을 때 사용 - order 페이지에서 필요한 데이터 - user 정보, book 정보
    @PostMapping("/order")
    public String order(@AuthenticationPrincipal UserSecurityDto userSecurityDto, Integer[] cartId, Model model) { 
          Long orderNo = orderService.create(cartId); // 주문번호 생성
          User user = userService.read(userSecurityDto.getId()); // User 정보 찾기
          List<BookOrderDto> order = new ArrayList<>(); // order 페이지에 필요한 책 데이터 리스트
          Integer total = 0;
          for (Integer i : cartId) {
              Book book = cartService.read(i).getBook();
              
              BookOrderDto bookData = BookOrderDto.builder()
                      .bookId(book.getBookId()).prices(book.getPrices())
                      .cartId(i)
                      .count(cartService.read(i).getCartBookCount())
                      .bookName(book.getBookName()).publisher(book.getPublisher())
                      .bookImage(book.getBookImage()).author(book.getAuthor())
                      .category(book.getCategory()).bookgroup(book.getBookgroup())
                      .build();
              
              order.add(bookData);
              
              total += book.getPrices() * cartService.read(i).getCartBookCount();
          }
          
          model.addAttribute("user", user);
          model.addAttribute("order", order);
          model.addAttribute("orderNo", orderNo);
          model.addAttribute("total", total);
          
          return "book/order";
      }
    
    // (하은수정) 디테일창에서 바로 구매하기 버튼 눌러서 한 권만 구매할 때 사용
    @PostMapping("/orderOne")
    public String orderNow(@AuthenticationPrincipal UserSecurityDto userSecurityDto, BuyInfoDto dto, Model model) {
        User user = userService.read(userSecurityDto.getId());
        
        // order 페이지에 필요한 책 데이터 리스트
        Book book = bookService.read(dto.getBookId());
        
        // 책 수량을 선택하지 않고 & 바로 구매할 경우, 책 수량 1로 고정
        if (dto.getCount() == null) {
            dto.setCount(1);
        }
        
        BookOrderDto order = BookOrderDto.fromEntity(book, dto);
        
        Integer total = dto.getCount() * book.getPrices();
        Long orderNo = orderService.createFromDetail(user.getId(), dto); // 주문번호 생성

        model.addAttribute("order", order);
        model.addAttribute("user", user);
        model.addAttribute("orderNo", orderNo);
        model.addAttribute("total", total);
        
        return "book/order";
    }
    
}
