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
import ejb.EmailServiceEJBRemote;
import ejb.UsersEJBRemote;

@WebServlet("/Login")
public class Login extends HttpServlet {
 private static final long serialVersionUID = 1L;
 @EJB
 UsersEJBRemote ejbremote;
 @EJB
 public EmailServiceEJBRemote emailejbremote;

 /**
  * @see HttpServlet#HttpServlet()
  */
 public Login() {
  super();
 }

 /**
  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  response.setContentType("text/html");

  Logger logger = Logger.getLogger(Register.class.getName());


  String email = request.getParameter("email");
  String password = request.getParameter("password");

  User loggedUser = ejbremote.getUser(email);

  HttpSession session=request.getSession();  

  //emailejbremote.sendAccountActivationLinkToBuyer("fabiocordeiro1998@gmail.com", "Fabz");

  if(ejbremote.checkUserLogin(email, password)){
    session.setAttribute("email",email);  
    session.setAttribute("country", loggedUser.getCountry());
    response.sendRedirect("MainMenu.jsp");
  }
  else{
    logger.warning("Item creation failed. Empty fields are not accepted.");
    response.sendRedirect("Login.jsp");
  }
 }

 /**
  * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  response.sendRedirect("Login.jsp");
  doGet(request, response);
 }

}