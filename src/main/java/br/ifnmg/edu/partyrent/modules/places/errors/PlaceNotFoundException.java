package br.ifnmg.edu.partyrent.modules.places.errors;

import br.ifnmg.edu.partyrent.shared.exceptions.BaseException;

public class PlaceNotFoundException extends BaseException {
    public PlaceNotFoundException() {
        super(404, "Place not found.");
    }
}
