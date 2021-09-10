package br.com.biblioteca.domains.security.user;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByEmailIgnoreCase(String email);

}
