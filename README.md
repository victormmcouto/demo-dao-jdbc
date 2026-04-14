# demo-dao-jdbc

Projeto Java para estudo do padrão de projeto DAO (Data Access Object) utilizando JDBC para comunicação direta com o banco de dados, sem frameworks ORM.

## Objetivo

Este projeto serve como referência de aprendizado para:

- Estabelecer e gerenciar conexões com banco de dados via JDBC
- Implementar o padrão DAO para separar a lógica de acesso a dados da lógica de negócio
- Manipular `PreparedStatement`, `ResultSet` e transações manualmente
- Configurar credenciais de banco de dados externamente via arquivo de propriedades

## Tecnologias

- Java 21
- JDBC (Java Database Connectivity)
- MySQL
- MySQL Connector/J (adicionado como User Library no Eclipse)
- Eclipse IDE

## Estrutura do Projeto

```
demo-dao-jdbc/
├── src/                  # Fontes Java (entidades, DAOs, interfaces, aplicação)
├── bin/                  # Classes compiladas (geradas pelo Eclipse)
├── db.properties         # Configurações de conexão com o banco de dados
├── .classpath            # Classpath do Eclipse (inclui MySQL Connector)
└── .project              # Descritor do projeto Eclipse
```

## Configuração

Os parâmetros de conexão são lidos do arquivo `db.properties` na raiz do projeto:

```properties
user=seu_usuario
password=sua_senha
dburl=jdbc:mysql://localhost:3306/seu_banco
useSSL=false
```

Atualize esses valores de acordo com sua instalação local do MySQL antes de executar a aplicação.

## Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/victormmcouto/demo-dao-jdbc.git
   ```

2. Importe o projeto no Eclipse (File > Import > Existing Projects into Workspace).

3. Adicione o JAR do MySQL Connector/J como uma User Library com o nome `MySQLConnector` (Project > Properties > Java Build Path > Libraries).

4. Edite o arquivo `db.properties` com suas credenciais e o nome do schema.

5. Execute a classe de aplicação desejada a partir do diretório `src`.

## Conceitos Abordados

- Ciclo de vida de `DriverManager` e `Connection`
- `PreparedStatement` para consultas parametrizadas seguras
- Iteração sobre `ResultSet` e mapeamento para objetos Java
- Interfaces DAO e suas implementações concretas com JDBC
- Tratamento de exceções e liberação de recursos com try-with-resources