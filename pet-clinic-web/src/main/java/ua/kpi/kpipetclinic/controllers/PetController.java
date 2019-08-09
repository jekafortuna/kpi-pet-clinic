package ua.kpi.kpipetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.kpi.kpipetclinic.model.Owner;
import ua.kpi.kpipetclinic.model.Pet;
import ua.kpi.kpipetclinic.model.PetType;
import ua.kpi.kpipetclinic.services.OwnerService;
import ua.kpi.kpipetclinic.services.PetService;
import ua.kpi.kpipetclinic.services.PetTypeService;
import ua.kpi.kpipetclinic.view.ViewConstants;

import javax.validation.Valid;
import java.util.Collection;

@RequestMapping("/owners/{ownerId}")
@Controller
public class PetController {

    private ViewConstants viewConstants;

    private final PetService petService;
    private final PetTypeService petTypeService;
    private final OwnerService ownerService;

    public PetController(PetService petService, PetTypeService petTypeService, OwnerService ownerService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
    }

    @ModelAttribute("types")
    public Collection <PetType> populatePetTypes(){
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") Long ownerId){
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields(ViewConstants.ID);
    }

    @GetMapping("/pets/new")
    public String initCreationForm(Owner owner, Model model){
        Pet pet = new Pet();
        owner.getPets().add(pet);
        model.addAttribute(ViewConstants.PET, pet);
        return ViewConstants.VIEW_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/new")
    public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, Model model){
        if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null){
            result.rejectValue("name", "dublicate", "already exists");
        }
        owner.getPets().add(pet);
        if (result.hasErrors()){
            model.addAttribute(ViewConstants.PET, pet);
            return ViewConstants.VIEW_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            petService.save(pet);

            return ViewConstants.VIEW_REDIRECT_TO_OWNERS_FORM + owner.getId();
        }
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable Long petId, Model model){
        model.addAttribute(ViewConstants.PET, petService.findById(petId));
        return ViewConstants.VIEW_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(@Valid Pet pet, BindingResult result, Owner owner, Model model){
        if (result.hasErrors()){
            pet.setOwner(owner);
            model.addAttribute(ViewConstants.PET, pet);
            return ViewConstants.VIEW_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            owner.getPets().add(pet);
            petService.save(pet);

            return ViewConstants.VIEW_REDIRECT_TO_OWNERS_FORM + owner.getId();
        }
    }
}
