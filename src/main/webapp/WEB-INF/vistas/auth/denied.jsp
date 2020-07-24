<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
    <jsp:include page="../fragmentos/head.jsp" />

    <body class="d-flex flex-column align-items-center justify-content-center py-4 min-vh-100">
        <!-- Contenido -->
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card text-white bg-danger">
                        <div class="card-header">
                            <spring:message code="titles.access_denied" />
                        </div>

                        <div class="card-body">
                            <p class="card-text">
                                <spring:message code="content.access_denied" />
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /Contenido -->

        <jsp:include page="../fragmentos/dependencias.jsp" />
    </body>
</html>
