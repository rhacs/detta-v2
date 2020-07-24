<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                <div class="col-lg-9 col-md-8 border-bottom pb-2">
                    <div class="row">
                        <div class="col-10"><h3><spring:message code="titles.professionals.list" /></h3></div>
                        <div class="col-2 text-right">
                            <button type="button" class="btn btn-sm btn-primary" data-action="agregar" data-type="profesionales">
                                <i class="fas fa-plus-square"></i>
                            </button>
                        </div>
                    </div>

                    <div class="table-responsive">
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
                                            <tr role="button" data-member="profesionales" data-id="${profesional.getId()}" ${!profesional.isEnabled() ? 'class="table-secondary"' : ''}>
                                                <th scope="row" class="text-nowrap">${profesional.getId()}</th>
                                                <td class="text-nowrap">${profesional.getNombre()}</td>
                                                <td class="text-nowrap">${profesional.getEmail()}</td>
                                                <td class="text-nowrap">${profesional.getTelefono()}</td>
                                                <td class="text-nowrap text-right">
                                                    <core:choose>
                                                        <core:when test="${profesional.isEnabled()}"><spring:message code="form.label.yes" /></core:when>
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
