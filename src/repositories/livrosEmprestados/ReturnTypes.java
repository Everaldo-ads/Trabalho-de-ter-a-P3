package src.repositories.livrosEmprestados;

import java.time.LocalDate;
import src.entities.Livro;

/**
 * Contém as especificações de retorno estruturado (DTO) para transações financeiras de empréstimo.
 */
public class ReturnTypes {
    
    // Estrutura de transferência contendo informações completas do status da locação
    public static class LivrosEmprestadosGetType {
        public int user_id;
        public Livro livro;
        public LocalDate dataEmprestimo;
        public LocalDate dataDevolucao;
    }
}
