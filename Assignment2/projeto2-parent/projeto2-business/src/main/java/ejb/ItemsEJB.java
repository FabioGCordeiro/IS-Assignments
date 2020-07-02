package ejb;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import data.Item;
import data.User;

@Stateless
public class ItemsEJB implements ItemsEJBRemote {
 @PersistenceContext(name="User")
 EntityManager em;


 Logger logger = Logger.getLogger(ItemsEJB.class.getName());

    public boolean createItem(String name, String category, String countryOrigin, Float price, String userEmail,Integer insertionDate, String photo) {
        if (!name.equals("") && !category.equals("") && !countryOrigin.equals("") && price!=null) {
            Item newItem = new Item(name, category, countryOrigin, price, insertionDate);
            logger.info("Trying to create a new item: "+newItem.toString());
            Query q = em.createQuery("from User u where u.email = :e");
            q.setParameter("e", userEmail);
            User user = (User) q.getSingleResult();
            newItem.setUser(user);
            newItem.setPhoto(photo);
            em.persist(newItem);
            logger.info("Item created");
            return true; 
        }
        return false;
    }


    public boolean editItem(String name, String category, String countryOrigin, Float price, int id){
        if(!name.equals("") && !category.equals("") && !countryOrigin.equals("") && price!=null){
            logger.info("Trying to create an item");
            Query q = em.createQuery("update Item set name='" + name + "' where id = "+ id);
            q.executeUpdate();
            q = em.createQuery("update Item set category='" + category + "' where id = "+ id);
            q.executeUpdate();
            q = em.createQuery("update Item set countryOrigin='" + countryOrigin + "' where id = "+ id);
            q.executeUpdate();
            q = em.createQuery("update Item set price=" + price + " where id = "+ id);
            q.executeUpdate();
            logger.info("Item Edited");
            return true;
        }
        else{
            return false;
        }
    }

    public void deleteItem(int itemId, String userEmail){
        logger.info("Trying to delete an item");
        Query q = em.createQuery("from User u where u.email = :e");
        q.setParameter("e",userEmail);
        User user = (User) q.getSingleResult();

        // DELETE ITEMS
        for (Item i : user.getItems()) {
            if(i.getId().equals(itemId)){
                q = em.createQuery("delete Item item where item.id = :i");
                q.setParameter("i",i.getId());
            }
        }
        q.executeUpdate();
        logger.info("Item deleted");
    }

    public List<Item> getItems(){
        logger.info("Getting all items");
        Query q = em.createQuery("from Item");
        List <Item> items = q.getResultList();
        
        return items;
    }

    public List<Item> getItemsByPrice(Float lowestPrice, Float highestPrice){
        if(lowestPrice!=null && highestPrice!=null){
            Query q = em.createQuery("from Item where price >= :p1 and price <= :p2");
            q.setParameter("p1",lowestPrice);
            q.setParameter("p2", highestPrice);
            List <Item> items = q.getResultList();
            logger.info("Getting items between " + lowestPrice + "€ and " + highestPrice +"€");
            return items;
        }

        logger.info("No items found");
        return null;
    }

    public List<Item> getItemsByDate(int date){
        if((Integer)date!=null){
            Query q = em.createQuery("from Item where insertionDate >= :p1");
            q.setParameter("p1",date);
            List <Item> items = q.getResultList();
            logger.info("Getting items inserted after " + date);
            return items;
        }

        logger.info("No items found");
        return null;
    }

    public List<Item> getItemsByCategory(String category){
        if(!category.equals("")){
            Query q = em.createQuery("from Item where category like '%"+ category +"%'");
            List <Item> items = q.getResultList();
            logger.info("Getting items from category: "+category);
            return items;
        }
        logger.info("No items found");
        return null;
    }

    public List<Item> getItemsByName(String name){
        if(!name.equals("")){
            Query q = em.createQuery("from Item where name like '%"+ name +"%'");
            List <Item> items = q.getResultList();
            logger.info("Getting items with name similar to: "+name);
            return items; 
        }
        
        logger.info("No items found");
        return null;
    }

    public List<Item> getItemsByCountry(String country){
        if(!country.equals("")){
            Query q = em.createQuery("from Item where countryOrigin = :c");
            q.setParameter("c",country);
            List <Item> items = q.getResultList();
            logger.info("Getting items from my country: "+country);
            return items;
        }
        
        logger.info("No items found");
        return null;
    }

    public Item getItem(Integer id){
        if(id!=null){
            Query q = em.createQuery("from Item i where i.id = :i");
            q.setParameter("i",id);
            Item item = (Item) q.getSingleResult();
            logger.info("Getting items with id: " + id);
            return item;
        }

        logger.info("Item nor found");
        return null;
    }

    public List<Item> getNewestItems(){
        /*Query q = em.createQuery("from Item");
        List <Item> items = q.getResultList();

        List<Item> newestItems = new ArrayList<Item>();

        if(items.size()>=3){
            for(int i=0; i<3; i++){
                newestItems.add(i, items.get(i));
            }
        }
        else{
            for(int i=0; i<items.size(); i++){
                newestItems.add(i, items.get(i));
            }
        }*/

        Query q = em.createQuery("FROM Item ORDER BY id DESC").setMaxResults(3);
        List<Item> newestItems = q.getResultList();

        newestItems.sort(Comparator.comparing(Item::getId));
        
        logger.info("Getting newest items for catalogue");
        return newestItems;
    }

    
}