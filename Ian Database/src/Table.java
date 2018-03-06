import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Table {
	ArrayList<String> table;

    Table(String[] columnTitles) {

    	table = new ArrayList<String>();
    	//ArrayList<String> header = Row.createRow(columnTitles);
    	Collections.addAll(table, columnTitles);
    	//System.out.println("header");
    	printTable(table);
    	
 }
    

    void printTable(ArrayList<String> table) {
    	
        for (int i = 0; i < table.size(); i++) {
            String value = table.get(i);
            System.out.println("Element: " + value);
        }

    	//System.out.println(Arrays.toString(houseAddress));
    }
    
    
    
    public static void main(String[] args) {
       
    	String[] myStringArray = {"a","b","c"};
    	Table newtable = new Table(myStringArray);
    	//System.out.print(newtable);
    	//printTable(newtable);
    }

}

