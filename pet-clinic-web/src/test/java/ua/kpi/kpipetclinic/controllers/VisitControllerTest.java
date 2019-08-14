package ua.kpi.kpipetclinic.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriTemplate;
import ua.kpi.kpipetclinic.model.Owner;
import ua.kpi.kpipetclinic.model.Pet;
import ua.kpi.kpipetclinic.model.PetType;
import ua.kpi.kpipetclinic.model.Visit;
import ua.kpi.kpipetclinic.services.PetService;
import ua.kpi.kpipetclinic.services.VisitService;
import ua.kpi.kpipetclinic.view.ViewConstants;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    ViewConstants viewConstants;

    @Mock
    VisitService visitService;

    @Mock
    PetService petService;

    @InjectMocks
    VisitController visitController;

    Set<Visit> visits;

    MockMvc mockMvc;

    private final UriTemplate visitsUriTemplate = new UriTemplate("/owners/{ownerId}/pets/{petId}/visits/new");
    private final Map<String, String> uriVariables = new HashMap<>();
    private URI visitsUri;

    @BeforeEach
    void setUp() {
        Long petId = 1L;
        Long ownerId = 1L;
        when(petService.findById(anyLong()))
                .thenReturn(
                        Pet.builder()
                                .id(petId)
                                .birthDate(LocalDate.of(2018,11,13))
                                .name("Cutie")
                                .visits(new HashSet<>())
                                .owner(Owner.builder()
                                        .id(ownerId)
                                        .lastName("Doe")
                                        .firstName("Joe")
                                        .build())
                                .petType(PetType.builder()
                                        .name("Dog").build())
                                .build()
                );

        uriVariables.clear();
        uriVariables.put("ownerId", ownerId.toString());
        uriVariables.put("petId", petId.toString());
        visitsUri = visitsUriTemplate.expand(uriVariables);

        mockMvc = MockMvcBuilders
                .standaloneSetup(visitController)
                .build();
    }

    @Test
    void initNewVisitForm() throws Exception {
//        mockMvc.perform(get(ViewConstants.VIEW_OWNERS_OWNERS_ID_PETS_PETS_ID_VISITS_NEW_FORM))
        mockMvc.perform(get(visitsUri))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewConstants.VIEW_PETS_CREATE_OR_UPDATE_VISIT_FORM));
    }

    @Test
    void processNewVisitForm() throws Exception {
//        mockMvc.perform(post(ViewConstants.VIEW_OWNERS_OWNERS_ID_PETS_PETS_ID_VISITS_NEW_FORM)
        mockMvc.perform(post(visitsUri)
                        .param(ViewConstants.DATE, "2018-11-11"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(ViewConstants.VIEW_REDIRECT_TO_OWNERS_OWNER_ID_FORM))
                .andExpect(model().attributeExists(ViewConstants.VISIT));
    }
}