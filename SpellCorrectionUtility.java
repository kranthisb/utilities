/*
	Description : 
		a) Given a word, it transforms it by 
			i) Adding a Single character in between all the characters of the word (26*n combinations)
			ii) Deleting a single character (n combinations)
			iii) Replacing a single character with all letters of the alphabet (26*n combinations)
		b) 	Map these records against the list in the input file
		c) Increment the hit counter for the matching word so that its probability increases
	
	Steps to run : 
	a) create a file commonwords.txt with sample content as below..
		hotel
		restaurent
		room
		tax
		vat
		total
		price

	b) To compile :  javac SpellCorrectionUtility.java & To run : java SpellCorrectionUtility
*/

import java.util.*;
import java.io.*;

public class SpellCorrectionUtility{
	HashMap<String, Integer> spellMap = new HashMap<String , Integer>();
	HashMap<String, Integer> suggestedSpells = null;
	String alphabets = "abcdefghijklmnopqrstuvwxyz";

	private void readSpellList(){
		FileInputStream fis = null;
    	BufferedReader reader = null;

		try{
			fis =  new FileInputStream("/Users/kborra/Documents/Project_Documents/Teserract/ocr-tess4j-example-master/commonwords.txt");

			reader =  new BufferedReader(new InputStreamReader(fis));
			String line = null;//reader.readLine();

            while((line = reader.readLine()) != null){
                spellMap.put(line, 0);
            }  
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				fis.close();
			}catch(Exception e){}	
		}
	}
	
	/* 
		Find word in spellMap and increment the counter. 
		The counter is maintained to capture the most commonly used words.
		For ex: word "hote" will match "hotel" with counter as 2 and not "hot" that has counter 1.
	 */
	 
	private void findSpellsInList(String word){
		Integer counter = spellMap.get(word);
		if( counter != null){
			suggestedSpells.put(word, counter);
		}
	}
	
	/*
		One letter insert, delete, replace.....
		TODO: transpose
	*/

	private void editAndFindSpell(String word){
		int len = word.length();
		for(int i=0; i< len+1; i++){
			String part1 =  word.substring(0, i);
			String part2 = word.substring(i, len);
			String part3 = "";
			if(i+1 <=  len)
				part3 = word.substring(i+1, len);

			//insert letter
			for(int j = 0; j < alphabets.length(); j++){
					StringBuilder insert = new StringBuilder(part1).append(alphabets.charAt(j)).append(part2);
					findSpellsInList(insert.toString());	
			}
			
			//Delete letter
			StringBuilder delete = new StringBuilder(part1).append(part3);
			findSpellsInList(delete.toString());
			
			//replace letter
			for(int j = 0; j < alphabets.length(); j++){
				StringBuilder replace = new StringBuilder(part1).append(alphabets.charAt(j)).append(part3);
				findSpellsInList(replace.toString());
			}
			
			//transpose (swap adjacent letters)
			//TOOD	
		}
	}
	
	private String maxSuggestedSpells(){
		System.out.println(suggestedSpells);
		SortedSet<Map.Entry<String, Integer>> sortedset = new TreeSet<Map.Entry<String, Integer>>(
            new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> e1,
                        Map.Entry<String, Integer> e2) {
                    return e2.getValue().compareTo(e1.getValue());
                }
            });
	  	sortedset.addAll(suggestedSpells.entrySet());
	  	return (sortedset.first().toString().split("=")[0]);
	}

	private String correctSpell(String word){
		findSpellsInList(word);
		editAndFindSpell(word);
		return maxSuggestedSpells();
	}
	
	public void readInputAndFindMatch(){
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			System.out.println("Enter Word");
			String word = sc.nextLine();

			suggestedSpells = new HashMap<String , Integer>();
			String matchingWord = correctSpell(word.toLowerCase());
			
			Integer counter = spellMap.get(matchingWord);
			spellMap.put(matchingWord, counter+1);
			
			System.out.println("Matching Word : " + matchingWord);
		}
	}	
	public static void main(String[] args){
		SpellCorrectionUtility spc = new SpellCorrectionUtility();
		spc.readSpellList();
		spc.readInputAndFindMatch();
		
	}
}
