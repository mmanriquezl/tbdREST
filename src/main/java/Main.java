import static spark.Spark.*;

import com.google.gson.Gson;
import controllersSql2o.DogSql2o;
import models.Dog;
import org.sql2o.Sql2o;


public class Main {

    public static void main(String[] args) {

        Sql2o sql2o = new Sql2o("jdbc:postgresql://127.0.0.1:5432/tbd","tbduser","tbdpass");
        DogSql2o dogSql2o = new DogSql2o(sql2o);

        get("/", (req, res) -> "{\"mensaje\":\"Corriendo\"}");

        get("/dogs", (req, res)->{
            return new Gson().toJson(dogSql2o.getAllDogs());
        });
        /*get("/dogs", (req, res)->{
            int limit = 10;
            if ( req.queryParams("limit")!= null){
                limit = Integer.valueOf(req.queryParams("limit"));
            }

            int offset = 0;
            if(req.queryParams("offset")!=null){
                offset = Integer.valueOf(req.queryParams("offset"));
            }

            return new Gson().toJson(dogSql2o.getAllDogsPaginated(limit, offset));
        });*/
        post("/dogs", (req, res)->{
            Dog dog = new Gson().fromJson(req.body(), Dog.class);
            int result = dogSql2o.createDog(dog);
            res.status(201);
            return result;
        });

        after((req, res) -> {
            res.type("application/json");
        });
    }


}
