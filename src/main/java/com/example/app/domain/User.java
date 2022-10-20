package com.example.app.domain;

import lombok.Data;

@Data
public class User {

	private Integer id;
	
	private String loginId;
	
	private String loginPass;
	
	private String name;
	
	private String role;
}
