--inserindo autores
INSERT INTO bibliotecaapi.tb_autor(id, nome, nacionalidade, data_nascimento)
values ('3404b937-e7f9-49bc-8230-da5f2b8c41f0', 'Adolf Hitler', 'Austria', '1889-04-20'),
       ('411a5759-08b2-4b32-a9cf-a368a4e68e5c', 'Karl Marx', 'Alemanha', '1883-03-14'),
       ('bf5f2972-8d89-447d-8805-b8fea4051abd', 'Gregorio Duvivier', 'Brasil', '1986-04-11') ON CONFLICT DO NOTHING;
