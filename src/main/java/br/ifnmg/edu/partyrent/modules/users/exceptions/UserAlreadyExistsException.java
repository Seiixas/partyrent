package br.ifnmg.edu.partyrent.modules.users.exceptions;

import br.ifnmg.edu.partyrent.shared.exceptions.BaseException;

public class UserAlreadyExistsException extends BaseException {
    public UserAlreadyExistsException() {
        super(400, "This user already exists.");
    }
}
