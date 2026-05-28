package src;

import src.database.Database;
import src.entities.*;

public class Main {
    public static void main(String[] args) {
        try {
            new Database.Model<User>("users", User.class, "id");
            new Database.Model<Livro>("livros", Livro.class, "id");
        } catch (NoSuchFieldException e) {
            System.out.println("Error creating model: " + e.getMessage());
            return;
        } catch (SecurityException e) {
            System.out.println("Error creating model: " + e.getMessage());
            return;
        }
        Database.startDB();
    }
}