
import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;

public class Table implements Serializable {
	private ArrayList<Row> tabledata = new ArrayList<Row>();
	private ArrayList<String> keydata = new ArrayList<String>();
	private String selectedkey; //unused - stores key of selected row. 
	private String tabletitle; 
	private int headersize; 

	ArrayList<Row> getTable() { return tabledata;}
	ArrayList<String> getKey() {return keydata;	}
	String tabletitle() {return tabletitle;}; 
	int getHeader() {return headersize;	}
	
	Table(){}

	//Table Constructor that creates a new Table from any Row
	Table(Row firstrow, String title) {
		headersize = firstrow.field().size();
		tabletitle = title; 
		addRow(firstrow);
	}

	//Table Constructor that allows for user to define custom Header Fields
	Table(String[] headerfields, String title) {
		headersize = headerfields.length; 
		tabletitle = title; 
		addRow(headerfields);
	}
		

	//checks that there are no duplicate keys. Recreates Row if there is. 
	Row keyChecker( Row row) {
		if(keydata.contains(row.key())) {
			Row newrowkey = new Row(row.field());
			return newrowkey; 
		}
		return row; 
	}
	
	//creates a row with String "rowdata" and places it in the Table. 
	void addRow(String[] rowdata) {
		Row newrow = new Row(rowdata);
		newrow = keyChecker(newrow);
		if (rowSizing(newrow)==true) {
		Collections.addAll(tabledata, newrow);
		Collections.addAll(keydata, newrow.key());
	}}
	
	void addRow(Row row) {
		if(rowSizing(row)==true) {
		row = keyChecker( row);	
		Collections.addAll(tabledata, row);
		Collections.addAll(keydata, row.key());
	}}
	
	//checks rows fit within headersize 
	boolean rowSizing(Row row) {
		if(row.field().size() != headersize) {
			System.out.println("   Wrong number for elements for Row");
			return false;
		}
		return true; 
	}
	
	//adds a column with a field/header name and empty fields. 
	void addColumn(String fieldname) {
		tabledata.get(0).rowAddCol(fieldname);
		headersize = headersize + 1; 
		for (int i = 1; i < tabledata.size(); i++) {
			tabledata.get(i).rowAddCol("empty");
			}
	}
	
	//Deletes a row object at the specific element "rownumber" in Table. 
	void deleteRow(int rownumber) {
		tabledata.remove(rownumber);
	}
	
	//Deletes a row based on it's key. A key can be saved with the selectRow method. 
	void deleteRowKey( String keyint, ArrayList<String> key) {
		 int rownumber = 0; 
			    for(int i=0; i < key.size(); i++) {
			        String s = key.get(i);
			        if (s == keyint){rownumber = i;}}
		for (int i = 0; i <= headersize; i++) {
		tabledata.remove(rownumber);
		}}
	
	//Prints the content of a specific row, and saves that rows key in Table to be used later for targetting. 
	void selectRow(int rownumber) {
				System.out.print("   Selected Row Contains: " );
				Row value = tabledata.get(rownumber);
				value.rowPrint();
				System.out.println("... End Selection");
				selectedkey = value.key(); 
		    }
	
	//replaces a Row with another Row. 
	void updateRow(int rownumber, Row row) {
				tabledata.set(rownumber, row);
			}

	//searches table for a string, and returns row it is contained in. 
	Row searchRows(String string) {
		Row newrow = new Row();
		for (int i = 0; i < tabledata.size(); i++) {
			if(tabledata.get(i).rowContains(string) == true) {
			newrow = tabledata.get(i);
		}}
		return newrow; 
	}
	
	//Prints a table
	void printTable(Table source) {
		System.out.println("TITLE:" + source.tabletitle);
		for (int i = 0; i < source.tabledata.size(); i++) {
			Row value = source.tabledata.get(i);
			value.rowPrint();
	        System.out.println();
		}
		  System.out.println();
	}

	
	 //-----------------------------------------------------------//
	//-------------------------TESTING---------------------------//
   //-----------------------------------------------------------//
	
	//This is where the values for our first test table are defined. 
	//String could easily come from user input
	Table createTableTest() {
		String[] header = { "ID", "NAME", "KIND", "Owner" };
		String[] rowone = { "1", "Fido", "dog", "ab123" };
		String[] rowtwo = { "2", "Wanda", "fish", "ef789" };
		String[] rowthree = { "3", "Garfield", "cat", "ab123" };
		Table newtable = new Table(header, "Test Table One");
		newtable.addRow(rowone);
		newtable.addRow(rowtwo);
		newtable.addRow(rowthree);
		return newtable;
	}

	//Second test table
	Table createTableTestTwo() {
		String[] header = { "USERNAME", "NAME"};
		String[] rowone = { "ab123", "Jo"};
		String[] rowtwo = { "cd456", "Sam"};
		String[] rowthree = { "ef789", "Amy"};
		String[] rowfour = { "gh012", "Pete"};
		Table newtable = new Table(header, "Test Table Two");
		newtable.addRow(rowone);
		newtable.addRow(rowtwo);
		newtable.addRow(rowthree);
		newtable.addRow(rowfour);
		return newtable;
	}

	void test() {
		//Creates two test tables
		File newfile = new File();
		System.out.println("Creating Test Tables...");
		Table testtable = createTableTest();
		Table testtabletwo = createTableTestTwo();
		//Saves a test table to a file
		System.out.println("Testing Saving...");
		newfile.saveTable(testtable, "src\\Files\\testtable.ser");
		
		//Tests the table printing function
		System.out.println("Testing Printing...");
		System.out.println("Printing Table One...\n");
		printTable(testtabletwo);
		System.out.println("Printing Table Two...\n");
		printTable(testtable);
		
		//Tests loading from earlier file, and prints result for comparison 
		System.out.println("Testing Loading...");
		Table loadtable = newfile.deserialize("src\\Files\\testtable.ser");
		System.out.println("Printing Loaded File...");
		printTable(loadtable);

		//Test row update method that changes values of a row in a table. 
		System.out.println("Testing Row Update...Adding 99, Pete, gargoyle, and ab666");
		String[] rowupdate = { "99", "Pete", "gargoyle", "ab666" };
		Row newrow = new Row(rowupdate);
		testtable.updateRow(2, newrow);
		printTable(testtable);
		
		//testing the ability to select a row, and store its key as the selectedkey
		System.out.println("Testing Row Select... Selecting element 2\n");
		testtable.selectRow(2);
		testtabletwo.selectRow(2);
		
		//Tests the delete row method and prints for comparison
		System.out.println("\nTesting Delete Row...\n");
		testtable.deleteRow(1);
		printTable(testtable);
		
		//Test the addColumn method, which adds a column to a specified table; 
		System.out.println("Testing Add Column...\n");
		testtable.addColumn("test");
		testtable.addColumn("testtwo");
		testtable.addColumn("testthree");
		printTable(testtable);
		
		//checks that a row of wrong dimensions cannot be added to a table
		System.out.println("Testing Row Limits...Should throw error:");
		//should fail 
		String[] columntest = { "3", "Garfield", "cat", "ab123" };
		testtable.addRow(columntest);
		
		//should pass
		String[] columntest2 = { "45", "Godzilla", "superlizard", "godz23", "newcomlumn", "test", "test"};
		testtable.addRow(columntest2);
		printTable(testtable);
		
		//searches for row containing string
		System.out.println("Searching rows for string:Godzilla...");
		Row searchrow = testtable.searchRows("Godzilla");
		searchrow.rowPrint();
	}
	
	void run(String[] args) {
		if (args.length == 0)
			test();
		else if (args.length >= 1) {
			System.out.println("This Class Takes No Arguments");
		}
	}

	public static void main(String[] args) {
		Table newtable = new Table();
		newtable.run(args);
	}

}
