CREATE TYPE situacao_info AS ENUM (
    'EM_ANDAMENTO',
    'DEVOLUCAO_ATRASADA',
    'DEVOLVIDO',
    'DEVOLVIDO_COM_ATRASO'
);

CREATE TABLE tb_locacao
(
    id                      UUID PRIMARY KEY,
    cliente_id              UUID NOT NULL,
    data_locacao            TIMESTAMP,
    data_prevista_devolucao TIMESTAMP,
    data_devolucao          TIMESTAMP,
    situacao                situacao_info,
    updated_at              TIMESTAMP,
    FOREIGN KEY (cliente_id) references tb_cliente (id)
);

CREATE TABLE tb_locacao_livro
(
    locacao_id UUID,
    livro_id   UUID,
    FOREIGN KEY (locacao_id) references tb_locacao (id),
    FOREIGN KEY (livro_id) references tb_livro (id)
);

CREATE TABLE tb_estoque
(
    id                    UUID PRIMARY KEY,
    livro_id              UUID NOT NULL,
    quantidade_disponivel INT,
    FOREIGN KEY (livro_id) references tb_livro (id)
);