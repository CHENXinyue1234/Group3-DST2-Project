<%--
  Created by IntelliJ IDEA.
  User: hello
  Date: 2019-12-3
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="generator" content="">
    <title>Dashboard Template · Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="<%=request.getContextPath()%>/static/bootstrap/css/bootstrap.css" rel="stylesheet">
    <script src="<%=request.getContextPath()%>/static/jquery/jquery-3.4.1.js"></script>
    <script src="<%=request.getContextPath()%>/static/bootstrap/js/bootstrap.bundle.min.js"></script>
    <!-- Custom styles for this template -->
    <link href="<%=request.getContextPath()%>/static/css/app.css" rel="stylesheet">
    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }
    </style>
</head>
<body>
<nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
    <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">Precision Medicine Matching System</a>

</nav>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="nav.jsp">
            <jsp:param name="active" value="drugs"/>
        </jsp:include>

        <c:choose>
            <c:when test="${not empty searchResult}">
                <c:set var="drugs" value="${searchResult}"/>
            </c:when>
            <c:otherwise>
                <c:set var="drugs" value="${drugs}"/>
            </c:otherwise>
        </c:choose>

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h2>Drugs</h2>
            </div>
            <c:if test="${not empty error}">
                <div class="alert alert-warning" role="alert">
                        ${error}
                </div>
            </c:if>
            <c:if test="${empty error}">
                <div class="table-responsive">
                    <table class="table table-striped table-sm">
                        <thead>
                        <tr>
                            <th>Drug IDs</th>
                            <th>Name</th>
                            <th>Drug Url</th>
                            <th>Biomarker</th>
                            <th>Drug Label IDs</th>
                            <th>Dosing Guideline IDs</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${drugs}" var="item">
                            <tr>
                                <td>${item.id}</td>
                                <td>${item.name}</td>
                                <td>${item.drugUrl}</td>
                                <td>${item.biomarker}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${empty item.drugLabelId}">
                                            None
                                        </c:when>
                                        <c:otherwise>
                                            <c:forTokens items="${item.drugLabelId}" delims="," var="labelId">
                                                ${labelId}<br/>
                                            </c:forTokens>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${empty item.dosingGuidelineId}">
                                            None
                                        </c:when>
                                        <c:otherwise>
                                            <c:forTokens items="${item.dosingGuidelineId}" delims="," var="dgId">
                                                ${dgId}<br/>
                                            </c:forTokens>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                </div>
            </c:if>
        </main>
    </div>
</div>
</body>
</html>
