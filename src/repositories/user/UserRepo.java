package src.repositories.user;

import java.util.ArrayList;

import src.database.Database;
import src.entities.*;

import src.repositories.livrosEmprestados.LivrosEmprestadosRepo;

import src.repositories.livrosEmprestados.ReturnTypes.LivrosEmprestadosGetType;

public class UserRepo {

    public static boolean userExists(int id) {
        for (User user : Database.users) {
            if (user.id == id) {
                return true;
            }
        }
        return false;
    }

    public static ReturnTypes.UserAddType addUser(ParamTypes.UserAddType user) {
        User newUser = new User();
        newUser.id = Database.users.size() + 1;
        newUser.nome = user.nome;
        Database.users.add(newUser);
        ReturnTypes.UserAddType userDTO = new ReturnTypes.UserAddType();
        userDTO.id = newUser.id;
        userDTO.nome = newUser.nome;
        userDTO.livrosEmprestados = new ArrayList<>();
        return userDTO;
    }

    public static ReturnTypes.UserGetType getUserById(int id) {
        for (User user : Database.users) {
            if (user.id == id) {
                ReturnTypes.UserGetType userDTO = new ReturnTypes.UserGetType();
                userDTO.id = user.id;
                userDTO.nome = user.nome;
                userDTO.livrosEmprestados = new ArrayList<>();
                for (LivrosEmprestados livroEmprestado : Database.livrosEmprestados) {
                    if (livroEmprestado.user_id == user.id) {

                        Livro livro = LivrosEmprestadosRepo.converterLivroEmprestadoToLivro(
                            livroEmprestado
                        );

                        LivrosEmprestadosGetType livroEmprestadoDTO = new LivrosEmprestadosGetType();
                        livroEmprestadoDTO.user_id = id;
                        livroEmprestadoDTO.livro = livro;
                        livroEmprestadoDTO.dataEmprestimo = livroEmprestado.dataEmprestimo;
                        livroEmprestadoDTO.dataDevolucao = livroEmprestado.dataDevolucao;

                        userDTO.livrosEmprestados.add(livroEmprestadoDTO);
                    }
                }
                return userDTO;
            }
        }
        return null;
    }

    public static void deleteUserById(int id) {
        for (User user : Database.users) {
            if (user.id == id) {
                Database.users.remove(user);
                break;
            }
        }
    }


    public static ReturnTypes.UserUpdateType updateUser(int id, ParamTypes.UserUpdateType user) {
        for (User u : Database.users) {
            if (u.id == id) {
                if (user.novosLivrosEmprestados != null) {
                    LivrosEmprestadosRepo.addEmprestimos(id, user.novosLivrosEmprestados);
                }
                if (user.livrosDevolvidos != null) {
                    LivrosEmprestadosRepo.devolverLivros(id, user.livrosDevolvidos);
                }
                if (user.nome != null) {
                    u.nome = user.nome;
                }
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
