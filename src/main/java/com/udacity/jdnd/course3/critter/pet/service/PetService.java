package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;

import java.util.List;

public interface PetService {
    Pet save(Pet pet);
    Pet findById(Long id);
    List<Pet> findAllByCustomerId(Long customerId);
}
