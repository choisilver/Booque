package site.book.project.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.User;
import site.book.project.dto.BookOrderDto;
import site.book.project.dto.OrderFinalInfoDto;
import site.book.project.dto.UserSecurityDto;
import site.book.project.service.OrderService;
import site.book.project.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderResultController {
    
    private final OrderService orderService;
    private final UserService userService;
    
    // (하은수정) 무통장입금, 카카오페이 결과 페이지 다르게 보일 수 있게
    @PostMapping("/orderResult")
    public String orderResult(@AuthenticationPrincipal UserSecurityDto userSecurityDto, Integer[] cartId, OrderFinalInfoDto dto, Model model) {
        User user = userService.read(userSecurityDto.getId()); // user data
        
        // order table update
        if (cartId.length == 0) {
            orderService.updateInfo(dto.getOrderNo(), dto);
            log.info("하은 orderNo = {}", dto.getOrderNo());

        } else {
            orderService.updateInfo(cartId, dto);
        }
        
        // 주문완료시 장바구니 내역은 삭제
        orderService.deleteCart(cartId);
        
        // order table data 보여주기 - orderNo 사용
        List<BookOrderDto> orderInfo = orderService.readByOrderNo(dto.getOrderNo());
        Long orderNo = dto.getOrderNo();
        
        Integer total = 0;
        Integer points = 0;
        
        for (int a = 0; a < orderInfo.size(); a++) {
            total += orderInfo.get(a).getPrices() * orderInfo.get(a).getCount();
            points += (int) ((Integer) total * 0.05);
            userService.update(points, user.getId());
            log.info("하은 적립 포인트 = {}", points);
        }
        
        model.addAttribute("user", user);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("orderNo", orderNo);
        model.addAttribute("total", total);
        model.addAttribute("order", dto);
        model.addAttribute("points", points);
        
        if (dto.getPayOption().equals("무통장입금")) {
            return "book/orderCash";
        } else {
            return "book/orderKaKao";
        }
        
    }
    
    // (하은) 결제 페이지에서 결제취소 버튼 누르면 order DB 삭제하고 메인 페이지로 이동
    @PostMapping("/orderCancel")
    public String orderCancel(OrderFinalInfoDto dto) {
        
        orderService.deleteInOrder(dto.getOrderNo());
        
        return "redirect:/";
    }
    
}
