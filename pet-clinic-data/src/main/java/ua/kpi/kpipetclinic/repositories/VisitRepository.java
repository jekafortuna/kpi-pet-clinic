package ua.kpi.kpipetclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.kpi.kpipetclinic.model.Visit;

public interface VisitRepository extends CrudRepository<Visit, Long> {
}
