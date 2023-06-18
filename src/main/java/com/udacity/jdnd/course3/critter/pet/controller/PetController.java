package com.udacity.jdnd.course3.critter.pet.controller;

import com.udacity.jdnd.course3.critter.pet.dto.PetDTO;
import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    private PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petService.save(convertToPet(petDTO));
        return convertToPetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertToPetDTO(petService.findById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> petList = petService.findAllByCustomerId(ownerId);
        return petList.stream().map(pet -> convertToPetDTO(pet)).collect(Collectors.toList());
    }

    private Pet convertToPet(PetDTO petDTO) {
        Customer customer = new Customer();
        customer.setId(petDTO.getOwnerId());

        Pet pet = new Pet();
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setCustomer(customer);
        pet.setName(petDTO.getName());
        pet.setNotes(petDTO.getNotes());
        pet.setType(petDTO.getType());

        return pet;
    }

    private PetDTO convertToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setName(pet.getName());
        petDTO.setType(pet.getType());
        petDTO.setNotes(pet.getNotes());
        petDTO.setId(pet.getId());
        petDTO.setOwnerId(pet.getCustomer().getId());

        return petDTO;
    }
}
