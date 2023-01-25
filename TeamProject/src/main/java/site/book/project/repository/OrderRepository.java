package site.book.project.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import site.book.project.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByOrderByOrderIdDesc();
    
    // (하은) orderNo가 동일한 데이터 불러오기
    List<Order> findByOrderNo(Long orderNo);
    
    // (하은) cartId에 따른 order DB 불러오기
    Order findByOrderNoAndUserIdAndBookBookId(Long orderNo, Integer id, Integer bookId);
    
    void deleteByOrderNo(Long orderNo);
    
    // (은정) userId로 오더 리스트 최근순
    List<Order> findByUserIdOrderByOrderNoDesc(Integer userId);
    
   // List<Integer> findByUserIdGroupByOrderNo(Integer userId);

}
