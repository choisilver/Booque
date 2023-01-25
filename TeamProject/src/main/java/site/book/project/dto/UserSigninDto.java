package site.book.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.book.project.domain.User;

@NoArgsConstructor
@Data
public class UserSigninDto {
    
    private String username;
    private String password;
    
    public User dto() {
        return User.builder().username(username).password(password).build();
    }

}
