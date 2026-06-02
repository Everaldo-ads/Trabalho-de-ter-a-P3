package src.repositories.user;

import java.util.ArrayList;

public class ParamTypes {
    
    public static class UserAddType {
        public String nome;
    }

    public static class UserUpdateType {
        public String nome;
        public ArrayList<Integer> novosLivrosEmprestados;
        public ArrayList<Integer> livrosDevolvidos;
    }

    
}
