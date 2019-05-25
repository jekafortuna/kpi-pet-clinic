package ua.kpi.kpipetclinic.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ua.kpi.kpipetclinic.model.Owner;
import ua.kpi.kpipetclinic.model.Vet;
import ua.kpi.kpipetclinic.services.OwnerService;
import ua.kpi.kpipetclinic.services.VetService;
import ua.kpi.kpipetclinic.services.map.OwnerServiceMap;
import ua.kpi.kpipetclinic.services.map.VetServiceMap;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader() {
        ownerService = new OwnerServiceMap();
        vetService = new VetServiceMap();
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glennane");

        ownerService.save(owner2);

        System.out.println("Loaded owners...");

        Vet vet1 = new Vet();
        vet1.setId(1L);
        vet1.setFirstName("Alex");
        vet1.setLastName("Black");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setId(2L);
        vet2.setFirstName("John");
        vet2.setLastName("Brown");

        vetService.save(vet2);

        System.out.println("Loaded vets...");
    }
}
