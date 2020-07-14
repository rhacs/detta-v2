// Esperar a que termine de cargar la página
$(function() {
    // Al presionar el enlace de cierre de sesión
    $('#logout').on('click', function(event) {
        // Prevenir acción por defecto
        event.preventDefault();

        // Enviar formulario
        $('#l').submit();
    });
});