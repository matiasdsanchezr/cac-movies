package com.ar.apimovies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import org.apache.commons.dbutils.DbUtils;

import com.ar.apimovies.dto.SigninRequest;
import com.ar.apimovies.model.User;

public class UserDao {

  public User getUserByEmail(String email_to_find) {
    DatabaseConnection databaseConn = new DatabaseConnection();
    Connection conn = databaseConn.conectar();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      ps = conn.prepareStatement("SELECT * FROM user WHERE email = ?");
      ps.setString(1, email_to_find);
      rs = ps.executeQuery();
      while (rs.next()) {
        Long userId = rs.getLong("user_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String hash = rs.getString("password");
        String email = rs.getString("email");
        Timestamp birthdate = rs.getTimestamp("birthdate");
        String country = rs.getString("country");
        User user = new User(userId, firstName, lastName, hash, email, birthdate, country);
        return user;
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DbUtils.closeQuietly(rs);
      DbUtils.closeQuietly(ps);
      DbUtils.closeQuietly(conn);
    }

    return null;
  }

  public Long addUser(User user) {
    DatabaseConnection databaseConn = new DatabaseConnection();
    Connection conn = databaseConn.conectar();
    PreparedStatement ps = null;
    ResultSet rs = null;

    String insertUserSql = "INSERT INTO user(first_name,last_name,email,password,birthdate,country) VALUES(?,?,?,?,?,?)";

    try {
      ps = conn.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, user.getFirstName());
      ps.setString(2, user.getLastName());
      ps.setString(3, user.getEmail());
      ps.setString(4, user.getPassword());
      ps.setTimestamp(5, user.getBirthdate());
      ps.setString(6, user.getCountry());

      if (ps.executeUpdate() > 0) {
        rs = ps.getGeneratedKeys();
        if (rs.next()) {
          System.out.println("Se cargo exitosamente un nuevo usuario");
          return rs.getLong(1);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DbUtils.closeQuietly(rs);
      DbUtils.closeQuietly(ps);
      DbUtils.closeQuietly(conn);
    }

    System.out.println("Error al insertar el nuevo usuario");
    return null;
  }

  public User signWithEmailAndPassword(SigninRequest signinDto) {
    DatabaseConnection databaseConn = new DatabaseConnection();
    Connection conn = databaseConn.conectar();
    PreparedStatement ps = null;
    ResultSet rs = null;

    String SignInWithEmailAndPassword = "SELECT * FROM user WHERE email = ?";

    try {
      ps = conn.prepareStatement(SignInWithEmailAndPassword);
      ps.setString(1, signinDto.getEmail());
      rs = ps.executeQuery();
      while (rs.next()) {
        if (!signinDto.getPassword().equals(rs.getString("password")))
          return null;

        Long userId = rs.getLong("user_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String email = rs.getString("email");
        Timestamp birthdate = rs.getTimestamp("birthdate");
        String country = rs.getString("country");
        User user = new User(userId, firstName, lastName, "", email, birthdate, country);
        return user;
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DbUtils.closeQuietly(rs);
      DbUtils.closeQuietly(ps);
      DbUtils.closeQuietly(conn);
    }

    return null;
  }
}
