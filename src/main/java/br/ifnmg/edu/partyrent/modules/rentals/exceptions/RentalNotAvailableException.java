package br.ifnmg.edu.partyrent.modules.rentals.exceptions;

import br.ifnmg.edu.partyrent.shared.exceptions.BaseException;

public class RentalNotAvailableException extends BaseException {
    public RentalNotAvailableException() {
        super(400, "This rent is not available due date range selected.");
    }
}
