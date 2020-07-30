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
                                <form:hidden path="usuario.id" />
                                <form:hidden path="usuario.rol.id" />

                                <div class="form-group">
                                    <form:label path="nombre"><spring:message code="form.label.client_name" /></form:label>
                                    <form:input path="nombre" class="form-control" cssErrorClass="form-control is-invalid" autofocus="autofocus" />
                                    <form:errors path="nombre" cssClass="invalid-feedback" />
                                </div>

                                <div class="form-group">
                                    <form:label path="rut"><spring:message code="form.label.rut" /></form:label>
                                    <core:choose>
                                        <core:when test="${accion eq 'editar'}">
                                            <form:input path="rut" class="form-control" readonly="true" aria-labelledby="rutHelp" />
                                        </core:when>
                                        <core:otherwise>
                                            <form:input path="rut" class="form-control" cssErrorClass="form-control is-invalid" aria-labelledby="rutHelp" />
                                        </core:otherwise>
                                    </core:choose>
                                    <form:errors path="rut" cssClass="invalid-feedback" />
                                    <small id="rutHelp" class="form-text text-muted"><spring:message code="form.label.rut.help" /></small>
                                </div>

                                <div class="form-group">
                                    <form:label path="usuario.email"><spring:message code="form.label.email" /></form:label>
                                    <core:choose>
                                        <core:when test="${accion eq 'editar'}">
                                            <form:input path="usuario.email" class="form-control" readonly="true" />
                                        </core:when>
                                        <core:otherwise>
                                            <form:input path="usuario.email" class="form-control" cssErrorClass="form-control is-invalid" />
                                        </core:otherwise>
                                    </core:choose>
                                    <form:errors path="usuario.email" cssClass="invalid-feedback" />
                                </div>

                                <div class="form-group">
                                    <form:label path="telefono"><spring:message code="form.label.phone" /></form:label>
                                    <form:input path="telefono" class="form-control" aria-labelledby="phoneHelp" cssErrorClass="form-control is-invalid" />
                                    <form:errors path="telefono" cssClass="invalid-feedback" />
                                    <small id="phoneHelp" class="form-text text-muted"><spring:message code="form.label.phone.help" /></small>
                                </div>

                                <div class="form-group">
                                    <form:label path="giro"><spring:message code="form.label.giro" /></form:label>
                                    <form:input path="giro" class="form-control" cssErrorClass="form-control is-invalid" />
                                    <form:errors path="giro" cssClass="invalid-feedback" />
                                </div>

                                <div class="form-group">
                                    <form:label path="empleados"><spring:message code="form.label.employees" /></form:label>
                                    <form:input path="empleados" class="form-control" cssErrorClass="form-control is-invalid" />
                                    <form:errors path="empleados" cssClass="invalid-feedback" />
                                </div>

                                <div class="form-group">
                                    <form:label path="tipo"><spring:message code="form.label.client_type" /></form:label>
                                    <form:select path="tipo" class="form-control" cssErrorClass="form-control is-invalid">
                                        <form:option value="1"><spring:message code="form.label.client_type.principal" /></form:option>
                                        <form:option value="2"><spring:message code="form.label.client_type.contractor" /></form:option>
                                        <form:option value="3"><spring:message code="form.label.client_type.subcontractor" /></form:option>
                                        <form:option value="4"><spring:message code="form.label.client_type.transitional" /></form:option>
                                    </form:select>
                                    <form:errors path="tipo" cssClass="invalid-feedback" />
                                </div>

                                <div class="form-group">
                                    <form:label path="usuario.enabled"><spring:message code="form.label.enabled" /></form:label>
                                    <form:select path="usuario.enabled" class="form-control" cssErrorClass="form-control is-invalid" aria-labelledby="enabledHelp">
                                        <form:option value="true"><spring:message code="form.label.yes" /></form:option>
                                        <form:option value="false"><spring:message code="form.label.no" /></form:option>
                                    </form:select>
                                    <form:errors path="usuario.enabled" cssClass="invalid-feedback" />
                                    <small id="enabledHelp" class="form-text text-muted"><spring:message code="form.label.enabled.help" /></small>
                                </div>

                                <div class="form-group">
                                    <form:label path="profesional.id"><spring:message code="form.label.professional" /></form:label>
                                    <form:select path="profesional.id" class="form-control">
                                        <form:options items="${profesionales}" itemLabel="nombre" itemValue="id" />
                                    </form:select>
                                    <form:errors path="profesional.id" cssClass="invalid-feedback" />
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
