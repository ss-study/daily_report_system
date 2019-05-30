<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <title>日報管理システム</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <div id="header_menu">

                    <div class="header_left_content_wrapper">
                        <div id="header_title">
                            <h1><a href="<c:url value='/' />">日報管理システム</a></h1>
                        </div>
                    </div>

                    <c:if test="${sessionScope.login_employee != null}">

                        <div class="header_left_content_wrapper">
                            <div class="header_menu_caption">メニュー</div>
                            <div class="header_menu_contents">
                                <span class="header_menu_content"><a href="<c:url value='/reports/new' />">新規日報</a></span>
                                <span class="header_menu_content"><a href="<c:url value='/reports/index' />">日報一覧</a></span>
                                <span class="header_menu_content"><a href="<c:url value='/follows/index' />">フォロー</a></span>
                            </div>
                        </div>

                        <c:if test="${sessionScope.login_employee.admin_flag == 1}">
                            <div class="header_left_content_wrapper">
                                <div class="header_menu_caption">管理者メニュー</div>
                                <div class="header_menu_contents">
                                    <span class="header_menu_content"><a href="<c:url value='/employees/new' />">新規従業員の登録</a></span>
                                    <span class="header_menu_content"><a href="<c:url value='/employees/index' />">従業員管理</a></span>
                                </div>
                            </div>
                        </c:if>

                        <div class="header_right_content_wrapper">
                            <div class="header_menu_caption"><c:out value="${sessionScope.login_employee.name}" />&nbsp;さん</div>
                            <div class="header_menu_contents">
                                <span class="header_menu_content"><a href="<c:url value='/logout' />">ログアウト</a></span>
                            </div>
                        </div>

                    </c:if>
                </div>
            </div>
            <div id="content">
                ${param.content}
            </div>
            <div id="footer">
                Daily Report System ver. 2019.05.15 produced by Shota Sekino.
            </div>
        </div>
    </body>
</html>