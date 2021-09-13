--Clientes inserts
INSERT INTO tb_telefone(id, ddd, numero, tipo, created_at, updated_at, deleted_at) values
('66e3ad4c-dc01-4908-aeec-7ea73ecd3315', '43', '998452534', 'CELULAR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),
('2fc644fb-c098-49f1-a491-76cc6de92f6e', '44', '994856142', 'CELULAR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),
('c58edfd3-3ac9-43bf-8947-92dd84189328', '11', '994564854', 'CELULAR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL) ON CONFLICT DO NOTHING;

INSERT INTO tb_endereco(id, logradouro, numero, cep, cidade, estado, complemento, created_at, updated_at, deleted_at) values
('fa8c0a64-625c-4917-85e1-f32291a0d739', 'Rua Paranagua', '59', '86600167', 'Londrina', 'PR', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),
('356fc596-5587-4ecf-95a3-a178fc10f85f', 'Rua Jaburu', '584', '86707000', 'Arapongas', 'PR', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),
('3e6ecfc7-e7da-4eb2-9398-4b36337536a6', 'Rua Dianópolis', '1100', '13125-100', 'São Paulo', 'SP', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL) ON CONFLICT DO NOTHING;

INSERT INTO tb_cliente(id, nome, cpf, telefone_id, endereco_id, data_nascimento, created_at, updated_at, deleted_at) values
('0542a825-57f0-4fc9-ab97-fc652263cd97', 'Leonardo Arruda', '64367341046', '66e3ad4c-dc01-4908-aeec-7ea73ecd3315', 'fa8c0a64-625c-4917-85e1-f32291a0d739', '05-08-1998', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),
('3100be3a-9d80-423c-b16c-749ec749b5af', 'Victor Pietro', '02175467066', '2fc644fb-c098-49f1-a491-76cc6de92f6e', '356fc596-5587-4ecf-95a3-a178fc10f85f', '05-04-1995', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),
('29006e7b-4d54-404a-902b-87364a4a59e8', 'Olavo Wilke', '26912955088', 'c58edfd3-3ac9-43bf-8947-92dd84189328', '3e6ecfc7-e7da-4eb2-9398-4b36337536a6', '05-08-1996', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL) ON CONFLICT DO NOTHING;

--Livros inserts
INSERT INTO tb_editora(id, nome, created_at, updated_at, deleted_at) values
('f5e9621e-a57f-48c6-83c4-96426e9d2004', 'Tipografia Nacional', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
('508660f7-9a44-4060-a455-99e6db911089', 'José Olympio', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP, null),
('3a7f98bc-19c9-493a-9447-48ae49963d0b', 'NovaTec Editora', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null) ON CONFLICT DO NOTHING;

INSERT INTO tb_genero_literario(id, nome, created_at, updated_at, deleted_at) values
('1390a59e-1ef5-4f22-ae83-c9015bcd4e11', 'Romance', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
('58e7e02c-fbf8-4a5f-b650-ae8f1ada6cb9', 'Novela', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
('fe47c57a-80fd-430b-a9ae-6224290798a4', 'Técnico', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null) ON CONFLICT DO NOTHING;

INSERT INTO tb_livro(id, titulo, autor_id, data_publicacao, editora_id, genero_literario_id, isbn, created_at, updated_at, deleted_at) values
('3b792c7a-c9bc-4e0d-8834-f3414a4ed3ad', 'Memórias Póstumas de Braz Cubas', '3404b937-e7f9-49bc-8230-da5f2b8c41f0', '1981-04-02', 'f5e9621e-a57f-48c6-83c4-96426e9d2004', '1390a59e-1ef5-4f22-ae83-c9015bcd4e11', '123456789123', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),
('5882eecd-d5f0-4c78-8544-e8c778c1f229', 'Notas do Subterrâneo', '411a5759-08b2-4b32-a9cf-a368a4e68e5c', '1961-04-03','508660f7-9a44-4060-a455-99e6db911089', '58e7e02c-fbf8-4a5f-b650-ae8f1ada6cb9', '123456789124', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL ),
('79ecf4c4-bc61-4367-8ad4-10b60ab315ee', 'Refatoração', 'bf5f2972-8d89-447d-8805-b8fea4051abd', '1999-01-01', '3a7f98bc-19c9-493a-9447-48ae49963d0b', 'fe47c57a-80fd-430b-a9ae-6224290798a4',  '123456789125', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL) ON CONFLICT DO NOTHING;
