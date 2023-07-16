package br.ifnmg.edu.partyrent.modules.users.exceptions;

import br.ifnmg.edu.partyrent.shared.exceptions.BaseException;

import java.util.List;

public class PasswordNotValidException extends BaseException {
    public PasswordNotValidException(List<String> details) {
        super(400, "Password is invalid.");
        setDetails(details);
    }
}
