<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
    <!-- Head -->
    <head>
        <!-- Configuración de Caracteres -->
        <meta charset="ISO-8859-1">

        <!-- Configuración inicial de ancho y escala -->
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Título de la página -->
        <title>detta <core:if test="${not empty titulo}"> [${titulo}]</core:if></title>

        <!-- Hojas de Estilo -->
        <!-- Bootstrap -->
        <link rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.0/css/bootstrap.min.css"
            integrity="sha512-rO2SXEKBSICa/AfyhEK5ZqWFCOok1rcgPYfGOqtX35OyiraBg6Xa4NnBJwXgpIRoXeWjcAmcQniMhp22htDc6g=="
            crossorigin="anonymous" />

        <!-- flag-icon-css -->
        <link rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.5.0/css/flag-icon.min.css"
            integrity="sha512-Cv93isQdFwaKBV+Z4X8kaVBYWHST58Xb/jVOcV9aRsGSArZsgAnFIhMpDoMDcFNoUtday1hdjn0nGp3+KZyyFw=="
            crossorigin="anonymous" />

        <!-- Chart.js -->
        <link rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.css"
            integrity="sha512-/zs32ZEJh+/EO2N1b0PEdoA10JkdC3zJ8L5FTiQu82LR9S/rOQNfQN7U59U9BC12swNeRAz3HSzIL2vpp4fv3w=="
            crossorigin="anonymous" />

        <!-- detta -->
        <link rel="stylesheet" href="<core:url value="/res/css/estilo.css" />">
    </head>
    <!-- /Head -->
