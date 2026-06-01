package src.repositories.livro;

import src.entities.Livro;

/**
 * Encapsula as classes de retorno estruturado (DTOs) despachadas pelo repositório de livros.
 */
public class ReturnTypes {

    // DTO genérico usado para retornar os dados de exibição de uma busca de livros
    public static class LivroGetType extends Livro {
    }

    // DTO retornado para sinalizar o sucesso e o ID final do livro registrado
    public static class LivroAddType extends LivroGetType {
    }

    // DTO retornado com o espelho das alterações consolidadas no acervo
    public static class LivroUpdateType extends LivroGetType {
    }
}