package site.book.project.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.Cart;
import site.book.project.domain.Order;
import site.book.project.domain.User;
import site.book.project.dto.CartDto;
import site.book.project.service.CartService;

@Slf4j
@SpringBootTest
public class CartRepositoryTests {

    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    
//    @Test
//    @Transactional
//    public void test() {
//        Assertions.assertNotNull(cartRepository);
//        
//        Assertions.assertNotNull(cartService);
//        
//        

//        List<Cart> cart = cartService.readAll();
//        for(Cart c: cart) {
//            log.info("cccart {}" , c.getBook().getAuthor());
//        }
        
        
//    }
    
//    @Test
//    public void testAddCart() {
//        Assertions.assertNotNull(cartRepository);
//        
//        User user = userRepository.findById(1).get();
//        Book book = bookRepository.findById(56).get();
//        
//        Cart cart = Cart.builder().cartBookCount(3).book(book).user(user).build();
//        
//        cartRepository.save(cart);
//        
//        log.info("add(책이름:{}, 수량:{})", cart.getBook().getBookName(), cart.getCartBookCount());
        
//    }
    
    @Test
    public void testUpdateCart() {
        Assertions.assertNotNull(cartRepository);
        
        Cart cart = cartRepository.findByUserIdAndBookBookId(3, 91);
        cartService.updateCount(3, 91, 20);
        cartRepository.save(cart);

        log.info("cart 변경 수량={}", cartService.updateCount(3, 91, 20));
        
    }
    
//    @Test
//    @Transactional
//    public void test() {
//        Assertions.assertNotNull(orderRepository);
//        
//        Assertions.assertNotNull(orderService);
//        List<Order> list = orderService.readAllDesc();
//        
//        for(Order o : list) {
//            log.info("order table 제ㅁ발 보여라~~! {} ",o);
//        }
//        
//        Cart cart = cartRepository.findById(4).get();
//        log.info("테스트하기 복잡하다 증말 {},{},{}" , cart, cart.getBook(), cart.getUser());
//      
//        // order no를 만들어야 함. 
//        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")) + cart.getUser().getId() ;
//        log.info("date = {}" ,date);
//        Integer orderNo = Integer.parseInt(date.toString());
//        log.info("주문번호= {}" ,orderNo);
//        
//        Order order = Order.builder()
//                .book(cart.getBook()).user(cart.getUser())
//                .orderBookCount(cart.getCartBookCount()).orderNo(orderNo).orderDate(LocalDateTime.now()).build();
//        
//        log.info("생성 할거얌 {}" , order);
//        log.info("저장 할거얌 {}" , orderRepository.save(order));
//    }
    
}
