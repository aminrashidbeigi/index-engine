package index;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Scanner;


public class wordCount {
	static BitSet byteArray;
	static String[] fileString = new String [100];
	static boolean isInFile = false;
	static Scanner sc = new Scanner (System.in);
	static PDFtoTXT PTT = new PDFtoTXT();
	static String[] neededFileName;
	static ArrayList <String> wordsToSearch = new ArrayList<String>();
	static int wordCounter = 0;
	static String fileName;
	
	public static void wordCount(){
		int numberOfFiles = PTT.numberOfFiles;
		neededFileName = new String[numberOfFiles];
		String wordToSearch = Executer.wordToSearch;
		byteArray = new BitSet(numberOfFiles);
		for (int i = 0; i < numberOfFiles; i++) {
			try {
				neededFileName[i] = PDFtoTXT.neededFileName[i];
				int numbersOfRepetition = 0;
				String line = "";
				fileName = neededFileName[i]+".txt";
				BufferedReader bReader = new BufferedReader(new FileReader(fileName));
				while ((line = bReader.readLine()) != null) {
					int posFound = line.indexOf(wordToSearch);
					if (posFound > - 1) {
						numbersOfRepetition++;
					}
				}
				bReader.close();
				if(numbersOfRepetition > 0){
					isInFile = true;
					byteArray.set(i, true);
					fileString[i] = fileName;
				}
				else{
					isInFile = false;
					byteArray.set(i, false);
				}
			}
			catch (IOException e) {
				System.out.println("Error: " + e.toString());
			}
		wordCounter ++;
		}
	}
}