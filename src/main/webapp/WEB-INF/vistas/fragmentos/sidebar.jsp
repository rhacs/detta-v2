<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<core:set var="uri" value="${pageContext.request.requestURI}" />

                <!-- Sidebar -->
                <div id="sidebar" class="col-lg-3 col-md-4 d-md-block border-right collapse">
                    <h6 class="text-black-50 font-weight-bold mb-2 text-uppercase">
                        <spring:message code="titles.dashboard" />
                    </h6>

                    <ul class="nav nav-custom nav-pills flex-column">
                        <li class="nav-item">
                            <a class="nav-link ${fn:contains(uri, 'reportes') ? 'active' : ''}" href="<core:url value="/" />">
                                <i class="fas fa-clipboard fa-fw mr-1"></i>
                                <spring:message code="titles.reports" />
                            </a>
                        </li>

                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li class="nav-item">
                            <a class="nav-link ${fn:contains(uri, 'profesionales') ? 'active' : ''}" href="<core:url value="/profesionales" />">
                                <i class="fas fa-user-tie fa-fw mr-1"></i>
                                <spring:message code="titles.professionals" />
                            </a>
                        </li>
                    </sec:authorize>

                    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')">
                        <li class="nav-item">
                            <a class="nav-link ${fn:contains(uri, 'clientes') ? 'active' : ''}" href="<core:url value="/clientes" />">
                                <i class="fas fa-address-book fa-fw mr-1"></i>
                                <spring:message code="titles.clients" />
                            </a>
                        </li>
                    </sec:authorize>

                        <li class="nav-item">
                            <a class="nav-link ${fn:contains(uri, 'accidentes') ? 'active' : ''}" href="<core:url value="/accidentes" />">
                                <i class="fas fa-notes-medical fa-fw mr-1"></i>
                                <spring:message code="titles.accidents" />
                            </a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link ${fn:contains(uri, 'capacitaciones') ? 'active' : ''}" href="<core:url value="/capacitaciones" />">
                                <i class="fas fa-chalkboard-teacher fa-fw mr-1"></i>
                                <spring:message code="titles.trainings" />
                            </a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link ${fn:contains(uri, 'asesorias') ? 'active' : ''}" href="<core:url value="/asesorias" />">
                                <i class="fas fa-people-arrows fa-fw mr-1"></i>
                                <spring:message code="titles.consulting" />
                            </a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link ${fn:contains(uri, 'visitas') ? 'active' : ''}" href="<core:url value="/visitas" />">
                                <i class="fas fa-calendar-check fa-fw mr-1"></i>
                                <spring:message code="titles.visits" />
                            </a>
                        </li>
                    </ul>

                    <h6 class="text-black-50 font-weight-bold mt-5 mb-2 text-uppercase">
                        <spring:message code="titles.account" />
                    </h6>

                    <ul class="nav nav-custom nav-pills flex-column">
                        <li class="nav-item">
                            <form id="logout" action="<core:url value="/logout" />" method="post">
                                <a data-logout="true" class="nav-link" role="button">
                                    <i class="fas fa-sign-out-alt fa-fw mr-1"></i>
                                    <spring:message code="form.button.logout" />
                                </a>
                                <sec:csrfInput />
                            </form>
                        </li>
                    </ul>
                </div>
                <!-- /Sidebar -->