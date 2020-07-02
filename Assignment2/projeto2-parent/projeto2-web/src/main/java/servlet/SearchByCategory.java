package servlet;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Item;
import ejb.ItemsEJBRemote;

@WebServlet("/SearchByCategory")
public class SearchByCategory extends HttpServlet {
 private static final long serialVersionUID = 1L;
 @EJB
 ItemsEJBRemote ejbremote;

 Logger logger = Logger.getLogger(Register.class.getName());

 /**
  * @see HttpServlet#HttpServlet()
  */
 public SearchByCategory() {
  super();
 }

 /**
  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");

    try{
      
      String category = request.getParameter("category");
      HttpSession session = request.getSession();
      String email = session.getAttribute("email").toString();

      List<Item> categoryItems = ejbremote.getItemsByCategory(category);

      if((Integer.parseInt(request.getParameter("order")) == 0)){
        //ordena mais antigo -> recente (primeiro por data, depois por id caso sejam iguais)
        categoryItems.sort(Comparator.comparing(Item::getInsertionDate).thenComparing(Item::getId));
        logger.info("Sorting from most recent to most old");
      }
      else if((Integer.parseInt(request.getParameter("order")) == 1)){
          //ordena mais recente -> antigo (primeiro por data, depois por id caso sejam iguais)
          categoryItems.sort(Comparator.comparing(Item::getInsertionDate).thenComparing(Item::getId).reversed());
          logger.info("Sorting from most old to most most");
      }
      else if((Integer.parseInt(request.getParameter("order")) == 2)){
        //ordena mais barato -> caro (primeiro por data, depois por id caso sejam iguais)
        categoryItems.sort(Comparator.comparing(Item::getPrice).thenComparing(Item::getId));
        logger.info("Sorting from cheapest to most expensive");
      }
      else if((Integer.parseInt(request.getParameter("order")) == 3)){
        //ordena mais caro -> barato (primeiro por data, depois por id caso sejam iguais)
        categoryItems.sort(Comparator.comparing(Item::getPrice).reversed().thenComparing(Item::getId));
        logger.info("Sorting from most expensive to cheapest");
      }
      else if((Integer.parseInt(request.getParameter("order")) == 4)){
        //ordena mais A -> Z (primeiro por data, depois por id caso sejam iguais)
        categoryItems.sort(Comparator.comparing(Item::getName,String.CASE_INSENSITIVE_ORDER).thenComparing(Item::getId));
        logger.info("Sorting from A to Z");
      }
      else if((Integer.parseInt(request.getParameter("order")) == 5)){
        //ordena mais Z -> A (primeiro por data, depois por id caso sejam iguais)
        categoryItems.sort(Comparator.comparing(Item::getName,String.CASE_INSENSITIVE_ORDER).reversed().thenComparing(Item::getId));
        logger.info("Sorting from Z to A");
      }
      
      request.setAttribute("items", categoryItems);
      request.setAttribute("category", category);
      request.getRequestDispatcher("ShowCategoryItemsList.jsp").forward(request, response); 

      }catch(NullPointerException | NumberFormatException e){
        response.sendRedirect("Error.jsp");
    }
    
 }

 /**
  * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  doGet(request, response);
 }

}