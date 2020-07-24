<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
    <jsp:include page="../fragmentos/head.jsp" />

    <body>
        <jsp:include page="../fragmentos/navbar.jsp" />

        <!-- Contenido -->
        <div class="container py-4">
            <div class="row">
                <jsp:include page="../fragmentos/sidebar.jsp" />

                <!-- Principal -->
                <div class="col-lg-9 col-md-8">
                    <h3 class="border-bottom pb-2">
                        <core:choose>
                            <core:when test="${accion eq 'agregar' }"><spring:message code="titles.professionals.add" /></core:when>
                            <core:otherwise><spring:message code="titles.professionals.edit" /></core:otherwise>
                        </core:choose>
                    </h3>

                    <div class="card mt-4">
                        <div class="card-body">
                            <form:form method="post" modelAttribute="profesional">
                                <div class="form-group">
                                    <form:label path="nombre"><spring:message code="form.label.name" /></form:label>
                                    <form:input path="nombre" type="text" class="form-control" cssErrorClass="invalid" autocomplete="name" autofocus="autofocus" required="required" />
                                </div>

                                <div class="form-group">
                                    <form:label path="email"><spring:message code="form.label.email" /></form:label>
                                    <form:input path="email" type="email" class="form-control" cssErrorClass="invalid" autocomplete="email" required="required" />
                                </div>

                                <div class="form-group">
                                    <form:label path="telefono"><spring:message code="form.label.phone" /></form:label>
                                    <form:input path="telefono" type="tel" class="form-control" cssErrorClass="invalid" autocomplete="tel" required="required" />
                                </div>

                                <div class="form-group">
                                    <form:label path="enabled"><spring:message code="form.label.enabled" /></form:label>
                                    <form:select path="enabled" class="form-control" cssErrorClass="invalid">
                                        <form:option value="1"><spring:message code="form.label.yes" /></form:option>
                                        <form:option value="0"><spring:message code="form.label.no" /></form:option>
                                    </form:select>
                                </div>

                                <div class="form-group text-right">
                                    <button type="button" data-action="cancelar" class="btn btn-secondary">
                                        <spring:message code="form.button.cancel" />
                                    </button>

                                    <button type="submit" class="btn btn-primary">
                                        <spring:message code="form.button.send" />
                                    </button>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
                <!-- /Principal -->
            </div>
        </div>
        <!-- /Contenido -->

        <jsp:include page="../fragmentos/dependencias.jsp" />

        <script type="text/javascript" src="<core:url value="/res/js/general.js" />"></script>
    </body>
</html>
