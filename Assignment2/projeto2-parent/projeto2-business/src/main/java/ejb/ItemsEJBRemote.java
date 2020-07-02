package ejb;

import java.util.List;

import javax.ejb.Remote;

import data.Item;

@Remote
public interface ItemsEJBRemote {
    public boolean createItem(String name, String category, String countryOrigin, Float price, String userEmail,Integer insertionDate, String photo);
    public void deleteItem(int itemId, String userEmail);
    public boolean editItem(String name, String category, String countryOrigin, Float price, int id);
    public List<Item> getItems();
    public List<Item> getItemsByPrice(Float lowestPrice, Float HighestPrice);
    public List<Item> getItemsByDate(int date);
    public List<Item> getItemsByCategory(String category);
    public List<Item> getItemsByName(String name);
    public List<Item> getItemsByCountry(String country);
    public Item getItem(Integer id);
    public List<Item> getNewestItems();
}