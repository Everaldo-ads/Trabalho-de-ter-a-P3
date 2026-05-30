package src.repositories.user;
import java.util.ArrayList;
import src.entities.Livro;
import src.repositories.livro.LivroGetType;

public class UserGetType {
    public int id;
    public String nome;
    public ArrayList<LivroGetType> livrosEmprestados;
}
