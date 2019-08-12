package ua.kpi.kpipetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.kpi.kpipetclinic.model.Pet;
import ua.kpi.kpipetclinic.model.Visit;
import ua.kpi.kpipetclinic.services.PetService;
import ua.kpi.kpipetclinic.services.VisitService;
import ua.kpi.kpipetclinic.view.ViewConstants;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;

@Controller
public class VisitController {

    private ViewConstants viewConstants;

    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void dataBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");

        dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException{
                setValue(LocalDate.parse(text));
            }
        });
    }

    /**
     * Method called each and every @RequestMapping annotated method
     * We make sure that always have fresh data
     */
    @ModelAttribute(ViewConstants.VISIT)
    public Visit loadPetWithVisit(@PathVariable("petId") Long petId, Model model){
        Pet pet = petService.findById(petId);
        model.addAttribute(ViewConstants.PET, pet);

        Visit visit = new Visit();
        pet.getVisits().add(visit);
        visit.setPet(pet);
        return visit;
    }

    /**
     * Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm(...) is called
     */
    @GetMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String initNewVisitForm(@PathVariable("petId") Long petId, Model model){
        return ViewConstants.VIEW_PETS_CREATE_OR_UPDATE_VISIT_FORM;
    }

    /**
     * Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm(...) is called
     */
    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(@Valid Visit visit, BindingResult result){
        if (result.hasErrors()){
            return ViewConstants.VIEW_PETS_CREATE_OR_UPDATE_VISIT_FORM;
        } else {
            visitService.save(visit);

            return ViewConstants.VIEW_REDIRECT_TO_OWNERS_OWNER_ID_FORM;
        }
    }
}
