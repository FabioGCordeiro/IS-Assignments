<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="ISO-8859-1" session="false"%>

<%
HttpSession session = request.getSession(false);
String user = (String) session.getAttribute("email");

if(user==null){
    response.sendRedirect("Error.jsp");
}
%>


<html>
    <title>MyBay - Search Category</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../lib/w3.css">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <body>
    <header class="w3-container w3-black">
        <h1>Please insert a category</h1>
    </header>

    <div class="w3-container w3-half w3-margin-top">

    <form action="SearchByCategory" class="w3-container w3-card-4">
        <p>
            <input class="w3-input" type="text" name="category" style="width:90%">
            <label class="w3-label w3-validate">Category</label>
            <input type="hidden" name="order" value=0></input>
        </p>
        <input class="w3-btn w3-section w3-deep-purple w3-ripple" type="submit" value="Search">
        
        </form>

    </div>
    <div style=position:absolute;bottom:10px;left:45%>
        <a href=InitialMenu.jsp><button class="w3-btn w3-xlarge w3-round-xlarge w3-deep-purple w3-hover-black" style="font-weight:900;"> Logout </button></a><br><br>
    </div>

    
    </body>
</html>
