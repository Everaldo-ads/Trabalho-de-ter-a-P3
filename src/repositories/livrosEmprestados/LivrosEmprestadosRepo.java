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

public class LivrosEmprestadosRepo {
    
    public static void addEmprestimos(int userId, ArrayList<Integer> livrosId) {
        if (!UserRepo.userExists(userId)) {
            System.out.println("Usuário com id " + userId + " não existe.");
            return;
        }
        UserGetType user = UserRepo.getUserById(userId);
        
        ArrayList<LivrosEmprestados> novosEmprestimos = new ArrayList<>();
        for (Integer livroId : livrosId) {
            if (!LivroRepo.livroExists(livroId)) {
                System.out.println("Livro com id " + livroId + " não existe.");
                return;
            }
            LivroGetType livro = LivroRepo.getLivroById(livroId);
            if (livro.copiasDisponiveis <= 0) {
                System.out.println("Livro com id " + livroId + " não tem cópias disponíveis.");
                return;
            }
            for (LivrosEmprestados livroEmprestado : Database.livrosEmprestados) {
                if (livroEmprestado.livro_id == livroId && livroEmprestado.user_id == user.id) {
                    System.out.println("Livro com id " + livroId + " já está emprestado para o usuário.");
                    return;
                }
            }
            livro.copiasDisponiveis -= 1;

            LivrosEmprestados novoEmprestimo = new LivrosEmprestados();
            novoEmprestimo.user_id = user.id;
            novoEmprestimo.livro_id = livroId;
            novoEmprestimo.dataEmprestimo = LocalDate.now();
            novoEmprestimo.dataDevolucao = null;
            novosEmprestimos.add(novoEmprestimo);
        }
        Database.livrosEmprestados.addAll(novosEmprestimos);
    }

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
            boolean livroFoiEmprestado = false;
            for (LivrosEmprestados livroEmprestado : Database.livrosEmprestados) {
                if (livroEmprestado.livro_id == livroId && livroEmprestado.user_id == user.id) {
                    livroEmprestado.dataDevolucao = LocalDate.now();
                    livroFoiEmprestado = true;
                    break;
                }
            }
            if (!livroFoiEmprestado) {
                System.out.println("Livro com id " + livroId + " não está emprestado para o usuário.");
                return;
            }
            LivroGetType livro = LivroRepo.getLivroById(livroId);
            livro.copiasDisponiveis += 1;
        }
    }

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

    public static Livro converterLivroEmprestadoToLivro(LivrosEmprestados livroEmprestado) {
        for (Livro livro : Database.livros) {
            if (livro.id == livroEmprestado.livro_id) {
                return livro;
            }
        }
        return null;
    }

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
