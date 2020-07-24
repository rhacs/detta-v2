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
                            <h3><spring:message code="titles.clients.list" /></h3>
                        </div>

                        <div class="col-2 text-right">
                            <button type="button" class="btn btn-sm btn-primary" data-action="agregar" data-type="clientes">
                                <i class="fas fa-plus-square fa-fw"></i>
                            </button>
                        </div>
                    </div>

                    <core:if test="${not empty param.noid}">
                        <div class="alert alert-warning my-3"><spring:message code="form.error.noid" arguments="${param.noid}" /></div>
                    </core:if>

                    <div class="table-responsive mt-4">
                        <table class="table table-hover table-striped">
                            <thead>
                                <tr>
                                    <th scope="col" class="text-nowrap">#</th>
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.client_name" /></th>
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.rut" /></th>
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.email" /></th>
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.phone" /></th>
                                    <th scope="col" class="text-nowrap text-right"><spring:message code="form.label.enabled" /></th>
                                </tr>
                            </thead>

                            <tbody>
                                <core:choose>
                                    <core:when test="${clientes != null && clientes.size() > 0}">
                                        <core:forEach items="${clientes}" var="cliente" >
                                            <tr role="button" data-member="clientes" data-id="${cliente.getId()}" ${!cliente.isEnabled() ? 'class="table-warning"' : ''}>
                                                <th scope="row" class="text-nowrap">${cliente.getId()}</th>
                                                <td class="text-nowrap">${cliente.getNombre()}</td>
                                                <td class="text-nowrap">${cliente.getRut()}</td>
                                                <td class="text-nowrap">${cliente.getEmail()}</td>
                                                <td class="text-nowrap">${cliente.getTelefono()}</td>
                                                <td class="text-nowrap text-right">
                                                    <core:choose>
                                                        <core:when test="${cliente.isEnabled()}"><spring:message code="form.label.yes" /></core:when>
                                                        <core:otherwise><spring:message code="form.label.no" /></core:otherwise>
                                                    </core:choose>
                                                </td>
                                            </tr>
                                        </core:forEach>
                                    </core:when>
                                    <core:otherwise>
                                        <tr>
                                            <th scope="row" class="text-center" colspan="6"><spring:message code="form.error.no_info" /></th>
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
