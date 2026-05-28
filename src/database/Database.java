package src.database;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Database {

    private static ArrayList<Model> models = new ArrayList<Model>();

    private static class Relationship {
        private String relationFieldName;
        private String fieldName;
        private Model<?> model;

        public Relationship(Model<?> model, String relationFieldName, String fieldName) {
            this.relationFieldName = relationFieldName;
            this.fieldName = fieldName;
            this.model = model;
        }

        public String getRelationFieldName() {
            return relationFieldName;
        }
        public Model<?> getModel() {
            return model;
        }
        public String getFieldName() {
            return fieldName;
        }
    }

    public static class Model<T> {
        private ArrayList<T> data;
        private ArrayList<Relationship> relationships;
        private String name;
        private Class<T> clazz;
        private String primaryKey;

        public Model(String name, Class<T> clazz, String primaryKey)  throws NoSuchFieldException, SecurityException {
            this.name = name;
            this.data = new ArrayList<T>();
            this.relationships = new ArrayList<Relationship>();
            models.add(this);
            this.clazz = clazz;
            if (clazz.getField(primaryKey)==null) {
                throw new Error("Primary key field not found: " + primaryKey);
            }
            this.primaryKey = primaryKey;
        }

        public String getPrimaryKey() {
            return primaryKey;
        }
        public String getName() {
            return name;
        }
    }

    public static void startDB() {
        for (Model<?> model: models) {
            for (Field field: model.clazz.getFields()) {
                Class<?> fieldType = field.getType();
                for (Model<?> m: models) {
                    if (m.clazz==fieldType) {
                        Relationship relationship = new Relationship(
                            m, m.getPrimaryKey(), field.getName()
                        );
                        model.relationships.add(relationship);
                    } else if (m.clazz==ArrayList.class) {
                        Relationship relationship = new Relationship(
                            m, m.getPrimaryKey(), field.getName()
                        );
                        model.relationships.add(relationship);
                    }
                }
            }
        }
    }

    public static Model<?> searchModelByName(String name) throws Error {
        for (Model<?> model : models) {
            if (model.name==name) {
                return model;
            }
        }
        throw new Error("Model not found: " + name);
    }

}