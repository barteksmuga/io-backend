package io.controllers;

import io.exceptions.models.ResourceNotFoundException;
import io.models.NoteModel;
import io.repositories.NoteRepository;
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

    @Autowired
    NoteRepository noteRepository;

    @GetMapping("/displayAll")
    public List<NoteModel> getAllNotes() {
        return noteRepository.findAll();
    }

    @GetMapping("/{id}")
    public NoteModel getNoteById(@PathVariable(value = "id") Long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Name", "Last", "Empty", noteId));
    }

    @PostMapping("/addNew")
    public NoteModel createNote(@Valid @RequestBody NoteModel note) {
        return noteRepository.save(note);
    }

    @PutMapping("/{id}")
    public NoteModel updateNote(@PathVariable(value = "id") Long noteId,
                                @Valid @RequestBody NoteModel noteDetails) {
        NoteModel note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Name", "Last", "Empty", noteId));

        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());
        note.setText(noteDetails.getText());

        NoteModel updatedNote = noteRepository.save(note);
        return updatedNote;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
        NoteModel noteModel = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Name", "Last", "Empty", noteId));

        noteRepository.delete(noteModel);
        return ResponseEntity.ok().build();
    }

}
