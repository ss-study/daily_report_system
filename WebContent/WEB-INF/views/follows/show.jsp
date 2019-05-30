<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">

        <h2>あなたがフォローしている社員</h2>

        <p><a href="<c:url value='/follows/index' />">フォロー中の社員の日報</a></p>

        <h3>【あなたがフォローしている社員 一覧】</h3>
        <table id="report_list">
            <tbody>
                <tr>
                    <th class="report_name">氏名</th>
                </tr>
                <c:forEach var="employee" items="${employees}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="report_name">
                            <a href="<c:url value='/individual/show?employeeid=${employee.id}' />">
                                <c:out value="${employee.name}" />
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </c:param>
</c:import>