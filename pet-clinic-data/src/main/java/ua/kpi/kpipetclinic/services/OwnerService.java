package ua.kpi.kpipetclinic.services;

import ua.kpi.kpipetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long>{

    Owner findByLastName(String lastName);

}