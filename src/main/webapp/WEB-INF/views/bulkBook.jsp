<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page contentType="text/html; charset=utf8"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>ホーム｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
</head>
<body class="wrapper">
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logo.png" />
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>
            </ul>
        </div>
    </header>
    <main>
        <form action="<%=request.getContextPath()%>/bulkRegist" method="post" enctype="multipart/form-data" id="data_upload_form">
            <h1>一括登録</h1>
            <div class="bulk_form">
                <h2>CSVファイルをアップロードすることで書籍を一括登録できます。</h2>
                <div class="caution">
                    <p>「書籍名、著者名、出版社、出版日、ISBN、説明文」の形式で記載してください。</p>
                    <p>※サムネイル画像は一括登録できません。編集画像で1冊単位で登録してください。</p>
                </div>
                <c:if test="${!empty errorlist}">
                    <div class="error">
                     <c:forEach var="error" items="${errorlist}">
                     ${error}<br>
                     </c:forEach>
                    </div>
                </c:if>
                <input type="file" name="csv_file" accept=".csv" required>
                <div class="addBookBtn_box">
                    <button type="submit" id="add-btm" class="btn_bulkRegist">一括登録</button>
                </div>
                <c:if test="${!empty resultMessage}">
                    <div class="error_msg">${resultMessage}</div>
                </c:if>
            </div>
        </form>
    </main>
</body>
</html>
