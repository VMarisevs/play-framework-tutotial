package model.jpa.Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.inject.Inject;
import model.Pet;
import play.db.Database;

public class PetDao {

  private static final String INSERT_PET_QUERY = "INSERT INTO pet (owner,name,dob) VALUES (?,?,?)";
  private static final String FIND_OWNER_QUERY = "SELECT id FROM person WHERE id = ? ";

  private final Database db;

  @Inject
  public PetDao(Database db) {
    this.db = db;
  }

  public void addPet(Pet pet) throws SQLException {

    try(Connection connection = db.getConnection()){
      validateOwner(connection,pet.getOwner());

      PreparedStatement statement = connection.prepareStatement(INSERT_PET_QUERY);
      statement.setLong(1,pet.getOwner());
      statement.setString(2, pet.getName());
      statement.setDate(3, Date.valueOf(pet.getDob()));

      statement.executeUpdate();
    }
  }

  private void validateOwner(Connection connection, Long ownerId) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(FIND_OWNER_QUERY);
    statement.setLong(1, ownerId);
    ResultSet result = statement.executeQuery();
    if (!result.next()) throw new SQLException("Owner doesn't exist.");
  }
}
