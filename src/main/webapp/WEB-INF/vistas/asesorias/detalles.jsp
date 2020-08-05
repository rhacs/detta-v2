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
                            <h3><spring:message code="titles.consulting" /></h3>
                        </div>

                        <sec:authorize access="hasAnyAuthority('ROLE_ADMIN', 'ROLE_STAFF')">
                        <div class="col-2 text-right">
                            <div class="dropdown dropleft">
                                <button type="button" id="acciones" class="btn btn-sm btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <i class="fas fa-cogs fa-fw"></i>
                                </button>

                                <div class="dropdown-menu" aria-labelledby="acciones">
                                    <a class="dropdown-item" href="<core:url value="/asesorias/${asesoria.getId()}/editar" />">
                                        <spring:message code="form.button.edit" />
                                    </a>

                                    <a class="dropdown-item text-danger" data-toggle="modal" data-target="#delete" role="button">
                                        <spring:message code="form.button.delete" />
                                    </a>
                                </div>
                            </div>
                        </div>
                        </sec:authorize>
                    </div>

                    <core:if test="${not empty error}">
                        <div class="alert alert-warning mt-4">${error}</div>
                    </core:if>

                    <div class="table-responsive mt-4">
                        <table class="table table-striped">
                            <tbody>
                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.date" /></th>
                                    <td class="text-nowrap">${asesoria.getFecha()}</td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.time" /></th>
                                    <td class="text-nowrap">${asesoria.getHora()}</td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.address" /></th>
                                    <td>${asesoria.getDireccion()}</td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.topic" /></th>
                                    <td>${asesoria.getTema()}</td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.inspector" /></th>
                                    <td>${asesoria.getFiscalizador()}</td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.department" /></th>
                                    <td>${asesoria.getDepartamento()}</td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.description" /></th>
                                    <td>${asesoria.getDescripcion()}</td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.status" /></th>
                                    <td class="text-nowrap">
                                        <core:choose>
                                            <core:when test="${asesoria.getEstado() == 1}"><spring:message code="form.label.status.pendiente" /></core:when>
                                            <core:when test="${asesoria.getEstado() == 2}"><spring:message code="form.label.status.en_proceso" /></core:when>
                                            <core:otherwise><spring:message code="form.label.status.realizado" /></core:otherwise>
                                        </core:choose>
                                    </td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.client" /></th>
                                    <td>${asesoria.getCliente().getUsuario().getNombre()}</td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.professional" /></th>
                                    <td class="text-nowrap">${asesoria.getProfesional().getUsuario().getNombre()}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="row mb-4 border-bottom">
                        <div class="col-10">
                            <h3><spring:message code="titles.visits" /></h3>
                        </div>

                        <sec:authorize access="hasAnyAuthority('ROLE_ADMIN', 'ROLE_STAFF')">
                            <div class="col-2 text-right">
                                <button type="button" class="btn btn-sm btn-primary" data-action="visita" data-asesoria="${asesoria.getId()}">
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
                                    <th scope="col" class="text-nowrap"><spring:message code="form.label.reason" /></th>
                                </tr>
                            </thead>

                            <tbody>
                                <core:choose>
                                    <core:when test="${visitas != null && visitas.size() > 0}">
                                        <core:forEach items="${visitas}" var="visita">
                                            <tr role="button">
                                                <td class="text-nowrap">${visita.getFecha()}</td>
                                                <td class="text-nowrap">${visita.getHora()}</td>
                                                <td>${visita.getMotivo()}</td>
                                            </tr>
                                        </core:forEach>
                                    </core:when>

                                    <core:otherwise>
                                        <tr>
                                            <th scope="row" class="text-center" colspan="3"><spring:message code="form.error.no_info" /></th>
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
                        <spring:message code="confirmation.delete.content" arguments="${asesoria.getId()}" />
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                            <spring:message code="form.button.cancel" />
                        </button>

                        <form action="<core:url value="/asesorias/${asesoria.getId()}/eliminar" />" method="post">
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
