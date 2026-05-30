package src.repositories.livro;
import src.entities.Livro;

public class ParamTypes {

    public static class LivroAddType {
        public String titulo;
        public String autor;
        public String genero;
        public int copiasDisponiveis;
    }

    public static class LivroUpdateType {
        public String titulo;
        public String autor;
        public String genero;
        public Integer copiasDisponiveis;
    }
    
}
