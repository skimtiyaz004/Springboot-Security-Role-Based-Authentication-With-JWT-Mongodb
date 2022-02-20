package com.jtw.jwtfinal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Role {
	@Id
	private String id;
	private ERole roleName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ERole getRoleName() {
		return roleName;
	}
	public void setRoleName(ERole roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "Role{" +
				"id='" + id + '\'' +
				", roleName=" + roleName +
				'}';
	}
}
