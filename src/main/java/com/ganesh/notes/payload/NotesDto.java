package com.ganesh.notes.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ganesh.notes.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NotesDto {
	private Integer ID;
	private String Title;
	private String Description;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date;
	private UserDto userDto;
	 private String email; 

	public String getEmail() {
		return email;
	}

	 public void setEmail(String email) {
		 this.email = email;
	 }

	public Integer getID() {
		return this.ID;
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}

	public String getTitle() {
		return this.Title;
	}

	public void setTitle(String Title) {
		this.Title = Title;
	}

	public String getDescription() {
		return this.Description;
	}

	public void setDescription(String Description) {
		this.Description = Description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}
	
	

}
