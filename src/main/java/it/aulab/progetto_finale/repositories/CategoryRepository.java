package it.aulab.progetto_finale.repositories;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import it.aulab.progetto_finale.models.Category;
@Repository
public interface CategoryRepository extends ListCrudRepository<Category, Long>{
    
}