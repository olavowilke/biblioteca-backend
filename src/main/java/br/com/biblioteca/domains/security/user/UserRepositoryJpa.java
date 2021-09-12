package br.com.biblioteca.domains.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryJpa extends JpaRepository<User, UUID> {

    @Query("SELECT user FROM User user " +
            "LEFT JOIN FETCH user.authorities authority " +
            "WHERE UPPER(user.email) LIKE UPPER(:email)")
    Optional<User> findByEmailIgnoreCase(String email);

}
