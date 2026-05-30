package src.repositories.livrosEmprestados;
import java.time.LocalDate;

import src.entities.Livro;

public class ReturnTypes {
    
    public static class LivrosEmprestadosGetType {
        public int user_id;
        public Livro livro;
        public LocalDate dataEmprestimo;
        public LocalDate dataDevolucao;
    }

}
