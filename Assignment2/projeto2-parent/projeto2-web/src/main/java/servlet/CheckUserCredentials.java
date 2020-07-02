package servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.User;
import ejb.UsersEJBRemote;

@WebServlet("/CheckUserCredentials")
public class CheckUserCredentials extends HttpServlet {
 private static final long serialVersionUID = 1L;
 @EJB
 UsersEJBRemote ejbremote;

 /**
  * @see HttpServlet#HttpServlet()
  */
 public CheckUserCredentials() {
  super();
 }

 /**
  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  response.setContentType("text/html");

  if(!request.getParameter("email").equals("") && !request.getParameter("password").equals("")){
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    if(ejbremote.checkUserLogin(email, password)){

      User user = ejbremote.getUser(email);

      request.setAttribute("username", user.getUsername());
      request.setAttribute("email", user.getEmail());
      request.setAttribute("country", user.getCountry());

      request.getRequestDispatcher("EditUserInformation.jsp").forward(request, response);
    }
    else{
      response.sendRedirect("EditAccountInformation.jsp");
    }
  }
  else{
    response.sendRedirect("EditAccountInformation.jsp");
  }
 }

 /**
  * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  doGet(request, response);
 }

}