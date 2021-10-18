# Spring-Project

Cada portador de cartão (cliente) possui uma conta com seus dados.
A cada operação realizada pelo cliente uma transação é criada e associada à sua
respectiva conta.
Cada transação possui um tipo (compra a vista, compra parcelada, saque ou pagamento),
um valor e uma data de criação.
Transações de tipo compra e saque são registradas com valor negativo, enquanto
transações de pagamento são registradas com valor positivo.

## Tecnologias utilizadas

O projeto foi construído utilizando as seguintes dependências:

### Backend
* [Java](https://java.com/en/download/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Lombok](https://projectlombok.org)
* [Swagger2](https://swagger.io/)
* [H2 batabase](http://www.h2database.com/html/main.html)
* [PostgreSQL](https://www.postgresql.org/download/)
* [Maven](https://maven.apache.org/)
* [Docker](https://www.docker.com/)
 
 
## Arquitetura utilizada: Clean Code
Introdução. Arquitetura Limpa (Clean Architecture) é um padrão arquitetural proposto por Robert Martin – mais conhecido como Uncle Bob – com o objetivo de promover a implementação de sistemas que favorecem reusabilidade de código, coesão, independência de tecnologia e testabilidade.

![image](https://user-images.githubusercontent.com/76417013/137752644-bce899c3-e878-4ec1-9db0-4ce1054738f8.png)

## Executando a aplicação
1. Como pré-requisito, possuir [docker](https://www.docker.com/) e [docker-compose].
2. Baixar o projeto, acessar a pasta spring-project e executar o comando docker-compose up -d 
> Será baixada a imagem do banco de dados e da aplicação, onde a mesma ficará disponível na porta 8080.
> Importante: A imagem do banco de dados que o docker irá iniciar utiliza a porta 5432, fica imprescindível ter esta porta disponível ao iniciar a orquestragem dos containers.

## Documentação 
* [Documentação-Swagger2 [LOCAL]](http://localhost:8080/swagger-ui.html)
>![image](https://user-images.githubusercontent.com/76417013/137751951-cc9a3ec6-83cc-4884-baa6-01d3a8cfc3f6.png)

## Estrutura de Dados
![image](https://user-images.githubusercontent.com/76417013/137752221-3fd3475a-a79d-42cb-b92f-49e8b601c1d5.png)


