package model.jpa.impl;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import model.Person;
import model.jpa.DatabaseExecutionContext;
import model.jpa.PersonRepository;
import play.db.jpa.JPAApi;

public class JPAPersonRepository implements PersonRepository {

  private final JPAApi jpaApi;
  private final DatabaseExecutionContext executionContext;

  @Inject
  public JPAPersonRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
    this.jpaApi = jpaApi;
    this.executionContext = executionContext;
  }

  @Override
  public CompletionStage<Person> add(Person person) {
    return supplyAsync(() -> wrap(em -> insert(em, person)), executionContext);
  }

  @Override
  public CompletionStage<Stream<Person>> list() {
    return supplyAsync(() -> wrap(this::list), executionContext);
  }

  private <T> T wrap(Function<EntityManager, T> function) {
    return jpaApi.withTransaction(function);
  }

  private Person insert(EntityManager em, Person person) {
    em.persist(person);
    return person;
  }

  private Stream<Person> list(EntityManager em) {
    List<Person> persons = em.createQuery("select p from Person p", Person.class).getResultList();
    return persons.stream();
  }
}
