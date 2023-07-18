package br.ifnmg.edu.partyrent.modules.users.exceptions;

import br.ifnmg.edu.partyrent.shared.exceptions.BaseException;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException() {
        super(400, "This token is invalid.");
    }
}
