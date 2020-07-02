package servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.User;
import ejb.UsersEJBRemote;

@WebServlet("/EditUserInformation")
public class EditUserInformation extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB
    UsersEJBRemote ejbremote;

    Logger logger = Logger.getLogger(EditItem.class.getName());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUserInformation() {
    super();
 }

 /**
  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  response.setContentType("text/html");

  if(!request.getParameter("email").equals("") && !request.getParameter("password").equals("") && !request.getParameter("username").equals("") && !request.getParameter("country").equals("")){
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String username = request.getParameter("username");
    String country = request.getParameter("country");

    HttpSession session = request.getSession();
    String emailSession = session.getAttribute("email").toString();
    
    
    if(ejbremote.editUserInfo(username, password, email, country, emailSession)){
      session.setAttribute("country",country);
      session.setAttribute("email", email);
      response.sendRedirect("UserMenu.jsp");
    }
  }
  else{
      HttpSession session = request.getSession();
      String emailSession = session.getAttribute("email").toString();

      User user = ejbremote.getUser(emailSession);

      request.setAttribute("username", user.getUsername());
      request.setAttribute("email", user.getEmail());
      request.setAttribute("country", user.getCountry());

      logger.warning("Edit user failed. Empty fields are not accepted.");
      request.getRequestDispatcher("EditUserInformation.jsp").forward(request, response);
  }
 }

 /**
  * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  doGet(request, response);
 }

}