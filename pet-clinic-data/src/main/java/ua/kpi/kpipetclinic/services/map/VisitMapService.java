package ua.kpi.kpipetclinic.services.map;

import org.springframework.stereotype.Service;
import ua.kpi.kpipetclinic.model.Visit;
import ua.kpi.kpipetclinic.services.VisitService;

import java.util.Set;

@Service
public class VisitMapService extends AbstractMapService<Visit, Long> implements VisitService {

    @Override
    public Set<Visit> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Visit object) {
        super.delete(object);
    }

    @Override
    public Visit save(Visit visit) {

        if (visit.getPet() == null || visit.getPet().getOwner() == null
                                   || visit.getPet().getId() == null
                                   || visit.getPet().getOwner().getId() == null){
            throw new RuntimeException("Invalid Visit!");
        }
        return super.save(visit);
    }

    @Override
    public Visit findById(Long id) {
        return super.findById(id);
    }
}
