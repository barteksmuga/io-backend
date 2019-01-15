package io.controllers;

import io.exceptions.models.ResourceNotFoundException;
import io.models.AuthorModel;
import io.repositories.AuthorRepository;
import io.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("/note/author")
public class AuthorController {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    AuthorRepository authorRepository;

    @GetMapping("/{noteId}/authors")
    public Page<AuthorModel> getAllAuthors(@PathVariable(value = "noteId") Long noteId, Pageable pageable) {
        return authorRepository.findByNoteId(noteId, pageable);
    }

    @PostMapping("/{noteId}/authors")
    public AuthorModel createAuthors(@PathVariable(value = "noteId") Long noteId,
                                @Valid @RequestBody AuthorModel author) {

        return noteRepository.findById(noteId).map(note -> {
            author.setNote(note);
            return authorRepository.save(author);
        }).orElseThrow(() -> new ResourceNotFoundException("NoteId " + noteId + " not found"));
    }

    @PutMapping("/{noteId}/authors/{authorId}")
    public AuthorModel updateAuthor(@PathVariable(name = "noteId") Long noteId,
                               @PathVariable(name = "authorId") Long authorId,
                               @Valid @RequestBody AuthorModel authorReq) {
        if (!noteRepository.existsById(noteId)) {
            throw new ResourceNotFoundException("NoteId " + noteId + " not found");
        }

        return authorRepository.findById(authorId).map(author -> {
            author.setFirstName(authorReq.getFirstName());
            author.setLastName(authorReq.getLastName());
            return authorRepository.save(author);
        }).orElseThrow(() -> new ResourceNotFoundException("AuthorId " + authorId + " not found"));
    }

    @DeleteMapping("/{noteId}/authors/{authorId}")
    public ResponseEntity<?> deleteAuthor(@PathVariable (name="noteId") Long noteId,
                                          @PathVariable (name="authorId") Long authorId){
        if(!authorRepository.existsById(authorId))
            throw new ResourceNotFoundException("NoteId " + noteId + " not found");

        return authorRepository.findById(authorId).map(author -> {
            authorRepository.delete(author);
            return ResponseEntity.ok().body("Succes");
        }).orElseThrow(()->new ResourceNotFoundException("AuthorId " + authorId + " not found"));
    }
}
