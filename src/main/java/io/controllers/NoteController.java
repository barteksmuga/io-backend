package io.controllers;

import io.exceptions.models.ResourceNotFoundException;
import io.models.NoteModel;
import io.repositories.NoteRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {
    private final static Logger logger = Logger.getLogger(NoteController.class);
    @Autowired
    NoteRepository noteRepository;

    @GetMapping("/displayAll")
    public List<NoteModel> getAllNotes() {
        logger.info("GET, display all notes");
        return noteRepository.findAll();
    }

    @GetMapping("/{id}")
    public NoteModel getNoteById(@PathVariable(value = "id") Long noteId) {
        logger.info("GET, get note by id, noteId: ["+noteId+"]");
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Name", "id", noteId));
    }

    @PostMapping("/addNew")
    public NoteModel createNote(@RequestParam("title") String title, @RequestParam("content") String content) {
        logger.info("POST, create new note");
        NoteModel note = new NoteModel(title, content);
        return noteRepository.save(note);
    }

    @PutMapping("/{id}")
    public NoteModel updateNote(@PathVariable(value = "id") Long noteId,
                                @Valid @RequestBody NoteModel noteDetails) {
        NoteModel note = noteRepository.findById(noteId)
                .orElseThrow(() ->new ResourceNotFoundException("Name", "id", noteId));

        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());


        NoteModel updatedNote = noteRepository.save(note);
        logger.info("PUT, update note by id, noteId: ["+noteId+"]");
        return updatedNote;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
        NoteModel noteModel = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Name", "id", noteId));

        noteRepository.delete(noteModel);
        logger.info("DELETE, delete note by id, noteId: ["+noteId+"]");
        return ResponseEntity.ok().build();
    }

}
