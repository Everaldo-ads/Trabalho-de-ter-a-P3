package src.repositories.livrosEmprestados;

import java.time.LocalDate;
import java.util.ArrayList;
import src.database.Database;
import src.entities.Livro;
import src.entities.LivrosEmprestados;
import src.repositories.livro.LivroRepo;
import src.repositories.user.UserRepo;
import src.repositories.user.ReturnTypes.UserGetType;
import src.repositories.livro.ReturnTypes.LivroGetType;
import src.repositories.livro.ParamTypes.LivroUpdateType;

/**
 * Repositório responsável pelo processamento de transações de empréstimos, devoluções e estoque.
 */
public class LivrosEmprestadosRepo {
    
    // Processa uma lista de empréstimos de livros em lote para um usuário
    public static void addEmprestimos(int userId, ArrayList<Integer> livrosId) {
        // 1. PRIMEIRO: Valida a integridade do usuário antes de iniciar o loop de transações
        if (!UserRepo.userExists(userId)) {
            System.out.println("Usuário com id " + userId + " não existe.");
            return;
        }
        UserGetType user = UserRepo.getUserById(userId);
        
        ArrayList<LivrosEmprestados> novosEmprestimos = new ArrayList<>();
        
        // 2. SEGUNDO: Inicia a varredura e aplicação de regras de negócio em lote para cada livro solicitado
        for (Integer livroId : livrosId) {
            // Regra A: Valida se o livro realmente existe
            if (!LivroRepo.livroExists(livroId)) {
                System.out.println("Livro com id " + livroId + " não existe.");
                return;
            }
            // Regra B: Valida a existência de estoque físico para empréstimo
            LivroGetType livro = LivroRepo.getLivroById(livroId);
            if (livro.copiasDisponiveis <= 0) {
                System.out.println("Livro com id " + livroId + " não tem cópias disponíveis.");
                return;
            }
            // Regra C: Impede o usuário de retirar múltiplos exemplares do mesmo título ao mesmo tempo
            for (LivrosEmprestados livroEmprestado : Database.livrosEmprestados) {
                if (livroEmprestado.livro_id == livroId && livroEmprestado.user_id == user.id && livroEmprestado.dataDevolucao == null) {
                    System.out.println("Livro com id " + livroId + " já está emprestado para o usuário.");
                    return;
                }
            }
            
            // 3. TERCEIRO: Consome uma cópia do acervo físico e salva o estado atualizado do estoque
            livro.copiasDisponiveis -= 1;
            LivroUpdateType livroUpdate = new LivroUpdateType();
            livroUpdate.copiasDisponiveis = livro.copiasDisponiveis;
            LivroRepo.updateLivro(livroId, livroUpdate);

            // 4. QUARTO: Instancia o registro definitivo de transação com a data atualizada
            LivrosEmprestados novoEmprestimo = new LivrosEmprestados();
            novoEmprestimo.user_id = user.id;
            novoEmprestimo.livro_id = livroId;
            novoEmprestimo.dataEmprestimo = LocalDate.now();
            novoEmprestimo.dataDevolucao = null;
            novosEmprestimos.add(novoEmprestimo);
        }
        
        // 5. QUINTO: Consolida os registros aprovados na tabela do banco de dados simulado
        Database.livrosEmprestados.addAll(novosEmprestimos);
    }

    // Processa a devolução em lote e efetua o reabastecimento do estoque físico do catálogo
    public static void devolverLivros(int userId, ArrayList<Integer> livrosId) {
        if (!UserRepo.userExists(userId)) {
            System.out.println("Usuário com id " + userId + " não existe.");
            return;
        }
        UserGetType user = UserRepo.getUserById(userId);
        
        for (Integer livroId : livrosId) {
            if (!LivroRepo.livroExists(livroId)) {
                System.out.println("Livro com id " + livroId + " não existe.");
                return;
            }
            
            // 1. PRIMEIRO: Localiza o empréstimo pendente e insere a data de devolução atual
            boolean livroFoiEmprestado = false;
            for (LivrosEmprestados livroEmprestado : Database.livrosEmprestados) {
                if (livroEmprestado.livro_id == livroId && livroEmprestado.user_id == user.id && livroEmprestado.dataDevolucao == null) {
                    livroEmprestado.dataDevolucao = LocalDate.now();
                    livroFoiEmprestado = true;
                    break;
                }
            }
            
            // 2. SEGUNDO: Barra o processo se o usuário tentar devolver um livro que não retirou
            if (!livroFoiEmprestado) {
                System.out.println("Livro com id " + livroId + " não está emprestado para o usuário.");
                return;
            }
            
            // 3. TERCEIRO: Devolve um exemplar ao estoque do acervo geral do catálogo
            LivroGetType livro = LivroRepo.getLivroById(livroId);
            livro.copiasDisponiveis += 1;
            LivroUpdateType livroUpdate = new LivroUpdateType();
            livroUpdate.copiasDisponiveis = livro.copiasDisponiveis;
            LivroRepo.updateLivro(livroId, livroUpdate);
        }
    }

    // Retorna a lista de todas as transações ativas (pendentes) vinculadas ao ID de um usuário
    public static ArrayList<LivrosEmprestados> getByUserId(int userId) {
        if (!UserRepo.userExists(userId)) {
            System.out.println("Usuário com id " + userId + " não existe.");
            return new ArrayList<>();
        }
        ArrayList<LivrosEmprestados> emprestimosDoUsuario = new ArrayList<>();
        for (LivrosEmprestados livroEmprestado : Database.livrosEmprestados) {
            if (livroEmprestado.user_id == userId && livroEmprestado.dataDevolucao == null) {
                emprestimosDoUsuario.add(livroEmprestado);
            }
        }
        return emprestimosDoUsuario;
    }

    // Método Utilitário: Converte um registro isolado de empréstimo em uma Entidade Livro correspondente
    public static Livro converterLivroEmprestadoToLivro(LivrosEmprestados livroEmprestado) {
        for (Livro livro : Database.livros) {
            if (livro.id == livroEmprestado.livro_id) {
                return livro;
            }
        }
        return null;
    }

    // Método Utilitário: Converte uma lista de empréstimos em uma lista de entidades de Livros
    public static ArrayList<Livro> converterLivrosEmprestadosToLivro(ArrayList<LivrosEmprestados> livrosEmprestados) {
        ArrayList<Livro> livros = new ArrayList<>();
        for (LivrosEmprestados livroEmprestado : livrosEmprestados) {
            Livro livro = converterLivroEmprestadoToLivro(livroEmprestado);
            if (livro != null) {
                livros.add(livro);
            }
        }
        return livros;
    }
}