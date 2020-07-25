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
                            <h3><spring:message code="titles.accidents.details" /></h3>
                        </div>

                        <div class="col-2 text-right">
                            <div class="dropdown dropleft">
                                <button type="button" id="acciones" class="btn btn-sm btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <i class="fas fa-cogs fa-fw"></i>
                                </button>

                                <div class="dropdown-menu" aria-labelledby="acciones">
                                    <a class="dropdown-item" href="<core:url value="/accidentes/${accidente.getId()}/editar" />">
                                        <spring:message code="form.button.edit" />
                                    </a>

                                    <sec:authorize access="hasAnyAuthority('ROLE_ADMIN', 'ROLE_STAFF')">
                                    <a class="dropdown-item text-danger" data-toggle="modal" data-target="#delete" role="button">
                                        <spring:message code="form.button.delete" />
                                    </a>
                                    </sec:authorize>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="table-responsive mt-4">
                        <table class="table table-striped">
                            <tbody>
                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.date" /></th>
                                    <td class="text-nowrap">${accidente.getFecha()}</td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.time" /></th>
                                    <td class="text-nowrap">${accidente.getHora()}</td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.address" /></th>
                                    <td>${accidente.getDireccion()}</td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.place" /></th>
                                    <td>${accidente.getLugar()}</td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.circumstance" /></th>
                                    <td>${accidente.getCircunstancia()}</td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.details" /></th>
                                    <td>${accidente.getDetalles()}</td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.accident_class" /></th>
                                    <td class="text-nowrap">
                                        <core:choose>
                                            <core:when test="${accidente.getClasificacion() == 1}">
                                                <spring:message code="form.label.accident_class.mild" />
                                            </core:when>
                                            <core:when test="${accidente.getClasificacion() == 2}">
                                                <spring:message code="form.label.accident_class.serious" />
                                            </core:when>
                                            <core:when test="${accidente.getClasificacion() == 3 }">
                                                <spring:message code="form.label.accident_class.fatal" />
                                            </core:when>
                                            <core:otherwise>
                                                <spring:message code="form.label.accident_class.other" />
                                            </core:otherwise>
                                        </core:choose>
                                    </td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.accident_type" /></th>
                                    <td class="text-nowrap">
                                        <core:choose>
                                            <core:when test="${accidente.getTipo() == 1}">
                                                <spring:message code="form.label.accident_type.work" />
                                            </core:when>
                                            <core:otherwise>
                                                <spring:message code="form.label.accident_type.journey" />
                                            </core:otherwise>
                                        </core:choose>
                                    </td>
                                </tr>

                                <tr>
                                    <th scope="row" class="text-nowrap"><spring:message code="form.label.evidence" /></th>
                                    <td class="text-nowrap">
                                        <core:choose>
                                            <core:when test="${accidente.getEvidencia() == 1}">
                                                <spring:message code="form.label.evidence.certificate" />
                                            </core:when>
                                            <core:when test="${accidente.getEvidencia() == 2 }">
                                                <spring:message code="form.label.evidence.statement" />
                                            </core:when>
                                            <core:when test="${accidente.getEvidencia() == 3}">
                                                <spring:message code="form.label.evidence.witnesses" />
                                            </core:when>
                                            <core:otherwise>
                                                <spring:message code="form.label.evidence.other" />
                                            </core:otherwise>
                                        </core:choose>
                                    </td>
                                </tr>
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
                        <spring:message code="confirmation.delete.content" arguments="${accidente.getId()}" />
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                            <spring:message code="form.button.cancel" />
                        </button>

                        <form action="<core:url value="/accidentes/${accidente.getId()}/eliminar" />" method="post">
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
