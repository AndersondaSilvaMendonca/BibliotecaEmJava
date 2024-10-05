Projeto Biblíoteca feito em Java, usando Mysql 

Com crição do banco de dados abaixo e as tabelas para o correto funcionandomento:
CREATE DATABASE biblioteca;
USE biblioteca;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('admin', 'comum') NOT NULL,
    nome VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE livros (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nome_autor VARCHAR(100) NOT NULL,
    nome_livro VARCHAR(100) NOT NULL,
    ano_publicacao INT NOT NULL,
    quantidade INT DEFAULT 0
);

Tela inicial quando executado:
=== Biblioteca ===
1. Cadastrar Usuário
2. Login
3. Sair
Escolha uma opção:

Tela após o login:
=== Biblioteca ===
1. Cadastrar Livro
2. Listar Livros
3. Pesquisar Livro
4. Excluir Livro
5. Excluir Usuário
6. Listar Usuários
7. Emprestar Livro
8. Devolver Livro
9. Sair
Escolha uma opção: 
