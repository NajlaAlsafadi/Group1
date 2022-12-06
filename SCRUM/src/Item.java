import java.util.Date;

public class Item {

	private String itmName;
	private String itmType;
	private double itmPrice;
	private int itmAmount;
	private Date itmExpDate;
	
	public Item() {}
	
	public Item(String name, String type, double price, int amount, Date date) {
		
		this.itmName = name;
		this.itmType = type;
		this.itmPrice = price;
		this.itmAmount = amount;
		this.itmExpDate = date;
				
	}

	public String getItmName() {
		return itmName;
	}

	public void setItmName(String itmName) {
		this.itmName = itmName;
	}

	public String getItmType() {
		return itmType;
	}

	public void setItmType(String itmType) {
		this.itmType = itmType;
	}

	public double getItmPrice() {
		return itmPrice;
	}

	public void setItmPrice(double itmPrice) {
		this.itmPrice = itmPrice;
	}

	public double getItmAmount() {
		return itmAmount;
	}

	public void setItmAmount(int itmAmount) {
		this.itmAmount = itmAmount;
	}
	
	public Date getItmExpDate() {
		return itmExpDate;
	}

	public void setItmExpDate(Date itmExpDate) {
		this.itmExpDate = itmExpDate;
	}
	
	public String toString() {
		
		return "Item name: "+this.itmName+"\nItem type: "+this.itmType+"\nItem price: "+this.itmPrice+"\nItem expiry date: "+this.itmExpDate;
		
	}
}
