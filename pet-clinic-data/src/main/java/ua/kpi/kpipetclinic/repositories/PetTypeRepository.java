package ua.kpi.kpipetclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.kpi.kpipetclinic.model.PetType;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {
}
