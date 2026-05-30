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
    
    public static void addLivro(LivroAddType livro) {
        if (!UserRepo.userExists(livro.autor_id)) {
            System.out.println("Autor com id " + livro.autor_id + " não existe.");
            return;
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
    }

    public static LivroGetType getLivroById(int id) {
        for (Livro livro : Database.livros) {
            if (livro.id == id) {
                LivroGetType livroGet = new LivroGetType();
                livroGet.id = livro.id;
                livroGet.titulo = livro.titulo;
                livroGet.autor = UserRepo.getUserById(livro.autor_id);
                livroGet.genero = livro.genero;
                livroGet.copiasDisponiveis = livro.copiasDisponiveis;
                return livroGet;
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

    public static LivroGetType updateLivro(int id, LivroUpdateType livro) {
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
                LivroGetType livroGet = new LivroGetType();
                livroGet.titulo = l.titulo;
                livroGet.autor = UserRepo.getUserById(l.autor_id);
                livroGet.genero = l.genero;
                livroGet.copiasDisponiveis = l.copiasDisponiveis;
                return livroGet;
            }
        }
        return null;
    }

}
