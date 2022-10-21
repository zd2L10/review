package com.example.app.domain;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class User {

	private Integer id;
	
	@NotBlank
	private String loginId;
	
	@NotBlank
	private String loginPass;
	
	private String name;
	
	private String role;
	
	private String confpass;
	
	private String login;
}
