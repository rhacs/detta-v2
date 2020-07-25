<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
    <jsp:include page="../fragmentos/head.jsp" />

    <body class="d-flex flex-column align-items-center justify-content-center bg-info py-4 min-vh-100">
        <!-- Contenido -->
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-4 col-md-6">
                    <form:form action="./login" method="post" modelAttribute="usuario">
                        <h1 class="text-center text-white mb-4">detta</h1>

                        <core:if test="${param.error}">
                            <div class="alert alert-warning"><spring:message code="form.error.bad_credentials" /></div>
                        </core:if>

                        <core:if test="${param.logout}">
                            <div class="alert alert-success"><spring:message code="form.success.logout" /></div>
                        </core:if>

                        <div class="card">
                            <div class="card-header text-right">
                                <button type="button" data-toggle="modal" data-target="#lenguaje" class="btn btn-sm"><i class="fas fa-language fa-fw"></i></button>
                            </div>
                            <div class="card-body p-5">
                                
                                <div class="form-group">
                                    <form:label path="email"><spring:message code="form.label.email" /></form:label>
                                    <form:input path="email" type="email" class="form-control" autocomplete="email" autofocus="autofocus" required="required" />
                                </div>

                                <div class="form-group">
                                    <form:label path="password"><spring:message code="form.label.password" /></form:label>
                                    <form:password path="password" class="form-control" required="required" />
                                </div>

                                <div class="form-group mb-0">
                                    <form:button class="btn btn-primary w-100 mt-4 py-3"><spring:message code="form.button.login" /></form:button>
                                </div>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
        <!-- /Contenido -->

        <!-- Modal: Lenguaje -->
        <div class="modal fade" id="lenguaje" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-sm modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-body">
                        <button type="button" class="close" data-dismiss="modal" aria-label="<spring:message code="form.button.close" />">
                            <span aria-hidden="true">&times;</span>
                        </button>

                        <div class="list-group mt-5">
                            <a href="?lang=es" class="list-group-item list-group-item-action">
                                <span class="flag-icon flag-icon-es mr-2"></span>
                                Espa&ntilde;ol
                            </a>

                            <a href="?lang=en" class="list-group-item list-group-item-action">
                                <span class="flag-icon flag-icon-gb mr-2"></span>
                                English
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /Modal: Lenguaje -->

        <jsp:include page="../fragmentos/dependencias.jsp" />
    </body>
</html>
