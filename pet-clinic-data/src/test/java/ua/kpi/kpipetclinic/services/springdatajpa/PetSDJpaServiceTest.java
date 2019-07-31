package ua.kpi.kpipetclinic.services.springdatajpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.kpi.kpipetclinic.model.Pet;
import ua.kpi.kpipetclinic.repositories.PetRepository;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class PetSDJpaServiceTest {

    public static final String PET_NAME = "PetName";

    @Mock
    PetRepository petRepository;

    @InjectMocks
    PetSDJpaService petSDJpaService;

    Pet returnPet;

    @BeforeEach
    void setUp() {
        returnPet = Pet.builder().name(PET_NAME).build();
    }

    @Test
    void findAll() {
        Set<Pet> returnPetSet = new HashSet<>();
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }
}