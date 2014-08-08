package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.libs.Json;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index2.render("Your new application is ready."));
    }

    /**
     * 測試網址:http://www.websocket.org/echo.html
     * Location:ws://localhost:9000/socket
     * Connect-可以看到RESPONSE訊息
     * Send-可以在Play console看到接收到的訊息
     */
    public static WebSocket<String> socket() {
        return new WebSocket<String>() {

            // Called when the Websocket Handshake is done.
            public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {

                // For each event received on the socket,
                in.onMessage(new Callback<String>() {
                    public void invoke(String event) {

                        // Log events to the console
                        System.out.println(event);

                    }
                });

                // When the socket is closed.
                in.onClose(new Callback0() {
                    public void invoke() {

                        System.out.println("Disconnected");

                    }
                });

                // Send a single 'Hello!' message
                out.write("Hello!");

            }

        };
    }
    
    public static Result layoutDemo() {
    	// 以下三行示範使用同個layout, 但使用不同方式傳值
        return ok(uselayoutByParameter.render());
//        return ok(uselayoutByIndex.render());
//        return ok(uselayoutMix.render());
    }
    
    public static Result useNotice() {
    	return ok(useNotice.render());
    }
    
    /**
     * Handle JSON request
     * 使用curl指令測試
     * curl --header "Content-type: application/json" --request POST --data '{"name": "Guillaume"}' http://localhost:9000/sayHello
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
