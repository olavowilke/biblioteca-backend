CREATE TABLE tb_editora
(
    id         UUID PRIMARY KEY,
    nome       VARCHAR(50) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE tb_genero_literario
(
    id         UUID PRIMARY KEY,
    nome       VARCHAR(50) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE tb_livro
(
    id                  UUID PRIMARY KEY,
    titulo              VARCHAR(50) NOT NULL,
    autor_id            UUID        NOT NULL,
    data_publicacao     DATE,
    editora_id          UUID        NOT NULL,
    genero_literario_id UUID        NOT NULL,
    isbn                VARCHAR(12),
    created_at          TIMESTAMP,
    updated_at          TIMESTAMP,
    deleted_at          TIMESTAMP,
    FOREIGN KEY (autor_id) references tb_autor (id),
    FOREIGN KEY (editora_id) references tb_editora (id),
    FOREIGN KEY (genero_literario_id) references tb_genero_literario (id)
);


