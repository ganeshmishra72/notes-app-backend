package com.ganesh.notes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ganesh.notes.entities.Notes;
import com.ganesh.notes.entities.User;

public interface NotesRepo extends JpaRepository<Notes, Integer>{
    List<Notes> findByUser(User user);
}
