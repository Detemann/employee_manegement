-- Inserindo dois registros de funcionários na tabela FUNCIONARIO
INSERT INTO FUNCIONARIO (NOME, CPF, EMAIL, SALARIO_BRUTO, SALARIO_LIQUIDO)
VALUES ('João Silva', '12345678901', 'joao@email.com', 5000.00, 4000.00);

INSERT INTO FUNCIONARIO (NOME, CPF, EMAIL, SALARIO_BRUTO, SALARIO_LIQUIDO)
VALUES ('Maria Santos', '98765432101', 'maria@email.com', 6000.00, 4800.00);

-- Inserindo dois registros de departamentos na tabela DEPARTAMENTO
INSERT INTO DEPARTAMENTO (NOME, SIGLA)
VALUES ('Departamento de Vendas', 'VD');

INSERT INTO DEPARTAMENTO (NOME, SIGLA)
VALUES ('Departamento de TI', 'TI');