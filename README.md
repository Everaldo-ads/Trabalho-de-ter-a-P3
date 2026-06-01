# Sistema de Gerenciamento de Biblioteca

## Descrição

O projeto atual é constituído por um sistema de
gerenciamento de uma biblioteca, funcionando por terminal, onde os usuários podem cadastrar 
livros, realizar empréstimos e devoluções, e consultar informações sobre os livros disponíveis.

## Estrutura

* `src` -> Contém o código principal do aplicativo.
    * `database` -> Contém classes responsáveis por armazernar as informações de 
    Usuário e Livro.
    * `entities` -> Contém classes que representam as entidades do sistema.
    * `repositories` -> Contém classes responsáveis por acessar os dados armazenados no banco.

## Documentos Relacionados

[Documento de Requisitos](docs/Documento_de_Requisitos.pdf)
[Diagrama de Classes](docs/Diagrama_de_Classes.pdf)



# 📖 Manual do Usuário — Sistema de Gerenciamento de Biblioteca

Este manual instrui o operador sobre como navegar e utilizar todas as funcionalidades do Sistema de Gerenciamento de Biblioteca. O software funciona inteiramente via linha de comando (console) e utiliza identificadores numéricos (IDs) para associar usuários, livros e movimentações de estoque.

---

## 🖥️ Inicialização e Navegação Geral

Ao iniciar o sistema, o **Menu Principal** será renderizado na tela. Para acionar qualquer funcionalidade, o operador deve:

1. Digitar o **número** correspondente à opção desejada.
2. Pressionar a tecla **Enter**.

> ⚠️ **Regra Importante de Entrada:** Sempre que o sistema solicitar um ID ou Quantidade, digite **apenas números inteiros**. O envio de letras ou caracteres especiais em campos numéricos causará a interrupção imediata do programa.

---

## 📋 Tabela de Referência Rápida do Menu

| Opção | Funcionalidade | Descrição Básica |
| :---: | :--- | :--- |
| **1** | Cadastrar Livro | Insere um novo título no acervo com estoque inicial. |
| **2** | Cadastrar Usuário | Registra um novo leitor no sistema. |
| **3** | Realizar Empréstimo de Livros | Registra a saída de um ou mais livros para um usuário. |
| **4** | Realizar Devolução de Livros | Dá baixa em livros emprestados e os devolve ao estoque. |
| **5** | Consultar Livro por ID | Exibe os detalhes completos de um livro específico. |
| **6** | Consultar Usuário por ID | Mostra os dados do leitor e seu histórico de empréstimos. |
| **7** | Listar Todos os Livros Cadastrados | Exibe todo o catálogo cadastrado no sistema. |
| **0** | Sair do Sistema | Encerra a execução do programa com segurança. |

---

## 🛠️ Passo a Passo das Funcionalidades

### 1. Cadastrar Livro
Permite incluir uma nova obra literária na base de dados. O ID do livro é gerado automaticamente de forma sequencial pelo sistema.

* **Como usar:** Selecione a opção `1`. O sistema solicitará consecutivamente:
  * **Título do Livro** *(Ex: O Alquimista)*
  * **Autor do Livro** *(Ex: Paulo Coelho)*
  * **Gênero** *(Ex: Drama / Filosofia)*
  * **Quantidade de Cópias Disponíveis** *(Ex: 5)*
* **Resultado:** O sistema exibirá a mensagem de confirmação juntamente com o ID gerado para aquele livro (guarde este número para consultas e empréstimos).

---

### 2. Cadastrar Usuário
Registra o cliente ou leitor que utilizará os serviços da biblioteca. O ID também é gerado automaticamente.

* **Como usar:** Selecione a opção `2` e digite o **Nome do Usuário**.
* **Resultado:** Mensagem de sucesso na tela exibindo o ID do usuário cadastrado.

---

### 3. Realizar Empréstimo de Livros
Esta funcionalidade realiza saídas **em lote**, o que significa que você pode incluir múltiplos livros em um único atendimento.

* **Como usar:** 
  1. Escolha a opção `3` e informe o **ID do Usuário**.
  2. Se o usuário existir, o sistema pedirá o **ID do Livro**.
  3. Após informar o ID do livro, o sistema perguntará: `Deseja incluir mais um livro neste empréstimo? (S/N)`.
  4. Digite `S` para adicionar outro livro ou `N` para fechar o pacote e concluir o empréstimo.
* **🛑 Regras de Negócio Automáticas:** O sistema bloqueará o empréstimo se:
  * O usuário ou o livro não estiverem cadastrados.
  * O livro escolhido estiver com o estoque zerado (0 cópias).
  * O usuário tentar pegar um livro que ele já possui atualmente (empréstimo pendente).

---

### 4. Realizar Devolução de Livros
Dá baixa nas pendências do usuário e devolve os exemplares ao estoque do catálogo automaticamente. Também funciona em lote.

* **Como usar:** Selecione a opção `4`, informe o **ID do Usuário** e, em seguida, digite o **ID do Livro** que está sendo devolvido. Confirme se há mais livros a devolver digitando `S` ou `N`.
* **Resultado:** O estoque do livro é reabastecido em `+1` para cada exemplar devolvido.

---

### 5. Consultar Livro por ID
Busca rápida para verificar informações ou quantidade em estoque de uma obra.

* **Como usar:** Selecione a opção `5` e digite o **ID do Livro**.
* **Resultado:** O console imprimirá o Título, Autor, Gênero e o número exato de cópias físicas disponíveis no momento.

---

### 6. Consultar Usuário por ID
Esta é a ferramenta de auditoria do operador. Ela exibe o perfil do cliente e o status temporal de suas leituras.

* **Como usar:** Selecione a opção `6` e digite o **ID do Usuário**.
* **Resultado:** O sistema listará o nome do usuário e um relatório detalhado de seus livros com os seguintes status:
  * `PENDENTE (Com o usuário)`: Livro ainda está em posse do leitor.
  * `DEVOLVIDO em [Data]`: Registro histórico de que o livro já retornou à biblioteca.

---

### 7. Listar Todos os Livros Cadastrados
Gera um relatório completo e panorâmico de todo o acervo da biblioteca.

* **Como usar:** Selecione a opção `7`.
* **Resultado:** Uma listagem sequencial exibindo ID, Título, Autor e Estoque de cada livro existente no banco de dados simulado.
