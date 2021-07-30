package br.com.cooperforte.avaliacao.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.exception.JDBCConnectionException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.cooperforte.avaliacao.api.builder.Resposta;
import br.com.cooperforte.avaliacao.api.builder.RespostaBuilder;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleConstraint(DataIntegrityViolationException ex, WebRequest request) {

        System.out.println("[ERROR DataIntegrityViolationException]: " + ex.getMessage());
        ex.printStackTrace();

        Resposta resposta = RespostaBuilder.getBuilder().criaErro(ex).mensagem("Ocorreu um erro inesperado, por favor tente mais tarde").build();

        return new ResponseEntity<Object>(resposta, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ DataAccessException.class })
    public ResponseEntity<Object> handleBanco(DataAccessException dae, WebRequest request) {

        System.out.println("[ERROR DataAccessException]: " + dae.getMessage());
        dae.printStackTrace();

        Resposta resposta = RespostaBuilder.getBuilder().criaErro(dae).mensagem("Ocorreu um erro ao tentar acessar o banco de dados").build();

        return new ResponseEntity<Object>(resposta, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ JDBCConnectionException.class })
    public ResponseEntity<Object> handleBanco(JDBCConnectionException dae, WebRequest request) {

        System.out.println("[ERROR JDBCConnectionException]: " + dae.getMessage());
        dae.printStackTrace();

        Resposta resposta = RespostaBuilder.getBuilder().criaErro(dae).mensagem("Ocorreu um erro ao tentar acessar o banco de dados").build();

        return new ResponseEntity<Object>(resposta, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleAcessoNegado(AccessDeniedException e, WebRequest request) {

        System.out.println("[ERROR AccessDeniedException]: " + e.getMessage());
        e.printStackTrace();

        Resposta resposta = RespostaBuilder.getBuilder().criaErro(e).mensagem("Acesso negado").build();

        return new ResponseEntity<Object>(resposta, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleGenerico(Exception ex, WebRequest request) {

        System.out.println("[ERROR Exception]: " + ex.getMessage());
        ex.printStackTrace();

        Resposta resposta = RespostaBuilder.getBuilder().criaErro(ex).mensagem("Ocorreu um erro no servidor").build();
        HttpStatus errorCode = HttpStatus.BAD_REQUEST;
        if (ex.getCause() != null) {
            errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Object>(resposta, new HttpHeaders(), errorCode);
    }

    @ExceptionHandler({ ServiceException.class })
    public ResponseEntity<Object> handleServiceException(Exception ex, WebRequest request) {

        System.out.println("[ERROR ServiceException]: " + ex.getMessage());
        ex.printStackTrace();

        Resposta resposta = RespostaBuilder.getBuilder().criaErro(ex).criaMensagem(ex).build();
        HttpStatus errorCode = HttpStatus.BAD_REQUEST;
        if (ex.getCause() != null) {
            errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Object>(resposta, new HttpHeaders(), errorCode);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        System.out.println("[ERROR MethodArgumentNotValidException]: " + ex.getMessage());
        ex.printStackTrace();

        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getDefaultMessage());
        }

        Resposta resposta = RespostaBuilder.getBuilder().criaErro(ex).mensagens(errors).build();

        return new ResponseEntity<Object>(resposta, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionSystemException.class)
    protected ResponseEntity<?> handleConstraintViolation(TransactionSystemException ex, WebRequest request) {

        System.out.println("[ERROR TransactionSystemException]: " + ex.getMessage());
        ex.printStackTrace();

        Throwable cause = ex.getRootCause();
        if (cause instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) cause).getConstraintViolations();

            List<String> errors = new ArrayList<String>();
            constraintViolations.forEach(violation -> {
                errors.add(violation.getMessage());
            });

            Resposta resposta = RespostaBuilder.getBuilder().criaErro(ex).mensagens(errors).build();

            return new ResponseEntity<Object>(resposta, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

        Resposta resposta = RespostaBuilder.getBuilder().criaErro(ex).criaMensagem(ex).build();
        HttpStatus errorCode = HttpStatus.BAD_REQUEST;
        if (ex.getCause() != null) {
            errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Object>(resposta, new HttpHeaders(), errorCode);
    }

}
