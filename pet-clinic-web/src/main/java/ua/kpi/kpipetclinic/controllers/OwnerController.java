package ua.kpi.kpipetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.kpipetclinic.model.Owner;
import ua.kpi.kpipetclinic.services.OwnerService;
import ua.kpi.kpipetclinic.view.ViewConstants;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private ViewConstants viewConstants;

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    /**
     * Id property drives everything in the DB.
     * Disallow the web form to address and manipulate the ID property.
     * Good thing in security wise
     * @param dataBinder
     */
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields(ViewConstants.ID);
    }

    @RequestMapping("/find")
    public String findOwners(Model model){
        model.addAttribute(ViewConstants.OWNER, Owner.builder().build());
        return ViewConstants.VIEW_OWNERS_FIND_OWNERS_FORM;
    }

    @GetMapping("/new")
    public String initCreationOwnerForm(Model model){
        model.addAttribute(ViewConstants.OWNER, Owner.builder().build());
        return ViewConstants.VIEW_OWNERS_CREATE_AND_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Owner owner, BindingResult result){
        if (result.hasErrors()){
            return ViewConstants.VIEW_OWNERS_CREATE_AND_UPDATE_FORM;
        } else {
            Owner savedOwner = ownerService.save(owner);
            return ViewConstants.VIEW_REDIRECT_TO_OWNERS_FORM + savedOwner.getId();
        }
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable Long ownerId, Model model){
        model.addAttribute(ownerService.findById(ownerId));
        return ViewConstants.VIEW_OWNERS_CREATE_AND_UPDATE_FORM;
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result, @PathVariable Long ownerId){
        if (result.hasErrors()){
            return ViewConstants.VIEW_OWNERS_CREATE_AND_UPDATE_FORM;
        } else {
            owner.setId(ownerId);
            Owner savedOwner = ownerService.save(owner);
            return ViewConstants.VIEW_REDIRECT_TO_OWNERS_FORM + savedOwner.getId();
        }
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Model model){

        // allow parameters GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        List<Owner> results = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");

        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("lastName", "notFound", "not found");
            return ViewConstants.VIEW_OWNERS_FIND_OWNERS_FORM;
        } else if (results.size() == 1) {
            // 1 owner found
            owner = results.get(0);
            return ViewConstants.VIEW_REDIRECT_TO_OWNERS_FORM + owner.getId();
        } else {
            // multiple owners found
            model.addAttribute("selections", results);
            return ViewConstants.VIEW_OWNERS_OWNERS_LIST_FORM;
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId){
        ModelAndView modelAndView = new ModelAndView(ViewConstants.VIEW_OWNERS_OWNER_DETAILS_FORM);
        modelAndView.addObject(ownerService.findById(ownerId));
        return modelAndView;
    }
}
