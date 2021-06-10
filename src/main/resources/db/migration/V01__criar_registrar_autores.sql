CREATE TABLE bibliotecaapi.tb_autor(
    id UUID PRIMARY KEY,
    nome VARCHAR(50),
    nacionalidade VARCHAR(50),
    data_nascimento DATE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);