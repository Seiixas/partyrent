package br.ifnmg.edu.partyrent.modules.presentation.desktop;

import org.springframework.http.ResponseEntity;

public class ResponseValidator<T> {

    public T validate(ResponseEntity responseEntity) {
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("Response error!");
            return null;
        }

        if (responseEntity.getBody() == null) {
            System.out.println("Body error!");
            return null;
        }

        return (T) responseEntity.getBody();
    }
}
