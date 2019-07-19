package ua.kpi.kpipetclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.kpi.kpipetclinic.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

    Owner findByLastName(String lastName);
}
