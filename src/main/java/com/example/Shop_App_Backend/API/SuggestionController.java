package com.example.Shop_App_Backend.API;

import com.example.Shop_App_Backend.Domain.Shoe;
import com.example.Shop_App_Backend.Domain.Suggestion;
import com.example.Shop_App_Backend.Service.SuggestionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/suggestion")
@AllArgsConstructor
@Validated // This annotation is used at the controller level to enable method validation.
@CrossOrigin(origins = "http://localhost:5173") // Specifying frontend origin
public class SuggestionController {
    private SuggestionService service;

    @PostMapping("/add/{id}")
    public Suggestion add(@Valid @RequestBody Suggestion newEntity, @PathVariable("id") String id)
    { return this.service.addService(newEntity, Integer.valueOf(id)); }

    @PostMapping("/add2")
    public Suggestion add2(@Valid @RequestBody Suggestion newEntity)
    { return this.service.addServiceWithoutReference(newEntity); }

    @PostMapping("/all")
    public List<Suggestion> addAll(@Valid @RequestBody List<Suggestion> entities)
    {
        return this.service.addAllService(entities);
    }

    public ResponseEntity<Object> showMessage(Object messageOrEntity, HttpStatus status)
    {
        return ResponseEntity.status(status).body(messageOrEntity);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll()
    {
        List<Suggestion> advice = this.service.getAll();
        if(advice.isEmpty())
        {
            // there are no suggestions saved in the database
            return this.showMessage("There is no advice yet.", HttpStatus.NOT_FOUND); // 404
        }
        // if there are tips in the database, a list of them is returned
        return ResponseEntity.ok(advice); // 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") String id)
    {
        if(Objects.equals(id, "undefined"))
            id = "2";
        Suggestion suggestion = this.service.getEntityById(Integer.parseInt(id));
        if(suggestion != null)
        {
            // the suggestion was found
            return ResponseEntity.ok(suggestion); // 200
        }
        else
        {
            // the suggestion was not found
            String errorMessage = "Suggestion with id " + id + " was not found.";
            return this.showMessage(errorMessage, HttpStatus.NOT_FOUND); // 404
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer id)
    {
        String deleteMessage;
        HttpStatus status;
        if(this.service.exists(id))
        {
            // the advice was found and deleted
            this.service.deleteService(id);
            deleteMessage = "Advice with id " + id + " was successfully deleted.";
            status = HttpStatus.OK; // 200
        }
        else
        {
            // the advice was not found and the deletion can't be done
            deleteMessage = "Advice with id " + id + " was not found.";
            status = HttpStatus.NOT_FOUND; // 404
        }
        return this.showMessage(deleteMessage, status);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Integer id, @Valid @RequestBody Suggestion entity)
    {
        Suggestion suggestion = this.service.updateService(id, entity);
        if(suggestion != null)
        {
            // the suggestion was found and updated
            return ResponseEntity.status(HttpStatus.OK).body(suggestion); // 200
        }
        else
        {
            // the suggestion was not found and the update operation cannot be completed
            String errorMessage = "The suggestion with id " + id + " was not found.";
            return this.showMessage(errorMessage, HttpStatus.NOT_FOUND); // 404
        }
    }
}
