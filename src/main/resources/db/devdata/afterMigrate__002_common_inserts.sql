INSERT INTO bibliotecaapi.tb_livro(id, titulo, autor_id, data_publicacao, editora, genero_literario, isbn, created_at, updated_at, deleted_at) values
('3b792c7a-c9bc-4e0d-8834-f3414a4ed3ad', 'Mein Kempf', '3404b937-e7f9-49bc-8230-da5f2b8c41f0', '1925-07-18', 'Eher Verlag', 'Doido', 'testeisbn', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),
('5882eecd-d5f0-4c78-8544-e8c778c1f229', 'O Capital', '411a5759-08b2-4b32-a9cf-a368a4e68e5c', '1867-09-14','Editora', 'Comédia', 'testeisbn', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL ),
('79ecf4c4-bc61-4367-8ad4-10b60ab315ee', 'Como viver com testosterona baixa: um guia', 'bf5f2972-8d89-447d-8805-b8fea4051abd', '1969-03-08', 'LowT', 'Comédia',  'testeisbn', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL) ON CONFLICT DO NOTHING;

