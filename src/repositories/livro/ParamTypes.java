package src.repositories.livro;

/**
 * Encapsula os pacotes de parâmetros (DTOs) aceitos pelo repositório para operações com livros.
 */
public class ParamTypes {

    // Dados obrigatórios para realizar a inserção de um livro no sistema
    public static class LivroAddType {
        public String titulo;
        public String autor;
        public String genero;
        public int copiasDisponiveis;
    }

    // Dados aceitos de forma opcional para efetuar atualizações parciais do acervo
    public static class LivroUpdateType {
        public String titulo;
        public String autor;
        public String genero;
        public Integer copiasDisponiveis;
    }
}
