package src.repositories.livro;
import src.database.Database;
import src.entities.Livro;

public class LivroRepo {

    public static boolean livroExists(int id) {
        for (Livro livro : Database.livros) {
            if (livro.id == id) {
                return true;
            }
        }
        return false;
    }
    
    public static ReturnTypes.LivroAddType addLivro(ParamTypes.LivroAddType livro) {


        int id = Database.livros.get(Database.livros.size()-1).id + 1;

        Livro novoLivro = new Livro();
        novoLivro.id = id;
        novoLivro.titulo = livro.titulo;
        novoLivro.autor = livro.autor;
        novoLivro.genero = livro.genero;
        novoLivro.copiasDisponiveis = livro.copiasDisponiveis;
        Database.livros.add(novoLivro);

        ReturnTypes.LivroAddType livroDTO = new ReturnTypes.LivroAddType();
        livroDTO.id = id;
        livroDTO.titulo = livro.titulo;
        livroDTO.autor = livro.autor;
        livroDTO.genero = livro.genero;
        livroDTO.copiasDisponiveis = livro.copiasDisponiveis;
        return livroDTO;
    }

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

    public static void deleteLivroById(int id) {
        for (Livro livro : Database.livros) {
            if (livro.id == id) {
                Database.livros.remove(livro);
                break;
            }
        }
    }

    public static ReturnTypes.LivroUpdateType updateLivro(int id, ParamTypes.LivroUpdateType livro) {
        for (Livro l : Database.livros) {
            if (l.id == id) {
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
