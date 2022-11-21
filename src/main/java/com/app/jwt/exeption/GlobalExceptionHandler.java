package com.app.jwt.exeption;

import com.app.jwt.dto.ErrorDetalles;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.constraints.Null;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    //ResponseEntityExceptionHandler para añadir el método handleMethodArgumentNotValid y mandar mensajes cuando hay algo equivocado en la entrada de datos
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetalles> manejarResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
         ErrorDetalles errorDetalles = new ErrorDetalles(new Date(), exception.getMessage(),webRequest.getDescription(false));
         return new ResponseEntity<>(errorDetalles, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BlogAppException.class)
    public ResponseEntity<ErrorDetalles> manejarBlogAppException(BlogAppException exception, WebRequest webRequest){
        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(), exception.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetalles> manejarGlobalException(Exception exception, WebRequest webRequest){
        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(), exception.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetalles, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //EN ESTE MËTODO SE VAN A GENERAR LOS MENSAJES DE ERROR DE CUALQUIER CAMPO QUE STÄN EN PublicacionDTO
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String>  errores = new HashMap<>();
        //obtiene todos los erores
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String nombreCampo= ((FieldError)error).getField();//campo en el que me está mandando el error
            String mensaje = error.getDefaultMessage();

            errores.put(nombreCampo,mensaje);
        });
        return new  ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}
