package ua.kpi.kpipetclinic.services;

import ua.kpi.kpipetclinic.model.Pet;

import java.util.Set;

public interface PetService {

    Pet findById(Long id);

    Pet save(Pet pet);

    Set<Pet> findAll();
}
