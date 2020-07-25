<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
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

                    <core:if test="${not empty error}">
                        <div class="alert alert-danger my-3">${error}</div>
                    </core:if>

                    <div class="card mt-4">
                        <div class="card-body">
                            <form:form method="post" modelAttribute="profesional">
                                <form:hidden path="id" />

                                <div class="form-group">
                                    <form:label path="nombre"><spring:message code="form.label.name" /></form:label>
                                    <form:input path="nombre" type="text" class="form-control" minlength="10" maxlength="100" cssErrorClass="invalid" autocomplete="name" autofocus="autofocus" required="required" />
                                </div>

                                <div class="form-group">
                                    <form:label path="email"><spring:message code="form.label.email" /></form:label>
                                    <core:choose>
                                        <core:when test="${accion eq 'editar'}">
                                            <form:input path="email" type="email" class="form-control" autocomplete="email" required="required" readonly="true" />
                                        </core:when>
                                        <core:otherwise>
                                            <form:input path="email" type="email" class="form-control" minlength="10" maxlength="150" cssErrorClass="invalid" autocomplete="email" required="required" />
                                        </core:otherwise>
                                    </core:choose>
                                </div>

                                <div class="form-group">
                                    <form:label path="telefono"><spring:message code="form.label.phone" /></form:label>
                                    <form:input path="telefono" pattern="[0-9]+" minlength="7" maxlength="20" type="tel" class="form-control" cssErrorClass="invalid" autocomplete="tel" required="required" aria-labelledby="phoneHelp" />
                                    <small id="phoneHelp" class="form-text text-muted"><spring:message code="form.label.phone.help" /></small>
                                </div>

                                <div class="form-group">
                                    <form:label path="enabled"><spring:message code="form.label.enabled" /></form:label>
                                    <form:select path="enabled" class="form-control" cssErrorClass="invalid" aria-labelledby="enabledHelp">
                                        <form:option value="true"><spring:message code="form.label.yes" /></form:option>
                                        <form:option value="false"><spring:message code="form.label.no" /></form:option>
                                    </form:select>
                                    <small id="enabledHelp" class="form-text text-muted"><spring:message code="form.label.enabled.help" /></small>
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
