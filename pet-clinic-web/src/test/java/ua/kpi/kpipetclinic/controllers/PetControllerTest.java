package ua.kpi.kpipetclinic.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.kpi.kpipetclinic.model.Owner;
import ua.kpi.kpipetclinic.model.Pet;
import ua.kpi.kpipetclinic.model.PetType;
import ua.kpi.kpipetclinic.services.OwnerService;
import ua.kpi.kpipetclinic.services.PetService;
import ua.kpi.kpipetclinic.services.PetTypeService;
import ua.kpi.kpipetclinic.view.ViewConstants;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    PetService petService;

    @Mock
    PetTypeService petTypeService;

    @Mock
    OwnerService ownerService;

    @InjectMocks
    PetController petController;

    MockMvc mockMvc;

    Owner owner;
    Set<PetType> petTypes;
    ViewConstants viewConstants;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(1L).build();

        petTypes = new HashSet<>();
        petTypes.add(PetType.builder().id(1L).name("Dog").build());
        petTypes.add(PetType.builder().id(2L).name("Cat").build());

        mockMvc = MockMvcBuilders
                .standaloneSetup(petController)
                .build();
    }

    @Test
    void populatePetTypes() {
    }

    @Test
    void findOwner() {
    }

    @Test
    void initOwnerBinder() {
    }

    @Test
    void initCreationPetForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(get(ViewConstants.VIEW_OWNERS_1_PETS_NEW_FORM))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(ViewConstants.OWNER))
                .andExpect(model().attributeExists(ViewConstants.PET))
                .andExpect(view().name(ViewConstants.VIEW_PETS_CREATE_OR_UPDATE_FORM));
    }

    @Test
    void processCreationPetForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post(ViewConstants.VIEW_OWNERS_1_PETS_NEW_FORM))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(ViewConstants.VIEW_REDIRECT_TO_OWNERS_1_FORM));

        verify(petService).save(any());
    }

    @Test
    void initUpdatePetForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);
        when(petService.findById(anyLong())).thenReturn(Pet.builder().id(2L).build());

        mockMvc.perform(get(ViewConstants.VIEW_OWNERS_1_PETS_2_EDIT_FORM))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(ViewConstants.OWNER))
                .andExpect(model().attributeExists(ViewConstants.PET))
                .andExpect(view().name(ViewConstants.VIEW_PETS_CREATE_OR_UPDATE_FORM));
    }

    @Test
    void processUpdatePetForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post(ViewConstants.VIEW_OWNERS_1_PETS_2_EDIT_FORM))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(ViewConstants.VIEW_REDIRECT_TO_OWNERS_1_FORM));

        verify(petService).save(any());
    }
}