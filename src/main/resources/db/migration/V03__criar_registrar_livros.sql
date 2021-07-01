CREATE TABLE tb_livro (
    id UUID PRIMARY KEY,
    titulo VARCHAR(50) NOT NULL,
    autor_id UUID NOT NULL,
    data_publicacao DATE,
    editora VARCHAR(50) NOT NULL,
    genero_literario VARCHAR(50),
    isbn VARCHAR(50),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    FOREIGN KEY(autor_id) references tb_autor(id)
);