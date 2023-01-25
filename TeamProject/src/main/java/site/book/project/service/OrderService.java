package site.book.project.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.Cart;
import site.book.project.domain.Order;
import site.book.project.domain.User;
import site.book.project.dto.BookOrderDto;
import site.book.project.dto.BuyInfoDto;
import site.book.project.dto.OrderFinalInfoDto;
import site.book.project.repository.BookRepository;
import site.book.project.repository.CartRepository;
import site.book.project.repository.OrderRepository;
import site.book.project.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
// 장바구니 내역에 있는걸 가져와서 결제 창에 보일 수 있도록
// 결제확인(완료)이 되면 장바구니(CART)테이블 동일한 값(list로 받은 값) 삭제 TODO
// 결제 취소시 다시 결제 테이블(ORDER) 삭제  TODO
// !!!!! 테스트용으론 orderDATE 넣어놈!!!! 
    
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    
    
    public List<Order> readByUserId(Integer userId){
        List<Order> list = orderRepository.findByUserIdOrderByOrderNoDesc(userId);
        
        
        return list;
    }
    
    
    
    
    
    public List<Order> readAll(){
        return orderRepository.findAll();
    }
    
    // 최신순
    public List<Order> readAllDesc(){
        return orderRepository.findByOrderByOrderIdDesc();
    }
    
    // 결제 완료 버튼을 누르면 장바구니데이터는 사라지고(cart에서 delete를 만들면 됨! 여기서 필요 없음)
    // 결제 취소 버튼을 누르면 (creat()) 결제데이터 사라짐.
    public void orderBtn(Integer[] cartId) {
        
    	for(Integer i : cartId) {
    		log.info("장바구니에 있는 데이터 삭제");
    		cartRepository.deleteById(i);
    	}
    	
    }
    
    // (하은) 은정 코드 수정
    // 장바구니(cart)에서 받은 데이터로 
    /**
     * 장바구니 -> 결제창 
     * 결제 버튼 누름과 동시에 실행될 메서드
     * 총 금액 필요 함?? TODO
     * @param cartId 결제할 책 정보를 가지고 있는 PK
     * @return 뭘 리턴해야 할지 모르겠음. 여러줄의 객체가 생성되는데..!
     */
    public Long create(Integer[] cartId) { 
        // cart가 여러개 1,2,3, ... 
        // String date = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")); // ex) 20221209
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMddHHmmSS"));
        
        List<Order> orderList = new ArrayList<>();
        // Integer orderNo = 0;
        Long orderNo = 0L;
        Integer total = 0;
        for(Integer i : cartId) {
            Cart c = cartRepository.findById(i).get();
            
            orderNo = Long.parseLong(date + c.getUser().getId());

            // (하은) 각 책 당 total 구하기
            total = c.getCartBookCount() * c.getBook().getPrices();
            
            Order o = Order.builder().user(c.getUser())
                    .book(c.getBook())
                    .orderBookCount(c.getCartBookCount())
                    .orderDate(LocalDateTime.now())
                    .orderNo(orderNo).total(total)
                    .build();
            
            orderRepository.save(o);
            
            orderList.add(o);
        }
        return orderNo; 
    }
    
    // (은정)
    /**
     * 검색 페이지에서 바로 결제
     * @param userId
     * @param bookId
     * @return
     */
    public Long createFromSearch(Integer userId, Integer bookId) {
    	Book book = bookRepository.findById(bookId).get();
    	
    //	Integer total = dto.getCount() * dto.getPrice(); // 수량 X 가격
    	String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMddHHmmSS")); // ex) 20221209
    	Long orderNo = Long.parseLong(date + userId);
    	
    	User user = userRepository.findById(userId).get();
    	
    	Order order = Order.builder().orderNo(orderNo).user(user).book(book)
    			.orderDate(LocalDateTime.now()).orderBookCount(1).total(book.getPrices()).build();
    	
    	Order orderResult = orderRepository.save(order);
    	
    	return orderResult.getOrderNo();
    }
    
    // (하은수정) 디테일 페이지에서 바로 구매하는 페이지로 넘어할 때 사용
    public Long createFromDetail(Integer userId, BuyInfoDto dto) { // bookId & count로 주문정보(order table) 생성
        User user = userRepository.findById(userId).get();
        Book book = bookRepository.findById(dto.getBookId()).get(); // Book 정보
        Integer total = dto.getCount() * book.getPrices(); // 수량 X 가격
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMddHHmmSS"));
        Long orderNo = Long.parseLong(date + userId);
        
        Order order = Order.builder().orderNo(orderNo).user(user).book(book)
                .orderDate(LocalDateTime.now()).orderBookCount(dto.getCount()).total(total).build();
        
        Order orderResult = orderRepository.save(order);
        
        return orderResult.getOrderNo();
    }
        
    // (하은) 바로 구매하는 책에 대한 order 테이블 데이터 불러오기
    public Order readbyOrderId(Integer orderId) {
        
        Order order = orderRepository.findById(orderId).get();
        
        return order;
    }

    // (하은) orderResult로 넘어갈 때 최종 주문&배송정보 order DB에 업데이트하기 위해
    public void updateInfo(Integer[] cartId, OrderFinalInfoDto dto) {
        // Order DB에 업데이트할 데이터 -> 배송정보(3개), 메시지, 결제방식        
        // order DB에는 cartId가 없음 -> 버튼 누르면서 생성된 orderNo, bookId로 cartId 찾아 update
        // cartId에 맞는 userId, bookId를 찾고, 그걸로 orderNo 비교하면서 찾기
        
        for (Integer i : cartId) {
            Cart cart = cartRepository.findById(i).get();
            
            log.info("하은 사용자 번호={}, 책 번호={}", cart.getUser().getId(), cart.getBook().getBookId());
            
            Order order = orderRepository.findByOrderNoAndUserIdAndBookBookId(dto.getOrderNo(), cart.getUser().getId(), cart.getBook().getBookId());
            
            log.info("하은 order 정보={}", order);
            
            order.update(dto.getPostcode(), dto.getAddress(), dto.getDetailAddress(), dto.getPayOption(), dto.getMessage());
            orderRepository.save(order);
        }
                
    }
    
    // (하은) detail -> orderResult로 넘어갈 때 DB 업데이트 하기 위해
    public void updateInfo(Long orderNo, OrderFinalInfoDto dto) {
        // findByOrderNo가 List<Order>로 되어있어서 그냥 사용..
        List<Order> orderList = orderRepository.findByOrderNo(orderNo);
        
        Order order = orderList.get(0).update(dto.getPostcode(), dto.getAddress(), dto.getDetailAddress(), dto.getPayOption(), dto.getMessage());
        
        orderRepository.save(order);
    }
    
    // (하은수정) cart -> order 넘어갈 때 페이지에 띄울 데이터 저장
    public List<BookOrderDto> readByOrderNo(Long orderNo) {
        List<Order> order =  orderRepository.findByOrderNo(orderNo);
        
        List<BookOrderDto> orderList = new ArrayList<>();
        
        for (Order o : order) {
            BookOrderDto dto = BookOrderDto.builder().bookId(o.getBook().getBookId())
                    .prices(o.getBook().getPrices()).count(o.getOrderBookCount()).bookName(o.getBook().getBookName())
                    .publisher(o.getBook().getPublisher()).bookImage(o.getBook().getBookImage()).author(o.getBook().getAuthor())
                    .category(o.getBook().getCategory()).bookgroup(o.getBook().getBookgroup())
                    .build();
            
            orderList.add(dto);
        }
        
        return orderList;
    } 
        
    @Transactional
    // (하은) 결제창에서 결제 완료 후에는 장바구니 내역 삭제하기
    public void deleteCart(Integer[] cartId) {
        for (Integer i : cartId) {
            cartRepository.deleteById(i);
        }
    }

    @Transactional
    // (하은) 결제창에서 결제 취소 버튼 누르면 order DB 삭제(Long 타입이라 새로 생성)
    public void deleteInOrder(Long orderNo) {
        orderRepository.deleteByOrderNo(orderNo);
    }





//    public List<OrderNoList> listOrderNo(Integer id) {
//        List<Order> list = readByUserId(id);
//        OrderNoList noList = null;
//        // 오더넘버가 같은 것들을 어떻게 처리할 수 있지?
//        for(Order o : list) {
//            noList.builder().
//            
//            
//        }
//        return null;
//    }
    
}
