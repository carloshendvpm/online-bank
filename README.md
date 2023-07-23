# Banco Digital (Desafio di2win)

Este é um projeto simples que implementa uma API RESTful para simular algumas funcionalidades básicas de um banco digital. 

## Recursos

A API fornece os seguintes recursos:

- Gerenciamento de contas bancárias (criação, consulta de saldo, depósito, saque, bloqueio, extrato).
- Implementa uma regra de negócio que limita o valor diário de saques.
- Permite consulta de extrato por períodos específicos.

## Tecnologias Utilizadas

Este projeto foi desenvolvido utilizando as seguintes tecnologias:

- **Java 17**: Linguagem de programação utilizada para o desenvolvimento do projeto.
- **Spring Boot**: Framework usado para facilitar o setup e desenvolvimento de aplicações Spring.
- **Spring Data JPA**: Usado para persistência de dados e facilitar operações de CRUD.
- **Lombok**: Biblioteca Java que ajuda a reduzir a verbosidade do código.
- **Maven**: Ferramenta de gerenciamento de dependências.
- **Swagger**: Ferramenta para documentação e teste de APIs.
- **Docker** : Ferramente para rodar a API dentro de um container.

## Instalação e Execução

Para executar este projeto localmente, você precisa ter instalado em sua máquina o Java 11 e o Maven.

Siga os seguintes passos:

1. Clone o repositório para sua máquina local usando `git clone https://github.com/seu-usuario/banco-digital.git`
2. Navegue até o diretório do projeto, por exemplo: `cd banco-digital`
3. Execute o comando `mvn clean install` para fazer o build do projeto
4. Execute o comando `mvn spring-boot:run` para iniciar a aplicação
5. A aplicação estará disponível em `http://localhost:8080`

## API Endpoints

Os endpoints da API estão documentados através do Swagger e podem ser acessados em `http://localhost:8080/swagger-ui.html` quando a aplicação estiver rodando.
![image](https://github.com/carloshendvpm/online-bank/assets/80500801/703322aa-5a28-4fc2-b5dc-d755cf84500c)



