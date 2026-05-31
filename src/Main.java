package src;

import src.database.Database;
import src.entities.*;
import src.repositories.livro.LivroRepo;
import src.repositories.user.UserRepo;
import src.repositories.livrosEmprestados.LivrosEmprestadosRepo;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Classe principal que gerencia a interface com o usuário via console.
 * Utiliza estruturas de repetição e decisão para navegar no menu do sistema.
 */
public class Main {
    public static void main(String[] args) {
        // Scanner para leitura de dados do teclado no console
        Scanner teclado = new Scanner(System.in);
        int opcao;

        // Estrutura de repetição do-while para manter o menu ativo até o usuário decidir sair
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
            
            opcao = teclado.nextInt();
            teclado.nextLine(); // LIMPEZA DE BUFFER: Evita que o Java pule a leitura de Strings nas próximas linhas

            // Estrutura condicional switch-case para direcionar a ação escolhida pelo usuário
            switch (opcao) {
                case 1:
                    System.out.println("\n--- CADASTRAR NOVO LIVRO ---");
                    
                    // 1. PRIMEIRO: Instancia o pacote de dados (DTO) para enviar ao repositório
                    src.repositories.livro.ParamTypes.LivroAddType novoLivro = new src.repositories.livro.ParamTypes.LivroAddType();
                    
                    // 2. SEGUNDO: Captura as informações de texto que o usuário digitar
                    System.out.print("Título do Livro: ");
                    novoLivro.titulo = teclado.nextLine();
                    
                    System.out.print("Autor do Livro: ");
                    novoLivro.autor = teclado.nextLine();
                    
                    System.out.print("Gênero: ");
                    novoLivro.genero = teclado.nextLine();
                    
                    System.out.print("Quantidade de Cópias Disponíveis: ");
                    novoLivro.copiasDisponiveis = teclado.nextInt();
                    teclado.nextLine(); // Limpeza de buffer após leitura de inteiro

                    // 3. TERCEIRO: Envia o pacote preenchido para o repositório salvar no banco de dados
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

                    // 3. TERCEIRO: Repassa o objeto para a classe de repositório cadastrar na lista
                    src.repositories.user.ReturnTypes.UserAddType usuarioSalvo = UserRepo.addUser(novoUsuario);
                    System.out.println(">> Usuário cadastrado com sucesso! ID gerado: " + usuarioSalvo.id);
                    break;

                case 3:
                    System.out.println("\n--- SOLICITAR EMPRÉSTIMO ---");
                    
                    // 1. PRIMEIRO: Coleta o ID de quem vai pegar o livro emprestado
                    System.out.print("Digite o ID do Usuário: ");
                    int idUserEmp = teclado.nextInt();
                    teclado.nextLine(); 

                    // 2. SEGUNDO: Valida se esse usuário realmente existe no sistema antes de continuar
                    if (!UserRepo.userExists(idUserEmp)) {
                        System.out.println(">> Erro: Usuário não encontrado!");
                        break;
                    }

                    // 3. TERCEIRO: Cria a lista vazia para acumular um ou mais livros no mesmo pedido
                    ArrayList<Integer> idsLivrosEmp = new ArrayList<>();
                    String respEmp;
                    do {
                        // Passo A: Faz a pergunta na tela para o usuário
                        System.out.print("Digite o ID do Livro que deseja pegar: ");
                        
                        // Passo B: Captura o ID correto, limpa o buffer e adiciona na lista dinâmica
                        int idLivro = teclado.nextInt();
                        teclado.nextLine(); 
                        idsLivrosEmp.add(idLivro);

                        System.out.print("Deseja incluir mais um livro neste empréstimo? (S/N): ");
                        respEmp = teclado.nextLine();
                    } while (respEmp.equalsIgnoreCase("S"));

                    // 4. QUARTO: Despacha a lista inteira de IDs de livros coletados para o processamento final
                    LivrosEmprestadosRepo.addEmprestimos(idUserEmp, idsLivrosEmp);
                    break;

                case 4:
                    System.out.println("\n--- REALIZAR DEVOLUÇÃO ---");
                    
                    // 1. PRIMEIRO: Identifica qual usuário está vindo devolver o material
                    System.out.print("Digite o ID do Usuário: ");
                    int idUserDev = teclado.nextInt();
                    teclado.nextLine();

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
                        int idLivro = teclado.nextInt();
                        teclado.nextLine();
                        idsLivrosDev.add(idLivro);

                        System.out.print("Deseja devolver mais um livro agora? (S/N): ");
                        respDev = teclado.nextLine();
                    } while (respDev.equalsIgnoreCase("S"));

                    // 4. QUARTO: Manda os dados para dar baixa nas pendências e devolver os itens ao estoque
                    LivrosEmprestadosRepo.devolverLivros(idUserDev, idsLivrosDev);
                    break;

                case 5:
                    System.out.println("\n--- CONSULTAR LIVRO ---");
                    
                    // 1. PRIMEIRO: Coleta o código identificador do livro procurado
                    System.out.print("Digite o ID do livro procurado: ");
                    int idLivroBusca = teclado.nextInt();
                    teclado.nextLine();

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
                    
                    // 1. PRIMEIRO: Solicita o ID do usuário que queremos inspecionar
                    System.out.print("Digite o ID do usuário procurado: ");
                    int idUserBusca = teclado.nextInt();
                    teclado.nextLine();

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