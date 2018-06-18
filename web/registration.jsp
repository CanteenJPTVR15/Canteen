<%-- 
    Document   : registration
    Created on : Apr 10, 2018, 2:07:59 PM
    Author     : pupil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet" type="text/css" href="css/registration.css">
	<title>Registration</title>
</head>
<body>
<div id="container">
  <h1>Registration</h1>
  <form method="POST" action="controller?command=REGUSER">
      <input type="text" name="login" placeholder="Login" required>
    <input type="text" name="name" placeholder="Name" required>
    <input type="text" name="lastname" placeholder="LastName" required>
    <input type="email" name="email" placeholder="E-mail" required>
    <input type="password" name="pass" placeholder="Password" required>
    <input type="password" name="pass2" placeholder="Password" required>
    <button type="submit">Зарегистрироваться</button>
    </form>
    <a href="controller?command=login" style="text-decoration: none">Back</a>
</div>
</body>
</html>
