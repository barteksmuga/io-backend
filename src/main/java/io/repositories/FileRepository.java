package io.repositories;

import io.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    @Query(value = "SELECT * FROM files f WHERE f.name = :name OR f.extension = :extension", nativeQuery = true)
    Optional<File> findByNameOrExtension (@Param("name") String name, @Param("extension") String extension);
}
