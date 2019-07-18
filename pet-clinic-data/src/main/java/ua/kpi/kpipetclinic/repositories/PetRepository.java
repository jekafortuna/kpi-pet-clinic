package ua.kpi.kpipetclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.kpi.kpipetclinic.model.Pet;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
