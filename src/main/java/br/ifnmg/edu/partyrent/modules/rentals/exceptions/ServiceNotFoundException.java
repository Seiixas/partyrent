package br.ifnmg.edu.partyrent.modules.rentals.exceptions;

import br.ifnmg.edu.partyrent.shared.exceptions.BaseException;

public class ServiceNotFoundException extends BaseException {
    public ServiceNotFoundException() {
        super(404, "Service not found.");
    }
}
