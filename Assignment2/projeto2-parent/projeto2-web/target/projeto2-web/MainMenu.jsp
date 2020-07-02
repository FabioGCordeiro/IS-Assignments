<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1" session="false"%>

<%
HttpSession session = request.getSession(false);
String user = (String) session.getAttribute("email");

if(user==null){
    response.sendRedirect("Error.jsp");
}
%>

<html>
<title>MyBay - MainMenu</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../lib/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<style>
        .dropbtn {
          background-color:black;
          color: white;
          font-size: 16px;
          block-size: 150px;
          inline-size:100px;
          border: none;
          cursor: pointer;
        }
        
        .dropdown {
          position: relative;
          display: inline-block;
        }
        
        .dropdown-content {
          display: none;
          position: absolute;
          background-color: rgb(77, 1, 77);
          text-align: center;
          min-width: 100px;
          box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.918);
          z-index: 1;
        }
        
        .dropdown-content a {
          color: black;
          padding: 50px 16px;
          text-decoration: none;
          display: block;
        }


        .dropdown-content a:hover {background-color: #f1f1f1}
        
        .dropdown:hover .dropdown-content {
          display: block;
        }
        
</style>

    <body style="background-image: url('myBayFinal2.jpg');">
        <div class="dropdown w3-col" style="position:absolute;">
        <button class="dropbtn w3-xxxlarge" style="font-weight:900;width:100%;height: 15%;">Main Menu</button>
        <div class="dropdown-content w3-col" style="position: absolute;">
            <a style="height: 5%" href="UserMenu.jsp"><button class="w3-btn w3-xlarge w3-round-xlarge w3-white w3-hover-black" style="font-weight:900;"> User Menu </button></a>
            <a href="SearchItems.jsp"><button class="w3-btn w3-xlarge w3-round-xlarge w3-white w3-hover-black" style="font-weight:900;"> Shopping Menu </button></a>
        </div>
        </div>
        <br><br>
        <br><br>
        <div style=position:absolute;bottom:10px;left:45%>
                <a href=InitialMenu.jsp><button class="w3-btn w3-xlarge w3-round-xlarge w3-deep-purple w3-hover-black" style="font-weight:900;"> Logout </button></a><br><br>
        </div>
    </body>
</html>