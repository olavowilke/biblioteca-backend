CREATE TABLE tb_autor(
    id UUID PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    nacionalidade VARCHAR(50),
    data_nascimento DATE NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);