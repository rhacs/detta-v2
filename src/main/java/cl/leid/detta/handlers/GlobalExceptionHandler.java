package cl.leid.detta.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import cl.leid.detta.api.exceptions.ConflictException;
import cl.leid.detta.api.exceptions.EmptyRepositoryException;
import cl.leid.detta.api.exceptions.InformationNotFoundException;
import cl.leid.detta.modelos.ErrorResponse;

@RestControllerAdvice(basePackages = { "cl.leid.detta.api" })
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Maneja las excepciones {@link InformationNotFoundException} que se arroja
     * cuando el repositorio no encuentra los detalles del registro solicitado
     * 
     * @param e objeto {@link InformationNotFoundException} con la información de la
     *          excepción
     * @return un objeto {@link ResponseEntity} con la respuesta
     */
    @ExceptionHandler(value = InformationNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    protected ResponseEntity<ErrorResponse> handleInformationNotFoundException(InformationNotFoundException e) {
        // Estado HTTP
        HttpStatus notFound = HttpStatus.NOT_FOUND;

        // Error
        ErrorResponse response = new ErrorResponse(notFound, e);

        // Generar y mostrar respuesta
        return ResponseEntity.status(notFound).body(response);
    }

    /**
     * Maneja las excepciones {@link EmptyRepositoryException} que se arroja cuando
     * el repositorio solicitado se encuentra vacío
     * 
     * @param e objeto {@link EmptyRepositoryException} con la información de la
     *          excepción
     * @return un objeto {@link ResponseEntity} con la respuesta
     */
    @ExceptionHandler(value = EmptyRepositoryException.class)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    protected ResponseEntity<ErrorResponse> handleEmptyRepositoryException(EmptyRepositoryException e) {
        // Estado HTTP
        HttpStatus noContent = HttpStatus.NO_CONTENT;

        // Error
        ErrorResponse response = new ErrorResponse(noContent, e);

        // Generar y mostrar respuesta
        return ResponseEntity.status(noContent).body(response);
    }

    @ExceptionHandler(value = ConflictException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponse> handleConflictException(ConflictException e) {
        // Estado HTTP
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        // Error
        ErrorResponse response = new ErrorResponse(badRequest, e);
        response.agregarError(e.getCampo(), e.getMensaje(), e.getValorRechazado());

        // Generar respuesta
        return ResponseEntity.status(badRequest).body(response);
    }

}
