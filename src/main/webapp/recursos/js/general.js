// Esperar a que termine de cargar la página
$(function() {
    // Al presionar el enlace "Cerrar sesión"
    $('[data-logout]').on('click', function(event) {
        // Prevenir acción por defecto
        event.preventDefault();

        // Enviar formulario
        $('#logout').submit();
    });

    // Al presionar una fila de una tabla que esté marcada con data-member
    $('[data-member]').on('click', function() {
        // Obtener el miembro
        let miembro = $(this).data('member');

        // Obtener el identificador
        let id = $(this).data('id');

        // Redireccionar
        $(location).attr('href', '/detta/' + miembro + '/' + id);
    });

    // Al presionar un botón de acción
    $('[data-action]').on('click', function() {
        // Obtener acción
        let accion = $(this).data('action');

        // Verificar acción
        if (accion === 'agregar') {
            // Obtener tipo
            let tipo = $(this).data('type');

            // Redireccionar
            $(location).attr('href', '/detta/' + tipo + '/agregar');
        } else if (accion === 'cancelar') {
            // Volver atrás
            history.back(1);
        } else if (accion === 'visita') {
            // Obtener id de la asesoria
            let asesoriaId = $(this).data('asesoria');

            // Obtener método
            let method = $(this).data('method');

            // Filtrar método
            if (method === 'add') {
                // Redireccionar
                $(location).attr('href', '/detta/asesorias/' + asesoriaId + '/visitas/agregar');
            } else if (method === 'show') {
                // Obtener id de la visita
                let visitaId = $(this).data('visita');

                // Redireccionar
                $(location).attr('href', '/detta/asesorias/' + asesoriaId + '/visitas/' + visitaId);
            }
        }
    });
});
