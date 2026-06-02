package src.repositories.user;

import java.util.ArrayList;
import src.database.Database;
import src.entities.*;
import src.repositories.livrosEmprestados.LivrosEmprestadosRepo;
import src.repositories.livrosEmprestados.ReturnTypes.LivrosEmprestadosGetType;

/**
 * Repositório responsável pela persistência e manipulação dos dados de usuários.
 */
public class UserRepo {

    // Método para verificar se um usuário existe na base de dados pelo ID
    public static boolean userExists(int id) {
        for (User user : Database.users) {
            if (user.id == id) {
                return true;
            }
        }
        return false;
    }

    // Método para cadastrar um novo usuário no sistema
    public static ReturnTypes.UserAddType addUser(ParamTypes.UserAddType user) {
        // 1. PRIMEIRO: Instancia a entidade principal do banco e gera o próximo ID sequencial
        User newUser = new User();
        newUser.id = Database.users.size() + 1;
        newUser.nome = user.nome;
        
        // 2. SEGUNDO: Salva o novo usuário na lista oficial do banco de dados simulado
        Database.users.add(newUser);
        
        // 3. TERCEIRO: Cria e preenche o DTO de retorno para enviar os dados de forma segura à tela
        ReturnTypes.UserAddType userDTO = new ReturnTypes.UserAddType();
        userDTO.id = newUser.id;
        userDTO.nome = newUser.nome;
        userDTO.livrosEmprestados = new ArrayList<>();
        
        return userDTO;
    }

    // Método para buscar um usuário pelo ID trazendo o histórico de empréstimos
    public static ReturnTypes.UserGetType getUserById(int id) {
        for (User user : Database.users) {
            if (user.id == id) {
                // 1. PRIMEIRO: Instancia o DTO de exibição e preenche os dados básicos do usuário
                ReturnTypes.UserGetType userDTO = new ReturnTypes.UserGetType();
                userDTO.id = user.id;
                userDTO.nome = user.nome;
                userDTO.livrosEmprestados = new ArrayList<>();
                
                // 2. SEGUNDO: Varre o histórico global de empréstimos para encontrar os itens deste usuário
                for (LivrosEmprestados livroEmprestado : Database.livrosEmprestados) {
                    if (livroEmprestado.user_id == user.id) {

                        // Passo A: Converte o registro de empréstimo em uma entidade de Livro completa
                        Livro livro = LivrosEmprestadosRepo.converterLivroEmprestadoToLivro(livroEmprestado);

                        // Passo B: Empacota os dados do empréstimo e o livro dentro do DTO de exibição
                        LivrosEmprestadosGetType livroEmprestadoDTO = new LivrosEmprestadosGetType();
                        livroEmprestadoDTO.user_id = id;
                        livroEmprestadoDTO.livro = livro;
                        livroEmprestadoDTO.dataEmprestimo = livroEmprestado.dataEmprestimo;
                        livroEmprestadoDTO.dataDevolucao = livroEmprestado.dataDevolucao;

                        // Passo C: Adiciona o item na lista de livros associados ao perfil do usuário
                        userDTO.livrosEmprestados.add(livroEmprestadoDTO);
                    }
                }
                return userDTO;
            }
        }
        return null;
    }

    // Método para remover um usuário do banco de dados pelo ID
    public static void deleteUserById(int id) {
        for (User user : Database.users) {
            if (user.id == id) {
                Database.users.remove(user);
                break;
            }
        }
    }

    // Método para atualizar os dados cadastrais e empréstimos em lote do usuário
    public static ReturnTypes.UserUpdateType updateUser(int id, ParamTypes.UserUpdateType user) {
        for (User u : Database.users) {
            if (u.id == id) {
                // 1. PRIMEIRO: Verifica e processa novos lotes de empréstimos solicitados
                if (user.novosLivrosEmprestados != null) {
                    LivrosEmprestadosRepo.addEmprestimos(id, user.novosLivrosEmprestados);
                }
                // 2. SEGUNDO: Verifica e processa lotes de devoluções solicitadas
                if (user.livrosDevolvidos != null) {
                    LivrosEmprestadosRepo.devolverLivros(id, user.livrosDevolvidos);
                }
                // 3. TERCEIRO: Atualiza as informações cadastrais básicas (Nome) se enviadas
                if (user.nome != null) {
                    u.nome = user.nome;
                }
                
                // 4. QUARTO: Reconstrói o DTO de retorno com a situação atualizada do usuário
                ReturnTypes.UserUpdateType userDTO = new ReturnTypes.UserUpdateType();
                userDTO.livrosEmprestados = new ArrayList<>();
                ArrayList<LivrosEmprestados> livrosEmprestados = LivrosEmprestadosRepo.getByUserId(id);

                for (LivrosEmprestados livroEmprestado : livrosEmprestados) {
                    Livro livro = LivrosEmprestadosRepo.converterLivroEmprestadoToLivro(livroEmprestado);

                    LivrosEmprestadosGetType livroEmprestadoDTO = new LivrosEmprestadosGetType();
                    livroEmprestadoDTO.user_id = id;
                    livroEmprestadoDTO.livro = livro;
                    livroEmprestadoDTO.dataEmprestimo = livroEmprestado.dataEmprestimo;
                    livroEmprestadoDTO.dataDevolucao = livroEmprestado.dataDevolucao;

                    userDTO.livrosEmprestados.add(livroEmprestadoDTO);
                }

                userDTO.id = u.id;
                userDTO.nome = u.nome;
                return userDTO;
            }
        }
        return null;
    }
}