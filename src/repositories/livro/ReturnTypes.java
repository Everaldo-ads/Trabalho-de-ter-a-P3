package src.repositories.livro;

import src.entities.Livro;

public class ReturnTypes {

    public static class LivroGetType extends Livro {
    }

    public static class LivroAddType extends LivroGetType {
    }

    public static class LivroUpdateType extends LivroGetType {
    }
}
