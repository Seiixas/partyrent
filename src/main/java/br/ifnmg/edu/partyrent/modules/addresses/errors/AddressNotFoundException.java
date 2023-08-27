package br.ifnmg.edu.partyrent.modules.addresses.errors;

import br.ifnmg.edu.partyrent.shared.exceptions.BaseException;

public class AddressNotFoundException extends BaseException {
    public AddressNotFoundException() {
        super(404, "Address not found.");
    }
}
