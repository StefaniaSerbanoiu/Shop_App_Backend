package com.example.Shop_App_Backend.Service;

import com.example.Shop_App_Backend.Domain.Shoe;
import com.example.Shop_App_Backend.Domain.Suggestion;
import com.example.Shop_App_Backend.Repository.ShoeRepository;
import com.example.Shop_App_Backend.Repository.SuggestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class SuggestionService {
    private SuggestionRepository repository;
    private ShoeRepository shoeRepository;

    public Suggestion addServiceWithoutReference(Suggestion entity) { return this.repository.save(entity); } // without shoe id

    public Suggestion addService(Suggestion entity, Integer id) {
        Shoe shoe = shoeRepository.getReferenceById(id);
        if(shoe != null)
        {
            entity.setShoe(shoe);
            return this.repository.save(entity);
        }
         return addServiceWithoutReference(entity); // if the shoe doesn't exist, add the suggestion by itself
    }

    public List<Suggestion> addAllService(List<Suggestion> entities) { return this.repository.saveAll(entities); }

    public Suggestion getEntityById(Integer id) { return this.repository.findById(id).orElse(null); }

    public List<Suggestion> getAll() { return this.repository.findAll(); }

    public boolean exists(Integer id) { return this.repository.existsById(id); }

    public void deleteService(Integer id) { this.repository.deleteById(id); }

    public Suggestion updateService(Integer id, Suggestion newEntity)
    {
        Suggestion entityForUpdate = this.repository.findById(id).orElse(null);
        if(entityForUpdate != null)
        {
            entityForUpdate.setTitle(newEntity.getTitle());
            entityForUpdate.setAdvice(newEntity.getAdvice());
            return this.repository.save(entityForUpdate);
        }
        return null;
    }
}
