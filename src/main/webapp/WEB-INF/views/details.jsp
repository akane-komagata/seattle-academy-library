<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="UTF-8">
<title>書籍の詳細｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="resources/css/lightbox.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="resources/js/lightbox.js" /></script>
</head>
<body class="wrapper">
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logo2.png" />
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
            <ul>
                <li><a href="<%= request.getContextPath()%>/home" class="menu">Home</a></li>
                <li><a href="<%= request.getContextPath()%>/">ログアウト</a></li>
            </ul>
        </div>
    </header>
    <main>
        <h1>書籍の詳細</h1>
        <div class="content_body detail_book_content">
            <div class="content_left">
                <span>書籍の画像</span>
                <div class="book_thumnail">
                    <a href="${bookDetailsInfo.thumbnailUrl}" data-lightbox="image-1"> <c:if test="${bookDetailsInfo.thumbnailUrl == 'null'}">
                            <img class="book_noimg" src="resources/img/noImg.png">
                        </c:if> <c:if test="${bookDetailsInfo.thumbnailUrl != 'null'}">
                            <img class="book_noimg" src="${bookDetailsInfo.thumbnailUrl}">
                        </c:if> <input type="hidden" name="bookId" value="${bookDetailsInfo.bookId}">
                    </a>
                </div>
                <p>${lendingStatus}</p>
                <c:if test="${!empty error}">
                    <div class="error">${error}</div>
                </c:if>
            </div>
            <div class="content_right">
                <div>
                    <span>書籍名</span>
                    <p>${bookDetailsInfo.title}</p>
                </div>
                <div>
                    <span>著者名</span>
                    <p>${bookDetailsInfo.author}</p>
                </div>
                <div>
                    <span>出版社</span>
                    <p>${bookDetailsInfo.publisher}</p>
                </div>
                <div>
                    <span>出版日</span>
                    <p>${bookDetailsInfo.publishDate}</p>
                </div>
                <div>
                    <span>ISBN</span>
                    <c:if test="${bookDetailsInfo.isbn == 'null'}">
                    <p> </p>
                    </c:if>
                    <c:if test="${bookDetailsInfo.isbn != 'null'}">
                    <p>${bookDetailsInfo.isbn}</p>
                    </c:if>
                </div>
                <div>
                    <span>説明文</span>
                    <c:if test="${bookDetailsInfo.description == 'null'}">
                    <p> </p>
                    </c:if>
                    <c:if test="${bookDetailsInfo.description != 'null'}">
                    <p>${bookDetailsInfo.description}</p>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="edtDelBookBtn_box">
            <form method="post" action="rentBook">
                <button type="submit" value="${bookDetailsInfo.bookId}" name="bookId" ${disabled} class="btn_rentBook" ${rentDisabled}>借りる</button>
            </form>
            <form method="post" action="returnBook">
                <button type="submit" value="${bookDetailsInfo.bookId}" id="bt1" name="bookId" class="btn_returnBook" ${returnDisabled}>返す</button>
            </form>
            <form method="post" action="editBook">
                <!-- EditBookControlerへ -->
                <button type="submit" value="${bookDetailsInfo.bookId}" name="bookId" ${disabled} class="btn_editBook">編集</button>
            </form>
            <form method="post" action="deleteBook">
                <button type="submit" value="${bookDetailsInfo.bookId}" name="bookId" class="btn_deleteBook" ${deleteDisabled}>削除</button>
            </form>
        </div>
    </main>
</body>
</html>
