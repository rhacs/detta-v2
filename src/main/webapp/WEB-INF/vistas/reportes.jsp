<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <div class="row">
                            <div class="col-12">
                                <h3 class="border-bottom pb-2"><spring:message code="titles.reports.statistics" /></h3>

                                <div class="row">
                                    <div class="col-12 mb-4">
                                        <div class="card">
                                            <div class="card-header"><spring:message code="titles.reports.statistics.permonth" /></div>
                                            <div class="card-body"></div>
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="card">
                                            <div class="card-header"><spring:message code="titles.reports.statistics.pertype" /></div>
                                            <div class="card-body"></div>
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="card">
                                            <div class="card-header"><spring:message code="titles.reports.statistics.perclass" /></div>
                                            <div class="card-body"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-12 mt-5">
                                <h3 class="border-bottom pb-2"><spring:message code="titles.reports.actions" /></h3>

                                <table class="table table-hover table-striped">
                                    <thead>
                                        <tr>
                                            <th scope="col" class="text-nowrap">#</th>
                                            <th scope="col" class="text-nowrap"><spring:message code="form.label.date" /></th>
                                            <th scope="col" class="text-nowrap"><spring:message code="form.label.time" /></th>
                                            <th scope="col" class="text-nowrap"><spring:message code="form.label.user" /></th>
                                            <th scope="col" class="text-nowrap"><spring:message code="form.label.details" /></th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <core:choose>
                                            <core:when test="${acciones != null && acciones.size() > 0}">
                                                <core:forEach end="10" items="${acciones}" var="accion">
                                                    <core:choose>
                                                        <core:when test="${accion.getCategoria() == 1}">
                                                            <tr class="table-danger">
                                                        </core:when>
                                                        <core:otherwise>
                                                            <tr>
                                                        </core:otherwise>
                                                    </core:choose>
                                                    <th scope="row" class="text-nowrap">${accion.getId()}</th>
                                                    <td class="text-nowrap">${accion.getFecha()}</td>
                                                    <td class="text-nowrap">${accion.getHora()}</td>
                                                    <td class="text-nowrap">${accion.getEmail()}</td>
                                                    <td>${accion.getDetalles()}</td>
                                                </tr>
                                                </core:forEach>
                                            </core:when>
                                            <core:otherwise>
                                                <tr>
                                                    <th scope="row" class="text-center" colspan="5"><spring:message code="form.error.no_info" /></th>
                                                </tr>
                                            </core:otherwise>
                                        </core:choose>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </sec:authorize>
                </div>
                <!-- /Principal -->
            </div>
        </div>
        <!-- /Contenido -->

        <jsp:include page="./fragmentos/dependencias.jsp" />

        <script type="text/javascript" src="<core:url value="/res/js/general.js" />"></script>
    </body>
</html>
