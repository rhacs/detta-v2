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
                            <h3><spring:message code="titles.consulting" /></h3>
                        </div>

                        <sec:authorize access="hasAnyAuthority('ROLE_ADMIN', 'ROLE_STAFF')">
                            <div class="col-2 text-right">
                                <button type="button" class="btn btn-sm btn-primary" data-action="agregar" data-type="asesorias">
                                    <i class="fas fa-plus-square fa-fw"></i>
                                </button>
                            </div>
                        </sec:authorize>
                    </div>

                    <core:if test="${not empty param.noid}">
                        <div class="alert alert-warning"><spring:message code="form.error.noid" arguments="${param.noid}" /></div>
                    </core:if>

                    <core:if test="${not empty param.remid}">
                        <div class="alert alert-success"><spring:message code="form.success.delete" arguments="${param.remid}" /></div>
                    </core:if>

                </div>
                <!-- /Principal -->
            </div>
        </div>
        <!-- /Contenido -->

        <jsp:include page="./fragmentos/dependencias.jsp" />

        <script type="text/javascript" src="<core:url value="/res/js/general.js" />"></script>
    </body>
</html>
