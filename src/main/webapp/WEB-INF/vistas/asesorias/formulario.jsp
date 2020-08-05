<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
                        <spring:message code="titles.consulting" />
                    </h3>

                    <core:if test="${not empty error}">
                        <div class="alert alert-danger my-3">${error}</div>
                    </core:if>

                    <div class="card mt-4">
                        <div class="card-body">
                            <form:form method="post" modelAttribute="asesoria">
                                <form:hidden path="id" />

                                <div class="form-group">
                                    <form:label path="fecha"><spring:message code="form.label.date" /></form:label>
                                    <form:input path="fecha" cssClass="form-control" cssErrorClass="form-control is-invalid" />
                                    <form:errors path="fecha" cssClass="invalid-feedback" />
                                </div>

                                <div class="form-group">
                                    <form:label path="hora"><spring:message code="form.label.time" /></form:label>
                                    <form:input path="hora" cssClass="form-control" cssErrorClass="form-control is-invalid" />
                                    <form:errors path="hora" cssClass="invalid-feedback" />
                                </div>

                                <div class="form-group">
                                    <form:label path="direccion"><spring:message code="form.label.address" /></form:label>
                                    <form:input path="direccion" cssClass="form-control" cssErrorClass="form-control is-invalid" />
                                    <form:errors path="direccion" cssClass="invalid-feedback" />
                                </div>

                                <div class="form-group">
                                    <form:label path="tema"><spring:message code="form.label.topic" /></form:label>
                                    <form:input path="tema" cssClass="form-control" cssErrorClass="form-control is-invalid" />
                                    <form:errors path="tema" cssClass="invalid-feedback" />
                                </div>

                                <div class="form-group">
                                    <form:label path="fiscalizador"><spring:message code="form.label.inspector" /></form:label>
                                    <form:input path="fiscalizador" cssClass="form-control" cssErrorClass="form-control is-invalid" />
                                    <form:errors path="fiscalizador" cssClass="invalid-feedback" />
                                </div>

                                <div class="form-group">
                                    <form:label path="departamento"><spring:message code="form.label.department" /></form:label>
                                    <form:input path="departamento" cssClass="form-control" cssErrorClass="form-control is-invalid" />
                                    <form:errors path="departamento" cssClass="invalid-feedback" />
                                </div>

                                <div class="form-group">
                                    <form:label path="descripcion"><spring:message code="form.label.description" /></form:label>
                                    <form:textarea path="descripcion" cssClass="form-control" cssErrorClass="form-control is-invalid" rows="4" />
                                    <form:errors path="descripcion" cssClass="invalid-feedback" />
                                </div>

                                <div class="form-group">
                                    <form:label path="estado"><spring:message code="form.label.status" /></form:label>
                                    <form:select path="estado" cssClass="form-control">
                                        <form:option value="1"><spring:message code="form.label.status.pendiente" /></form:option>
                                        <form:option value="2"><spring:message code="form.label.status.en_proceso" /></form:option>
                                        <form:option value="3"><spring:message code="form.label.status.realizado" /></form:option>
                                    </form:select>
                                </div>

                                <div class="form-group">
                                    <form:label path="cliente.id"><spring:message code="form.label.client" /></form:label>
                                    <form:select path="cliente.id" cssClass="form-control">
                                        <form:options items="${clientes}" itemLabel="usuario.nombre" itemValue="id" />
                                    </form:select>
                                </div>

                                <div class="form-group">
                                    <form:label path="profesional.id"><spring:message code="form.label.professional" /></form:label>
                                    <form:select path="profesional.id" cssClass="form-control">
                                        <form:options items="${profesionales}" itemLabel="usuario.nombre" itemValue="id" />
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
