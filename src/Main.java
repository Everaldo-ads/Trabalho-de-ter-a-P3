package src;
import java.util.ArrayList;

import src.database.Database;
import src.database.Model;
import src.entities.*;

public class Main {
    public static void main(String[] args) {
        Model<User> userModel = new Model<>("Users");
        Model<Livro> livroModel = new Model<>("Livros");

        ArrayList<Model> modelArrayList = new ArrayList<Model>();
        modelArrayList.add(userModel);
        modelArrayList.add(livroModel);
        Database database = new Database(modelArrayList);
    }
}