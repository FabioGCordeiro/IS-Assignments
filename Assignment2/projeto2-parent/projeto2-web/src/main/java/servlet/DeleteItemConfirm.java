package servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ejb.UsersEJBRemote;
import ejb.ItemsEJBRemote;

@WebServlet("/DeleteItemConfirm")
public class DeleteItemConfirm extends HttpServlet {
 private static final long serialVersionUID = 1L;
 @EJB
 ItemsEJBRemote itemsejbremote;
 @EJB
 UsersEJBRemote ejbremote;


 /**
  * @see HttpServlet#HttpServlet()
  */
 public DeleteItemConfirm() {
  super();
 }

 /**
  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  response.setContentType("text/html");

  if(!request.getParameter("email").equals("") && !request.getParameter("password").equals("") && !request.getParameter("id").equals("")){
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    int id = Integer.parseInt(request.getParameter("id"));
    if(ejbremote.checkUserLogin(email, password)){
      itemsejbremote.deleteItem(id, email);
      response.sendRedirect("MainMenu.jsp");
    }
    else{
      response.sendRedirect("DeleteItem.jsp");
    }
  }
  else{
    response.sendRedirect("DeleteItem.jsp");
  }
 }

 /**
  * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  doGet(request, response);
 }

}