package site.book.project.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.dto.CartDto;
import site.book.project.service.CartService;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CartRestController {

    private final CartService cartService;
    
    @GetMapping("api/cart/all/{userId}")
    public ResponseEntity<List<CartDto>> read(@PathVariable Integer userId){
        
        return ResponseEntity.ok(cartService.cartDtoList(userId));
    }

    @PostMapping("api/cartid")
    public ResponseEntity<List<CartDto>> cartListll(@RequestBody ArrayList<Integer> ckList){

        cartService.deleteCart(ckList);
        // 유저 번호 그냥 얻자...  가장 끝번호에 추가하면 되잖아
        // 유저 번호 
        List<CartDto> cartDtoList =  cartService.cartDtoList(ckList.get(ckList.size()-1));
        log.info("유저번호가 제대로 넘어 갔나 ? {} , {}",ckList.get(ckList.size()-1) , cartDtoList );
        
        return ResponseEntity.ok(cartDtoList);
    }

    /**
     * mapping 주소값은 넘어오는 parameter와 동일하지 않아도
     * @param count 장바구니 변경되는 수량
     * @param cartId 디비에 저장된 장바구니 고유값
     */
    @GetMapping("api/cartCount/{count}/{cartId}")
    public void updateCount(@PathVariable Integer count, @PathVariable Integer cartId){
        log.info("장바구니 수량 변경~~~~~~{},{}", count, cartId);
        cartService.cartCountUpdate(count, cartId);
        
    }
    

    @GetMapping("/cartTest/{test}")
    public void test(@PathVariable Integer test) {
        log.info("이거 맞아???? 뭐지..??    {}" ,test);
    }
    
 
    
}
