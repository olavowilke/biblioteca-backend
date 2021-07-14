INSERT INTO tb_historico_locacao(id, cliente_id, livro_id, data_locacao, data_prevista_devolucao, data_devolucao, situacao, updated_at) values
('0ae38660-8acf-4a08-94b2-dcc0b13ed725', '0542a825-57f0-4fc9-ab97-fc652263cd97', '3b792c7a-c9bc-4e0d-8834-f3414a4ed3ad', '2021-07-12', '2021-07-19', null, 'EM_ANDAMENTO',CURRENT_TIMESTAMP),
('d8f0fc72-21e1-42c7-8d84-af8768664878', '3100be3a-9d80-423c-b16c-749ec749b5af', '5882eecd-d5f0-4c78-8544-e8c778c1f229', '2021-07-12', '2021-07-19', null, 'EM_ANDAMENTO',CURRENT_TIMESTAMP),
('3237e1c6-1158-4e3d-a2ea-129c96bbf717', '29006e7b-4d54-404a-902b-87364a4a59e8', '79ecf4c4-bc61-4367-8ad4-10b60ab315ee', '2021-07-12', '2021-07-19', null, 'EM_ANDAMENTO',CURRENT_TIMESTAMP) ON CONFLICT DO NOTHING;

INSERT INTO tb_estoque(id, livro_id, quantidade_disponivel) values
('df75f98c-b70e-4a82-80ba-b34ffaa7afd8', '3b792c7a-c9bc-4e0d-8834-f3414a4ed3ad', 10),
('de51136c-341b-4693-b993-a58f85f697cc', '5882eecd-d5f0-4c78-8544-e8c778c1f229', 10),
('0bd6742f-4a0d-49a7-a64a-6babc1f36923', '79ecf4c4-bc61-4367-8ad4-10b60ab315ee', 10) ON CONFLICT DO NOTHING;