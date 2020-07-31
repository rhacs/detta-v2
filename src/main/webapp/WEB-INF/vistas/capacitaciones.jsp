<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
    <jsp:include page="./fragmentos/head.jsp" />

    <body>
        <jsp:include page="./fragmentos/navbar.jsp" />

        <!-- Contenido -->
        <div class="container py-4">
            <div class="row">
                <jsp:include page="./fragmentos/sidebar.jsp" />

                <!-- Principal -->
                <div class="col-lg-9 col-md-8">
                    <div class="row mb-4 border-bottom">
                        <div class="col-10">
                            <h3><spring:message code="titles.trainings" /></h3>
                        </div>

                        <sec:authorize access="hasAnyAuthority('ROLE_ADMIN', 'ROLE_STAFF')">
                            <div class="col-2 text-right">
                                <button type="button" class="btn btn-sm btn-primary" data-action="agregar" data-type="capacitaciones">
                                    <i class="fas fa-plus-square fa-fw"></i>
                                </button>
                            </div>
                        </sec:authorize>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-hover table-striped">
                            <thead>
                                <tr>
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.date" /></th>
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.time" /></th>
                                    <sec:authorize access="hasAnyAuthority('ROLE_ADMIN', 'ROLE_STAFF')">
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.professional" /></th>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyAuthority('ROLE_ADMIN', 'ROLE_CLIENT')">
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.client" /></th>
                                    </sec:authorize>
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.status" /></th>
                                </tr>
                            </thead>
                            <tbody>
                                <core:choose>
                                    <core:when test="${capacitaciones != null && capacitaciones.size() > 0}">
                                        <core:forEach items="${capacitaciones}" var="capacitacion">
                                            <td>${capacitacion.getFecha()}</td>
                                            <td>${capacitacion.getHora()}</td>

                                            <sec:authorize access="hasAnyAuthority('ROLE_ADMIN', 'ROLE_STAFF')">
                                            <td>${capacitacion.getProfesional().getUsuario().getNombre()}</td>
                                            </sec:authorize>

                                            <sec:authorize access="hasAnyAuthority('ROLE_ADMIN', 'ROLE_CLIENT')">
                                            <td>${capacitacion.getCliente().getUsuario().getNombre()}</td>
                                            </sec:authorize>

                                            <td>
                                                <core:choose>
                                                    <core:when test="${capacitacion.getEstado() == 1}"><spring:message code="form.label.status.pendiente" /></core:when>
                                                    <core:when test="${capacitacion.getEstado() == 2}"><spring:message code="form.label.status.en_proceso" /></core:when>
                                                    <core:otherwise><spring:message code="form.label.status.realizado" /></core:otherwise>
                                                </core:choose>
                                            </td>
                                        </core:forEach>
                                    </core:when>
                                    <core:otherwise>
                                        <tr>
                                            <th scope="row" colspan="5" class="text-center"><spring:message code="form.error.no_info" /></th>
                                        </tr>
                                    </core:otherwise>
                                </core:choose>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- /Principal -->
            </div>
        </div>
        <!-- /Contenido -->

        <jsp:include page="./fragmentos/dependencias.jsp" />

        <script type="text/javascript" src="<core:url value="/res/js/general.js" />"></script>
    </body>
</html>
