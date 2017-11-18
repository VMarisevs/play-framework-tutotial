package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import model.Person;
import model.Pet;
import model.jpa.Dao.PetDao;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;

import play.mvc.Controller;
import play.mvc.Result;

public class PetController extends Controller {

  @Inject
  private PetDao petDao;
  @Inject
  private HttpExecutionContext ec;

  public Result addPet() {
    JsonNode json = Optional.ofNullable(request().body().asJson()).orElseThrow(
        () -> new IllegalArgumentException("Cannot create new person with null value"));

    Pet pet =  Json.fromJson(json, Pet.class);

    petDao.addPet(pet);

    return ok();
  }
}
