## API com Spring

Esta é uma API RESTful desenvolvida com **Spring Boot** e **Spring Security**, que implementa autenticação usando **JWT (JSON Web Tokens)**. A API é conectada a um banco de dados **MySQL** para persistência de dados de usuários e roles (papéis). A implementação visa demonstrar boas práticas de segurança e autenticação em uma aplicação moderna.

## Funcionalidades

- **Autenticação com JWT**: Usuários podem autenticar-se na API utilizando um token JWT.
- **Controle de Acesso com Spring Security**: Proteção de rotas por roles (papéis) definidos no sistema.
- **Persistência de Dados**: Utiliza o banco de dados MySQL para armazenar informações de usuários, roles e dados sensíveis.
- **Endpoints RESTful**: Seguindo as práticas de design de APIs REST.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para criar a aplicação backend.
- **Spring Security**: Para autenticação e controle de acesso.
- **JWT (JSON Web Tokens)**: Para autenticação stateless e troca de tokens entre cliente e servidor.
- **MySQL**: Banco de dados relacional para persistência.
- **Maven**: Gerenciamento de dependências e construção do projeto.

---
