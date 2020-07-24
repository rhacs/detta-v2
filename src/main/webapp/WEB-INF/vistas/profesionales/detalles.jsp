<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
                    <div class="row border-bottom pb-2">
                        <div class="col-10">
                            <h3><spring:message code="titles.professionals.details" /></h3>
                        </div>

                        <div class="col-2 text-right">
                            <div class="dropdown dropleft">
                                <button type="button" id="acciones" class="btn btn-sm btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <i class="fas fa-cogs fa-fw"></i>
                                </button>

                                <div class="dropdown-menu" aria-labelledby="acciones">
                                    <a class="dropdown-item" href="<core:url value="/profesionales/${profesional.getId()}/editar" />">
                                        <spring:message code="form.button.edit" />
                                    </a>

                                    <a class="dropdown-item text-danger" data-toggle="modal" data-target="#delete" role="button">
                                        <spring:message code="form.button.delete" />
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="table-responsive mt-4">
                        <table class="table table-striped">
                            <tbody>
                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.name" /></th>
                                    <td class="text-nowrap">${profesional.getNombre()}</td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.email" /></th>
                                    <td class="text-nowrap">${profesional.getEmail()}</td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.phone" /></th>
                                    <td class="text-nowrap">${profesional.getTelefono()}</td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.enabled" /></th>
                                    <td class="text-nowrap">
                                        <core:choose>
                                            <core:when test="${profesional.isEnabled()}"><spring:message code="form.label.yes" /></core:when>
                                            <core:otherwise><spring:message code="form.label.no" /></core:otherwise>
                                        </core:choose>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <h3 class="border-bottom pb-2 mt-5"><spring:message code="titles.clients.list" /></h3>

                    <div class="table-responsive mt-4">
                        <table class="table table-hover table-striped">
                            <thead>
                                <tr>
                                    <th scope="col" class="text-nowrap">#</th>
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.rut" /></th>
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.client_name" /></th>
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.phone" /></th>
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.enabled" /></th>
                                </tr>
                            </thead>

                            <tbody>
                                <core:choose>
                                    <core:when test="${clientes != null && clientes.size() > 0}">
                                        <core:forEach items="${clientes}" var="cliente">
                                            <tr role="button" data-member="clientes" data-id="${cliente.getId()}" ${!cliente.isEnabled() ? 'class="table-warning"' : '' }>
                                                <th scope="row" class="text-nowrap">${cliente.getId()}</th>
                                                <td class="text-nowrap">${cliente.getRut()}</td>
                                                <td>${cliente.getNombre()}</td>
                                                <td class="text-nowrap">${cliente.getTelefono()}</td>
                                                <td>
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

        <!-- Modal: Lenguaje -->
        <div class="modal fade" id="delete" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" aria-labelledby="modalTitle">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modalTitle">
                            <spring:message code="confirmation.delete.title" />
                        </h5>

                        <button type="button" class="close" data-dismiss="modal" aria-label="<spring:message code="form.button.close" />">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body">
                        <spring:message code="confirmation.delete.content" arguments="${profesional.getNombre()}" />
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                            <spring:message code="form.button.cancel" />
                        </button>

                        <form action="<core:url value="/profesionales/${profesional.getId()}/eliminar" />" method="post">
                            <sec:csrfInput />
                            <button type="submit" class="btn btn-danger">
                                <spring:message code="form.button.delete" />
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- /Modal: Lenguaje -->

        <jsp:include page="../fragmentos/dependencias.jsp" />

        <script type="text/javascript" src="<core:url value="/res/js/general.js" />"></script>
    </body>
</html>
