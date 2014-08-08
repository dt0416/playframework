package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.Json;
import play.mvc.*;
import views.html.*;

public class JsonDemo extends Controller {
    /**
     * Handle JSON request
     * 使用curl指令測試
     * curl --header "Content-type: application/json" --request POST --data '{"name": "Guillaume"}' http://localhost:9000/sayHelloToJSON
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result sayHello() {
        JsonNode json = request().body().asJson();
        String name = json.findPath("name").textValue();
        if(name == null) {
            return badRequest("Missing parameter [name]\n"); // \n是使用curl測試用方便，實際情況不需加
        } else {
            return ok("Hello " + name + "\n"); // \n是使用curl測試用方便，實際情況不需加
        }
    }
    
    /**
     * Serving a JSON response
     * 使用curl指令測試
     * curl --header "Content-type: application/json" --request POST --data '{"name": "Guillaume"}' http://localhost:9000/sayHelloInJSON
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result sayHelloInJSON() {
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        String name = json.findPath("name").textValue();
        if(name == null) {
            result.put("status", "KO");
            result.put("message", "Missing parameter [name]");
            return badRequest(result + "\n"); // \n是使用curl測試用方便，實際情況不需加
        } else {
            result.put("status", "OK");
            result.put("message", "Hello " + name);
            return ok(result + "\n"); // \n是使用curl測試用方便，實際情況不需加
        }
    }
}
