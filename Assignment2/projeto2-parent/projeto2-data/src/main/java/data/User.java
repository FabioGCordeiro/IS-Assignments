package data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Player
 *
 */
@Entity
public class User implements Serializable {
 private static final long serialVersionUID = 1L;
 @Id @GeneratedValue(strategy=GenerationType.AUTO)
 int id;

 @Column(nullable = false)
 private String password;
 private String username;
 private String country;

 @Column(unique=true)
 private String email;
 
 @OneToMany(mappedBy="user",fetch = FetchType.EAGER)
 private List<Item> items;
 
 public User() {
  super();
 }

 public User(String username, String password, String email, String country) {
  super();
  this.username = username;
  this.password = password;
  this.email = email;
  this.country = country;
 }

 public String getUsername() {
  return this.username;
 }

 public void setName(String username) {
  this.username = username;
 }

 public String getPassword() {
  return this.password;
 }

 public void setPassword(String password) {
  this.password = password;
 }

 public String getEmail() {
     return email;
 }

 public void setEmail(String email) {
     this.email = email;
 }

 public String getCountry() {
     return country;
 }

 public void setCountry(String country) {
     this.country = country;
 }
 
 public int getId() {
     return id;
 }

 /**
  * @param id the id to set
  */
 public void setId(int id) {
     this.id = id;
 }

 /**
  * @return the items
  */
 public List<Item> getItems() {
     return items;
 }

 /**
  * @param items the items to set
  */
 public void setItems(List<Item> items) {
     this.items = items;
 }

 @Override
 public String toString() {
  return " Name: " + this.username +  " Password: " + this.password;
 }
   
}