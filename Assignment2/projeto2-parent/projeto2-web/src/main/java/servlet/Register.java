package servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ejb.UsersEJBRemote;


// http://127.0.0.1:8080/project2-web/PlayersTallerThan?fill=1
// url = http://127.0.0.1:8080/project2-web/PlayersTallerThan?height=1.80
@WebServlet("/Register")
public class Register extends HttpServlet {
 private static final long serialVersionUID = 1L;
 @EJB
 UsersEJBRemote ejbremote;

 /**
  * @see HttpServlet#HttpServlet()
  */
 public Register() {
  super();
 }

 /**
  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  response.setContentType("text/html");

  Logger logger = Logger.getLogger(Register.class.getName());

  if(!request.getParameter("username").equals("") && !request.getParameter("password").equals("") && !request.getParameter("email").equals("") && !request.getParameter("country").equals("")){
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String email = request.getParameter("email");
    String country = request.getParameter("country");
  
    try{
      if(ejbremote.createUser(username, password, email, country)){
        response.sendRedirect("Login.jsp");
      }
    }catch(EJBTransactionRolledbackException e){
      logger.info("Email already in use");
      response.sendRedirect("Register.jsp");
    }
  }
  else{
    logger.warning("Item creation failed. Empty fields are not accepted.");
    response.sendRedirect("Register.jsp");
  }

 }

 /**
  * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  response.sendRedirect("Register.jsp");
  doGet(request, response);
 }

}