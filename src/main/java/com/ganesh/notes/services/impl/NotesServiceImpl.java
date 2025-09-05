package com.ganesh.notes.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ganesh.notes.entities.Notes;
import com.ganesh.notes.entities.User;
import com.ganesh.notes.payload.NotesDto;
import com.ganesh.notes.payload.UserDto;
import com.ganesh.notes.repositories.NotesRepo;
import com.ganesh.notes.repositories.UserRepo;
import com.ganesh.notes.services.NotesService;

@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepo notesRepo;

	@Autowired
	private UserRepo userRepo;

	// CREATE
	@Override
	public NotesDto createNote(NotesDto notesDto, String email) {
		User user = userRepo.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("❌ User not found with email: " + email));

		notesDto.setDate(new Date());

		Notes notes = DtoToNotes(notesDto);
		notes.setUser(user);

		Notes res = this.notesRepo.save(notes);
		return this.NotesToDto(res);
	}

	// UPDATE
	@Override
	public NotesDto updateNote(NotesDto notesDto, Integer notesId) {
		Notes notes = this.notesRepo.findById(notesId)
				.orElseThrow(() -> new RuntimeException("❌ Note not found with id: " + notesId));

		notesDto.setDate(new Date());
		notes.setTitle(notesDto.getTitle());
		notes.setDate(notesDto.getDate());
		notes.setDescription(notesDto.getDescription());

		Notes res = this.notesRepo.save(notes);
		return this.NotesToDto(res);
	}

	// DELETE
	@Override
	public void deleteNote(Integer notesId) {
		Notes notes = this.notesRepo.findById(notesId)
				.orElseThrow(() -> new RuntimeException("❌ Note not found with id: " + notesId));

		this.notesRepo.delete(notes);
	}

	// GET single note
	@Override
	public NotesDto getNote(Integer notesId) {
		Notes notes = this.notesRepo.findById(notesId)
				.orElseThrow(() -> new RuntimeException("❌ Note not found with id: " + notesId));

		return this.NotesToDto(notes);
	}

	// GET notes by user (email)
	@Override
	public List<NotesDto> getNoteByUser(String email) {
		User user = userRepo.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("❌ User not found with email: " + email));

		List<Notes> notes = this.notesRepo.findByUser(user);

		return notes.stream().map(this::NotesToDto).collect(Collectors.toList());
	}

	// GET all notes
	@Override
	public List<NotesDto> getAllNote() {
		List<Notes> notes = this.notesRepo.findAll();
		return notes.stream().map(this::NotesToDto).collect(Collectors.toList());
	}

	// 🔹 Helpers
	public NotesDto NotesToDto(Notes notes) {
		NotesDto notesDto = new NotesDto();
		notesDto.setID(notes.getId());
		notesDto.setTitle(notes.getTitle());
		notesDto.setDate(notes.getDate());
		notesDto.setDescription(notes.getDescription());
		notesDto.setUserDto(this.UserToDto(notes.getUser()));
		notesDto.setEmail(notes.getUser().getEmail()); // ✅ link back email
		return notesDto;
	}

	public Notes DtoToNotes(NotesDto notesDto) {
		Notes notes = new Notes();
		notes.setTitle(notesDto.getTitle());
		notes.setDate(notesDto.getDate());
		notes.setDescription(notesDto.getDescription());
		return notes;
	}

	public User DtoToUser(UserDto userDto) {
		User user = new User();
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		return user;
	}

	public UserDto UserToDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		return userDto;
	}
}
