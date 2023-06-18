package com.udacity.jdnd.course3.critter.pet.service;


import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PetServiceImpl implements PetService {
    private PetRepository petRepository;
    private CustomerRepository customerRepository;

    public PetServiceImpl(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public Pet save(Pet pet) {
        Optional<Customer> optionalCustomer = customerRepository.findById(pet.getCustomer().getId());
        if (optionalCustomer.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found, id = ".concat(pet.getCustomer().getId().toString()));
        }
        optionalCustomer.get().getPets().add(pet);
        return petRepository.save(pet);
    }

    @Override
    public Pet findById(Long id) {
        Optional<Pet> optionalPet = petRepository.findById(id);
        if (optionalPet.isEmpty()) {
            throw new PetNotFoundException("Pet not found, id = ".concat(id.toString()));
        }
        return optionalPet.get();
    }

    @Override
    public List<Pet> findAllByCustomerId(Long customerId) {
        return petRepository.findAllByCustomerId(customerId);
    }
}
