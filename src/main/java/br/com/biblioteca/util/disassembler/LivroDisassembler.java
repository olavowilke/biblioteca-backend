package br.com.biblioteca.util.disassembler;

import br.com.biblioteca.domains.livro.Livro;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LivroDisassembler {

    public static List<UUID> mapToIds(List<Livro> livros) {
        return livros.stream()
                .map(Livro::getId)
                .distinct()
                .collect(Collectors.toList());
    }

}
