import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

public class Sprint1 {

	public static void main(String[] args) {

		//Users and items lists
		ArrayList<User> userList = new ArrayList<User>();
		ArrayList<Item> itemList = new ArrayList<Item>();

		JFrame frame = new JFrame("Retail Outlet");
		frame.setLayout(new BorderLayout());
		JTextArea textArea = new JTextArea(500,50);
		frame.add(textArea,BorderLayout.NORTH);

		JMenu registerMI = new JMenu("Register/Login");
		JMenu itemMI = new JMenu("Items");
		JMenu shopMI = new JMenu("Shop");
		//JMenu loginMI = new JMenu("Login");
		JMenu exitMI = new JMenu("Exit");

		JMenuItem registerItem = new JMenuItem("Register");
		JMenuItem loginItem = new JMenuItem("Login");
		JMenuItem viewInDate = new JMenuItem("View Items");
		//Action Listeners
		shopMI.add(viewInDate);
		viewInDate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JLabel emptyListErr = new JLabel("Item list is empty.");
				int x = 0;
				if(itemList.isEmpty()) {

					JOptionPane.showMessageDialog(null, new Object[] {emptyListErr}, "Delete item error", JOptionPane.ERROR_MESSAGE);

				}else {
					textArea.setText("");
					for (Item i : itemList) { 		
						
						try {
							
							Item ite = itemList.get(x);
							String date1 = ite.getItemExpDateStr();
							Date date;
							date = new SimpleDateFormat("dd/MM/yyyy").parse(date1);
							Date currentDate = new Date();
							if(date.after(currentDate))
							textArea.append(itemList.get(x).toString());
							x++;
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
						
						
					}

				}


			}
		});



		registerItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				//Components for registration dialog
				JLabel regLbl = new JLabel("Register with the system");
				JLabel space = new JLabel("");
				JLabel usrType = new JLabel("Choose a user type:");
				String[] users = {"Staff","Customer"};
				JComboBox<String> userBox = new JComboBox<String>(users);
				JLabel usrLbl = new JLabel("Enter username:");
				JTextField usrTfld = new JTextField();
				JLabel pswdLbl1 = new JLabel("Enter password:");
				JPasswordField pswdFld1 = new JPasswordField();
				JLabel pswdLbl2 = new JLabel("Re-enter password:");
				JPasswordField pswdFld2 = new JPasswordField();
				JLabel emptyUsrErr = new JLabel("Enter a username.");
				JLabel takenUsrErr = new JLabel("Username already registered. Please choose a different username and try again.");
				JLabel pswdErr = new JLabel("Password fields do not match.");
				JLabel validReg = new JLabel("You have successfully registered with the system.");
				Boolean taken = false;

				//Registration dialog box
				int register = JOptionPane.showOptionDialog(null, new Object[] {regLbl,space,usrType,userBox,usrLbl,usrTfld,pswdLbl1,pswdFld1,pswdLbl2,pswdFld2},
						"Registration", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);


				if(register == JOptionPane.OK_OPTION) {

					String userType = (String)userBox.getSelectedItem();

					//Check if user name is already registered 
					for(User u: userList) {

						if(u.getUsername().equals(usrTfld.getText())) {
							//User name taken error message
							JOptionPane.showMessageDialog(null, new Object[] {takenUsrErr}, "Username error", JOptionPane.ERROR_MESSAGE);
							taken = true;

						}						
					}

					if(!taken) {

						//Check that user name text field is not empty and that password fields match
						if(usrTfld.getText().equals("")) {

							JOptionPane.showMessageDialog(null, new Object[] {emptyUsrErr}, "Username error", JOptionPane.ERROR_MESSAGE);

						}
						else if(!pswdFld1.getText().equals(pswdFld2.getText())) {

							JOptionPane.showMessageDialog(null, new Object[] {pswdErr}, "Password error", JOptionPane.ERROR_MESSAGE);

						}
						else {
							//Create user and add it to user list
							User user = new User(usrTfld.getText(), pswdFld1.getText(), userType);
							userList.add(user);

							//User added message
							JOptionPane.showMessageDialog(null, new Object[] {validReg}, "Registration", JOptionPane.INFORMATION_MESSAGE);

						}						
					}				
				}
			}});

		loginItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				//Components for login dialog
				JLabel logLbl = new JLabel("Login to the system");
				JLabel space = new JLabel("");
				JLabel usrLbl = new JLabel("Enter username:");
				JTextField usrTfld = new JTextField();
				JLabel pswdLbl = new JLabel("Enter password:");
				JPasswordField pswdFld = new JPasswordField();
				JLabel validLog = new JLabel("You are now logged into the system.");
				JLabel logErr = new JLabel("Login error.");
				Boolean loggedIn = false;

				//Login dialog box
				int login = JOptionPane.showOptionDialog(null, new Object[] {logLbl,space,usrLbl,usrTfld,pswdLbl,pswdFld},
						"Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);


				if(login == JOptionPane.OK_OPTION) {

					//Find user in user list
					for(User u: userList) {

						if(u.getUsername().equals(usrTfld.getText()) && u.getPassword().equals(pswdFld.getText())) {

							loggedIn = true;

						}					
					}

					if(loggedIn) {
						//Logged in message
						JOptionPane.showMessageDialog(null, new Object[] {validLog}, "Login", JOptionPane.INFORMATION_MESSAGE);

					}
					else
						//Login error message
						JOptionPane.showMessageDialog(null, new Object[] {logErr}, "Login error", JOptionPane.ERROR_MESSAGE);
				}
			}});



		registerMI.add(registerItem);
		registerMI.add(loginItem);

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

				//Item dialog box
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
						//delete this item before demo!
						textArea.setText("");
						textArea.append(item.toString());

						//Item added message
						JOptionPane.showMessageDialog(null, new Object[] {validItm}, "Add item", JOptionPane.INFORMATION_MESSAGE);
					}

				}
				else if(addItms == JOptionPane.CANCEL_OPTION) {}
			}

		});

		JMenuItem delItem = new JMenuItem("View Items");

		delItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int size = itemList.size();
				String[] itmsArr = new String[size];
				JLabel emptyListErr = new JLabel("Item list is empty.");


				if(itemList.isEmpty()) {

					JOptionPane.showMessageDialog(null, new Object[] {emptyListErr}, "Delete item error", JOptionPane.ERROR_MESSAGE);

				}
				else {

					for(int i = 0; i < itemList.size(); i++) {

						String itmName = itemList.get(i).getItmName()+ " "+itemList.get(i).getItemExpDateStr();

						itmsArr[i] = itmName;
					}

					JLabel delLbl = new JLabel("Choose an item to delete:");
					JComboBox<String> delBox = new JComboBox<String>(itmsArr);
					JLabel validDel = new JLabel("Item has been deleted from the system.");

					int delItm = JOptionPane.showOptionDialog(null, new Object[] {delLbl, delBox},
							"View Items", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

					if(delItm == JOptionPane.OK_OPTION) {

						Item item = null;
						String delSelect = (String)delBox.getSelectedItem();

						for(Item i: itemList) {

							if(i.getItmName().equals(delSelect))

								item = i;

						}

						itemList.remove(item);

						JOptionPane.showMessageDialog(null, new Object[] {validDel}, "Add item", JOptionPane.INFORMATION_MESSAGE);

					}					
				}								
			}			
		});

		itemMI.add(delItem);
		itemMI.add(addItem);

		// create a menu bar and use it in this JFrame
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(registerMI);
		menuBar.add(shopMI);
		//menuBar.add(loginMI);
		menuBar.add(itemMI);
		menuBar.add(exitMI);
		frame.setJMenuBar(menuBar);

		// Final JFrame methods to set close operation + set size and visibility
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,550);
		frame.setVisible(true);

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

}