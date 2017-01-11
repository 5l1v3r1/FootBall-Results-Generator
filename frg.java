package frg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class frg {
	static ArrayList<game> games; 
	static int inputErrors = 0;

	public static void main(String[] args) throws FileNotFoundException {
		games = new ArrayList<game>();
		Scanner s = new Scanner(System.in);
		String choice;

		System.out.println("Enter 1 to import match results from file or any other key to manually enter them");
		choice = s.nextLine();
		if (choice.equals("1")) {
			System.out.print("\nChoice 1 Selected\nEnter File Path\n"); //in the format C:/input.txt
			File file = null;
			try {
				String FilePath = s.nextLine();
				file = new File(FilePath);
				Scanner exception = new Scanner(file);
				exception.close();
			} catch (FileNotFoundException e) {
				System.out.println("File Not Found");
				System.exit(0);
			}
			Scanner ReadFile = new Scanner(file);
			int lineNumber = 0;
			while (ReadFile.hasNextLine()) {
				String contents = ReadFile.nextLine();
				if (!contents.contains(":")) {
					inputErrors++;
					continue;
				}
				if (contents.split(":").length < 4) {
					inputErrors++;
					continue;
				}
				String[] args2 = contents.split(":");
				String hn = args2[0].trim();
				String an = args2[1].trim();
				int hs = -1;
				int as = -1;
				if (hn == null || an == null || hn.isEmpty() || an.isEmpty()) {
					inputErrors++;
					continue;
				}
				try {
					hs = Integer.parseInt(args2[2].trim());
					as = Integer.parseInt(args2[3].trim());
				} catch (NumberFormatException nfe) {
					inputErrors++;
					continue;
				}

				games.add(new game(hn, an, hs, as));

				lineNumber++;
		
			}
			System.out.println("There were " + inputErrors + " invalid entries in this file");
		}
		System.out.println("Enter: \"HomeTeam : AwayTeam : HomeScore : AwayScore\" to enter more results or \"stop\" to Calculate Totals");
		for (int maxruns = 0; maxruns < 100; maxruns++) {
			String input = s.nextLine();
			if (input.equals("stop")) {
				break;
			}
			if (!input.contains(":")) {
				System.out.println("No/wrong delimiter!");
				inputErrors++;
				continue;
			}
			if (input.split(":").length < 4) {
				System.out.println("Not enough arguments!");
				inputErrors++;
				continue;
			}
			String[] args1 = input.split(":");
			String hn = args1[0].trim();
			String an = args1[1].trim();
			int hs = -1;
			int as = -1;
			if (hn == null || an == null || hn.isEmpty() || an.isEmpty()) {
				System.out.println("Home or Away team name is invalid!");
				inputErrors++;
				continue;
			}
			try {
				hs = Integer.parseInt(args1[2].trim());
				as = Integer.parseInt(args1[3].trim());
			} catch (NumberFormatException nfe) {
				System.out.println("Home or Away score is invalid!");
				inputErrors++;
				continue;
			}
			// no errors, add new game to arraylist
			games.add(new game(hn, an, hs, as));
		}
		// user entered stop
		int totalGames = 0;
		int totalHome = 0;
		int totalAway = 0;
		int highestHome = 0;
		int highestAway = 0;
		for (game g : games) {
			if (g.homeScore > highestHome)
				highestHome = g.homeScore;
			if (g.awayScore > highestAway)
				highestAway = g.awayScore;
			totalHome += g.homeScore;
			totalAway += g.awayScore;
			totalGames++;
			g.print();
		}
		System.out.print("\nTotals\n--------------------------\n");
		System.out.println("Total number of games played: " + totalGames);
		System.out.println("Total home score: " + totalHome);
		System.out.println("Total away score: " + totalAway);
		System.out.println("Highest home score: " + highestHome);
		System.out.println("Highest away score: " + highestAway);
		System.out.println("Total invalid entries: " + inputErrors);
		//creating html page
		FileWriter fileWriter = null;
    	BufferedWriter writer = null;
    	String dir = "C:/Users/user/Desktop/HtmlWrite/index.html"; //Where html file is created 
    	try {
    		
    	    fileWriter = new FileWriter(dir);
    	    writer = new BufferedWriter(fileWriter);  
    	    int size = games.size();
    	    
    	    writer.write("This file is stored in " + dir); 
    	    writer.write("<br>");
    	    writer.write("Printing Match Results");
    	    //Styling
    	    writer.write("<style>"); writer.newLine(); 
    	    writer.write("table { "); writer.newLine(); 
    	    writer.write("font-family: arial, sans-serif;"); writer.newLine();
    	    writer.write("border-collapse: collapse;"); writer.newLine();
    	    writer.write("width: 100%;"); writer.newLine(); writer.write("}"); writer.newLine();
    	    writer.write("td, th {"); writer.newLine();
    	    writer.write("border: 2px solid #dddddd;"); writer.newLine();
    	    writer.write("text-align: left"); writer.newLine();
    	    writer.write("padding: 8px;"); writer.newLine(); writer.write("}"); writer.newLine();
    	    writer.write("tr:nth-child(even) {"); writer.newLine();
    	    writer.write("background-color: #dddddd;"); writer.newLine(); writer.write("}"); writer.newLine();
    	    writer.write("</style>"); writer.newLine();
    	    //main body of html
    	    writer.write("<body>"); writer.newLine();
    	    //results table
    	    writer.write("<table>"); writer.newLine();
    	    writer.write("<tr><th>Home Team</th><th>Away Team</th><th>Home Score</th><th>Away Score</th></tr>"); 
    	    for(int i=0;i<size;i++){
    	    	String homenames = games.get(i).homeName;
    	    	String awaynames = games.get(i).awayName;
    	    	int homescore = games.get(i).homeScore;
    	    	int awayscore = games.get(i).awayScore;
    	    	writer.write("<tr><td>" + homenames + "</td>" + "<td>" + awaynames + "</td>" + "<td><center>" + homescore + "<td><center>" + awayscore + "</td>" + "</center></tr>");
    	    }
    	    writer.write("</table>"); writer.newLine();
    	    writer.write("<br><br>"); //separate tables with break line
    	    writer.newLine();
    	    //creating totals table
    	    writer.write("<table>"); writer.newLine();
    	    writer.write("<tr><th>Totals</th><th></th></tr>"); 
    	    writer.write("<tr><td>Total Number Of Games Played</td> <td> <center>" + totalGames + "</td></tr>"); 
    	    writer.write("<tr><td>Total Home Score</td> <td> <center>" + totalHome + "</center> </td></tr>"); 
    	    writer.write("<tr><td>Total Away Score</td> <td> <center>" + totalAway + "</td></tr>"); 
    	    writer.write("<tr><td>Highest Home Score</td> <td> <center>" + highestHome + "</center></td></tr>"); 
    	    writer.write("<tr><td>Highest Away Score</td> <td> <center>" + highestAway + "</center> </td></tr>");
    	    writer.write("<tr><td>Total Invalid Entries</td> <td> <center>" + inputErrors + "</center></td></tr>"); 
    	    writer.write("</table> </body>");
    	    writer.close(); //closing writer object otherwise error produced
    	} catch (Exception e) {
    	  System.out.println("HTML file path does not exist!");
    	  System.exit(0);
    	}
    	System.out.println("\nHTML Report created at " + dir );
		return;
		
	}
}