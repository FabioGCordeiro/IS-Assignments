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
    <title>MyBay</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    
    
    <body style="background-image: url('myBayFinal2.jpg');">
            <div class="w3-top">
                <div class="w3-row w3-padding w3-black">
                    <div class="w3-col w3-block w3-black w3-left w3-bar-item w3-quarter" style="text-align:center;font-size:200%;">Search by:</div>
                    <div class="w3-col s4 w3-dropdown-hover ">
                        <div class="w3-button w3-block w3-black" style="font-size:150%;">Menu
                        <div class="w3-dropdown-content w3-bar-block w3-border w3-black">
                                <a class="w3-bar-item "><form style="width:100%" action="SearchAllItems">
                                        <input type=hidden name=order value=0></input>
                                        <button style="width:100%" type="submit"> Show All Items </button>
                                </form></a>
                                <a href="SearchByCategory.jsp" class="w3-bar-item "><button style="width: 100%"> Search By Category </button><br></br></a>
                                <a class="w3-bar-item "><form action="SearchByCountry">
                                        <input type=hidden name=order value=0></input>
                                        <button style="width:100%" type="submit"> Search By Country </button>
                                </form></a>
                                <a href="SearchItemsByPrice.jsp" class="w3-bar-item" ><button style="width:100%"> Search By Price </button><br></br></a>
                                <a href="SearchItemsByDate.jsp" class="w3-bar-item" ><button style="width:100%"> Search Items Posted After Date </button><br></br></a>
                        </div>
                        </div>
                    </div>
                </div>
                </div>
                <div style=position:absolute;bottom:10px;left:45%>
                        <a href=InitialMenu.jsp><button class="w3-btn w3-xlarge w3-round-xlarge w3-deep-purple w3-hover-black" style="font-weight:900;"> Logout </button></a><br><br>
                </div>
        </body>
</html>