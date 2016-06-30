package index;

import java.io.*;
import java.util.Scanner;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFtoTXT {
		static int numberOfFiles;
		static String [] neededFileName;
		private static Scanner sc;
		static File f;
	    static String fileLocation;
		public PDFtoTXT() {
	
		}
		
		public static void convertToTXT(){
			PDDocument pd;
			BufferedWriter wr;
			sc = new Scanner(System.in);
			System.out.println("Enter the directory : (or enter - to read files of this location)");
			String dir = sc.next();
			if(dir.equals("-")){
				File currDir = new File(".");
				fileLocation = currDir.getAbsolutePath();
				fileLocation = fileLocation.substring(0, fileLocation.length()-1);
			}
			else {
				fileLocation = dir;
			}
			f = new File(fileLocation);
			System.out.println("Enter number of files : (if you want to read all files in directory enter '0')");
			numberOfFiles = sc.nextInt();
			if (numberOfFiles == 0){
			    FilenameFilter textFilter = new FilenameFilter() {
			        public boolean accept(File dir, String name) {
			            return name.toLowerCase().endsWith(".pdf");
			        }
			    };
			    int count = 0;
			    File[] files = f.listFiles(textFilter);
			    for (File file : files){
			    	if(file.getName().endsWith(".pdf")){
			    		count ++;
			    		System.out.println(file.getName());
			    	}
			    }
			    numberOfFiles = count;
			    neededFileName = new String [numberOfFiles];
			    int i = 0;
			    for (File file : files) {        	
			        	neededFileName[i] = file.getName();
			        	try {
							 File input = new File(neededFileName[i]);  // The PDF file from where you would like to extract
						     File output = new File(neededFileName[i]+".txt"); // The text file where you are going to store the extracted data
						     pd = PDDocument.load(input);
						     PDFTextStripper stripper = new PDFTextStripper();
				             stripper.setStartPage(0); //Start extracting from page --
						     stripper.setEndPage(2); //Extract till page --
						     wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
				             stripper.writeText(pd, wr);
						     if (pd != null) {
						    	 pd.close();
						     }
						     // I use close() to flush the stream.
						     wr.close();
						 } catch (Exception e){
							 e.printStackTrace();
						 }
			        i++;
			    }
			}
			else{
				neededFileName = new String [numberOfFiles];
				for (int i = 0; i <numberOfFiles ; i++) {
					System.out.println("Enter name of file (with directory): ");
					neededFileName[i]= sc.next();
					try {
						 File input = new File(neededFileName[i]);  // The PDF file from where you would like to extract
					     File output = new File(neededFileName[i]+".txt"); // The text file where you are going to store the extracted data
					     pd = PDDocument.load(input);
				     PDFTextStripper stripper = new PDFTextStripper();
			         stripper.setStartPage(0); //Start extracting from page --
				     stripper.setEndPage(2); //Extract till page --
				     wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
		             stripper.writeText(pd, wr);
				     if (pd != null) {
				    	 pd.close();
				     }
				     // I use close() to flush the stream.
				     wr.close();
				 } catch (Exception e){
					 e.printStackTrace();
				 }
			}	
		}			
		}
}
