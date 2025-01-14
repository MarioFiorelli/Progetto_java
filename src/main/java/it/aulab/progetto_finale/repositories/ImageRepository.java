package it.aulab.progetto_finale.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.aulab.progetto_finale.models.Image;
import jakarta.transaction.Transactional;
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    @Transactional
    void deleteByPath(String imageUrl);

    // @Modifying
    // @Transactional
    // @Query(value = "DELETE FROM images WHERE path = :imageUrl", nativeQuery = true)
    // void deleteByPath(@Param("imageUrl") String imageUrl);

}