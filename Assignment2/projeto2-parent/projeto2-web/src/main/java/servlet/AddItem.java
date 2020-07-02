package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import ejb.ItemsEJBRemote;;

@WebServlet("/AddItem")
@MultipartConfig(maxFileSize = 16177215)
public class AddItem extends HttpServlet {
 private static final long serialVersionUID = 1L;
 @EJB
 ItemsEJBRemote ejbremote;

 /**
  * @see HttpServlet#HttpServlet()
  */
 public AddItem() {
  super();
 }

 /**
  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  response.setContentType("text/html; charset=UTF-8");
  Logger logger = Logger.getLogger(AddItem.class.getName());


    //PHOTO
    InputStream photo = null;
    Part filePart = request.getPart("photo");
    if (filePart != null) {
        // obtains input stream of the upload file
        photo = filePart.getInputStream();
    }
    byte[] photoBytes;
    photoBytes = IOUtils.toByteArray(photo);

    //GET PARAMETERS
    if(!request.getParameter("name").equals("") && !request.getParameter("category").equals("") && !request.getParameter("country").equals("") && !request.getParameter("price").equals("")){
      String name = request.getParameter("name");
      String category = request.getParameter("category");
      String countryOrigin = request.getParameter("country");
      Float price = null;
      try{
      price = Float.parseFloat(request.getParameter("price"));
      }
      catch(NumberFormatException e){
        logger.warning("Item creation failed. Floats are designated by '8.6' not '8,6'");
        response.sendRedirect("AddItem.jsp");
      }

      //CREATES INSERTION DATE AS INT
      Date d = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
      String date = formatter.format(d);
      String [] dateSplit = date.split(" ");
      String[] daySplit = dateSplit[0].split("-");
      int insertionDate = (Integer.parseInt(daySplit[2])*10000) + (Integer.parseInt(daySplit[1])*100) + (Integer.parseInt(daySplit[0]));
      
      //GET SESSION
      HttpSession session=request.getSession();

      //CREATE BASE64 IMAGE URL
      String encodedImage = Base64.getEncoder().encodeToString(photoBytes);
      String url = "data:image/jpg;base64," + encodedImage;


      if(ejbremote.createItem(name, category,countryOrigin, price, session.getAttribute("email").toString(),insertionDate, url)){
        response.sendRedirect("MainMenu.jsp");
      }
    }
    else{
      logger.warning("Item creation failed. Empty fields are not accepted.");
      response.sendRedirect("AddItem.jsp");
    }

  }

  /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }

}