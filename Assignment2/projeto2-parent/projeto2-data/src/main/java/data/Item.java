package data;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Player
 *
 */
@Entity
public class Item implements Serializable {
 private static final long serialVersionUID = 1L;
 @Id @GeneratedValue(strategy=GenerationType.AUTO)
 Integer id;


String photo;

 
 @Column(nullable = false)
 private String name;
 private String category;
 private String countryOrigin;
 private Float price;
 private Integer insertionDate;  

 @ManyToOne
 private User user;
 
 public Item() {
  super();
 }

 public Item(String name, String category, String countryOrigin, Float price, Integer insertionDate) {
  super();
  this.name = name;
  this.category = category;
  this.countryOrigin = countryOrigin;
  this.price = price;
  this.insertionDate = insertionDate;
 }

 public String getName() {
  return this.name;
 }

 public void setName(String name) {
  this.name = name;
 }

 public String getCategory() {
  return this.category;
 }

 public void setCategory(String category) {
  this.category = category;
 }

 public String getCountryOrigin() {
     return this.countryOrigin;
 }

 public void setCountryOrigin(String countryOrigin) {
     this.countryOrigin = countryOrigin;
 }


 public Integer getId() {
     return this.id;
 }

public void setId(int id) {
    this.id = id;
}

 public User getUser() {
     return this.user;
 }

 public void setUser(User user) {
     this.user = user;
 }

 /**
  * @return the price
  */
 public Float getPrice() {
     return this.price;
 }

 /**
  * @param price the price to set
  */
 public void setPrice(Float price) {
     this.price = price;
 }

 public Integer getInsertionDate() {
     return this.insertionDate;
 }

 public String getPhoto() {
     return this.photo;
 }


 public void setPhoto(String photo) {
     this.photo = photo;
 }

 


 @Override
 public String toString() {
  return "Name: " + this.getName() + "<br>" + "Category: " + this.getCategory() +  "<br>"+ "Country of Origin: " + this.getCountryOrigin() + "<br>"+ "Price: " + this.getPrice() + "€";
 }
   
}