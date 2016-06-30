package index;

import java.util.*;

public class Executer {
	static int numOfFiles;
	static boolean flag;
	static String wordToSearch;
	private static BitSet byteArray1;
	static ArrayList<String> wordString;
	private static ArrayList<String> fileString;
	
	public void execute () {
			Stack<String> output = new Stack<String>();
	        Stack<String> ops = new Stack<String>();
	        ArrayList<String> inputString = new ArrayList<String>();
	        wordString = new ArrayList<String>();
	        Scanner sc = new Scanner(System.in);
	 		boolean loop = true;
	 		PDFtoTXT.convertToTXT(); 		
			System.out.println("Enter words to search: ");
	 		
			while(loop){
	 			String s = sc.next();
	 			if(s.equalsIgnoreCase(".")){
	 				break;
	 			}
	 			else{
	 				inputString.add(s);
	 			}
	 		}
			for (int i = 0; i < inputString.size(); i++) {
				wordToSearch = inputString.get(i);
				if (wordToSearch.equalsIgnoreCase("!") || wordToSearch.equalsIgnoreCase("|") || wordToSearch.equalsIgnoreCase("&") || wordToSearch.equalsIgnoreCase("(") || wordToSearch.equalsIgnoreCase(")") || wordToSearch.equals ("or") || wordToSearch.equals ("if"));
				else
					wordString.add(wordToSearch);
			}
	 		for (int i = 0; i < inputString.size(); i++){
	 			wordToSearch = inputString.get(i);
	 			if (isOperator(wordToSearch)) {
	 				int pre = getPrecedence(wordToSearch);
	 				while (!ops.isEmpty() && getPrecedence(ops.peek())>pre) {
	 					output.push(ops.pop());
	 				}
	 				ops.push(wordToSearch);
	 			}
	 			else if ("(".equalsIgnoreCase(wordToSearch)){
	 				ops.push(wordToSearch);
	 			}
	 			else if (")".equalsIgnoreCase(wordToSearch)){
	 				while (!"(".equalsIgnoreCase(ops.peek())){
	 					output.push(ops.pop());
	 				}
	 				ops.pop();
	 			}
	 			else {
	 				output.push(wordToSearch);
	 			}
	 		}	
	 		while (!ops.isEmpty()) output.push(ops.pop());
	 		numOfFiles = PDFtoTXT.numberOfFiles;
	 		byteArray1 = new BitSet(numOfFiles);
	 		wordString = new ArrayList<String>();
	 		System.out.println(lastCheck(output));
	}
	
	
	private static ArrayList<String> lastCheck(Stack<String> wordString){
		fileString = new ArrayList<>();
		BitSet result = getFiles(wordString);
		for (int i=0; i<result.length(); i++) {
			if (result.get(i)) fileString.add(PDFtoTXT.neededFileName[i]);
		}
		return fileString;
	}
	
	private static BitSet getFiles(Stack<String> words) {
		wordToSearch = words.pop();
		switch (wordToSearch) {
			case "!": 
				BitSet a = getFiles(words);
				a.flip(0, numOfFiles);
				return a;
			case "|": 
				a = getFiles(words);
				BitSet b = getFiles(words);
				a.or(b);
				return a;
			case "&": 
				a = getFiles(words);
				b = getFiles(words);
				a.and(b);
				return a;
			default: 
				wordCount.wordCount();
				return wordCount.byteArray == null ? new BitSet(numOfFiles) : wordCount.byteArray;
		}
		
	}
	
	private static boolean isOperator(String word){
		return "!".equalsIgnoreCase(word)| "&".equalsIgnoreCase(word) | "|".equalsIgnoreCase(word);
	}
	
	private static int getPrecedence(String word) {
		if ("!".equalsIgnoreCase(word)) return 4;
		if ("&".equalsIgnoreCase(word)) return 3;
		if ("|".equalsIgnoreCase(word)) return 2;
		return -1;
	}
}
