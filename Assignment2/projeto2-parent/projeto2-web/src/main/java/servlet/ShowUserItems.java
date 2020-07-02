package servlet;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Item;
import data.User;
import ejb.UsersEJBRemote;

@WebServlet("/ShowUserItems")
public class ShowUserItems extends HttpServlet {
 private static final long serialVersionUID = 1L;
 @EJB
 UsersEJBRemote ejbremote;

 /**
  * @see HttpServlet#HttpServlet()
  */
 public ShowUserItems() {
  super();
 }

 /**
  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");

    HttpSession session = request.getSession();
    
    try{
        if(session.getAttribute("email").toString()!=null){
          String email = session.getAttribute("email").toString();

          if(ejbremote.getUser(email)==null){
            response.sendRedirect("Error.jsp");
          }
          else{
            User loggedUser = ejbremote.getUser(email);
            List<Item> userItems = loggedUser.getItems();
      
            if((Integer.parseInt(request.getParameter("order")) == 0)){
              //ordena mais antigo -> recente (primeiro por data, depois por id caso sejam iguais)
              userItems.sort(Comparator.comparing(Item::getInsertionDate).thenComparing(Item::getId));
            }
            else if((Integer.parseInt(request.getParameter("order")) == 1)){
                //ordena mais recente -> antigo (primeiro por data, depois por id caso sejam iguais)
                userItems.sort(Comparator.comparing(Item::getInsertionDate).thenComparing(Item::getId).reversed());
            }
            else if((Integer.parseInt(request.getParameter("order")) == 2)){
              //ordena mais barato -> caro (primeiro por data, depois por id caso sejam iguais)
              userItems.sort(Comparator.comparing(Item::getPrice).thenComparing(Item::getId));
            }
            else if((Integer.parseInt(request.getParameter("order")) == 3)){
              //ordena mais caro -> barato (primeiro por data, depois por id caso sejam iguais)
              userItems.sort(Comparator.comparing(Item::getPrice).reversed().thenComparing(Item::getId));
            }
            else if((Integer.parseInt(request.getParameter("order")) == 4)){
              //ordena mais A -> Z (primeiro por data, depois por id caso sejam iguais)
              userItems.sort(Comparator.comparing(Item::getName,String.CASE_INSENSITIVE_ORDER).thenComparing(Item::getId));
            }
            else if((Integer.parseInt(request.getParameter("order")) == 5)){
              //ordena mais Z -> A (primeiro por data, depois por id caso sejam iguais)
              userItems.sort(Comparator.comparing(Item::getName,String.CASE_INSENSITIVE_ORDER).reversed().thenComparing(Item::getId));
            }
      
            request.setAttribute("items", userItems);
            request.getRequestDispatcher("ShowUserItemsList.jsp").forward(request, response); }
        }
        else{
          response.sendRedirect("Error.jsp");
        }
      }catch(NullPointerException e){
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