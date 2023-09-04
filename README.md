# Sistema de Biblioteca Java

Este é um sistema de biblioteca Java que permite gerenciar leitores, autores, livros e empréstimos. O sistema foi desenvolvido com o objetivo de fornecer funcionalidades básicas para uma biblioteca, incluindo a capacidade de adicionar leitores, autores, livros, realizar empréstimos e listar informações sobre eles.

## Componentes do Código

O sistema é composto por várias partes inter-relacionadas:

### Conexão com o Banco de Dados

- **ConexaoBanco.java:** Este arquivo define uma classe para estabelecer a conexão com um banco de dados PostgreSQL.

### Classes de Modelo

- **Autor.java, Emprestimo.java, Leitor.java, Livro.java, Usuario.java:** Essas classes representam objetos de dados, como autores, empréstimos, leitores, livros e usuários. Elas definem a estrutura desses objetos.

### Serviço da Biblioteca

- **BibliotecaService.java:** Este arquivo define um serviço que realiza operações relacionadas à biblioteca, como criar tabelas no banco de dados, adicionar leitores, autores, livros e empréstimos, listar leitores, livros e empréstimos e buscar informações específicas.

### Aplicação Principal

- **Main.java:** Este é o componente principal da aplicação. Ele contém o método main que interage com o usuário por meio de um menu de console e utiliza as funcionalidades definidas no serviço da biblioteca para realizar operações no sistema.

## Funcionalidades Principais

- **Adicionar Leitor:** Permite adicionar novos leitores ao sistema com nome, CPF e ID.

- **Listar Leitores:** Lista todos os leitores cadastrados no sistema.

- **Adicionar Livro:** Permite adicionar novos livros ao sistema com título e nome do autor. Se o autor não existir, ele pode ser adicionado.

- **Listar Livros:** Lista todos os livros cadastrados no sistema, incluindo informações sobre o autor.

- **Realizar Empréstimo:** Permite realizar empréstimos de livros para leitores existentes.

- **Listar Empréstimos:** Lista todos os empréstimos realizados, incluindo informações sobre o leitor e o livro.

## Uso

1. Compile e execute o programa Java para iniciar a aplicação da biblioteca.
2. Siga as instruções do menu para executar várias operações relacionadas à biblioteca.
3. Certifique-se de que o banco de dados PostgreSQL esteja em execução e configurado corretamente com as credenciais apropriadas.

## Observações

- O código fornecido é uma estrutura básica para um sistema de biblioteca. Você pode expandi-lo e personalizá-lo de acordo com as necessidades específicas do seu projeto.
- Este README resume as funcionalidades principais e a estrutura geral do código fornecido. Se você tiver alguma informação adicional que deseja incluir ou se tiver alguma pergunta específica, por favor, me informe.
