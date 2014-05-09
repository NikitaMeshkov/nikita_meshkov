package nik.Data;

public class Users {
	private String userName;
	private String password;
	private String email;
		  
	public Users() {
	}
		  
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Users [userName=" + userName + ", password=" + password
				+ ", email=" + email + "]";
	}

		 
}
