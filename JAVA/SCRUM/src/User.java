
public class User {

	private String username;
	private String password;
	private String userType;
	
	public User() {}
	
	public User(String u, String p, String ut) {
		
		this.username = u;
		this.password = p;
		this.userType = ut;
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String toString() {
		
		return "User type: "+this.userType+"\nUsername: "+this.username;
		
	}
}
