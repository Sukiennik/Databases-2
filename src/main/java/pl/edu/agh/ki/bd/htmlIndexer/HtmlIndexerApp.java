package pl.edu.agh.ki.bd.htmlIndexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.edu.agh.ki.bd.htmlIndexer.model.ProcessedUrl;
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
				System.out.println("'c'      	- show parsed URLs with number of sentences");
				System.out.println("'n' WORD    - show number of WORD, existing in words index ");
				System.out.println("'d' URLs    - delete parsed URLS, space separated");
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
						try {
							if(indexer.checkIfAlreadyParsed(url)) {
								System.out.println("Indexing fault: " + url + " is already parsed.");
							} else {
								indexer.indexWebPage(url);
								System.out.println("Indexed: " + url);
							}
						} catch (Exception e) {
							System.out.println("Error indexing: " + e.getMessage());
						}
					}
			}
			else if (command.startsWith("f "))
			{
				Session session = HibernateUtils.getSession();
				Transaction transaction = session.beginTransaction();				
 				for (Sentence sentence : indexer.findSentencesByWords(command.substring(2)))
				{
 					session.update(sentence);
 					//System.out.println("Found in sentence: " + sentence.getWords().toString() + "::" + sentence.getProcessedurl().getUrl() );		
				}
 				transaction.commit();
 				session.close();
			}
			else if (command.startsWith("g "))
			{
				for(Sentence sentence : indexer.findSentenceByWordsNumber(Integer.parseInt(command.substring(2))))
				{
					
					//System.out.println("Found sentence: " + sentence.getWords().toString() );
				}
				
			}
			else if (command.startsWith("c"))
			{
				for(Object[] pu : indexer.findSentenceCountByUrl()) {
					try {
					System.out.println("URL: " +  pu[0] + ":: COUNT " + pu[1] );
					} catch (Exception e) {
						System.out.println("Error indexing: " + e.getMessage());
					}
				}
			}
			else if (command.startsWith("n "))
			{
				Session session = HibernateUtils.getSession();
				Transaction transaction = session.beginTransaction();				
 				Long number = indexer.findWordIndexCount(command.substring(2));				
 				if(number!=null) {
 	 				System.out.println("Number of word: >>>" + command.substring(2) + "<<< :: " + number);
 				}	
 				transaction.commit();
 				session.close();
			}
			else if (command.startsWith("d "))
			{
				Session session = HibernateUtils.getSession();
				Transaction transaction = session.beginTransaction();				
				for(String url : command.substring(2).split(" ")) {
					ProcessedUrl toDelete = indexer.deleteWebpage(url).iterator().next();
					session.delete(toDelete);
					session.flush();
					//indexer.indexWebPage(url);
					//indexer.indexWebPage(url);	
				}
 				transaction.commit();
 				session.close();
			}
			
			
			System.out.println("took "+ (new Date().getTime() - startAt)+ " ms");		
		}
	}
}
