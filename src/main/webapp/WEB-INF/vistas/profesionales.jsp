<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
                            <h3><spring:message code="titles.professionals.list" /></h3>
                        </div>

                        <div class="col-2 text-right">
                            <button type="button" class="btn btn-sm btn-primary" data-action="agregar" data-type="profesionales">
                                <i class="fas fa-plus-square fa-fw"></i>
                            </button>
                        </div>
                    </div>

                    <core:if test="${not empty param.noid}">
                        <div class="alert alert-warning my-3"><spring:message code="form.error.noid" arguments="${param.noid}" /></div>
                    </core:if>

                    <core:if test="${not empty param.remid}">
                        <div class="alert alert-warning my-3"><spring:message code="form.success.delete" arguments="${param.remid}" /></div>
                    </core:if>

                    <div class="table-responsive mt-4">
                        <table class="table table-hover table-striped">
                            <thead>
                                <tr>
                                    <th scope="col" class="text-nowrap">#</th>
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.name" /></th>
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.email" /></th>
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.phone" /></th>
                                    <th scope="col" class="text-nowrap text-right"><spring:message code="form.label.enabled" /></th>
                                </tr>
                            </thead>

                            <tbody>
                                <core:choose>
                                    <core:when test="${profesionales != null && profesionales.size() > 0}">
                                        <core:forEach items="${profesionales}" var="profesional" >
                                            <tr role="button" data-member="profesionales" data-id="${profesional.getId()}" ${!profesional.getUsuario().isEnabled() ? 'class="table-warning"' : ''}>
                                                <th scope="row" class="text-nowrap">${profesional.getId()}</th>
                                                <td class="text-nowrap">${profesional.getUsuario().getNombre()}</td>
                                                <td class="text-nowrap">${profesional.getUsuario().getEmail()}</td>
                                                <td class="text-nowrap">${profesional.getUsuario().getTelefono()}</td>
                                                <td class="text-nowrap text-right">
                                                    <core:choose>
                                                        <core:when test="${profesional.getUsuario().isEnabled()}"><spring:message code="form.label.yes" /></core:when>
                                                        <core:otherwise><spring:message code="form.label.no" /></core:otherwise>
                                                    </core:choose>
                                                </td>
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
                <!-- /Principal -->
            </div>
        </div>
        <!-- /Contenido -->

        <jsp:include page="./fragmentos/dependencias.jsp" />

        <script type="text/javascript" src="<core:url value="/res/js/general.js" />"></script>
    </body>
</html>
