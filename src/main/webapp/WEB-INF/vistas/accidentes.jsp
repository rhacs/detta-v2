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
                    <div class="row border-bottom pb-2">
                        <div class="col-10">
                            <h3><spring:message code="titles.accidents.list" /></h3>
                        </div>

                        <div class="col-2 text-right">
                            <button type="button" class="btn btn-sm btn-primary" data-action="agregar" data-type="accidentes">
                                <i class="fas fa-plus-square fa-fw"></i>
                            </button>
                        </div>
                    </div>

                    <core:if test="${not empty param.noid}">
                        <div class="alert alert-warning mt-4"><spring:message code="form.error.noid" arguments="${param.noid}" /></div>
                    </core:if>

                    <core:if test="${not empty param.remid}">
                        <div class="alert alert-danger mt-4"><spring:message code="form.success.delete" arguments="${param.remid}" /></div>
                    </core:if>

                    <div class="table-responsive mt-4">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.date" /></th>
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.time" /></th>
                                    <sec:authorize access="hasAnyAuthority('ROLE_ADMIN', 'ROLE_STAFF')">
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.client" /></th>
                                    </sec:authorize>
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.accident_type" /></th>
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.accident_class" /></th>
                                </tr>
                            </thead>
                            <tbody>
                                <core:choose>
                                    <core:when test="${accidentes != null && accidentes.size() > 0}">
                                        <core:forEach items="${accidentes}" var="accidente">
                                            <tr role="button" data-member="accidentes" data-id="${accidente.getId()}"
                                            <core:choose>
                                                <core:when test="${accidente.getClasificacion() == 2 }">
                                                    class="table-warning"
                                                </core:when>
                                                <core:when test="${accidente.getClasificacion() == 3 }">
                                                    class="table-danger"
                                                </core:when>
                                            </core:choose>
                                            >
                                                <td class="text-nowrap">${accidente.getFecha()}</td>
                                                <td class="text-nowrap">${accidente.getHora()}</td>
                                                <sec:authorize access="hasAnyAuthority('ROLE_ADMIN', 'ROLE_STAFF')">
                                                <td>${accidente.getCliente().getNombre()}</td>
                                                </sec:authorize>
                                                <td class="text-nowrap">
                                                    <core:choose>
                                                        <core:when test="${accidente.getTipo() == 1}"><spring:message code="form.label.accident_type.work" /></core:when>
                                                        <core:otherwise><spring:message code="form.label.accident_type.journey" /></core:otherwise>
                                                    </core:choose>
                                                </td>
                                                <td class="text-nowrap">
                                                    <core:choose>
                                                        <core:when test="${accidente.getClasificacion() == 1}"><spring:message code="form.label.accident_class.mild" /></core:when>
                                                        <core:when test="${accidente.getClasificacion() == 2}"><spring:message code="form.label.accident_class.serious" /></core:when>
                                                        <core:when test="${accidente.getClasificacion() == 3}"><spring:message code="form.label.accident_class.fatal" /></core:when>
                                                        <core:otherwise><spring:message code="form.label.accident_class.other" /></core:otherwise>
                                                    </core:choose>
                                                </td>
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
                <!-- /Principal -->
            </div>
        </div>
        <!-- /Contenido -->

        <jsp:include page="./fragmentos/dependencias.jsp" />

        <script type="text/javascript" src="<core:url value="/res/js/general.js" />"></script>
    </body>
</html>
