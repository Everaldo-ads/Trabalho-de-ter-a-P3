package src.database;
import java.util.ArrayList;

public class Database {

    public ArrayList<Model> models;

    public Database(ArrayList<Model> models) {
        this.models = models;
    }

    public Model searchByName(String name) throws Error {
        for (Model model : models) {
            if (model.name==name) {
                return model;
            }
        }
        throw new Error();
    }
}