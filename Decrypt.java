/*
  Programmer: Zamakhshari Abd Al-Ahad
  Description: This program reads and decrypts an encoded text file by reversing tranposition and ciphertext substitution
  Date created: 09/11/2016
*/
import java.util.*;
import java.io.*;
public class Decrypt
{
   public static void main(String[] args)
   {                       //variables to accept a input from the command line interface and generate the ciphertext
      String key = args[0]; 
      File inputFile = new File(args[1]);
		File outputFile = new File(args[2]);
		int hash = key.hashCode();
      Random random = new Random(hash);
      char temp = ('0');
		char [] orgAlphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',' '};
		char [] newAlphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',' '};      		
      for ( int i = 0; i < 100; i++)
		{                             //generates the ciphertext
			int pos1 = random.nextInt(27);
			int pos2 = random.nextInt(27);        
			temp = newAlphabet[pos1];
			newAlphabet[pos1] = newAlphabet[pos2];
		   newAlphabet[pos2] = temp;
		}
      Random random2  = new Random(hash); 
      int nums[] = {0,1,2,3,4,5,6,7};
      int orgNums[] = {0,1,2,3,4,5,6,7};
         for(int j = 0; j < 100; j++)
		   {                                //generates the order in which the rows of text will be written
		      int pos3 = random2.nextInt(8);
		      int pos4 = random2.nextInt(8);
         
		      int temp2 = nums[pos4];
            int temp3 = nums[pos3];
         
		      nums[pos3] = temp2;
		      nums[pos4] = temp3;
		   }
     //encrypt(orgAlphabet, newAlphabet, nums, inputFile, outputFile);
      
     decrypt(orgAlphabet, newAlphabet,nums, inputFile, outputFile); //decryption method that accepts the real alphabet, ciphertext alphabet, the row's order and the files
	}             
               
   public static void encrypt(char[] orgAlphabet, char[] newAlphabet, int[] nums, File inputFile, File outputFile)
   {
	 		FileReader in;
			FileWriter out;
			BufferedWriter writeFile;
			BufferedReader readFile;
			try
			{
            Scanner infile = new Scanner(inputFile);
				in = new FileReader(inputFile);
				readFile = new BufferedReader(in);
				out = new FileWriter(outputFile);
				writeFile = new BufferedWriter(out);
            String text = readFile.readLine();
            char temp = ('0');
				char[][] letterArray = new char[8][8];         
            outputFile.createNewFile();
				while (infile.hasNextLine())
				{
               int count = 0;
               char[] newText = new char[(text.length())];
               text = text.replaceAll("[^a-zA-Z ]", "").toUpperCase();
         		for (int i = 0; i < text.length(); i++)
         		{                                      //substitutes the letters in the line with their corresponding ciphertext
                  for(int j = 0; j < orgAlphabet.length; j++)
         			{
         				if(orgAlphabet[j] == text.charAt(i))
         				{
         					newText[i] = newAlphabet[j];
         				}	
         			}
         		}
		        for(int x = 0; x < 8; x++)
	            {                          //places the characters in the line into an 8x8 array
			         for(int y = 0; y < 8; y++)
			         {
				         letterArray[x][y] = newText[count];
				         count++;
			         }
	            }
               char transposedText[][] = new char[8][8];
               for(int x = 0; x < 8; x++)
		         {                          //applies transposition
				      for(int y = 0; y < 8; y++)
				      {
					      transposedText[y][x] = letterArray[x][y];
				      }
		         } 
              for(int x = 0; x < 8; x++)
		        { 
				      for(int y = 0; y < 8; y++)
				      {
                     writeFile.write(transposedText[nums[x]][y]);	//writes the encrypted text to the file	
				      }
              }
              break;
				}
				readFile.close();
				writeFile.close();
				in.close();
				out.close();
			}
			catch (FileNotFoundException e)
			{
	         System.out.println("File does not exist or could not be found.");
	         System.err.println("FileNotFoundException: " + e.getMessage());
     		} 
			catch (IOException e) 
			{
	         System.out.println("Problem reading file.");
	         System.err.println("IOException: " + e.getMessage());
      	}
   } 
   
   public static void decrypt(char[] orgAlphabet, char[] newAlphabet, int[] nums, File inputFile, File outputFile)   //decryption method that accepts the real alphabet, ciphertext alphabet, the row's order and the files
   {
      
	 	FileReader in;
		FileWriter out;
		BufferedWriter writeFile;
		BufferedReader readFile;
         try
			{
				Scanner infile = new Scanner(inputFile);
            in = new FileReader(inputFile);
				readFile = new BufferedReader(in);
				out = new FileWriter(outputFile);
				writeFile = new BufferedWriter(out);
            String text = readFile.readLine();
            char temp = ('0');
				char[][] transposedText = new char[8][8];
            outputFile.createNewFile();
				while (infile.hasNextLine())
				{	
              text = infile.nextLine();
              int count = 0;
              char[][] encryptedText = new char[8][8];
              for (int x = 0; x < 8; x++) 
              {                           //undoes transposition and reorders the rows
			         for (int y = 0; y < 8; y++)
                  {
				         encryptedText[y][nums[x]] = text.charAt(count);
				         count++;
			         }
		        }
		       text = ("");
              for (int x = 0; x < 8; x++) 
              {                           //sets the value of text to that of the encrypted line
         			for (int y = 0; y < 8; y++) 
                  {
         				text = text + String.valueOf(encryptedText[x][y]);
         			}
              }
              StringBuffer newText = new StringBuffer(text);
      		  for (int a = 0; a < newText.length(); a++) 
              {                                          //undoes the substitution
      			   for (int b = 0; b < orgAlphabet.length; b++)
                  {
      				   if (newText.charAt(a) == newAlphabet[b])
                     {
      					   newText.setCharAt(a, orgAlphabet[b]);
      					break;
      				   }
      			   }
      		  }
             writeFile.write(String.valueOf(newText));
             writeFile.newLine();
            }
		      readFile.close();
			   writeFile.close();
				in.close();
				out.close();
			}
			catch (FileNotFoundException e)
			{
	         System.out.println("File does not exist or could not be found.");
	         System.err.println("FileNotFoundException: " + e.getMessage());
     		} 
			catch (IOException e) 
			{
	         System.out.println("Problem reading file.");
	         System.err.println("IOException: " + e.getMessage());
      	}
   }    //the method does not return a value. It outputs to the file natively       
}
