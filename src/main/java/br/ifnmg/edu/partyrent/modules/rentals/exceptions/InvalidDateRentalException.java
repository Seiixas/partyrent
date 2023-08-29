package br.ifnmg.edu.partyrent.modules.rentals.exceptions;

import br.ifnmg.edu.partyrent.shared.exceptions.BaseException;

public class InvalidDateRentalException extends BaseException {
    public InvalidDateRentalException() {
        super(400, "Date params are invalid.");
    }
}
