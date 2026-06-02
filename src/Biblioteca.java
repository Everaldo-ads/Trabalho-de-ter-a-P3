package src;

import src.database.Database;
import src.entities.*;
import src.repositories.livro.LivroRepo;
import src.repositories.user.UserRepo;
import src.repositories.livrosEmprestados.LivrosEmprestadosRepo;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Classe responsável por gerenciar a execução do sistema de biblioteca e a interface de console.
 */
public class Biblioteca {
    // Método principal que roda o menu e direciona as ações
    public void iniciar() {
        Scanner teclado = new Scanner(System.in);
        int opcao;

        // Estrutura de repetição do-while para manter o menu ativo até o usuário decidir sair (Sentinela: 0)
        do {
            System.out.println("\n===========================================");
            System.out.println("   SISTEMA DE GERENCIAMENTO DE BIBLIOTECA   ");
            System.out.println("===========================================");
            System.out.println("1. Cadastrar Livro");
            System.out.println("2. Cadastrar Usuário");
            System.out.println("3. Realizar Empréstimo de Livros");
            System.out.println("4. Realizar Devolução de Livros");
            System.out.println("5. Consultar Livro por ID");
            System.out.println("6. Consultar Usuário por ID");
            System.out.println("7. Listar Todos os Livros Cadastrados");
            System.out.println("0. Sair do Sistema");
            System.out.print("Escolha uma opção: ");
            
            // 1. PRIMEIRO: Aplica a boa prática do slide de capturar a entrada como String para higienização
            String opcaoInput = teclado.nextLine();
            
            // Regra de Segurança: Se a entrada não contiver apenas dígitos, define uma opção inválida para o switch
            if (!opcaoInput.matches("\\d+")) {
                opcao = -1;
            } else {
                opcao = Integer.parseInt(opcaoInput); // Efetua o Parser seguro da String para Inteiro
            }

            // Estrutura condicional switch-case para direcionar a ação escolhida pelo usuário
            switch (opcao) {
               case 1:
    System.out.println("\n--- CADASTRAR NOVO LIVRO ---");
    
    // 1. PRIMEIRO: Instancia o pacote de dados (DTO) para enviar ao repositório
    src.repositories.livro.ParamTypes.LivroAddType novoLivro = new src.repositories.livro.ParamTypes.LivroAddType();
    
    // 2. SEGUNDO: Captura e valida o Título do Livro
    System.out.print("Título do Livro: ");
    novoLivro.titulo = teclado.nextLine();
    
    while (novoLivro.titulo.trim().isEmpty()) {
        System.out.println(">> Erro: O título do livro é um campo obrigatório.");
        System.out.print("Por favor, digite um título válido: ");
        novoLivro.titulo = teclado.nextLine();
    }
    
    // 3. TERCEIRO: Captura e valida o Autor do Livro
    System.out.print("Autor do Livro: ");
    novoLivro.autor = teclado.nextLine();
    
    while (novoLivro.autor.trim().isEmpty()) {
        System.out.println(">> Erro: O autor do livro é um campo obrigatório.");
        System.out.print("Por favor, digite um autor válido: ");
        novoLivro.autor = teclado.nextLine();
    }
    
    // 4. QUARTO: Captura e valida o Gênero do Livro
    System.out.print("Gênero: ");
    novoLivro.genero = teclado.nextLine();
    
    while (novoLivro.genero.trim().isEmpty()) {
        System.out.println(">> Erro: O gênero do livro é um campo obrigatório.");
        System.out.print("Por favor, digite um gênero válido: ");
        novoLivro.genero = teclado.nextLine();
    }
    
    // 5. QUINTO: Captura e valida a Quantidade de Cópias (Estoque)
    System.out.print("Quantidade de Cópias Disponíveis: ");
    String copiasInput = teclado.nextLine();
    
    while (!copiasInput.matches("\\d+")) {
        System.out.println(">> Erro: A quantidade de cópias deve ser um número inteiro positivo.");
        System.out.print("Por favor, digite uma quantidade válida: ");
        copiasInput = teclado.nextLine();
    }
    novoLivro.copiasDisponiveis = Integer.parseInt(copiasInput);

    // 6. SEXTO: Envia o pacote preenchido para o repositório salvar no banco de dados
    src.repositories.livro.ReturnTypes.LivroAddType livroSalvo = LivroRepo.addLivro(novoLivro);
    System.out.println(">> Livro cadastrado com sucesso! ID gerado: " + livroSalvo.id);
    break;

                case 2:
                    System.out.println("\n--- CADASTRAR NOVO USUÁRIO ---");
                    
                    // 1. PRIMEIRO: Instancia o pacote de parâmetros do usuário
                    src.repositories.user.ParamTypes.UserAddType novoUsuario = new src.repositories.user.ParamTypes.UserAddType();
                    
                    // 2. SEGUNDO: Coleta o nome do novo cliente
                    System.out.print("Nome do Usuário: ");
                    novoUsuario.nome = teclado.nextLine();

                    // VALIDAÇÃO COM WHILE: Só permite avançar se o operador digitar um nome válido
                    while (novoUsuario.nome.trim().isEmpty()) {
                        System.out.println(">> Erro: O nome do usuário não pode ficar em branco.");
                        System.out.print("Por favor, digite um nome válido: ");
                        novoUsuario.nome = teclado.nextLine();
                    }

                    // 3. TERCEIRO: Repassa o objeto para a classe de repositório cadastrar na lista
                    src.repositories.user.ReturnTypes.UserAddType usuarioSalvo = UserRepo.addUser(novoUsuario);
                    if (usuarioSalvo != null) {
                        System.out.println(">> Usuário cadastrado com sucesso! ID gerado: " + usuarioSalvo.id);
                    }
                    break;

                case 3:
                    System.out.println("\n--- SOLICITAR EMPRÉSTIMO ---");
                    
                    // 1. PRIMEIRO: Coleta o ID como String seguindo as recomendações de segurança
                    System.out.print("Digite o ID do Usuário: ");
                    String idUserEmpInput = teclado.nextLine();
                    
                    // Regra de Segurança: Valida se a entrada é estritamente numérica para evitar quebra do sistema
                    while (!idUserEmpInput.matches("\\d+")) {
                        System.out.println(">> Erro: O ID do usuário deve conter apenas números.");
                        System.out.print("Por favor, digite um ID válido: ");
                        idUserEmpInput = teclado.nextLine();
                    }
                    int idUserEmp = Integer.parseInt(idUserEmpInput); 

                    // 2. SEGUNDO: Valida se esse usuário realmente existe no sistema antes de continuar
                    if (!UserRepo.userExists(idUserEmp)) {
                        System.out.println(">> Erro: Usuário não encontrado!");
                        break;
                    }

                    // 3. TERCEIRO: Cria a lista vazia para acumular um ou mais livros no mesmo pedido
                    ArrayList<Integer> idsLivrosEmp = new ArrayList<>();
                    String respEmp;
                    do {
                        // Passo A: Solicita o ID do livro como String para higienização
                        System.out.print("Digite o ID do Livro que deseja pegar: ");
                        String idLivroInput = teclado.nextLine();
                        
                        // Passo B: Valida contra caracteres textuais antes de converter
                        while (!idLivroInput.matches("\\d+")) {
                            System.out.println(">> Erro: O ID do livro deve conter apenas números.");
                            System.out.print("Por favor, digite um ID válido: ");
                            idLivroInput = teclado.nextLine();
                        }
                        int idLivro = Integer.parseInt(idLivroInput);
                        idsLivrosEmp.add(idLivro);

                        System.out.print("Deseja incluir mais um livro neste empréstimo? (S/N): ");
                        respEmp = teclado.nextLine();
                    } while (respEmp.equalsIgnoreCase("S")); // Aplica a comparação de String do slide

                    // 4. QUARTO: Despacha a lista inteira de IDs de livros coletados para o processamento final
                    LivrosEmprestadosRepo.addEmprestimos(idUserEmp, idsLivrosEmp);
                    break;

                case 4:
                    System.out.println("\n--- REALIZAR DEVOLUÇÃO ---");
                    
                    // 1. PRIMEIRO: Identifica qual usuário está vindo devolver o material com captura higienizada
                    System.out.print("Digite o ID do Usuário: ");
                    String idUserDevInput = teclado.nextLine();
                    
                    while (!idUserDevInput.matches("\\d+")) {
                        System.out.println(">> Erro: O ID do usuário deve conter apenas números.");
                        System.out.print("Por favor, digite um ID válido: ");
                        idUserDevInput = teclado.nextLine();
                    }
                    int idUserDev = Integer.parseInt(idUserDevInput);

                    // 2. SEGUNDO: Valida a existência do usuário para evitar bugs de inconsistência
                    if (!UserRepo.userExists(idUserDev)) {
                        System.out.println(">> Erro: Usuário não encontrado!");
                        break;
                    }

                    // 3. TERCEIRO: Cria o lote dinâmico para receber as devoluções no console
                    ArrayList<Integer> idsLivrosDev = new ArrayList<>();
                    String respDev;
                    do {
                        System.out.print("Digite o ID do Livro que está sendo devolvido: ");
                        String idLivroDevInput = teclado.nextLine();
                        
                        while (!idLivroDevInput.matches("\\d+")) {
                            System.out.println(">> Erro: O ID do livro deve conter apenas números.");
                            System.out.print("Por favor, digite um ID válido: ");
                            idLivroDevInput = teclado.nextLine();
                        }
                        int idLivro = Integer.parseInt(idLivroDevInput);
                        idsLivrosDev.add(idLivro);

                        System.out.print("Deseja devolver mais um livro agora? (S/N): ");
                        respDev = teclado.nextLine();
                    } while (respDev.equalsIgnoreCase("S"));

                    // 4. QUARTO: Manda os dados para dar baixa nas pendências e devolver os itens ao estoque
                    LivrosEmprestadosRepo.devolverLivros(idUserDev, idsLivrosDev);
                    break;

                case 5:
                    System.out.println("\n--- CONSULTAR LIVRO ---");
                    
                    // 1. PRIMEIRO: Coleta o código identificador do livro procurado de forma segura
                    System.out.print("Digite o ID do livro procurado: ");
                    String idLivroBuscaInput = teclado.nextLine();
                    
                    while (!idLivroBuscaInput.matches("\\d+")) {
                        System.out.println(">> Erro: O ID do livro deve conter apenas números.");
                        System.out.print("Por favor, digite um ID válido: ");
                        idLivroBuscaInput = teclado.nextLine();
                    }
                    int idLivroBusca = Integer.parseInt(idLivroBuscaInput);

                    // 2. SEGUNDO: Solicita a busca ao repositório de livros
                    src.repositories.livro.ReturnTypes.LivroGetType livroAchado = LivroRepo.getLivroById(idLivroBusca);
                    
                    // 3. TERCEIRO: Se achar, mostra na tela. Se voltar nulo, avisa que não existe
                    if (livroAchado != null) {
                        System.out.println("ID: " + livroAchado.id);
                        System.out.println("Título: " + livroAchado.titulo);
                        System.out.println("Autor: " + livroAchado.autor);
                        System.out.println("Gênero: " + livroAchado.genero);
                        System.out.println("Cópias em Estoque: " + livroAchado.copiasDisponiveis);
                    } else {
                        System.out.println(">> Livro não encontrado no sistema.");
                    }
                    break;

                case 6:
                    System.out.println("\n--- CONSULTAR USUÁRIO ---");
                    
                    // 1. PRIMEIRO: Solicita o ID do usuário de forma protegida contra falhas textuais
                    System.out.print("Digite o ID do usuário procurado: ");
                    String idUserBuscaInput = teclado.nextLine();
                    
                    while (!idUserBuscaInput.matches("\\d+")) {
                        System.out.println(">> Erro: O ID do usuário deve conter apenas números.");
                        System.out.print("Por favor, digite um ID válido: ");
                        idUserBuscaInput = teclado.nextLine();
                    }
                    int idUserBusca = Integer.parseInt(idUserBuscaInput);

                    // 2. SEGUNDO: Busca as informações completas do perfil do usuário
                    src.repositories.user.ReturnTypes.UserGetType usuarioAchado = UserRepo.getUserById(idUserBusca);
                    
                    // 3. TERCEIRO: Se encontrar o cadastro, faz a impressão dos dados básicos e do histórico
                    if (usuarioAchado != null) {
                        System.out.println("ID: " + usuarioAchado.id);
                        System.out.println("Nome: " + usuarioAchado.nome);
                        System.out.println("--- Livros Associados (Histórico/Empréstimos) ---");
                        
                        // Valida se a lista de empréstimos está zerada antes de varrer
                        if (usuarioAchado.livrosEmprestados.isEmpty()) {
                            System.out.println("Nenhum registro de empréstimo.");
                        } else {
                            // Varre a lista de DTOs de empréstimos exibindo o status atual de cada item
                            for (src.repositories.livrosEmprestados.ReturnTypes.LivrosEmprestadosGetType emp : usuarioAchado.livrosEmprestados) {
                                String status = (emp.dataDevolucao == null) ? "PENDENTE (Com o usuário)" : "DEVOLVIDO em " + emp.dataDevolucao;
                                String tituloLivro = (emp.livro != null) ? emp.livro.titulo : "ID do Livro: " + emp.user_id;
                                System.out.println("- Livro: " + tituloLivro + " | Retirada: " + emp.dataEmprestimo + " | Status: " + status);
                            }
                        }
                    } else {
                        System.out.println(">> Usuário não encontrado no sistema.");
                    }
                    break;

               case 7:
                    System.out.println("\n--- TODOS OS LIVROS CADASTRADOS ---");
                    
                    // 1. PRIMEIRO: Verifica se há registros globais na base antes de tentar listar
                    if (Database.livros.isEmpty()) {
                        System.out.println("Nenhum livro no banco de dados.");
                    } else {
                        // 2. SEGUNDO: Varre e mostra a lista de livros cadastrados no sistema com seus detalhes básicos
                        for (Livro l : Database.livros) {
                            System.out.println("ID: " + l.id + " | Título: " + l.titulo + " | Autor: " + l.autor + " | Estoque: " + l.copiasDisponiveis);
                        }
                    }
                    break;

                case 0:
                    System.out.println("Encerrando o sistema de biblioteca");
                    break;

                default:
                    System.out.println(">> Opção inválida! Tente novamente.");
                    break;
            }

        } while (opcao != 0);

        teclado.close(); // Fecha o recurso do scanner para evitar Memory Leak
    }
}
