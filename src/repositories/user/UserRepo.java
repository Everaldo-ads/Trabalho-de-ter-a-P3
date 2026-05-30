package src.repositories.user;
import java.util.ArrayList;
import src.database.Database;
import src.entities.Livro;
import src.entities.User;
import src.repositories.livro.LivroGetType;
import src.repositories.livro.LivroRepo;

public class UserRepo {

    public static boolean userExists(int id) {
        for (User user : Database.users) {
            if (user.id == id) {
                return true;
            }
        }
        return false;
    }

    public static void addUser(UserAddType user) {
        User newUser = new User();
        newUser.id = Database.users.size() + 1;
        newUser.nome = user.nome;
        if (user.livrosEmprestados != null) {
            for (Integer livroId : user.livrosEmprestados) {
                if (!LivroRepo.livroExists(livroId)) {
                    System.out.println("Livro com id " + livroId + " não existe.");
                    return;
                }
            }
            newUser.livrosEmprestados = user.livrosEmprestados;
        } else {
            newUser.livrosEmprestados = new ArrayList<>();
        }
        Database.users.add(newUser);
    }

    public static UserGetType getUserById(int id) {
        for (User user : Database.users) {
            if (user.id == id) {
                UserGetType userGet = new UserGetType();
                userGet.id = user.id;
                userGet.nome = user.nome;
                userGet.livrosEmprestados = new ArrayList<>();
                for (Integer livroId : user.livrosEmprestados) {
                    LivroRepo.getLivroById(livroId);
                    userGet.livrosEmprestados.add(LivroRepo.getLivroById(livroId));
                }
                return userGet;
            }
        }
        return null;
    }

    public static void deleteUserById(int id) {
        for (User user : Database.users) {
            if (user.id == id) {
                Database.users.remove(user);
                break;
            }
        }
    }

    public static void updateEmprestarLivro(User user, UserUpdateType userUpdate) {
        if (userUpdate.novosLivrosEmprestados == null)
            return;
        
        ArrayList<LivroGetType> novosLivros = new ArrayList<>();
        for (Integer livroId : userUpdate.novosLivrosEmprestados) {
            if (!LivroRepo.livroExists(livroId)) {
                System.out.println("Livro com id " + livroId + " não existe.");
                return;
            }
            for (Integer livrosEmprestados : user.livrosEmprestados) {
                if (livrosEmprestados == livroId) {
                    System.out.println("Livro com id " + livroId + " já está emprestado para o usuário.");
                    return;
                }
            }
            novosLivros.add(LivroRepo.getLivroById(livroId));
        }
        for (LivroGetType livro : novosLivros) {
            user.livrosEmprestados.add(livro.id);
        }
    }

    public static void updateDevolverLivro(User user, UserUpdateType userUpdate) {
        if (userUpdate.livrosDevolvidos == null)
            return;
        
        for (Integer livroId : userUpdate.livrosDevolvidos) {
            if (!LivroRepo.livroExists(livroId)) {
                System.out.println("Livro com id " + livroId + " não existe.");
                return;
            }
            boolean livroEmprestado = false;
            for (Integer livrosEmprestados : user.livrosEmprestados) {
                if (livrosEmprestados == livroId) {
                    livroEmprestado = true;
                    break;
                }
            }
            if (!livroEmprestado) {
                System.out.println("Livro com id " + livroId + " não está emprestado para o usuário.");
                return;
            }
        }
        for (Integer livroId : userUpdate.livrosDevolvidos) {
            user.livrosEmprestados.remove(livroId);
        }
    }

    public static UserGetType updateUser(int id, UserUpdateType user) {
        for (User u : Database.users) {
            if (u.id == id) {
                updateEmprestarLivro(u, user);
                updateDevolverLivro(u, user);
                if (user.nome != null) {
                    u.nome = user.nome;
                }
                UserGetType userGet = new UserGetType();
                userGet.id = u.id;
                userGet.nome = u.nome;
                userGet.livrosEmprestados = new ArrayList<>();
                for (Integer livroId : u.livrosEmprestados) {
                    LivroRepo.getLivroById(livroId);
                    userGet.livrosEmprestados.add(LivroRepo.getLivroById(livroId));
                }
                return userGet;
            }
        }
        return null;
    }

}
