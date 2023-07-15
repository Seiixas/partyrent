package br.ifnmg.edu.partyrent.modules.users.exceptions;

import br.ifnmg.edu.partyrent.shared.exceptions.BaseException;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(404, "User not found.");
    }
}
