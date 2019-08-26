package controllersSql2o;

import models.Dog;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class DogSql2o {
    private Sql2o sql2o;
    public DogSql2o(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public List<Dog> getAllDogs(){
        try(Connection conn = sql2o.beginTransaction()){
            return conn.createQuery("select * from dog")
                    .executeAndFetch(Dog.class);
        }
    }

    public int createDog(Dog dog){
        try(Connection conn = sql2o.open()){
            int newId = conn.createQuery("insert into DOG(name) values (:name)")
                    .addParameter("name", dog.getName())
                    .executeUpdate().getResult();
            return newId;
        }
    }
}