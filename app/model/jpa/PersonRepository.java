package model.jpa;

import com.google.inject.ImplementedBy;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;
import model.Person;
import model.jpa.impl.JPAPersonRepository;

@ImplementedBy(JPAPersonRepository.class)
public interface PersonRepository {

  CompletionStage<Person> add(Person person);

  CompletionStage<Stream<Person>> list();
}
