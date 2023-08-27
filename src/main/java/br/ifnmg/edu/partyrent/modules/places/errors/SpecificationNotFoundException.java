package br.ifnmg.edu.partyrent.modules.places.errors;

import br.ifnmg.edu.partyrent.shared.exceptions.BaseException;

public class SpecificationNotFoundException extends BaseException {
    public SpecificationNotFoundException() {
        super(404, "Specification not found.");
    }
}
