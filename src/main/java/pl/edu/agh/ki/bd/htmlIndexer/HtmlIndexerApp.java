package pl.edu.agh.ki.bd.htmlIndexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import pl.edu.agh.ki.bd.htmlIndexer.model.Sentence;
import pl.edu.agh.ki.bd.htmlIndexer.persistence.HibernateUtils;

public class HtmlIndexerApp 
{

	public static void main(String[] args) throws IOException
	{
		HibernateUtils.getSession().close();

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		Index indexer = new Index(); 
		
		while (true)
		{
			System.out.println("\nHtmlIndexer [? for help] > : ");
			String command = bufferedReader.readLine();
	        long startAt = new Date().getTime();

			if (command.startsWith("?"))
			{
				System.out.println("'?'      	- print this help");
				System.out.println("'x'      	- exit HtmlIndexer");
				System.out.println("'i URLs'  	- index URLs, space separated");
				System.out.println("'f WORDS'	- find sentences containing all WORDs, space separated");
				System.out.println("'g' n   	- find sentences containing more than n words");
			}
			else if (command.startsWith("x"))
			{
				System.out.println("HtmlIndexer terminated.");
				HibernateUtils.shutdown();
				break;				
			}
			else if (command.startsWith("i "))
			{
				for (String url : command.substring(2).split(" "))
				{
					//try {
						indexer.indexWebPage(url);
						System.out.println("Indexed: " + url);
					//} catch (Exception e) {
					//	System.out.println("Error indexing: " + e.getMessage());
					//}
				}
			}
			else if (command.startsWith("f "))
			{
 				for (Sentence sentence : indexer.findSentencesByWords(command.substring(2)))
				{
 					System.out.println("Found in sentence: "  + "::" + sentence.getProcessedurl().getUrl() );		
				}
			}
			else if (command.startsWith("g "))
			{
				for(Sentence sentence : indexer.findSentenceByWordsNumber(Integer.parseInt(command.substring(2))))
				{
					System.out.println("Found sentence: " + sentence.getContent() );
				}
			}
			
			System.out.println("took "+ (new Date().getTime() - startAt)+ " ms");		

		}

	}

}
