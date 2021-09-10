package br.com.biblioteca.domains.security.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserFormDTO {

    private String name;
    private String email;
    private String password;

}
