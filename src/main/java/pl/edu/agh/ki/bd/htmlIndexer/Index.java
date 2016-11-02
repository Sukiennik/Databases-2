package pl.edu.agh.ki.bd.htmlIndexer;


import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pl.edu.agh.ki.bd.htmlIndexer.model.ProcessedUrl;
import pl.edu.agh.ki.bd.htmlIndexer.model.Sentence;
import pl.edu.agh.ki.bd.htmlIndexer.model.Word;
import pl.edu.agh.ki.bd.htmlIndexer.persistence.HibernateUtils;

public class Index 
{		

	public void indexWebPage(String url) throws IOException
	{
		
		Document doc = Jsoup.connect(url).get();
		Elements elements = doc.body().select("*");
		ProcessedUrl processedUrl = new ProcessedUrl(url, new Date());
		for (Element element : elements) 
		{
			if (element.ownText().trim().length() > 1)
			{
					for (String sentenceContent : element.ownText().split("\\. "))
					{	
					Sentence sentence = new Sentence(processedUrl);
						for(String word_content : getWords(sentenceContent)) {
							//check if word exists;
							Word word = new Word(word_content);
							sentence.addWord(word);
							word.addSentence(sentence);
						}
					
					processedUrl.addSentence(sentence);
					}
			}
		}
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();

		session.persist(processedUrl);
		transaction.commit();
		session.close();	
	}	
	
	/* TO-DO*/
	public List<Sentence> findSentencesByWords(String words)
	{
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		
		String query = "%" + words.replace(" ", "%") + "%";
		List<Sentence> result = session.createQuery("select s from Sentence s where s.content like :query", Sentence.class).setParameter("query", query).getResultList();
		
		transaction.commit();
		session.close();
		
		return result;
	}	
	
	/* TO-DO*/
	public List<Sentence> findSentenceByWordsNumber(int number) {
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		
		List<Sentence> result = session.createQuery("select s from Sentence s where length(s.content) > :number", Sentence.class).setParameter("number", number).getResultList();
		
		transaction.commit();
		session.close();
		return result;
		
	}
	
	public List<Object[]> findSentenceCountByUrl() {
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
	
		List<Object[]> result = session.createQuery("select pu.url, count(sen.id)"
				+ " FROM ProcessedUrl pu"
				+ " JOIN pu.sentences sen "
				+ " GROUP BY pu.url "
				+ " ORDER BY 2", Object[].class).getResultList();
	
		transaction.commit();
		session.close();
		return result;
	}
	
	public Long findWordIndexCount(String word) {
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		
		try {					
			Long result = session.createQuery("select count(content)"
					+ " FROM Word w"
					+ " WHERE w.content=:word"
					+ " GROUP BY w.content "
					, Long.class).setParameter("word",  word).getSingleResult();
			transaction.commit();

			return result;
		} 
		catch(NoResultException e) {
			System.out.println("Search error: " + e.getMessage());
			return null;
		}
		finally {
			session.close();
		}		
	}
	
	public List<ProcessedUrl> deleteWebpage(String url) {
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		
		List<ProcessedUrl> result = session.createQuery("from ProcessedUrl pu where pu.url = :url", ProcessedUrl.class).setParameter("url", url).getResultList();
		
		transaction.commit();
		session.close();
		return result;	
	}
	
	public boolean checkIfAlreadyParsed(String url) {
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		
		List<ProcessedUrl> result = session.createQuery("select p_url from ProcessedUrl p_url where p_url.url=:url", ProcessedUrl.class).setParameter("url", url).getResultList();
		
		transaction.commit();
		session.close();
		
		if(!result.isEmpty()) return true;
		
		/*for(ProcessedUrl pu : result) {
			if(pu.getUrl().equals(url)) {
				return true;
			}
		}*/
		return false;	
	}
	
	public static List<String> getWords(String text) {
	    List<String> words = new ArrayList<String>();
	    BreakIterator breakIterator = BreakIterator.getWordInstance();
	    breakIterator.setText(text);
	    int lastIndex = breakIterator.first();
	    while (BreakIterator.DONE != lastIndex) {
	        int firstIndex = lastIndex;
	        lastIndex = breakIterator.next();
	        if (lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(text.charAt(firstIndex))) {
	            words.add(text.substring(firstIndex, lastIndex));
	        }
	    }
	    return words;
	}
	
}
