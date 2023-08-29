package br.ifnmg.edu.partyrent.modules.rentals.services;

import br.ifnmg.edu.partyrent.modules.places.entities.Place;
import br.ifnmg.edu.partyrent.modules.rentals.exceptions.InvalidDateRentalException;
import br.ifnmg.edu.partyrent.modules.places.services.PlacesService;
import br.ifnmg.edu.partyrent.modules.rentals.dtos.CreateRentalDTO;
import br.ifnmg.edu.partyrent.modules.rentals.entities.Rental;
import br.ifnmg.edu.partyrent.modules.rentals.exceptions.RentalNotAvailableException;
import br.ifnmg.edu.partyrent.modules.rentals.repositories.RentalsRepository;
import br.ifnmg.edu.partyrent.modules.users.entities.User;
import br.ifnmg.edu.partyrent.modules.users.services.UsersService;
import br.ifnmg.edu.partyrent.shared.utils.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RentalsService {
    @Autowired
    private RentalsRepository rentalsRepository;

    @Autowired
    private PlacesService placesService;

    @Autowired
    private ServicesService servicesService;

    @Autowired
    private UsersService usersService;

    public Rental create(CreateRentalDTO createRentalDto, String userEmail) {
        LocalDateTime todayDate = LocalDateTime.now();

        if (createRentalDto.start_date().isBefore(todayDate) || createRentalDto.end_date().isBefore(createRentalDto.start_date())) {
            throw new InvalidDateRentalException();
        }

        var rentIsAvailable = this.rentalsRepository.findAvailability(
                createRentalDto.start_date(),
                createRentalDto.end_date()
        );

        if (!rentIsAvailable.isEmpty()) {
            throw new RentalNotAvailableException();
        }

        User user = this.usersService.findByEmail(userEmail);

        Place place = this.placesService.find(createRentalDto.place_id());
        List<br.ifnmg.edu.partyrent.modules.rentals.entities.Service> services = this.servicesService.findByIds(createRentalDto.service_ids());

        var daysOfRental = Date.calculateDaysDifference(createRentalDto.start_date(), createRentalDto.end_date());
        BigDecimal finalPrice = BigDecimal.ZERO;
        BigDecimal daysOfRentalConverted = new BigDecimal(daysOfRental);

        finalPrice = finalPrice.add(place.getPrice());

        Rental rental = new Rental();
        Set<br.ifnmg.edu.partyrent.modules.rentals.entities.Service> servicesSet = new HashSet<>(services);

        rental.setServices(servicesSet);
        rental.setPlace(place);
        rental.setUser(user);
        rental.setStart_date(createRentalDto.start_date());
        rental.setEnd_date(createRentalDto.end_date());

        for (var service : services) {
            finalPrice = finalPrice.add(service.getPrice());
            service.getRentals().add(rental);
        }

        finalPrice = finalPrice.multiply(daysOfRentalConverted);
        rental.setPrice(finalPrice);

        this.servicesService.bulkStore(services);
        this.rentalsRepository.save(rental);

        return rental;
    }
}
