INSERT INTO tb_user (id, name, email, password) VALUES
('8d0d8540-7563-4b75-9bfd-691db0b58007', 'admin', 'admin@biblioteca.com', 'YmlibGlvdGVjYUAxMjM='),--password: biblioteca@123
('544055fe-f15c-4e0c-9686-0baefa71c801', 'user', 'user@biblioteca.com', 'YmlibGlvdGVjYUAxMjM=')--password: biblioteca@123
ON CONFLICT DO NOTHING;

INSERT INTO tb_authority (id, code, description) VALUES
('cbec6edc-2783-4c6d-8838-168e3f8db23d', 'LIBCCOL', 'ROLE_CREATE_LIVRO'),
('e09dab7a-5185-4214-b156-1aa6a7f387f3', 'LIBCCOL', 'ROLE_READ_LIVRO')
ON CONFLICT DO NOTHING;

INSERT INTO tb_user_authority (user_id, authority_id) VALUES
('8d0d8540-7563-4b75-9bfd-691db0b58007', 'cbec6edc-2783-4c6d-8838-168e3f8db23d'), --role_create_livro
('544055fe-f15c-4e0c-9686-0baefa71c801', 'e09dab7a-5185-4214-b156-1aa6a7f387f3') --role_read_livro
ON CONFLICT DO NOTHING;

