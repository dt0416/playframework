package controllers;

import org.w3c.dom.Document;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.Json;
import play.libs.XPath;
import play.mvc.*;
import play.mvc.BodyParser.Xml;
import views.html.*;

public class XmlDemo extends Controller {
    /**
     * Handle XML request
     * 使用curl指令測試
     * curl --header "Content-type: application/xml" --request POST --data '<name>Guillaume</name>' http://localhost:9000/sayHelloToXML
     */
    @BodyParser.Of(Xml.class)
    public static Result sayHello() {
        Document dom = request().body().asXml();
        if(dom == null) {
            return badRequest("Expecting Xml data\n"); // \n是使用curl測試用方便，實際情況不需加
        } else {
            String name = XPath.selectText("//name", dom);
            if(name == null) {
                return badRequest("Missing parameter [name]\n"); // \n是使用curl測試用方便，實際情況不需加
            } else {
                return ok("Hello " + name + "\n"); // \n是使用curl測試用方便，實際情況不需加
            }
        }
    }
    
    /**
     * Serving an XML response
     * 使用curl指令測試
     * curl --header "Content-type: application/xml" --request POST --data '<name>Guillaume</name>' http://localhost:9000/sayHelloInXML
     */
    @BodyParser.Of(Xml.class)
    public static Result sayHelloInXML() {
        Document dom = request().body().asXml();
        if(dom == null) {
            return badRequest("Expecting Xml data\n"); // \n是使用curl測試用方便，實際情況不需加
        } else {
            String name = XPath.selectText("//name", dom);
            if(name == null) {
                return badRequest("<message \"status\"=\"KO\">Missing parameter [name]</message>\n"); // \n是使用curl測試用方便，實際情況不需加
            } else {
                return ok("<message \"status\"=\"OK\">Hello " + name + "</message>\n"); // \n是使用curl測試用方便，實際情況不需加
            }
        }
    }
    
}
