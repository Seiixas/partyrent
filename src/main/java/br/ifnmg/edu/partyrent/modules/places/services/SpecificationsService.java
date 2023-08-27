package br.ifnmg.edu.partyrent.modules.places.services;

import br.ifnmg.edu.partyrent.modules.places.dtos.CreateSpecificationDTO;
import br.ifnmg.edu.partyrent.modules.places.dtos.UpdateSpecificationDTO;
import br.ifnmg.edu.partyrent.modules.places.entities.Specification;
import br.ifnmg.edu.partyrent.modules.places.errors.SpecificationAlreadyExistsException;
import br.ifnmg.edu.partyrent.modules.places.errors.SpecificationNotFoundException;
import br.ifnmg.edu.partyrent.modules.places.repositories.SpecificationsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SpecificationsService {
    @Autowired
    private SpecificationsRepository specificationsRepository;

    public List<Specification> findByIds(List<UUID> ids) {
        return this.specificationsRepository.findAllById(ids);
    }

    public List<Specification> findAll() {
        return this.specificationsRepository.findAll();
    }

    public Specification find(UUID specificationId) {
        Optional<Specification> specification = this.specificationsRepository.findById(specificationId);

        if (specification.isEmpty()) {
            throw new SpecificationNotFoundException();
        }

        return specification.get();
    }

    public void store(CreateSpecificationDTO createSpecificationDto) {
        Specification specificationAlreadyExists = this.specificationsRepository.findByName(createSpecificationDto.name());

        if (specificationAlreadyExists != null) {
            throw new SpecificationAlreadyExistsException();
        }

        Specification specification = new Specification();

        BeanUtils.copyProperties(createSpecificationDto, specification);

        this.specificationsRepository.save(specification);
    }

    public void bulkStore(List<Specification> specifications) {
        this.specificationsRepository.saveAll(specifications);
    }

    public Specification update(UUID specificationId, UpdateSpecificationDTO updateSpecificationDto) {
        Optional<Specification> specification = this.specificationsRepository.findById(specificationId);

        if (specification.isEmpty()) {
            throw new SpecificationNotFoundException();
        }

        Specification specificationAlreadyExists = this.specificationsRepository.findByName(updateSpecificationDto.name());

        if (specificationAlreadyExists != null) {
            throw new SpecificationAlreadyExistsException();
        }

        Specification specificationToUpdate = specification.get();

        BeanUtils.copyProperties(updateSpecificationDto, specificationToUpdate);

        this.specificationsRepository.save(specificationToUpdate);

        return specificationToUpdate;
    }

    public void delete(UUID specificationId) {
        Optional<Specification> specification = this.specificationsRepository.findById(specificationId);

        if (specification.isEmpty()) {
            throw new SpecificationNotFoundException();
        }

        Specification specificationToRemove = specification.get();

        this.specificationsRepository.delete(specificationToRemove);
    }
}
