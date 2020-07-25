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
                        <core:choose>
                            <core:when test="${accion eq 'agregar' }"><spring:message code="titles.accidents.add" /></core:when>
                            <core:otherwise><spring:message code="titles.accidents.edit" /></core:otherwise>
                        </core:choose>
                    </h3>

                    <core:if test="${not empty error}">
                        <div class="alert alert-danger my-3">${error}</div>
                    </core:if>

                    <div class="card mt-4">
                        <div class="card-body">
                            <form:form method="post" modelAttribute="accidente">
                                <form:hidden path="id" />
                                <sec:authorize access="hasAuthority('ROLE_CLIENT')">
                                <input type="hidden" name="clienteId" id="clienteId" value="${cliente.getId()}">
                                </sec:authorize>

                                <div class="form-group">
                                    <form:label path="fecha"><spring:message code="form.label.date" /></form:label>
                                    <form:input path="fecha" class="form-control" placeholder="aaaa/mm/dd" pattern="[0-9]{4}\/[0-9]{2}\/[0-9]{2}" required="required" autofocus="autofocus" />
                                </div>

                                <div class="form-group">
                                    <form:label path="hora"><spring:message code="form.label.time" /></form:label>
                                    <form:input path="hora" class="form-control" type="time" pattern="[0-9]{2}[:][0-9]{2}" required="required"/>
                                </div>

                                <div class="form-group">
                                    <form:label path="direccion"><spring:message code="form.label.address" /></form:label>
                                    <form:input path="direccion" class="form-control" minlength="10" maxlength="250" required="required" />
                                </div>

                                <div class="form-group">
                                    <form:label path="lugar"><spring:message code="form.label.place" /></form:label>
                                    <form:input path="lugar" class="form-control" minlength="10" maxlength="250" required="required" />
                                </div>

                                <div class="form-group">
                                    <form:label path="circunstancia"><spring:message code="form.label.circumstance" /></form:label>
                                    <form:input path="circunstancia" class="form-control" minlength="10" maxlength="250" required="required" />
                                </div>

                                <div class="form-group">
                                    <form:label path="detalles"><spring:message code="form.label.details" /></form:label>
                                    <form:textarea path="detalles" class="form-control" rows="4" minlength="10" maxlength="2000" required="required" />
                                </div>

                                <div class="form-group">
                                    <form:label path="clasificacion"><spring:message code="form.label.accident_class" /></form:label>
                                    <form:select path="clasificacion" class="form-control">
                                        <form:option value="1"><spring:message code="form.label.accident_class.mild" /></form:option>
                                        <form:option value="2"><spring:message code="form.label.accident_class.serious" /></form:option>
                                        <form:option value="3"><spring:message code="form.label.accident_class.fatal" /></form:option>
                                        <form:option value="4"><spring:message code="form.label.accident_class.other" /></form:option>
                                    </form:select>
                                </div>

                                <div class="form-group">
                                    <form:label path="tipo"><spring:message code="form.label.accident_type" /></form:label>
                                    <form:select path="tipo" class="form-control">
                                        <form:option value="1"><spring:message code="form.label.accident_type.work" /></form:option>
                                        <form:option value="2"><spring:message code="form.label.accident_type.journey" /></form:option>
                                    </form:select>
                                </div>

                                <div class="form-group">
                                    <form:label path="evidencia"><spring:message code="form.label.evidence" /></form:label>
                                    <form:select path="evidencia" class="form-control">
                                        <form:option value="1"><spring:message code="form.label.evidence.certificate" /></form:option>
                                        <form:option value="2"><spring:message code="form.label.evidence.statement" /></form:option>
                                        <form:option value="3"><spring:message code="form.label.evidence.witnesses" /></form:option>
                                        <form:option value="4"><spring:message code="form.label.evidence.other" /></form:option>
                                    </form:select>
                                </div>

                                <sec:authorize access="hasAnyAuthority('ROLE_ADMIN', 'ROLE_STAFF')">
                                <div class="form-group">
                                    <form:label path="clienteId"><spring:message code="form.label.client" /></form:label>
                                    <form:select path="clienteId" class="form-control">
                                        <core:forEach items="${clientes}" var="cliente">
                                            <form:option value="${cliente.getId()}">${cliente.getNombre()}</form:option>
                                        </core:forEach>
                                    </form:select>
                                </div>
                                </sec:authorize>

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
