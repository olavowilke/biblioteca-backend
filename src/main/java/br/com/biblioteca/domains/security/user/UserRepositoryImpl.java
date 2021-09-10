package br.com.biblioteca.domains.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class UserRepositoryImpl implements UserRepository {

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Override
    public User save(User user) {
        return userRepositoryJpa.save(user);
    }

    @Override
    public Optional<User> findByEmailIgnoreCase(String email) {
        return Optional.empty();
    }

}
