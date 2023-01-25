package site.book.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import site.book.project.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    // 아이디로 유저 정보 찾기
    // @Query("select u from USERS u where u.username = :username")
    // User findByName(@Param(value="username") String username);
    @Transactional
    @EntityGraph(attributePaths = "roles")
    Optional<User> findByUsername(String username);
    Optional<User> findByNickName(String nickname);
    Optional<User> findByEmail(String email);
    
    
    //List<User> searchByPw(@Param(value="userId") String userId, @Param(value = "password") String password);
    
}
