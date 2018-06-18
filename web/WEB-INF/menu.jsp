<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Menu</title>
        <meta charset="UTF-8">
        <meta charset="utf-8">
        <link href="http://fontawesome.io/assets/font-awesome/css/font-awesome.css" rel="stylesheet"/>
	<link href="https://fonts.googleapis.com/css?family=Dosis" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Lobster" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="fonts/font-awesome/css/font-awesome.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
        <meta name="viewport" content="width=device-width,initial-scale=1">
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
        <link rel="stylesheet" href="css/menu.css">
    </head>
    <body>
        <div>
            <c:if test="${role == 'ADMIN'}"><a href="controller?command=master"><i class="fas fa-users-cog"></i></a></c:if>
            <c:if test="${role == 'MASTER'}"><a href="controller?command=master"><i class="fas fa-users-cog"></i></a></c:if>
            </div>
            <div class="menu-primary">
            <c:forEach var="menuList" items="${menus}">
                <div class="box-wrapper">
		<img src="http://www.freefoodphotos.com/imagelibrary/herbs/slides/chilis.jpg">
		<div class="box-content">

			<div class="title">
                            <div class="title"><fmt:formatDate value="${menuList.dateM}" pattern="EEEEE"></fmt:formatDate></div>
			</div>
			<div class="description">
                            <c:forEach var="dishes" items="${menuList.dish}">
                                <b>${dishes.name}</b> <br>
                            </c:forEach>
			</div>
			<span class="price">${menuList.groupname.name}</span>
			<div class="footer">
                            
			</div>
		</div>
                </div>
            </c:forEach>
        </div>
    
    </body>

</html>