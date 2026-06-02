package src.entities;

import java.time.LocalDate;

/**
 * Entidade de Relacionamento que registra o vínculo transacional de histórico entre Usuários e Livros.
 */
public class LivrosEmprestados {
    public int user_id;
    public int livro_id;
    public LocalDate dataEmprestimo;
    public LocalDate dataDevolucao;
}
