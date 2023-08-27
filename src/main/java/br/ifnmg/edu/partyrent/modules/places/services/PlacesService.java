package br.ifnmg.edu.partyrent.modules.places.services;

import br.ifnmg.edu.partyrent.modules.addresses.entities.Address;
import br.ifnmg.edu.partyrent.modules.addresses.services.AddressesService;
import br.ifnmg.edu.partyrent.modules.places.dtos.CreatePlaceDTO;
import br.ifnmg.edu.partyrent.modules.places.dtos.UpdatePlaceDTO;
import br.ifnmg.edu.partyrent.modules.places.entities.Place;
import br.ifnmg.edu.partyrent.modules.places.errors.PlaceNotFoundException;
import br.ifnmg.edu.partyrent.modules.places.repositories.PlacesRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlacesService {
    @Autowired
    private PlacesRepository placesRepository;

    @Autowired
    private AddressesService addressesService;

    public void store(CreatePlaceDTO createPlaceDto) {
        Address address = this.addressesService.store(createPlaceDto.address());

        Place newPlace = new Place();

        BeanUtils.copyProperties(createPlaceDto, newPlace);

        newPlace.setAddress(address);

        this.placesRepository.save(newPlace);
    }

    public Place find(UUID placeId) {
        Optional<Place> place = this.placesRepository.findById(placeId);

        if (place.isEmpty()) {
            throw new PlaceNotFoundException();
        }

        return place.get();
    }

    public List<Place> findAll(BigDecimal price_start, BigDecimal price_end, String name) {
        Sort sort = Sort.by(Sort.Order.asc("price"), Sort.Order.asc("name"));
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);

        return this.placesRepository.findAllFilteredAndSorted(price_start, price_end, name, pageable);
    }

    public Place update(UUID placeId, UpdatePlaceDTO updatePlaceDto) {
        Optional<Place> place = this.placesRepository.findById(placeId);

        if (place.isEmpty()) {
            throw new PlaceNotFoundException();
        }

        Place placeToUpdate = place.get();

        BeanUtils.copyProperties(updatePlaceDto, placeToUpdate);

        if (updatePlaceDto.address() != null) {
            Address address = placeToUpdate.getAddress();
            Address addressUpdated = this.addressesService.update(address.getId(), updatePlaceDto.address());
            placeToUpdate.setAddress(addressUpdated);
        }

        this.placesRepository.save(placeToUpdate);

        return placeToUpdate;
    }

    public void delete(UUID placeId) {
        Optional<Place> place = this.placesRepository.findById(placeId);

        if (place.isEmpty()) {
            throw new PlaceNotFoundException();
        }

        this.placesRepository.delete(place.get());
    }
}
