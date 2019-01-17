package io.repositories;

import io.models.AuthorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorModel, Long> {
    Page<AuthorModel> findByNoteId(Long noteAuthorId, Pageable pageable);

}
