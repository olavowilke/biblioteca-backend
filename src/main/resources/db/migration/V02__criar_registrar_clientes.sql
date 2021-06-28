CREATE TYPE tipo_telefone_info AS ENUM (
    'FIXO',
    'CELULAR'
);

create table tb_telefone (
	id UUID primary key,
	ddd varchar(3),
	numero varchar(20),
	tipo tipo_telefone_info,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

create table tb_endereco (
	id UUID primary key,
	logradouro varchar(100),
	numero varchar(50),
	cep varchar(50),
	cidade varchar(50),
	estado varchar(2),
	complemento varchar(200),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

create table tb_cliente (
	id UUID primary key,
	nome varchar(100),
	cpf varchar(11),
	telefone_id UUID,
	endereco_id UUID,
	data_nascimento date,
	created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
	foreign key (telefone_id) references tb_telefone(id),
	foreign key (endereco_id) references tb_endereco(id)
);