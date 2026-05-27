package src.database;
import java.util.ArrayList;

public class Model<T> {
    private ArrayList<T> data;
    public String name;

    public Model(String name) {
        this.name = name;
    }
}