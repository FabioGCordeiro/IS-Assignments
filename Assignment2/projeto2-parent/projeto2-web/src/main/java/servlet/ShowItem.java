package servlet;

import java.io.IOException;
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
import ejb.ItemsEJBRemote;
import ejb.UsersEJBRemote;

@WebServlet("/ShowItem")
public class ShowItem extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB
    ItemsEJBRemote ejbremote;
    @EJB
    UsersEJBRemote uejbremote;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowItem() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        try{
            HttpSession session = request.getSession();
            String email = session.getAttribute("email").toString();
    
            User user = uejbremote.getUser(email);
            List<Item> userItems = user.getItems();
            
            if(!request.getParameter("id").equals("")){
                int id = Integer.parseInt(request.getParameter("id"));
                session.setAttribute("id", id);

                
    
                Item itemToDetail = ejbremote.getItem(id);
                
                //CREATE BASE64 IMAGE URL
                String url = itemToDetail.getPhoto();
    
                //DISPLAY ITEM INFORMATION + IMAGE + BUTTON IF IT IS A USER'S ITEM
                for (Item item : userItems){
                    if(item.getId() == id){
                        request.setAttribute("hasPermission", "true");
                    }
                }

                request.setAttribute("name", itemToDetail.getName());
                request.setAttribute("category", itemToDetail.getCategory());
                request.setAttribute("countryOrigin", itemToDetail.getCountryOrigin());
                request.setAttribute("price", itemToDetail.getPrice());
                request.setAttribute("photo",url);
                request.getRequestDispatcher("ShowItem.jsp").forward(request, response);;
            }
            else{
                //response.sendRedirect("Error.jsp");
            }
        }catch(NullPointerException | NumberFormatException e){
            //response.sendRedirect("Error.jsp");
        }

 }

 /**
  * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  doGet(request, response);
 }

}