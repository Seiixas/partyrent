package br.ifnmg.edu.partyrent.modules.addresses.services;

import br.ifnmg.edu.partyrent.modules.addresses.dtos.CreateAddressDTO;
import br.ifnmg.edu.partyrent.modules.addresses.dtos.UpdateAddressDTO;
import br.ifnmg.edu.partyrent.modules.addresses.entities.Address;
import br.ifnmg.edu.partyrent.modules.addresses.errors.AddressNotFoundException;
import br.ifnmg.edu.partyrent.modules.addresses.repositories.AddressesRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AddressesService {
    @Autowired
    private AddressesRepository addressesRepository;

    public Address store(CreateAddressDTO createAddressDto) {
        Address address = new Address();

        BeanUtils.copyProperties(createAddressDto, address);

        this.addressesRepository.save(address);

        return address;
    }

    public Address update(UUID addressId, UpdateAddressDTO updateAddressDto) {
        Optional<Address> address = this.addressesRepository.findById(addressId);

        if (address.isEmpty()) {
            throw new AddressNotFoundException();
        }

        Address addressToUpdate = address.get();

        BeanUtils.copyProperties(updateAddressDto, addressToUpdate);

        this.addressesRepository.save(addressToUpdate);

        return addressToUpdate;
    }
}
