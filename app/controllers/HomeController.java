package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * This controller contains an action to handle HTTP requests to the application's home page.
 */
public class HomeController extends Controller {

  /**
   * An action that renders an HTML page with a welcome message. The configuration in the
   * <code>routes</code> file means that this method will be called when the application receives a
   * <code>GET</code> request with a path of <code>/</code>.
   */
  public Result index() {
    ObjectNode result = Json.newObject();
    result.put("/", "end point description");
    result.put("/hello", "basic json response examle");
    result.set("/person", Json.newObject()
            .put("GET", "get a list of persons")
            .put("POST","create a new person"));
    return ok(result);
  }


  public Result sayHello() {
    ObjectNode result = Json.newObject();
    result.put("exampleField1", "foobar");
    result.put("exampleField2", "Hello world!");
    return ok(result);
  }


  @BodyParser.Of(BodyParser.Json.class)
  public Result createPerson() {
    JsonNode json = request().body().asJson();
    String name = json.findPath("name").textValue();
    if (name == null) {
      return badRequest("Missing parameter [name]");
    } else {
      return ok("Hello " + name);
    }
  }
}
