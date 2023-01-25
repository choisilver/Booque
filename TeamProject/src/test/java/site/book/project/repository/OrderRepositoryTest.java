package site.book.project.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Cart;
import site.book.project.domain.Order;
import site.book.project.service.CartService;
import site.book.project.service.OrderService;

@Slf4j
@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    
    
    
    @Autowired
    private OrderService orderService;

    @Autowired
    private CartRepository cartRepository;
    
//    @Test
//    public void test() {
//        Assertions.assertNotNull(orderRepository);
//        Assertions.assertNotNull(orderService);
//        
//        // cart에 있는거 하나 읽어서 order table에 저장
//        Cart c = cartRepository.findById(4).get();
//        
//        log.info("카트 4번에 있는것 {}" ,c);
//        
//        List<Integer> oi = new ArrayList<>();
//        oi.add(1);
//        oi.add(2);
//        log.info("test");
//       Integer r = orderService.create(oi);
//    }
        
        // (하은) 해당 주문번호 관련 내역 읽어오기
        @Test
        public void testOrderNow() {
       //     Assertions.assertNotNull(orderService);
            Assertions.assertNotNull(orderRepository);
   
            
        }
        
        // (하은) 결제 완료시 최종 주문 & 배송 내역 order DB에 업데이트하기 테스트
//        @Test
        public void testOrderFinalInfoUpdate() {
            Assertions.assertNotNull(orderService);
            
            Order order = orderRepository.findById(2).get();
            log.info("하은 order DB update={}", order.getOrderId());
            
            order.update(12345, "서울시", "송파구", "무통장입금", "부재시 문앞에 놔주세요");
            orderRepository.save(order);
            
        }
        
}
