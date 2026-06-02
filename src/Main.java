package src;

/**
 * Ponto de entrada da aplicação Java.
 * Responsável apenas por instanciar a classe principal do sistema e dar a partida.
 */
public class Main {
    public static void main(String[] args) {
        // Instancia a classe que gerencia a biblioteca
        Biblioteca biblioteca = new Biblioteca();
        
        // Inicializa o menu interativo do sistema
        biblioteca.iniciar();
    }
}