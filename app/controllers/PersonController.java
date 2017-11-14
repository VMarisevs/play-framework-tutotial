package controllers;

import static play.libs.Json.toJson;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import javax.inject.Inject;
import model.Person;
import model.jpa.PersonRepository;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

public class PersonController extends Controller {

  private final PersonRepository personRepository;
  private final HttpExecutionContext ec;

  @Inject
  public PersonController(PersonRepository personRepository,
      HttpExecutionContext ec) {
    this.personRepository = personRepository;
    this.ec = ec;
  }

  public CompletionStage<Result> addPerson() {
    JsonNode json = request().body().asJson();

    Person person = Json.fromJson(json, Person.class);

    return personRepository.add(person).thenApplyAsync(p -> ok(), ec.current());
  }

  public CompletionStage<Result> getPersons() {
    return personRepository.list()
        .thenApplyAsync(personStream -> ok(toJson(personStream.collect(Collectors.toList()))),
            ec.current());
  }

}
