INSERT INTO tb_locacao(id, cliente_id, data_locacao, data_prevista_devolucao, data_devolucao, situacao, updated_at) values
('0ae38660-8acf-4a08-94b2-dcc0b13ed725', '0542a825-57f0-4fc9-ab97-fc652263cd97', '2021-07-12', '2021-07-19', null, 'EM_ANDAMENTO', CURRENT_TIMESTAMP),
('d8f0fc72-21e1-42c7-8d84-af8768664878', '3100be3a-9d80-423c-b16c-749ec749b5af', '2021-07-12', '2021-07-19', null, 'EM_ANDAMENTO', CURRENT_TIMESTAMP),
('3237e1c6-1158-4e3d-a2ea-129c96bbf717', '29006e7b-4d54-404a-902b-87364a4a59e8', '2021-07-12', '2021-07-19', null, 'EM_ANDAMENTO', CURRENT_TIMESTAMP) ON CONFLICT DO NOTHING;

INSERT INTO tb_locacao_livro(locacao_id, livro_id) values
('0ae38660-8acf-4a08-94b2-dcc0b13ed725', '3b792c7a-c9bc-4e0d-8834-f3414a4ed3ad'),
('0ae38660-8acf-4a08-94b2-dcc0b13ed725', '5882eecd-d5f0-4c78-8544-e8c778c1f229'),
('0ae38660-8acf-4a08-94b2-dcc0b13ed725', '79ecf4c4-bc61-4367-8ad4-10b60ab315ee'),
('d8f0fc72-21e1-42c7-8d84-af8768664878', '3b792c7a-c9bc-4e0d-8834-f3414a4ed3ad'),
('d8f0fc72-21e1-42c7-8d84-af8768664878', '5882eecd-d5f0-4c78-8544-e8c778c1f229'),
('3237e1c6-1158-4e3d-a2ea-129c96bbf717', '79ecf4c4-bc61-4367-8ad4-10b60ab315ee'),
('3237e1c6-1158-4e3d-a2ea-129c96bbf717', '79ecf4c4-bc61-4367-8ad4-10b60ab315ee') ON CONFLICT DO NOTHING;

INSERT INTO tb_estoque(id, livro_id, quantidade_disponivel) values
('24b04dfe-9135-4271-9fd4-b14a3eb806c4', '3b792c7a-c9bc-4e0d-8834-f3414a4ed3ad', 10),
('6c30e2ea-9a14-4d49-a7cf-894d993a639a', '5882eecd-d5f0-4c78-8544-e8c778c1f229', 10),
('7a79abfb-ec06-4f75-94b7-303cd9d0f7a5', '79ecf4c4-bc61-4367-8ad4-10b60ab315ee', 10) ON CONFLICT DO NOTHING;