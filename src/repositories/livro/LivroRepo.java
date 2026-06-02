package src.repositories.livro;

import src.database.Database;
import src.entities.Livro;

/**
 * Repositório responsável pelas regras de inserção, busca e atualização de livros no acervo.
 */
public class LivroRepo {

    // Valida se o livro especificado existe na base oficial através do ID
    public static boolean livroExists(int id) {
        for (Livro livro : Database.livros) {
            if (livro.id == id) {
                return true;
            }
        }
        return false;
    }
    
    // Realiza o cadastro físico de um livro gerando IDs auto-incrementais protegidos
    public static ReturnTypes.LivroAddType addLivro(ParamTypes.LivroAddType livro) {
        // 1. PRIMEIRO: Executa a trava de segurança ternária para evitar erros em listas vazias (Index -1)
        int id = Database.livros.isEmpty() ? 1 : Database.livros.get(Database.livros.size() - 1).id + 1;

        // 2. SEGUNDO: Instancia a entidade de persistência e realiza a cópia dos atributos coletados
        Livro novoLivro = new Livro();
        novoLivro.id = id;
        novoLivro.titulo = livro.titulo;
        novoLivro.autor = livro.autor;
        novoLivro.genero = livro.genero;
        novoLivro.copiasDisponiveis = livro.copiasDisponiveis;
        Database.livros.add(novoLivro);

        // 3. TERCEIRO: Transfere os dados consolidados para o DTO de retorno seguro da operação
        ReturnTypes.LivroAddType livroDTO = new ReturnTypes.LivroAddType();
        livroDTO.id = id;
        livroDTO.titulo = livro.titulo;
        livroDTO.autor = livro.autor;
        livroDTO.genero = livro.genero;
        livroDTO.copiasDisponiveis = livro.copiasDisponiveis;
        return livroDTO;
    }

    // Busca as informações cadastrais de um livro específico na lista pelo ID
    public static ReturnTypes.LivroGetType getLivroById(int id) {
        for (Livro livro : Database.livros) {
            if (livro.id == id) {
                ReturnTypes.LivroGetType livroDTO = new ReturnTypes.LivroGetType();
                livroDTO.id = livro.id;
                livroDTO.titulo = livro.titulo;
                livroDTO.autor = livro.autor;
                livroDTO.genero = livro.genero;
                livroDTO.copiasDisponiveis = livro.copiasDisponiveis;
                return livroDTO;
            }
        }
        return null;
    }

    // Executa a remoção física de um livro do catálogo pelo seu identificador
    public static void deleteLivroById(int id) {
        for (Livro livro : Database.livros) {
            if (livro.id == id) {
                Database.livros.remove(livro);
                break;
            }
        }
    }

    // Atualiza seletivamente as informações ou quantidades físicas de exemplares em estoque
    public static ReturnTypes.LivroUpdateType updateLivro(int id, ParamTypes.LivroUpdateType livro) {
        for (Livro l : Database.livros) {
            if (l.id == id) {
                // Atualizações condicionais baseadas nas modificações enviadas pelo administrador
                if (livro.autor != null) {
                    l.autor = livro.autor;
                }
                if (livro.titulo != null) {
                    l.titulo = livro.titulo;
                }
                if (livro.genero != null) {
                    l.genero = livro.genero;
                }
                if (livro.copiasDisponiveis != null) {
                    l.copiasDisponiveis = livro.copiasDisponiveis;
                }
                
                ReturnTypes.LivroUpdateType livroDTO = new ReturnTypes.LivroUpdateType();
                livroDTO.titulo = l.titulo;
                livroDTO.autor = l.autor;
                livroDTO.genero = l.genero;
                livroDTO.copiasDisponiveis = l.copiasDisponiveis;
                return livroDTO;
            }
        }
        return null;
    }
}
