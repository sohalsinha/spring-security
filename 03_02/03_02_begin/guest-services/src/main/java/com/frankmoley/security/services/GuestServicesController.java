package com.frankmoley.security.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Frank P. Moley III.
 */
@RestController
@RequestMapping("/guests")
public class GuestServicesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuestServicesController.class);

    private final GuestRepository repository;

    public GuestServicesController(GuestRepository repository){
        super();
        this.repository = repository;
    }

    @GetMapping
    public List<Guest> getAllGuests(){
        return new ArrayList<>(this.repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Guest> addGuest(@RequestBody GuestModel model){
        Guest guest = this.repository.save(model.translateModelToGuest());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(guest.getId()).toUri();
        return ResponseEntity.created(location).body(guest);
    }

    @GetMapping("/{id}")
    public Guest getGuest(@PathVariable Long id){
        Optional<Guest> guest = this.repository.findById(id);
        if(guest.isPresent()){
            return guest.get();
        }
        throw new GuestNotFoundException("Guest not found with id: " + id);
    }

    @PutMapping("/{id}")
    public Guest updateGuest(@PathVariable Long id, @RequestBody GuestModel model){
        Optional<Guest> existing = this.repository.findById(id);
        if(!existing.isPresent()){
            throw new GuestNotFoundException("Guest not found with id: " + id);
        }
        Guest guest = model.translateModelToGuest();
        guest.setId(id);
        return this.repository.save(guest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteGuest(@PathVariable Long id){
        Optional<Guest> existing = this.repository.findById(id);
        if(!existing.isPresent()){
            throw new GuestNotFoundException("Guest not found with id: " + id);
        }
        this.repository.deleteById(id);
    }
}
