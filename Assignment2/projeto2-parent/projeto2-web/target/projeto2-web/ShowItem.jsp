<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1" session="false"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%
HttpSession session = request.getSession(false);
String user = (String) session.getAttribute("email");

if(user==null){
    response.sendRedirect("Error.jsp");
}
%>


<html>
<title>MyBay - UserMenu</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../lib/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<body>

    <p>Name:  ${name} </p>
    <p>Category: ${category}</p>
    <p>Country of Origin:  ${countryOrigin} </p>
    <p>Price: ${price}&euro;</p>

    <div>
        <img src = "${photo}" width="100px" height="100px"></img>
    </div>

    <c:if test = "${hasPermission eq true}">
            <a href="EditItem.jsp"><button class="w3-btn w3-section w3-deep-purple w3-ripple"> Edit Item</button></a><br><br>
            <a href="DeleteItem.jsp"><button class="w3-btn w3-section w3-deep-purple w3-ripple">Delete Item</button></a><br><br>
    </c:if>



    <div style=position:absolute;bottom:10px;left:50%>
            <a href=InitialMenu.jsp><button class="w3-btn w3-xlarge w3-round-xlarge w3-deep-purple w3-hover-black" style="font-weight:900;"> Logout </button></a><br><br>
    </div>
</body>
</html>