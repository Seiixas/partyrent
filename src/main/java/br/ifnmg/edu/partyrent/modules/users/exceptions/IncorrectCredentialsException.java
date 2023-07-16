package br.ifnmg.edu.partyrent.modules.users.exceptions;

import br.ifnmg.edu.partyrent.shared.exceptions.BaseException;

public class IncorrectCredentialsException extends BaseException {
    public IncorrectCredentialsException() {
        super(401, "E-mail or password incorrect.");
    }
}
