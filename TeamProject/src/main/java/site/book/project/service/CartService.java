package site.book.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.Cart;
import site.book.project.domain.User;
import site.book.project.dto.CartDto;
import site.book.project.repository.BookRepository;
import site.book.project.repository.CartRepository;
import site.book.project.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;    
    private final UserRepository userRepository;
    
    public List<Cart> readAll(){
        return cartRepository.findAll();
    }
    
    //cartId를 갖고 userId
    
    
    
    // 선택한 책 삭제 (결제완료버튼에서도 사용할 예정)
    public void deleteCart(List<Integer> cartId) {
        
        for(int i=0; i<cartId.size()-1; i++) {
            cartRepository.deleteById(cartId.get(i));
            log.info("장바구니에 있는 데이터 삭제 될 정보 {}", i);
            
            
        }
        
    
    }
    
    
    /**
     * (은정)
     * 저장되어 있는 data를 cartDto로 변환, 리턴
     * @param userId 유저의 장바구니 table를 얻기 위해 
     * @return 장바구니창에서 보여질 DTO를 리스트로 리턴
     */
    public List<CartDto> cartDtoList(Integer userId) {
        List<Cart> list =  cartRepository.findByUserIdOrderByCartIdDesc(userId);
        
        List<CartDto> dtolist = new ArrayList<>(); 
        
        for(Cart c: list) {
        	// dto에 cart에서 찾은 book을 넣는다
            Book book = c.getBook();
            
            CartDto dto = new CartDto(book.getBookgroup(),
                        book.getCategory(),
                        book.getBookName(),
                        book.getAuthor(), 
                        book.getPrices(), 
                        book.getBookImage(), 
                        c.getCartBookCount()
                        , c.getCartId()
                        , book.getBookId());
            
            dtolist.add(dto);
        }
        
        return dtolist;
    }
    
    // (하은) detail 페이지 책 cart에 저장하기 -> 추가된 행 개수 리턴
    public void addCart(Integer userId, Integer bookId) {
    	log.info("Id(user={}, book={})", userId, bookId);
    	
    	// 넘길 USER, BOOK 객체 생성        
    	User user = userRepository.findById(userId).get();
    	Book book = bookRepository.findById(bookId).get();
    	
    	Cart cart = Cart.builder().cartBookCount(1).book(book).user(user).build();
    	
    	cartRepository.save(cart);
    	
    }
    // (하은) detail 페이지 책 cart에 저장하기 -> 추가된 행 개수 리턴
    public Integer addCart(Integer userId, Integer bookId, Integer count) {
        log.info("Id(user={}, book={})", userId, bookId);
        
        // 넘길 USER, BOOK 객체 생성        
        User user = userRepository.findById(userId).get();
        Book book = bookRepository.findById(bookId).get();
        
        Cart cart = Cart.builder().cartBookCount(count).book(book).user(user).build();
        
        cartRepository.save(cart);
        
        return count;
    }
    
    // (하은) 장바구니 유저 유무 확인
    public Integer checkUser(Integer userId, Integer bookId) {
        log.info("사용자 카트 사용유무(userId={}, bookId={})", userId, bookId);
        
        Cart cart = new Cart();
        cart = cartRepository.findByUserIdAndBookBookId(userId, bookId);
        
        if (cart != null) { // 사용자가 있으면 0을 리턴
            return 0;
        } else { 
            return 1; // 사용자가 없으면 1을 리턴
        }
    }
    
    // (하은) 한 유저가 해당 책을 이미 장바구니에 넣었을 때 수량 변경하기
    public Integer updateCount(Integer userId, Integer bookId, Integer count) {
        Cart cart = cartRepository.findByUserIdAndBookBookId(userId, bookId);
        Integer afterCount = count + cart.getCartBookCount();
        cart.update(afterCount);  
        
        cartRepository.save(cart);
        
        log.info("cartService(userId={}, bookId={}, count={})", userId, bookId, afterCount);
        
        return afterCount; // 바꾸기
    }
    
    // (하은) 카트 정보 읽기
    public Cart read(Integer cartId) { 
        Cart cart = cartRepository.findById(cartId).get();
        
        return cart;
        
    }    

    public Integer total(List<CartDto> cartList) {
        // for문에서 가격, 카운트 곱
        Integer total = 0;
        for(CartDto o : cartList) {
            total +=o.getCount()*o.getPrices();
        }
        
        return total;
    }

    @Transactional
    public void cartCountUpdate(Integer count, Integer cartId) {
        Cart cart =cartRepository.findById(cartId).get();
        cart.update(count);
        
    }
        
}
