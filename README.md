# App Escola - Gerenciamento de Alunos e Professores

Aplicativo mobile desenvolvido em Java no Android Studio com o objetivo de gerenciar alunos e professores, permitindo cadastro, consulta e organização de dados de forma eficiente.

---

##  Funcionalidades

-  Cadastro de alunos  
-  Cadastro de professores  
-  Consulta de alunos (simples e dinâmica)  
-  Menu de navegação para alunos e professores  
-  Validação e formatação de dados (ex: CPF com máscara)  
-  Armazenamento local com banco de dados SQLite  

---

##  Tecnologias Utilizadas

- Java  
- Android Studio  
- SQLite (banco de dados local)  
- XML (layouts)  

---

##  Estrutura do Projeto

O projeto está organizado em camadas, incluindo:

- **Modelos**:  
  - `Aluno.java`  
  - `Professor.java`  

- **Banco de Dados**:  
  - `BancoDB.java`  
  - `AlunoDB.java`  
  - `ProfessorDB.java`  

- **Telas (Activities)**:  
  - Cadastro de alunos e professores  
  - Consulta de dados  
  - Menus de navegação  

- **Utilitários**:  
  - Máscara de CPF (`MascaraCPF.java`)  

---
