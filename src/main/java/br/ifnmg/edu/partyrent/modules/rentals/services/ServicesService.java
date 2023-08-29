package br.ifnmg.edu.partyrent.modules.rentals.services;

import br.ifnmg.edu.partyrent.modules.rentals.dtos.CreateServiceDTO;
import br.ifnmg.edu.partyrent.modules.rentals.dtos.UpdateServiceDTO;
import br.ifnmg.edu.partyrent.modules.rentals.exceptions.ServiceAlreadyExistsException;
import br.ifnmg.edu.partyrent.modules.rentals.exceptions.ServiceNotFoundException;
import br.ifnmg.edu.partyrent.modules.rentals.repositories.ServicesRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServicesService {
    @Autowired
    private ServicesRepository servicesRepository;

    public List<br.ifnmg.edu.partyrent.modules.rentals.entities.Service> findByIds(List<UUID> ids) {
        var services = this.servicesRepository.findAllById(ids);

        if (services.isEmpty() || services.size() != ids.size()) {
            throw new ServiceNotFoundException();
        }

        return services;
    }

    public void store(CreateServiceDTO createServiceDto) {
        Optional<br.ifnmg.edu.partyrent.modules.rentals.entities.Service> service = this.servicesRepository.findByName(createServiceDto.name());

        if (service.isPresent()) {
            throw new ServiceAlreadyExistsException();
        }

        var serviceToCreate = new br.ifnmg.edu.partyrent.modules.rentals.entities.Service();

        BeanUtils.copyProperties(createServiceDto, serviceToCreate);

        this.servicesRepository.save(serviceToCreate);
    }

    public void bulkStore(List<br.ifnmg.edu.partyrent.modules.rentals.entities.Service> services) {
        this.servicesRepository.saveAll(services);
    }

    public br.ifnmg.edu.partyrent.modules.rentals.entities.Service update(UUID serviceId, UpdateServiceDTO updateServiceDto) {
        var service = this.servicesRepository.findById(serviceId);

        if (service.isEmpty()) {
            throw new ServiceNotFoundException();
        }

        var serviceAlreadyExists = this.servicesRepository.findByName(updateServiceDto.name());

        if (serviceAlreadyExists.isPresent()) {
            throw new ServiceAlreadyExistsException();
        }

        var serviceToUpdate = service.get();

        BeanUtils.copyProperties(updateServiceDto, serviceToUpdate);

        this.servicesRepository.save(serviceToUpdate);

        return serviceToUpdate;
    }

    public List<br.ifnmg.edu.partyrent.modules.rentals.entities.Service> findAll() {
        return this.servicesRepository.findAll();
    }

    public br.ifnmg.edu.partyrent.modules.rentals.entities.Service find(UUID serviceId) {
        var service = this.servicesRepository.findById(serviceId);

        if (service.isEmpty()) {
            throw new ServiceNotFoundException();
        }

        return service.get();
    }

    public void delete(UUID serviceId) {
        var service = this.find(serviceId);

        this.servicesRepository.delete(service);
    }
}
