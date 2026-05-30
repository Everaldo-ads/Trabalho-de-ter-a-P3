package src.repositories.livro;
import src.entities.Livro;

public class ParamTypes {

    public static class LivroAddType {
        public String titulo;
        public int autor_id;
        public String genero;
        public int copiasDisponiveis;
    }

    public static class LivroUpdateType {
        public String titulo;
        public Integer autor_id;
        public String genero;
        public Integer copiasDisponiveis;
    }
    
}
