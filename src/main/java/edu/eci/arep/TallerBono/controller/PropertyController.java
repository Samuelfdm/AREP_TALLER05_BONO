package edu.eci.arep.TallerBono.controller;

import edu.eci.arep.TallerBono.model.Property;
import edu.eci.arep.TallerBono.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyService propertyService; //Inyectado por constructor

    public PropertyController(PropertyService propertyService){
        this.propertyService = propertyService;
    }

    @PostMapping
    public ResponseEntity<Property> crearProperty(@RequestBody Property property) {
        Property propertyCreated = propertyService.crear(property);
        if (propertyCreated == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Si no creó la property, devuelve 204 No Content
        }
        return new ResponseEntity<>(propertyCreated, HttpStatus.CREATED); //Devuelve la property con 200 OK
    }

    @GetMapping("/consult")
    public ResponseEntity<List<Property>> consultarProperties() {
        List<Property> properties = propertyService.obtenerProperties(); // Obtiene la lista de properties
        if (properties.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si no hay properties, devuelve 204 No Content
        }
        return new ResponseEntity<>(properties, HttpStatus.OK); // Devuelve la lista de properties con 200 OK
    }

    @GetMapping("/consult/{propertyId}")
    public ResponseEntity<Property> consultarProperty(@PathVariable Long propertyId) {
        Property property = propertyService.obtenerProperty(propertyId);
        if (property == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si no está la property, devuelve 404 Not Found
        }
        return new ResponseEntity<>(property, HttpStatus.OK); //Devuelve la property con 200 OK
    }

    @DeleteMapping("/delete/{propertyId}")
    public ResponseEntity<Void> eliminarProperty(@PathVariable Long propertyId) {
        try {
            propertyService.eliminarProperty(propertyId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{propertyId}")
    public ResponseEntity<Property> actualizarProperty(@PathVariable Long propertyId, @RequestBody Property nuevaProperty) {
        Property property = propertyService.actualizarProperty(propertyId,nuevaProperty);
        if (property == null) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED); // Si no actualizó la property, devuelve 204 No Content
        }
        return new ResponseEntity<>(property, HttpStatus.OK); //Devuelve la property actualizada con 200 OK
    }
}