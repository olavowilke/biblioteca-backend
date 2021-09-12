--user inserts
INSERT INTO tb_user (id, name, email, password)
VALUES ('8d0d8540-7563-4b75-9bfd-691db0b58007', 'admin', 'admin@biblioteca.com',
        '$2a$12$7FEPnTtwKu9FxEjcoMFEb.yKltLugu26GURsT6cQD5UkeyVFbT3gW'),
       ('544055fe-f15c-4e0c-9686-0baefa71c801', 'user', 'user@biblioteca.com',
        '$2a$12$7FEPnTtwKu9FxEjcoMFEb.yKltLugu26GURsT6cQD5UkeyVFbT3gW') ON CONFLICT DO NOTHING;

--authority inserts
INSERT INTO tb_authority (id, code, description)
VALUES ('655dea4b-244b-4008-9204-b08930c1856a', 'LIBCAUTOR', 'ROLE_CREATE_AUTOR'),
       ('ae01e427-7e59-4814-9b5e-f8aab809ea1e', 'LIBRAUTOR', 'ROLE_READ_AUTOR'),
       ('6e774ba8-a1ac-4a8e-8228-b6f5cfea6f27', 'LIBCCLIENTE', 'ROLE_CREATE_CLIENTE'),
       ('68702fc8-e48b-4643-bbd1-0518735df74d', 'LIBRCLIENTE', 'ROLE_READ_CLIENTE'),
       ('e7e36b7d-e458-409e-9d0b-1e64ad91d6f0', 'LIBCEDITORA', 'ROLE_CREATE_EDITORA'),
       ('6b8ae6a9-27d1-4c7f-8c3f-632f0b555c1c', 'LIBREDITORA', 'ROLE_READ_EDITORA'),
       ('e1ac5ae7-7d74-4c80-acee-8a668b85a69b', 'LIBCGENLIT', 'ROLE_CREATE_GENLIT'),
       ('0f08c561-9920-4844-b22f-d8c7f9463305', 'LIBRGENLIT', 'ROLE_READ_GENLIT'),
       ('8d182f6f-18ec-4fcd-8998-898375e87b67', 'LIBCLIVRO', 'ROLE_CREATE_LIVRO'),
       ('13fd128e-d187-4ab8-81b9-fb047973a2aa', 'LIBRLIVRO', 'ROLE_READ_LIVRO'),
       ('00e9de25-b913-41ff-bed8-131ba1d031e3', 'LIBCLOCACAO', 'ROLE_CREATE_LOCACAO'),
       ('d7a006eb-6a9a-4d38-9acc-c6dd5bad4245', 'LIBRLOCACAO', 'ROLE_READ_LOCACAO') ON CONFLICT DO NOTHING;

INSERT INTO tb_user_authority (user_id, authority_id)
VALUES ('8d0d8540-7563-4b75-9bfd-691db0b58007', '655dea4b-244b-4008-9204-b08930c1856a'), --role_create_autor
       ('8d0d8540-7563-4b75-9bfd-691db0b58007', 'ae01e427-7e59-4814-9b5e-f8aab809ea1e'), --role_read_autor
       ('8d0d8540-7563-4b75-9bfd-691db0b58007', '6e774ba8-a1ac-4a8e-8228-b6f5cfea6f27'), --role_create_cliente
       ('8d0d8540-7563-4b75-9bfd-691db0b58007', '68702fc8-e48b-4643-bbd1-0518735df74d'), --role_read_cliente
       ('8d0d8540-7563-4b75-9bfd-691db0b58007', 'e7e36b7d-e458-409e-9d0b-1e64ad91d6f0'), --role_create_editora
       ('8d0d8540-7563-4b75-9bfd-691db0b58007', '6b8ae6a9-27d1-4c7f-8c3f-632f0b555c1c'), --role_read_editora
       ('8d0d8540-7563-4b75-9bfd-691db0b58007', 'e1ac5ae7-7d74-4c80-acee-8a668b85a69b'), --role_create_genlit
       ('8d0d8540-7563-4b75-9bfd-691db0b58007', '0f08c561-9920-4844-b22f-d8c7f9463305'), --role_read_genlit
       ('8d0d8540-7563-4b75-9bfd-691db0b58007', '8d182f6f-18ec-4fcd-8998-898375e87b67'), --role_create_livro
       ('8d0d8540-7563-4b75-9bfd-691db0b58007', '13fd128e-d187-4ab8-81b9-fb047973a2aa'), --role_read_livro
       ('8d0d8540-7563-4b75-9bfd-691db0b58007', '00e9de25-b913-41ff-bed8-131ba1d031e3'), --role_create_locacao
       ('8d0d8540-7563-4b75-9bfd-691db0b58007', 'd7a006eb-6a9a-4d38-9acc-c6dd5bad4245'), --role_read_locacao
       ('544055fe-f15c-4e0c-9686-0baefa71c801', 'ae01e427-7e59-4814-9b5e-f8aab809ea1e'), --role_read_autor
       ('544055fe-f15c-4e0c-9686-0baefa71c801', '68702fc8-e48b-4643-bbd1-0518735df74d'), --role_read_cliente
       ('544055fe-f15c-4e0c-9686-0baefa71c801', '6b8ae6a9-27d1-4c7f-8c3f-632f0b555c1c'), --role_read_editora
       ('544055fe-f15c-4e0c-9686-0baefa71c801', '0f08c561-9920-4844-b22f-d8c7f9463305'), --role_read_genlit
       ('544055fe-f15c-4e0c-9686-0baefa71c801', '13fd128e-d187-4ab8-81b9-fb047973a2aa'), --role_read_livro
       ('544055fe-f15c-4e0c-9686-0baefa71c801', '00e9de25-b913-41ff-bed8-131ba1d031e3'), --role_create_locacao
       ('544055fe-f15c-4e0c-9686-0baefa71c801', 'd7a006eb-6a9a-4d38-9acc-c6dd5bad4245')  --role_read_locacao
    ON CONFLICT DO NOTHING;