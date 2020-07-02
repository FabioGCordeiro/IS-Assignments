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
<title>MyBay - Edit Account Information</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../lib/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<body>

<header class="w3-container w3-black">
  <h1>Please enter your credentials before editing information:</h1>
</header>

<div class="w3-container w3-half w3-margin-top">

<form action="CheckUserCredentials" class="w3-container w3-card-4">

<p>
<input class="w3-input" type="text" name="email" style="width:90%">
<label class="w3-label w3-validate">Email</label></p>
<p>
<input class="w3-input" type="password" name="password" style="width:90%">
<label class="w3-label w3-validate">Password</label></p>

<p>
<button class="w3-btn w3-section w3-deep-purple w3-ripple" type="submit" value="Confirm"> Confirm </button>
<p class="w3-right"><a href="InitialMenu.jsp">Logout!</a></p></p>
</form>


</div>

</body>
</html> 
