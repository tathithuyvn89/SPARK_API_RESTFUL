package veho;

import static spark.Spark.*;
import static spark.Spark.exception;
import static veho.JsonUtil.json;
import static veho.JsonUtil.toJson;

public class UserController {
    public UserController(final UserService userService ){
        get("/users", (request, response) -> userService.getAllUsers(),json());

        get("/users/:id", (request, response) -> {
            String id = request.params(":id");
            User user = userService.getUser(id);
            if (user !=null) {
                return user;
            } else {
                response.status(400);
                return new ResponseError("No user with ud '%s' found", id);
            }
        }, json());

        post("/users", (request, response) -> userService.createUser(
                request.queryParams("name"),request.queryParams("email")
        ), json());

        put("/users/:id", (request, response) ->
                userService.updateUser(request.params(";id"),
                        request.queryParams("name"), request.queryParams("emails")), json());

        after((request, response) -> {response.type("application/json");});

        exception(IllegalArgumentException.class , (e,req,res) -> {
            res.status(400);
            res.body(toJson(new ResponseError(e)));
        });
    }
}
