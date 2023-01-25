package site.book.project.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import site.book.project.dto.UserModifyDto;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity(name = "USERS")
@SequenceGenerator(name = "USERS_SEQ_GEN", sequenceName = "USERS_SEQ", initialValue = 1, allocationSize = 1)
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ_GEN")
    private Integer id;  //PK
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String password;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phone;
    
    private String address;
    
    @Column(unique = true, nullable = false)
    private String nickName;
    
    @Column(length = 1000)
    private String userImage;
    
    @Column(length = 1000)
    private String fileName;
    
    @Column(length = 1000)
    private String filePath;
    
    @Builder.Default
    private Integer point = 0;
    
    @Builder.Default
    private String grade = "0"; 
    
    private String postIntro;
    
    @Builder.Default
    private Integer usedBookCount = 0;
    
    @Builder.Default
    private Integer booqueScore = 50;
    
    @Builder.Default
    private String booqueLevel = "새싹부끄";
    
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UserRole> roles = new HashSet<>();
    
    public User addRole(UserRole role) {
        roles.add(role);
        
        return this;
    }
    
    // (하은) 책 구매시 포인트 추가
    public User update(Integer point) {
        this.point = point;
        
        return this;
}
    
    public User updateImage(String fileName, String filePath) {
        this.fileName =fileName;
        this.filePath = filePath;
        
        return this;
    }
    
    public User updateProfile(UserModifyDto user) {
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
                
        return this;
    }
    
}