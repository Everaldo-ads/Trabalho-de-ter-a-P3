package src.repositories.livro;
import src.database.Database;
import src.entities.Livro;
import src.repositories.user.UserRepo;

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
        if (!UserRepo.userExists(livro.autor_id)) {
            System.out.println("Autor com id " + livro.autor_id + " não existe.");
            return null;
        }

        int autorId = livro.autor_id;
        int id = Database.livros.get(Database.livros.size()-1).id + 1;

        Livro novoLivro = new Livro();
        novoLivro.id = id;
        novoLivro.titulo = livro.titulo;
        novoLivro.autor_id = autorId;
        novoLivro.genero = livro.genero;
        novoLivro.copiasDisponiveis = livro.copiasDisponiveis;
        Database.livros.add(novoLivro);

        ReturnTypes.LivroAddType livroDTO = new ReturnTypes.LivroAddType();
        livroDTO.id = id;
        livroDTO.titulo = livro.titulo;
        livroDTO.autor_id = autorId;
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
                livroDTO.autor_id = livro.autor_id;
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
                if (livro.autor_id != null && UserRepo.userExists(livro.autor_id)) {
                    l.autor_id = livro.autor_id;
                } else {
                    System.out.println("Autor com id " + livro.autor_id + " não existe.");
                    return null;
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
                livroDTO.autor_id = l.autor_id;
                livroDTO.genero = l.genero;
                livroDTO.copiasDisponiveis = l.copiasDisponiveis;
                return livroDTO;
            }
        }
        return null;
    }

}
