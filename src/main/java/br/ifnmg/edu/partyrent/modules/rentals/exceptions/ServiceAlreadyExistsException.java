package br.ifnmg.edu.partyrent.modules.rentals.exceptions;

import br.ifnmg.edu.partyrent.shared.exceptions.BaseException;

public class ServiceAlreadyExistsException extends BaseException {
    public ServiceAlreadyExistsException() {
        super(400, "Service already exists.");
    }
}
