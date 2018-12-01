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
import java.util.Optional;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    NoteRepository noteRepository;

    @GetMapping("/getAll")
    public List<NoteModel> getAllNotes() {
        return noteRepository.findAll();
    }

    @GetMapping("/getById/{id}")
    public NoteModel getNoteById(@PathVariable(value = "id") Long noteId){
        return noteRepository.findById(noteId)
                .orElseThrow(()-> new ResourceNotFoundException("Name", "Last", "Empty", noteId));
    }

    @PostMapping("/post")
    public NoteModel createNote(@Valid @RequestBody NoteModel note) {
        return noteRepository.save(note);
    }

    @PutMapping("/put/{id}")
    public NoteModel updateNote(@PathVariable(value = "id") Long noteId,
                                @Valid @RequestBody NoteModel noteDetails){
        NoteModel note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Name", "Last","Empty", noteId));

        note.setFirstName(noteDetails.getFirstName());
        note.setLastName(noteDetails.getLastName());
        note.setText(noteDetails.getText());

        NoteModel updatedNote = noteRepository.save(note);
        return updatedNote;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId){
        NoteModel noteModel=noteRepository.findById(noteId)
                .orElseThrow(()-> new ResourceNotFoundException("Name", "Last","Empty", noteId));

        noteRepository.delete(noteModel);

        return ResponseEntity.ok().build();
    }

}
