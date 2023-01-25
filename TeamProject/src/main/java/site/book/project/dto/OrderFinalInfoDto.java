package site.book.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import site.book.project.domain.Order;

@AllArgsConstructor
@Builder
@Data
@ToString
@NoArgsConstructor
public class OrderFinalInfoDto {
    // (하은) order 최종 정보 DB에 업데이트하기 위해!
    
    private Long orderNo;
    private String username; // Order로 변환은 하지 않음
    private Integer postcode;
    private String address;
    private String detailAddress;
    private String phone; // Order로 변환은 하지 않음
    private String message;
    private String payOption;
    
    public Order toEntity() {
        return Order.builder().orderNo(orderNo).postcode(postcode).address(address)
                .detailAddress(detailAddress).payOption(payOption).message(message).build();
    }
    
}
