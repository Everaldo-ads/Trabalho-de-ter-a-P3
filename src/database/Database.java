package src.database;

import src.entities.*;
import java.util.ArrayList;

/**
 * Simulação de Banco de Dados Relacional em Memória.
 * Utiliza tabelas estruturadas com listas estáticas acessíveis globalmente pela aplicação.
 */
public class Database {
    // Tabela simulada que armazena o catálogo completo de livros
    public static ArrayList<Livro> livros = new ArrayList<>();
    
    // Tabela simulada que armazena os registros cadastrais de clientes do sistema
    public static ArrayList<User> users = new ArrayList<>();
    
    // Tabela transacional simulada que registra os históricos ativos e inativos de empréstimos
    public static ArrayList<LivrosEmprestados> livrosEmprestados = new ArrayList<>();
}