﻿
CREATE DATABASE sistema-biblioteca

CREATE TABLE Pessoa (
Nome VARCHAR(128),
Endereco VARCHAR(128),
Telefone VARCHAR(128),
Email VARCHAR(128),
CPF VARCHAR(128) PRIMARY KEY
)

CREATE TABLE Exemplar (
codISBN VARCHAR(128) PRIMARY KEY,
Titulo VARCHAR(128),
Autor VARCHAR(128),
Editora VARCHAR(128),
Disponivel BOOLEAN
)

CREATE TABLE Emprestimo (
DataEmprest DATE,
IDEmprest SERIAL PRIMARY KEY
)

CREATE TABLE pessoa_emp (
IDEmprest INTEGER,
CPF VARCHAR(128),
FOREIGN KEY(CPF) REFERENCES Pessoa (CPF) ON DELETE CASCADE,
FOREIGN KEY(IDEmprest) REFERENCES Emprestimo (IDEmprest) ON DELETE CASCADE
)

CREATE TABLE emprest_exemplar (
IDEmprest INTEGER,
codISBN VARCHAR(128),
FOREIGN KEY(IDEmprest) REFERENCES Emprestimo (IDEmprest) ON DELETE CASCADE,
FOREIGN KEY(codISBN) REFERENCES Exemplar (codISBN) ON DELETE CASCADE
)