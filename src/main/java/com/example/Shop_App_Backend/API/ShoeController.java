package com.example.Shop_App_Backend.API;
/*
import com.example.Shop_App_Backend.Domain.Client;
import com.example.Shop_App_Backend.Domain.Shoe;
import com.example.Shop_App_Backend.Domain.Transaction; */
import com.example.Shop_App_Backend.DTO.ShoeDTO;
import com.example.Shop_App_Backend.Domain.Shoe;
import com.example.Shop_App_Backend.Domain.Suggestion;
import com.example.Shop_App_Backend.Repository.UserRepository;
import com.example.Shop_App_Backend.Service.ShoeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/shoe") //
@AllArgsConstructor
@Validated // This annotation is used at the controller level to enable method validation.
@CrossOrigin(origins = "http://localhost:5173") // Specifying frontend origin
public class ShoeController {
    private ShoeService service;
    UserRepository userRepository;

    @PostMapping("/add/{username}")
    public Shoe add(@Valid @RequestBody Shoe newEntity, @PathVariable("username") String username) {
        return this.service.addProductForUser(newEntity, username);
    }

    @PostMapping("/all")
    public List<Shoe> addAll(@Valid @RequestBody List<Shoe> entities)
    {
        return this.service.addAllService(entities);
    }

    public ResponseEntity<Object> showMessage(Object messageOrEntity, HttpStatus status)
    {
        return ResponseEntity.status(status).body(messageOrEntity);
    }

    public ShoeDTO toShoeDTO(Shoe shoe)
    {
        return new ShoeDTO(shoe.getShoe_id(), shoe.getProduct_name(),
                shoe.getSize(), shoe.getPrice());
    }


    @GetMapping("/all")
    public ResponseEntity<Object> getAll(@RequestParam(required = false) String sortBy, @RequestParam(required = false) String sortOrder)
    {
        /*
        localhost:8080/shoe/all
        localhost:8080/shoe/all?sortBy=price&sortOrder=asc
        localhost:8080/shoe/all?sortBy=price&sortOrder=desc
         */
        /*
        List<ShoeDTO> shoeDTOS = service.getAllAsDTO();
        if(shoeDTOS != null)
        {
            if(!shoeDTOS.isEmpty())
            {
                return this.showMessage(shoeDTOS, HttpStatus.OK); // 200
            }
            else
            {
                return this.showMessage("There weren't any shoes added for this account.", HttpStatus.OK); // 200
            }
        }
        return this.showMessage("There weren't any shoes added for this account.", HttpStatus.OK); // 200

         */

        List<Shoe> shoes;
        Sort.Direction direction = Sort.Direction.ASC; // Default to ascending order

        // Check if sortOrder parameter is provided and set direction accordingly
        if (sortOrder != null && sortOrder.equalsIgnoreCase("desc"))
        {
            direction = Sort.Direction.DESC;
        }

        // Check if sortBy parameter is provided and sort accordingly
        if (sortBy != null && sortBy.equalsIgnoreCase("price"))  // Sorting by price case
        {
            //shoes = this.service.getAllSortedByPrice(direction);
            shoes = this.service.getAll();
        }
        else
        {
            shoes = this.service.getAll();
        }

        if(shoes.isEmpty())
        {
            // there are no shoes saved in the database
            return this.showMessage("There are no shoes yet.", HttpStatus.OK); // 200
        }
        // if there are shoes in the database, a list of them is returned
        return ResponseEntity.ok(shoes); // 200


    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") String id)
    {
        if(Objects.equals(id, "undefined"))
            id = "2";
        Shoe shoe = this.service.getEntityById(Integer.parseInt(id));
        if(shoe != null)
        {
            // the shoe was found, return it as DTO
            return ResponseEntity.ok(toShoeDTO(shoe)); // 200
        }
        else
        {
            // the shoe was not found
            String errorMessage = "Shoe with id " + id + " was not found.";
            return this.showMessage(errorMessage, HttpStatus.NOT_FOUND); // 404
        }
    }

    @GetMapping("/suggestions/{id}")
    public ResponseEntity<Object> getSuggestionsForShoe(@PathVariable("id") Integer shoeId)
    {
        Shoe shoe = this.service.getEntityById(shoeId);
        if(shoe != null)
        {
            // the shoe exists
            Set<Suggestion> suggestions = this.service.getSuggestionsForShoe(shoeId);
            if(!suggestions.isEmpty())
            {
                return this.showMessage(suggestions, HttpStatus.OK); // 200
            }
            else
            {
                // the shoe hasn't any style suggestions yet
                return this.showMessage("There was not any shoe with id " + shoeId + " which " +
                        "has style advice until now.", HttpStatus.NOT_FOUND); // 404
            }
        }
        // the shoe was not found
        return this.showMessage("There is no shoe with id " + shoeId + ".", HttpStatus.NOT_FOUND);
        // 404 status
    }

    /*
    @GetMapping("/transactions/{id}")
    public ResponseEntity<Object> getTransactionsWithShoe(@PathVariable("id") Integer shoeId)
    {
        Shoe shoe = this.service.getEntityById(shoeId);
        if(shoe != null)
        {
            // the shoe exists
            Set<Transaction> transactionSet = this.service.getTransactionsForShoe(shoeId);
            if(!transactionSet.isEmpty())
            {
                return this.showMessage(transactionSet, HttpStatus.OK); // 200
            }
            else
            {
                // the shoe was not ordered yet
                return this.showMessage("There was not any shoe with id " + shoeId + " which " +
                        "was ordered until now.", HttpStatus.NOT_FOUND); // 404
            }
        }
        // the shoe was not found
        return this.showMessage("There is no shoe with id " + shoeId + ".", HttpStatus.NOT_FOUND);
        // 404 status
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Object> getClients(@PathVariable("id") Integer shoeId)
    {
        Shoe shoe = this.service.getEntityById(shoeId);
        if(shoe != null)
        {
            // the shoe exists
            Set<Client> clients = this.service.getClientsWhoBoughtShoe(shoeId);
            if(!clients.isEmpty())
            {
                return this.showMessage(clients, HttpStatus.OK); // 200
            }
            else
            {
                // the shoe was not ordered yet
                return this.showMessage("There was not any shoe with id " + shoeId + " which " +
                        "was ordered until now.", HttpStatus.NOT_FOUND); // 404
            }
        }
        // the shoe was not found
        return this.showMessage("There is no shoe with id " + shoeId + ".", HttpStatus.NOT_FOUND);
        // 404 status
    }
     */

    @GetMapping("/get/{username}")
    public ResponseEntity<Object> getShoesByUser(@PathVariable("username") String username,
                                                 @RequestParam(required = false) String sortBy,
                                                 @RequestParam(required = false) String sortOrder)
    {
        /*
        localhost:8080/shoe/all
        localhost:8080/shoe/get/id?sortBy=price&sortOrder=asc
        localhost:8080/shoe/get/id?sortBy=price&sortOrder=desc
         */

        List<ShoeDTO> shoes = this.service.getAllByUserService(username);

        if(shoes.isEmpty())
        {
            return this.showMessage(shoes, HttpStatus.OK); // 200
        }

        if(shoes == null)
        {
            return this.showMessage("There is no username with username " + username + ".", HttpStatus.NOT_FOUND);
            // 404 status
        }

        Sort.Direction direction = Sort.Direction.ASC; // Default to ascending order

        // Check if sortOrder parameter is provided and set direction accordingly
        if (sortOrder != null && sortOrder.equalsIgnoreCase("desc"))
        {
            direction = Sort.Direction.DESC;
        }

        // Check if sortBy parameter is provided and sort accordingly
        if (sortBy != null && sortBy.equalsIgnoreCase("price"))  // Sorting by price case
        {
            return this.showMessage(this.service.getAllSortedByPrice(direction), HttpStatus.OK); // 200
        }
        return this.showMessage(this.service.getAllSortedByPrice(direction), HttpStatus.OK); // 200
    }

    @GetMapping("/filter/{valueForFiltering}")
    public ResponseEntity<Object> filter(@PathVariable("valueForFiltering") Integer valueForFiltering)
    {
        List<Shoe> shoes = this.service.filterByQuantity(valueForFiltering);
        if(shoes.isEmpty())
        {
            // there are no shoes matching the filter criteria
            String errorMessage = "There are no shoes with a quantity bigger than " + valueForFiltering + ".";
            return this.showMessage(errorMessage, HttpStatus.NOT_FOUND); // 404
        }
        return showMessage(shoes, HttpStatus.OK); // 200
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer id)
    {
        String deleteMessage;
        HttpStatus status;
        if(this.service.exists(id))
        {
            // the shoe was found and deleted
            this.service.deleteService(id);
            deleteMessage = "Shoe with id " + id + " was successfully deleted.";
            status = HttpStatus.OK; // 200
        }
        else
        {
            // the shoe was not found and the deletion can't be done
            deleteMessage = "Shoe with id " + id + " was not found.";
            status = HttpStatus.NOT_FOUND; // 404
        }
        return this.showMessage(deleteMessage, status);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Integer id, @Valid @RequestBody Shoe entity)
    {
        Shoe shoe = this.service.updateService(id, entity);
        if(shoe != null)
        {
            // the shoe was found and updated
            return ResponseEntity.status(HttpStatus.OK).body(shoe); // 200
        }
        else
        {
            // the shoe was not found and the update operation cannot be completed
            String errorMessage = "The shoe with id " + id + " was not found.";
            return this.showMessage(errorMessage, HttpStatus.NOT_FOUND); // 404
        }
    }


    @MessageMapping("/cronAdd")
    @SendTo("/topic/cronjob")
    public void cronJob() {
        addEntity();
    }

    @Scheduled(fixedRate = 15000)
    public void addEntity() {
        Random rand = new Random();
        // Generate random shoe entities
        String[] sizes = {"34", "36", "38", "40"};

        Random random = new Random();

        String productName = generateRandomName(10); // Generate random 10-letter name
        int price = 1234; // it could be randomly generated , but I want to be able to delete entities from db easily
        // price = random.nextInt(200) + 50; // Random price between 50 and 250
        int size = Integer.parseInt(sizes[random.nextInt(sizes.length)]);

        // Now you can create a Shoe object with these random values
        Shoe shoe = new Shoe();
        shoe.setShoe_id(rand.nextInt(1000));
        shoe.setProduct_name(productName);
        shoe.setSize(size);
        shoe.setPrice(price);
        shoe.setUser(userRepository.findById(1).orElse(null));

        service.addService(shoe);
    }



    // Method to generate a random string of given length
    private String generateRandomName(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char randomChar = (char) ('a' + random.nextInt(26));
            sb.append(randomChar);
        }
        return sb.toString();
    }






}
