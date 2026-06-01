package src.repositories.user;

import java.util.ArrayList;

/**
 * Encapsula as classes de DTO que determinam quais parâmetros entram no repositório vindos da tela.
 */
public class ParamTypes {
    
    // Objeto contendo apenas os dados obrigatórios para criação de usuário
    public static class UserAddType {
        public String nome;
    }

    // Objeto estruturado para receber requisições complexas de alteração cadastral em lote
    public static class UserUpdateType {
        public String nome;
        public ArrayList<Integer> novosLivrosEmprestados;
        public ArrayList<Integer> livrosDevolvidos;
    }
}