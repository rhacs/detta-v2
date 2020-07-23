// Esperar a que termine de cargar la página
$(function() {
    // Al presionar el enlace "Cerrar sesión"
    $('[data-logout]').on('click', function(event) {
        // Prevenir acción por defecto
        event.preventDefault();

        // Enviar formulario
        $('#logout').submit();
    });
});
