package src.repositories.user;

import java.util.ArrayList;
import src.repositories.livrosEmprestados.ReturnTypes.LivrosEmprestadosGetType;;

/**
 * Encapsula as classes de DTO que determinam quais dados saem do repositório de usuários para a tela.
 */
public class ReturnTypes {
    
    // DTO padrão para retornar a estrutura completa de dados do usuário consultado
    public static class UserGetType {
        public int id;
        public String nome;
        public ArrayList<LivrosEmprestadosGetType> livrosEmprestados;
    }

    // DTO específico para confirmação de novo usuário cadastrado
    public static class UserAddType extends UserGetType {
    }

    // DTO específico para confirmação de alterações cadastrais efetuadas
    public static class UserUpdateType extends UserGetType {
    }
}