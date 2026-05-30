package src.repositories.user;
import java.util.ArrayList;
import src.repositories.livrosEmprestados.ReturnTypes.LivrosEmprestadosGetType;;

public class ReturnTypes {
    
    public static class UserGetType {
        public int id;
        public String nome;
        public ArrayList<LivrosEmprestadosGetType> livrosEmprestados;
    }

    public static class UserAddType extends UserGetType {
    }

    public static class UserUpdateType extends UserGetType {
    }
}
