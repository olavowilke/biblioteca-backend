package br.com.biblioteca.domains.security.user;

import br.com.biblioteca.domains.security.user.dto.UserFormDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public User criar(UserFormDTO userFormDTO) {
        User user = new User(userFormDTO);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmailIgnoreCase(email);
        return userOptional.orElseThrow(() -> new UsernameNotFoundException(email));
    }

}

