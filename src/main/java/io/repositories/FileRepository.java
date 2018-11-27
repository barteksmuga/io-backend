package io.repositories;

import io.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    @Query(value = "SELECT * FROM files f WHERE f.name LIKE %:name%", nativeQuery = true)
    List<File> findByName (@Param("name") String name);
}
