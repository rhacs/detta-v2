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
                            <core:when test="${accion eq 'agregar' }"><spring:message code="titles.clients.add" /></core:when>
                            <core:otherwise><spring:message code="titles.clients.edit" /></core:otherwise>
                        </core:choose>
                    </h3>

                    <core:if test="${not empty error}">
                        <div class="alert alert-danger my-3">${error}</div>
                    </core:if>

                    <div class="card mt-4">
                        <div class="card-body">
                            <form:form method="post" modelAttribute="cliente">
                                <form:hidden path="id" />
                                <form:hidden path="usuarioId" />

                                <div class="form-group">
                                    <form:label path="nombre"><spring:message code="form.label.client_name" /></form:label>
                                    <form:input path="nombre" type="text" class="form-control" cssErrorClass="invalid" autocomplete="name" autofocus="autofocus" required="required" />
                                </div>

                                <div class="form-group">
                                    <form:label path="rut"><spring:message code="form.label.rut" /></form:label>
                                    <core:choose>
                                        <core:when test="${accion eq 'editar'}">
                                            <form:input path="rut" type="text" class="form-control" readonly="true" />
                                        </core:when>
                                        <core:otherwise>
                                            <form:input path="rut" type="text" class="form-control" cssErrorClass="invalid" required="required" />
                                        </core:otherwise>
                                    </core:choose>
                                </div>

                                <div class="form-group">
                                    <form:label path="email"><spring:message code="form.label.email" /></form:label>
                                    <core:choose>
                                        <core:when test="${accion eq 'editar'}">
                                            <form:input path="email" type="email" class="form-control" autocomplete="email" required="required" readonly="true" />
                                        </core:when>
                                        <core:otherwise>
                                            <form:input path="email" type="email" class="form-control" cssErrorClass="invalid" autocomplete="email" required="required" />
                                        </core:otherwise>
                                    </core:choose>
                                </div>

                                <div class="form-group">
                                    <form:label path="telefono"><spring:message code="form.label.phone" /></form:label>
                                    <form:input path="telefono" type="tel" class="form-control" cssErrorClass="invalid" autocomplete="tel" required="required" />
                                </div>

                                <div class="form-group">
                                    <form:label path="giro"><spring:message code="form.label.giro" /></form:label>
                                    <form:input path="giro" type="text" class="form-control" cssErrorClass="invalid" required="required" />
                                </div>

                                <div class="form-group">
                                    <form:label path="empleados"><spring:message code="form.label.employees" /></form:label>
                                    <form:input path="empleados" type="number" class="form-control" min="1" cssErrorClass="invalid" required="required" />
                                </div>

                                <div class="form-group">
                                    <form:label path="tipo"><spring:message code="form.label.client_type" /></form:label>
                                    <form:select path="tipo" class="form-control" cssErrorClass="invalid" required="required">
                                        <form:option value="1"><spring:message code="form.label.client_type.principal" /></form:option>
                                        <form:option value="2"><spring:message code="form.label.client_type.contractor" /></form:option>
                                        <form:option value="3"><spring:message code="form.label.client_type.subcontractor" /></form:option>
                                        <form:option value="4"><spring:message code="form.label.client_type.transitional" /></form:option>
                                    </form:select>
                                </div>

                                <div class="form-group">
                                    <form:label path="enabled"><spring:message code="form.label.enabled" /></form:label>
                                    <form:select path="enabled" class="form-control" cssErrorClass="invalid" required="required">
                                        <form:option value="true"><spring:message code="form.label.yes" /></form:option>
                                        <form:option value="false"><spring:message code="form.label.no" /></form:option>
                                    </form:select>
                                </div>

                                <div class="form-group">
                                    <form:label path="profesionalId"><spring:message code="form.label.professional" /></form:label>
                                    <form:select path="profesionalId" class="form-control" required="required">
                                        <core:forEach items="${profesionales}" var="profesional">
                                            <form:option value="${profesional.getId()}">${profesional.getNombre()}</form:option>
                                        </core:forEach>
                                    </form:select>
                                </div>

                                <div class="form-group text-right mb-0">
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
