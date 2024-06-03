package br.com.portalmudanca.enums;

public enum UserRole {

	ADMIN("admin"),
	USER("user");
	
	private String role;
	
	
	UserRole(String role) {
	  this.role = role;
	}
	
	
	public String getDescricao() {
		return role;
	}
	
	@Override
	public String toString() {
		return this.role;
	}
}
