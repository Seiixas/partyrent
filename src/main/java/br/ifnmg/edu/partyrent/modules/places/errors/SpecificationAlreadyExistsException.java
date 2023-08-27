package br.ifnmg.edu.partyrent.modules.places.errors;

import br.ifnmg.edu.partyrent.shared.exceptions.BaseException;

public class SpecificationAlreadyExistsException extends BaseException {
    public SpecificationAlreadyExistsException() {
        super(400, "Specification already exists.");
    }
}
