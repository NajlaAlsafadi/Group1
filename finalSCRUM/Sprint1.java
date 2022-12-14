import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.*;

public class Sprint1 extends JFrame{

	//Hashmap to determine who's logged in
	private Map<String, Object> loggedInMap = new HashMap<String, Object>();

	//Users and items lists
	private ArrayList<User> userList = new ArrayList<User>();
	private ArrayList<Item> itemList = new ArrayList<Item>();
	private ArrayList<Item> basket = new ArrayList<Item>();
	private ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
	private ArrayList<String> strCB = new ArrayList<String>();


	private JTextArea textArea = new JTextArea(500,50);
	private JCheckBox cb;

	protected int q;

	public Sprint1() {

		super("Retail Outlet");

		User user = new User("user", "p", "Staff", basket);		
		userList.add(user);

		setLayout(new BorderLayout());

		textArea.append(" Welcome to Retail Outlet!");
		textArea.setEditable(false);
		add(textArea,BorderLayout.NORTH);

		JMenu itemMI = new JMenu("Items");
		JMenu exitMI = new JMenu("Exit");
		JMenu shopMI = new JMenu("Shopping");

		JMenuItem viewInDate = new JMenuItem("View Items");
		JMenuItem viewBasket = new JMenuItem("View Basket");
		JMenuItem calBasket = new JMenuItem("Calculate Basket");
		shopMI.add(calBasket);
		shopMI.add(viewInDate);
		JTextField quantity = new JTextField();
		//Action Listeners
		shopMI.add(viewBasket);
		viewBasket.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText(basket.toString()); 
			}
		});
	
		viewInDate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String[] itmsArr = new String[3];
				JLabel emptyListErr = new JLabel("Item list is empty.");


				if(itemList.isEmpty()) {

					JOptionPane.showMessageDialog(null, new Object[] {emptyListErr}, "View item error", JOptionPane.ERROR_MESSAGE);

				}
				else {
					int x=0;
					itmsArr[0] = "Luxury";
					itmsArr[1] = "Essential";
					itmsArr[2] = "Gift";

					JLabel viewLbl = new JLabel("Choose a Category to Browse:");
					JComboBox<String> viewBox = new JComboBox<String>(itmsArr);
					JLabel validDel = new JLabel("Item has been deleted from the system.");
					Object[] btnOptions = {"View", "Cancel"};



					int vewItm = JOptionPane.showOptionDialog(null, new Object[] {viewLbl, viewBox},
							"View Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, btnOptions, btnOptions[1]);

					if(vewItm == JOptionPane.OK_OPTION) {
						textArea.setText("");
						JPanel panel = new JPanel();
						String VAT = null;
						String type = null;
						int xx = 0;

						for(Item i: itemList) {

							String viewCat = (String)viewBox.getSelectedItem();

							if(i.getItmType().equals(viewCat)) {

								String date1 = i.getItemExpDateStr();
								type = i.getItmType();

								Date date = null;
								try {
									date = new SimpleDateFormat("dd/MM/yyyy").parse(date1);
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								Date currentDate = new Date();

								if(date.after(currentDate)) {

									cb = new JCheckBox(i.getItmName());
									panel.add(cb);
									checkBoxes.add(cb);


									textArea.append("\n "+i.toString());
								}										
							}	
						}

						JLabel itmLbl = new JLabel("Tick the "+type+" items below:"); 
						if(type.equals("Luxury")) {
							VAT = "VAT Rate 20%"; 
						}
						if(type.equals("Essential")) {
							VAT = "VAT Rate 10%"; 
						}
						if(type.equals("Essential")) {
							VAT = "VAT	 Rate 5%"; 
						}
						JLabel vatLbl = new JLabel(VAT);
						Object[] itmBtnOptions = {"Calculate","Add to Basket","Cancel"};
						JLabel lbl = new JLabel("Quantity: ");
						
						int pickItm = JOptionPane.showOptionDialog(null, new Object[] {itmLbl, vatLbl, panel, lbl, quantity},
								type+" Item", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, itmBtnOptions, itmBtnOptions[2]);

						if(pickItm == JOptionPane.YES_OPTION) {
							double runningTotal = 0;
							for(Item i: basket) {

								String s = i.getItmType();

								if(s.equalsIgnoreCase("Luxury")) {

									i.setItmPrice(i.getItmPrice()*1.2);
									runningTotal = runningTotal + i.getItmPrice();
								}
								else if(s.equalsIgnoreCase("Essential")) {

									i.setItmPrice(i.getItmPrice()*1.1);
									runningTotal = runningTotal + i.getItmPrice();
								}
								else if(s.equalsIgnoreCase("Gift")) {

									i.setItmPrice(i.getItmPrice()*1.05);
									runningTotal = runningTotal + i.getItmPrice();
								}

							}
							for(Item i: itemList) {
								for(Item itm: basket) {
									if(itm.equals(i)) {
										int q = Integer.parseInt(quantity.getText());
										i.setItmAmount((i.getItmAmount()-q));
									}

								}
							}
							String s = String.valueOf(runningTotal);
							textArea.setText("You Owe: " + s);
						}
						else if(pickItm == JOptionPane.NO_OPTION) {

							//Add to Basket option
							Item item = null;
							String c;

							for(JCheckBox cb: checkBoxes) {

							    if(cb.isSelected()) {
							        c = cb.getText();
							        for(Item i: itemList) {

							            if(i.getItmName().equals(c)) {
							                item = i;
							                // Only add the item to the basket if it matches the selected checkbox
							                basket.add(item);
							            }
							        }
							    }
							}

							textArea.setText("BASKET: \n");
							for(Item i: basket) {
								textArea.append(i.getItmName());
							}
						}
					}
				}
			}					
		});
		
		calBasket.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				double runningTotal = 0;
				for(Item i: basket) {

					String s = i.getItmType();

					if(s.equalsIgnoreCase("Luxury")) {

						i.setItmPrice(i.getItmPrice()*1.2);
						runningTotal = runningTotal + i.getItmPrice();
					}
					else if(s.equalsIgnoreCase("Essential")) {

						i.setItmPrice(i.getItmPrice()*1.1);
						runningTotal = runningTotal + i.getItmPrice();
					}
					else if(s.equalsIgnoreCase("Gift")) {

						i.setItmPrice(i.getItmPrice()*1.05);
						runningTotal = runningTotal + i.getItmPrice();
					}

				}
				for(Item i: itemList) {
					for(Item itm: basket) {
						if(itm.equals(i)) {
							i.setItmAmount((i.getItmAmount()-q));
						}

					}
				}
				String s = String.valueOf(runningTotal);
				textArea.setText("You Owe: " + s);
			}

		});

		JMenuItem addItem = new JMenuItem("Add Item");


		addItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				//Components for item dialog
				JLabel itmLbl = new JLabel("Add items to the system.");
				JLabel space = new JLabel("");
				JLabel itmNameLbl = new JLabel("Enter item name:");
				JTextField itmTfld = new JTextField();

				JLabel typeLbl = new JLabel("Item type:");
				String[] types = {"Luxury","Essential","Gift"};
				JComboBox<String> typeBox = new JComboBox<String>(types);

				JLabel dayLbl = new JLabel("Expiration Day:");
				Integer[] days = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30};
				JComboBox<Integer> dayBox = new JComboBox<Integer>(days);
				JLabel monthLbl = new JLabel("Expiration Month:");
				Integer[] months = {1,2,3,4,5,6,7,8,9,10,11,12};
				JComboBox<Integer> monthBox = new JComboBox<Integer>(months);
				JLabel yearLbl = new JLabel("Expiration Year:");
				Integer[] years = {2022,2023,2024};
				JComboBox<Integer>yearBox = new JComboBox<Integer>(years);

				JLabel amtLbl = new JLabel("Amount of items:");
				JTextField amtTfld = new JTextField();

				JLabel emptyItmErr = new JLabel("Item must have a name.");
				JLabel nanAmtErr = new JLabel("Amount must be a positive integer.");
				JLabel validItm = new JLabel("You have successfully updated the system.");
				JLabel nonStaff = new JLabel("You must be logged in as a Staff user to add items.");

				User user;
				String userType = "";

				int addItms = JOptionPane.showOptionDialog(null, new Object[] {itmLbl,space,itmNameLbl,itmTfld,typeLbl,typeBox,dayLbl,dayBox,monthLbl,monthBox,yearLbl,yearBox,amtLbl,amtTfld},
						"Add Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);



				if(addItms == JOptionPane.OK_OPTION) {

					int yearRes = (Integer)yearBox.getSelectedItem();
					int monthRes = (Integer)monthBox.getSelectedItem();
					int dayRes = (Integer)dayBox.getSelectedItem();	
					String dateFormatStr = dayRes+"/"+monthRes+"/"+yearRes;
					Boolean validAmt;
					Double price = 0.0;

					String itmType = (String)typeBox.getSelectedItem();


					//Set price for selected item type
					if(typeBox.getSelectedItem().equals("Luxury")) {

						price = (double) 50;

					}
					else if(typeBox.getSelectedItem().equals("Essential")) {

						price = (double) 30;

					}
					else if(typeBox.getSelectedItem().equals("Gift")) {

						price = (double) 20;

					}

					//Check that amount is an integer and item text field is not empty
					validAmt = checkAmt(amtTfld.getText());

					if(itmTfld.getText().equals("")) {
						//Empty text field error message
						JOptionPane.showMessageDialog(null, new Object[] {emptyItmErr}, "Item name error", JOptionPane.ERROR_MESSAGE);

					}
					else if(!validAmt) {
						//Amount NaN error message
						JOptionPane.showMessageDialog(null, new Object[] {nanAmtErr}, "Amount error", JOptionPane.ERROR_MESSAGE);

					} 
					else {
						//Create item and add it to item list
						Integer amount = Integer.parseInt(amtTfld.getText());

						Item item = new Item(itmTfld.getText(),itmType,price,amount,dateFormatStr);
						itemList.add(item);
						textArea.append("\n "+item.toString()+" has been added to the system.");

						//Item added message
						JOptionPane.showMessageDialog(null, new Object[] {validItm}, "Add item", JOptionPane.INFORMATION_MESSAGE);
					}

				}
				else if(addItms == JOptionPane.CANCEL_OPTION) {}
				else

					JOptionPane.showMessageDialog(null, new Object[] {nonStaff}, "Access restricted", JOptionPane.ERROR_MESSAGE);		    		    			    
			}

		});

		viewBasket.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.append(basket.toString());	}
		});

		itemMI.add(addItem);

		JMenuItem viewItem = new JMenuItem("View item");

		viewItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int size = itemList.size();
				String[] itmsArr = new String[size];
				JLabel emptyListErr = new JLabel("Item list is empty.");


				if(itemList.isEmpty()) {

					JOptionPane.showMessageDialog(null, new Object[] {emptyListErr}, "View item error", JOptionPane.ERROR_MESSAGE);

				}
				else {
					textArea.setText(itemList.toString());
					for(int i = 0; i < itemList.size(); i++) {

						String itmName = itemList.get(i).getItmName()+ " "+itemList.get(i).getItemExpDateStr();

						itmsArr[i] = itmName;
					}

					JLabel delLbl = new JLabel("Item list:");
					JComboBox<String> delBox = new JComboBox<String>(itmsArr);
					JLabel validDel = new JLabel("Item has been deleted from the system.");
					Object[] btnOptions = {"Delete", "Cancel"};

					int delItm = JOptionPane.showOptionDialog(null, new Object[] {delLbl, delBox},
							"View Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, btnOptions, btnOptions[1]);

					if(delItm == JOptionPane.OK_OPTION) {

						User user;
						String userType = "";
						Boolean delete = false;
						Item item = null;

						String delSelect = (String)delBox.getSelectedItem();

						for(Item i: itemList) {

							if(i.getItmName().equals(delSelect))

								delete = true;
							item = i;

							itemList.remove(item);
							JOptionPane.showMessageDialog(null, new Object[] {validDel}, "Delete item", JOptionPane.INFORMATION_MESSAGE);
							textArea.setText("");
							break;
						}	

					}					
				}								
			}			
		});

		itemMI.add(viewItem);

		// create a menu bar and use it in this JFrame
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(shopMI);
		menuBar.add(itemMI);
		menuBar.add(exitMI);
		setJMenuBar(menuBar);

		// Final JFrame methods to set close operation + set size and visibility
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,550);
		setLocation(520, 150);
		setVisible(true);

	}

	public void getSelected() {

		for (Component c : getComponents()) {

			if (c instanceof JCheckBox)
				if (((JCheckBox) c).isSelected()) {


				}
		}
	}


	//Function checking if amount is an integer and positive
	public static boolean checkAmt(String s) {

		Boolean value = false;
		Integer number = 0;

		if(s == null || s.length() == 0) {

			value = false;

		}

		try {
			number = Integer.parseInt(s);
			value = true;
		}catch(NumberFormatException e) {
			value = false;
		}

		if(number <= 0) {
			value = false;
		}

		return value;
	}


	public static void main(String[] args) {

		new Sprint1();

	}		
}