package site.book.project.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.book.project.domain.User;
import site.book.project.dto.UserSecurityDto;
import site.book.project.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
        Optional<User> entity = userRepository.findByUsername(username);
        if (entity.isPresent()) {
            return UserSecurityDto.fromEntity(entity.get());
        } else {
            throw new UsernameNotFoundException(username + ": not found!");
        }
    }
    
}
