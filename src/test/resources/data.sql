INSERT INTO pauta(id, campo1, campo2, id_campo_texto, id_campo_numerico, id_campo_data, data_fechamento)
VALUES (1000, 'pauta1', 123, 'Texto', 999, '2025-06-02', '2025-12-02 18:14:48');

-- Inserindo pauta não aberta
INSERT INTO pauta(id, campo1, campo2, id_campo_texto, id_campo_numerico, id_campo_data)
VALUES (1001, 'pauta2', 124, 'Pauta não iniciada', 998, '2025-06-02');

-- Inserindo pauta fechada
INSERT INTO pauta(id, campo1, campo2, id_campo_texto, id_campo_numerico, id_campo_data, data_fechamento)
VALUES (1002, 'pauta3', 123, 'Pauta fechada', 997, '2025-05-02', '2025-05-02 18:14:48');

-- Associar um votante a uma pauta
INSERT INTO pauta_associados(id, id_associado)
VALUES (1000, '999');