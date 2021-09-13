INSERT INTO bibliotecaapi.tb_autor(id, nome, nacionalidade, data_nascimento) values
('3404b937-e7f9-49bc-8230-da5f2b8c41f0', 'Machado de Assis', 'Brasileiro', '1839-06-21'),
('411a5759-08b2-4b32-a9cf-a368a4e68e5c', 'Fiódor Dostoiévski', 'Rússia', '1821-11-11'),
('bf5f2972-8d89-447d-8805-b8fea4051abd', 'Martin Fowler', 'Reino Unido', '1963-12-18') ON CONFLICT DO NOTHING;
