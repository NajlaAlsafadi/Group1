import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Item {

	private String itmName;
	private String itmType;
	private double itmPrice;
	private int itmAmount;
	private String itemExpDateStr;

	public Item() {}

	public Item(String name, String type, double price, int amount, String date) {

		this.itmName = name;
		this.itmType = type;
		this.itmPrice = price;
		this.itmAmount = amount;
		this.itemExpDateStr = date;

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

	@SuppressWarnings("deprecation")
	public String toString() {

		return "Item name: "+this.itmName+"\nItem type: "+this.itmType+"\nItem price: "+this.itmPrice+"\nItem expiry date: "+this.itemExpDateStr + "\n" + "Quantity: "+ this.itmAmount;

	}

	public String getItemExpDateStr() {
		return itemExpDateStr;
	}

	public void setItemExpDateStr(String itemExpDateStr) {
		this.itemExpDateStr = itemExpDateStr;
	}
}
