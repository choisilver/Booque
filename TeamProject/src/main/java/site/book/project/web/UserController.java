package site.book.project.web;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.User;
import site.book.project.dto.UserRegisterDto;
import site.book.project.dto.UserSigninDto;
import site.book.project.service.UserService;

@Slf4j
@Controller 
@RequiredArgsConstructor
@RequestMapping("/user") 
public class UserController {
    
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    
    @GetMapping("/signup") 
    public String signUp() {
        log.info("signUp() GET");
        return "./signup";
    } 
    
    @GetMapping("/checkid")
    @ResponseBody 
    public ResponseEntity<String> checkUsername(String username) {
        log.info("checkUsername (username={})", username);
        
        String result = userService.checkUsername(username);
        
        return ResponseEntity.ok(result);
        
    } // Rest방식, 리턴값이 Body에 간다 => HTML을 만들지 않을거다!
    
    @GetMapping("/checknick")
    @ResponseBody
    public ResponseEntity<String> checkNickname(String nickname) {
        log.info("checkNickname (nickname={})", nickname);
        
        String result = userService.checkNickname(nickname);
        
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/checkemail")
    @ResponseBody
    public ResponseEntity<String> checkEmail(String email) {
        log.info("checkEmail (email={})", email);
        
        String result = userService.checkEmail(email);
        
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/checkpw")
    @ResponseBody
    public ResponseEntity<String> checkPw(String username, String password) {
        log.info("username = {}, password = {}", username, password);
        
        String result = userService.signIn(username, password);
        return ResponseEntity.ok(result);
    }

    
    @GetMapping("/signin")
    public String signIn() {
        log.info("signin() GET");
        
        return "./signin";
    }
    
   
    
    
    @PostMapping("/signup")
    public String signUp(UserRegisterDto dto) {
        log.info("signUp(dto = {}) POST", dto);
        
        userService.registerUser(dto);
       
        
        return "redirect:/"; // 회원가입 성공 후 이동(redirect)
    } 
    
}