<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1" session="false"%>

<%
HttpSession session = request.getSession();
session.setAttribute("email",null);
session.setAttribute("country",null);
%>

<html>
    <title>MyBay</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
    body {font-family: "Lato", sans-serif}

    body, html {height: 100%}
    .bgimg {
        background-color: rgb(77, 1, 77);
        background-image: url('myBayFinal2.jpg');
        background-repeat: no-repeat;
        min-height: 100%;
        background-position: center;
        background-size: 50%;
    }
    </style>
       
    <!--<head>
        <title>MyBay - Initial Menu</title>
    </head>-->
    <body >
        <div class="w3-top">
            <div class="w3-row w3-padding w3-black">
                <div class="w3-col s6">
                    <a href="Login.jsp" class="w3-button w3-block w3-black w3-center">LOGIN</a>
                </div>
                <div class="w3-col s6">
                    <a href="Register.jsp" class="w3-button w3-block w3-black">REGISTER</a>
                </div>
            </div>
            <!--<h1>Welcome to MyBay!</h1><br>
            <img src="myBay.jpg" alt="brand" width="250" height="160"><br><br>-->
            </div>
        
        </div>
        <div class="bgimg w3-display-container">
        </div>
    </body>
</html>