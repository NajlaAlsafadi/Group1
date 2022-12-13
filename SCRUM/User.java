import java.util.ArrayList;

public class User {

	private String username;
	private String password;
	private String userType;
	private ArrayList<Item> basket;
	
	public User() {}
	
	public User(String u, String p, String ut, ArrayList b) {
		
		this.username = u;
		this.password = p;
		this.userType = ut;
		this.basket = b;
		
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
	
	public ArrayList getBasket() {
		return basket;
	}

	public void setBasket(ArrayList<Item> basket) {
		this.basket = basket;
	}

	public String toString() {
		
		return "User: "+this.username+", User Type: "+this.userType;
		
	}
}
