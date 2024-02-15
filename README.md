# Projeto CRUD Realm

O Projeto CRUD Realm é uma aplicação desenvolvida para permitir que os usuários realizem operações básicas de CRUD (Create, Read, Update, Delete) em um banco de dados local utilizando o Realm. Com essa aplicação, os usuários podem inserir, visualizar, atualizar e excluir dados de maneira intuitiva e eficiente.Nele se pode cadastrar nome email, cpf/cnpj , endereço tendo validação de cnpj/cpf validos na hora de cadastro bem como validação de email e telefone validos. 

## Funcionalidades Principais

- **Inserir Dados:** Os usuários podem inserir novos dados por meio de um formulário de entrada.

- **Visualizar Dados:** Os dados armazenados podem ser visualizados em uma lista, apresentando as informações de forma organizada.

- **Atualizar Dados:** Os usuários podem atualizar as informações de um registro existente por meio de um formulário de edição.

- **Excluir Dados:** Os dados podem ser excluídos individualmente, proporcionando flexibilidade ao usuário para gerenciar seus registros.

- **Pesquisar Dados:** Existe um campo para pesquisa usuarios por nome, email ,cpf ou qualquer caractere presente na lista .

## Validações Implementadas

- **Validação de Email:** O aplicativo realiza validação de email para garantir que o formato do email inserido pelo usuário seja válido.

- **Validação de CPF:** Implementa validação de CPF para garantir que o número de CPF inserido seja válido de acordo com as regras estabelecidas.

- **Validação de Telefone:** Realiza validação de telefone para garantir que o número inserido pelo usuário esteja em um formato válido.

## Tecnologias Utilizadas

- **Arquitetura MVP (Model-View-Presenter):** O projeto adota a arquitetura MVP, separando claramente as responsabilidades de cada componente (Model, View, Presenter) para facilitar a manutenção e a escalabilidade do código.

- **Banco de Dados Realm:** Utiliza o Realm como banco de dados local para armazenamento e gerenciamento eficiente dos dados.

- **Kotlin:** A aplicação é desenvolvida em Kotlin, uma linguagem moderna e concisa que oferece recursos avançados de programação para o desenvolvimento de aplicativos Android.

## Boas Práticas de Desenvolvimento

- **Separação de Responsabilidades:** O projeto segue o princípio de separação de responsabilidades, garantindo que cada componente tenha uma função específica e bem definida.

- **Nomeação de Variáveis e Funções:** Utiliza nomes significativos para variáveis e funções, facilitando a compreensão do código por outros desenvolvedores.

- **Padrões de Codificação:** Adota padrões de codificação consistentes, seguindo as diretrizes da linguagem Kotlin e as práticas recomendadas pela comunidade.

- **Documentação Adequada:** O código é devidamente documentado, fornecendo informações úteis sobre o propósito e o funcionamento de cada componente.

- ## Algumas fotos do app

<table> 
  <tr>
    <td><img src="https://github.com/giuliano15/Crud-Realm-Kotlin/assets/12647380/46472993-86f2-4b8c-b725-cc7fdf0a0718" alt="Imagem 2"></td>
    <td><img src="https://github.com/giuliano15/Crud-Realm-Kotlin/assets/12647380/26fc9242-b775-475e-94a9-7a2a6fadfedf" alt="Imagem 2"></td>
    <td><img src="https://github.com/giuliano15/Crud-Realm-Kotlin/assets/12647380/1cbb59c5-8acc-43bf-99a6-c3825d73f76b" alt="Imagem 2"></td>
 </tr>
</table
![Screenshot_2024-02-14-20-47-28-1](https://github.com/giuliano15/Crud-Realm-Kotlin/assets/12647380/26fc9242-b775-475e-94a9-7a2a6fadfedf)
![Screenshot_2024-02-14-20-52-29-4](https://github.com/giuliano15/Crud-Realm-Kotlin/assets/12647380/1cbb59c5-8acc-43bf-99a6-c3825d73f76b)

## Como Usar

1. Clone o repositório em sua máquina local.
2. Abra o projeto em sua IDE favorita.
3. Compile e execute o aplicativo em um dispositivo ou emulador Android.

## Contribuição

Sinta-se à vontade para contribuir com o Projeto CRUD Realm! Siga estas etapas:

1. Faça um fork do projeto.
2. Crie uma nova branch com sua feature ou correção de bug: `git checkout -b minha-feature`
3. Faça commit de suas alterações: `git commit -m 'Adicionando nova feature'`
4. Faça push para a branch: `git push origin minha-feature`
5. Abra um Pull Request para que suas alterações sejam revisadas e mescladas.

## Licença

Este projeto está licenciado sob a licença [MIT](LICENSE.md) - veja o arquivo LICENSE.md para mais detalhes.

---

Este README.md apresenta o Projeto CRUD Realm de forma clara e organizada, destacando suas características principais, as tecnologias utilizadas, as validações implementadas e algumas boas práticas de desenvolvimento.
