<%-- 
    Document   : master
    Created on : Jun 5, 2018, 2:04:29 PM
    Author     : pupil
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" class="">
    <head>
        <title>Admin Panel</title>
        <meta charset="UTF-8"><meta name="robots" content="noindex">
        <link rel="canonical" href="https://codepen.io/uplusion23/pen/yzBbXj">
        <link rel="stylesheet" href="css/master.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
        <meta name="viewport" content="width=device-width,initial-scale=1">
    </head>
    <body>

        <div class="container">
            <div class="drawer">
                <a class="navicon" href="#"></a>
                <div class="menu">
                    <a data-menu="dashboard" href="controller?command=master" class="active"><i class="fas fa-home"></i></a>
                        <c:if test="${role == 'ADMIN' or role =='MASTER'}">
                            <c:if test="${role == 'ADMIN'}">
                            <a data-menu="users-delete" href="#" class=""><i class="fas fa-user-minus"></i></a>
                            <a data-menu="user-role" href="#"><i class="fas fa-user-shield"></i></a>
                            </c:if>
                            <c:if test="${role == 'MASTER'}">
                            <a data-menu="add-or-delete-group" href="#" class=""><i class="fas fa-user-friends"></i></a>
                            <a data-menu="user-group" href="#"><i class="fas fa-users"></i></a>
                            <a data-menu="add-or-delete-menu-category" href="#"><i class="fas fa-cart-plus"></i></a>
                            <a data-menu="add-dish" href="#"><i class="fas fa-utensils"></i></a>
                            <a data-menu="make-menu" href="#"><i class="fas fa-clipboard-list"></i></a>
                            <a data-menu="delete-menu" href="#"><i class="fas fa-clipboard-list">-</i></a>
                            </c:if>
                        </c:if>
                    <c:if test="${role == 'USER' or role == 'ADMIN' or role =='MASTER'}">         
                        <a href="controller?command=slider"><i class="fas fa-sign-out-alt"></i></a>
                    </c:if>
                </div>

                <span class="credits"></span>

            </div>

            <div class="content">
                <div class="page active" data-page="dashboard">
                    <div class="header">
                        <div class="title">
                            <h2>Админ панель - ${role}</h2>
                            <h1>${info}</h1>
                        </div>
                    </div>
                    <div class="stats">
                    </div>
                </div>

                <div class="page noflex" data-page="users-delete">
                    <div class="header">
                        <div class="title">
                            <h2>Удалить пользователя</h2>
                        </div>
                    </div>
                    <div class="grid">
                        <div class="user-edit">
                            <div class="header">
                                <span class="icon">
                                    <i class="icon ion-person"></i>
                                </span>
                            </div>
                        </div>
                        <div class="users-table">
                            <div class="users-item header">
                                <div class="table-item noflex">
                                    (Имя | Фамилия | Email)
                                </div>
                            </div>

                            <c:forEach var="user" items="${users}">
                                <form method="POST" action="controller?command=userdelete">
                                    <div class="users-item">
                                        <div class="table-item"> 
                                            ${user.name}
                                        </div>
                                        <div class="table-item"> 
                                            ${user.lastname}
                                        </div>
                                        <div class="table-item"> 
                                            ${user.email}
                                        </div>
                                        <div class="table-item">
                                            <select name="userId" hidden>
                                                <option value="${user.id}" selected hidden></option>
                                            </select>
                                        </div>
                                        <button type="submit"><a>Удалить</a></button>
                                    </div>
                                </form>
                                <hr>
                            </c:forEach>
                        </div>
                    </div>
                </div>

                <div class="page noflex" data-page="add-or-delete-group">
                    <div class="header">
                        <div class="title">
                            <h2>Добавить или удалить группу</h2>
                        </div>
                    </div>
                    <div class="grid">
                        <div class="user-edit">
                            <div class="header">
                                <span class="icon">
                                    <i class="icon ion-person"></i>
                                </span>
                            </div>
                        </div>
                        <div class="users-table">
                            <div class="users-item header">
                                <div class="table-item noflex">
                                    (Группа)
                                </div>
                            </div>
                            <div class="users-item">
                                <div class="table-item"> 
                                    <form method="POST" action="controller?command=addgroup">
                                        <input required type="text" placeholder="название группы" name="nameGroup">
                                        <button type="submit"><a>Добавить</a></button>
                                    </form>
                                    <form method="POST" action="controller?command=deletegroup">
                                        <select required name="groupId">
                                            <c:forEach var="group" items="${groups}">
                                                <option value="${group.id}" >${group.name}</option>
                                            </c:forEach>
                                        </select>
                                        <button type="submit"><a>Удалить</a></button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="page noflex" data-page="user-group">
                    <div class="header">
                        <div class="title">
                            <h2>Добавить группу пользователю</h2>
                        </div>
                    </div>
                    <div class="grid">
                        <div class="user-edit">
                            <div class="header">
                                <span class="icon">
                                    <i class="icon ion-person"></i>
                                </span>
                            </div>
                        </div>
                        <div class="users-table">
                            <div class="users-item header">
                                <div class="table-item noflex">
                                    (Имя | Фамилия | Группа)
                                </div>
                            </div>
                            <c:forEach var="user" items="${users}">
                                <form method="POST" action="controller?command=addusergroup">
                                    <div class="users-item">
                                        <div class="table-item"> 
                                            ${user.name}
                                        </div>
                                        <div class="table-item"> 
                                            ${user.lastname}
                                        </div>
                                        <div class="table-item">
                                            <select required name="groupName">
                                                <c:forEach var="group" items="${groups}">
                                                    <option value="${group.id}">${group.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="table-item">
                                            <select name="userId" hidden>
                                                <option value="${user.id}" selected hidden></option>
                                            </select>
                                        </div>
                                        <button type="submit"><a>Изменить</a></button>
                                    </div>
                                    <hr>
                                </form>
                            </c:forEach>

                        </div>
                    </div>
                </div>

                <div class="page noflex" data-page="user-role">
                    <div class="header">
                        <div class="title">
                            <h2>Изменить роль пользователя</h2>
                        </div>
                    </div>
                    <div class="grid">
                        <div class="user-edit">
                            <div class="header">
                                <span class="icon">
                                    <i class="icon ion-person"></i>
                                </span>
                            </div>
                        </div>
                        <div class="users-table">
                            <div class="users-item header">
                                <div class="table-item noflex">
                                    (Имя | Фамилия | Роль)
                                </div>
                            </div>
                            <c:forEach var="user" items="${users}">
                                <form method="POST" action="controller?command=changerole">
                                    <div class="users-item">
                                        <div class="table-item"> 
                                            ${user.name}
                                        </div>
                                        <div class="table-item"> 
                                            ${user.lastname}
                                        </div>
                                        <div class="table-item">
                                            <select required name="roleName">
                                                <c:forEach var="role" items="${roles}">
                                                    <option value="${role}">${role}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="table-item">
                                            <select name="userId" hidden>
                                                <option value="${user.id}" selected hidden></option>
                                            </select>
                                        </div>
                                        <button type="submit"><a>Изменить</a></button>
                                    </div>
                                </form>
                                <hr>
                            </c:forEach>
                        </div>
                    </div>
                </div>

                <div class="page noflex" data-page="add-or-delete-menu-category">
                    <div class="header">
                        <div class="title">
                            <h2>Добавить или удалить категорию для блюда</h2>
                        </div>
                    </div>
                    <div class="grid">
                        <div class="user-edit">
                            <div class="header">
                                <span class="icon">
                                    <i class="icon ion-person"></i>
                                </span>
                            </div>
                        </div>
                        <div class="users-table">
                            <div class="users-item header">
                                <div class="table-item noflex">
                                    (Категории)
                                </div>
                            </div>
                            <div class="users-item">
                                <div class="table-item"> 
                                    <form method="POST" action="controller?command=addcategory">
                                        <input required type="text" placeholder="Название категории" name="nameCategory">
                                        <button type="submit"><a>Добавить</a></button>
                                    </form>

                                    <form method="POST" action="controller?command=deletecategory">
                                        <select required name="categoryId">
                                            <c:forEach var="category" items="${categories}">
                                                <option value="${category.id}">${category.name}</option>
                                            </c:forEach>
                                        </select>
                                        <button type="submit"><a>Добавить</a></button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="page noflex" data-page="add-dish">
                    <div class="header">
                        <div class="title">
                            <h2>Добавить или удалить блюдо</h2>
                        </div>
                    </div>
                    <div class="grid">
                        <div class="user-edit">
                            <div class="header">
                                <span class="icon">
                                    <i class="icon ion-person"></i>
                                </span>
                            </div>
                        </div>
                        <div class="users-table">
                            <div class="users-item header">
                                <div class="table-item noflex">
                                    (Название блюда | Категория)
                                </div>
                            </div>
                            <div class="users-item">
                                <div class="table-item"> 
                                    <form method="POST" action="controller?command=addish">
                                        <input required type="text" placeholder="название блюда" name="dish_name">
                                        <select required name="category_select">
                                            <option selected disabled hidden>Категория</option>
                                            <c:forEach var="category" items="${categories}">
                                                <option value="${category.id}">${category.name}</option>
                                            </c:forEach>
                                        </select>
                                        <button type="submit"><a>Добавить</a></button>
                                    </form>


                                    <div class="dishList">
                                        <h2>Имеющиеся блюда: </h2>
                                        <c:forEach var="dish" items="${dishList}">
                                            <form method="POST" action="controller?command=deletedish">
                                                <select hidden="" name="disheId">
                                                <option selected hidden value="${dish.id}"></option>
                                            </select><b><p>${dish.name} | ${dish.category.name} <button type="submit"><a>Удалить</a></button></p></b>
                                            <hr>
                                            </form>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="page noflex" data-page="make-menu">
                    <div class="header">
                        <div class="title">
                            <h2>Составить меню</h2>
                        </div>
                    </div>
                    <div class="grid">
                        <div class="user-edit">
                            <div class="header">
                                <span class="icon">
                                    <i class="icon ion-person"></i>
                                </span>
                            </div>
                        </div>
                        <div class="users-table">
                            <div class="users-item header">
                                <div class="table-item noflex">
                                    (Блюда | Группа | Дата)
                                </div>
                            </div>
                            <div class="users-item">
                                <div class="table-item"> 
                                    <form class="menuCategory" method="POST" action="controller?command=addmenu">
                                        <c:forEach var="entry" items="${mapCategories}">
                                            <div>
                                                ${entry.key.name}
                                                <select name="kats[]">
                                                    <c:forEach var="dishes" items="${entry.value}">
                                                        <option selected disabled hidden>${entry.key.name}</option>
                                                        <option value="${dishes.id}">${dishes.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </c:forEach>
                                        <div>
                                            Группа
                                            <select name="kookId" class="kookSelect">
                                                <option selected disabled hidden>Группа</option>
                                                <c:forEach var="kooks" items="${groups}">
                                                    <option value="${kooks.id}">${kooks.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <input name="menuDate" class="date" required type="date">
                                        <button type="submit"><a>Добавить</a></button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="page noflex" data-page="delete-menu">
                    <div class="header">
                        <div class="title">
                            <h2>Удалить меню на выбранный день</h2>
                        </div>
                    </div>
                    <div class="grid">
                        <div class="user-edit">
                            <div class="header">
                                <span class="icon">
                                    <i class="icon ion-person"></i>
                                </span>
                            </div>
                        </div>
                        <div class="users-table">
                            <div class="users-item header">
                                <div class="table-item noflex">
                                    (Список)
                                </div>
                            </div>
                            <div class="users-item">
                                <div class="table-item"> 

                                    <c:forEach var="menuAllList" items="${menuall}">
                                        <form method="POST" action="controller?command=deletefrommenu">
                                            <select hidden required name="menuId">
                                                <option value="${menuAllList.id}"></option>
                                            </select>
                                            <fmt:formatDate value="${menuAllList.dateM}" pattern="dd-MM-YYYY"></fmt:formatDate>
                                            <c:forEach var="dishes" items="${menuAllList.dish}">
                                                ${dishes.name}
                                            </c:forEach>
                                            <button type="submit"><a>Удалить</a></button>
                                        </form>
                                        <hr>
                                    </c:forEach>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <div class="sidebar">
        </div>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script type="text/javascript" src="js/master.js"></script>
    </body></html>