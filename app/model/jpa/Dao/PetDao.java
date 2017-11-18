package model.jpa.Dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import model.Pet;
import model.jpa.DatabaseExecutionContext;
import play.api.Play;
import play.db.jpa.JPA;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;

public class PetDao {

//  private final JPAApi jpa;
//  private final DatabaseExecutionContext executionContext;
//
//  @Inject
//  public PetDao(JPAApi jpa, DatabaseExecutionContext executionContext) {
//    this.jpa = jpa;
//    this.executionContext = executionContext;
//  }

  @Transactional
  public boolean addPet(Pet pet) {
    JPAApi jpa = Play.current().injector().instanceOf(JPAApi.class);

    jpa.em().persist(pet);

//    EntityManager em = jpa.em();
//
//    em.getTransaction().begin();
//    em.persist(pet);
//    em.getTransaction().commit();
//    em.close();

    return true;
  }
}
