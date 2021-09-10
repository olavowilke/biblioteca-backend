package br.com.biblioteca.domains.security.user;

import br.com.biblioteca.domains.security.authority.Authority;
import br.com.biblioteca.domains.security.user.dto.UserFormDTO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Entity
@Table(name = "tb_user", schema = "bibliotecaapi")
public class User implements UserDetails {

    @Id
    private UUID id;
    private String name;
    private String email;
    private String password;

    @ManyToMany
    @JoinTable(name = "tb_user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private final Set<Authority> authorities = new HashSet<>();

    public User() {
        this.id = UUID.randomUUID();
    }

    public User(UserFormDTO userFormDTO) {
        this();
        this.name = userFormDTO.getName();
        this.email = userFormDTO.getEmail();
        this.password = userFormDTO.getPassword();
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getCode().toUpperCase()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
