package com.example.Shop_App_Backend.Repository;

import com.example.Shop_App_Backend.Domain.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Integer> {
}

