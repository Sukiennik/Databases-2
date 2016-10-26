package pl.edu.agh.ki.bd.htmlIndexer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pl.edu.agh.ki.bd.htmlIndexer.model.ProcessedUrl;
import pl.edu.agh.ki.bd.htmlIndexer.model.Sentence;
import pl.edu.agh.ki.bd.htmlIndexer.persistence.HibernateUtils;

public class Index 
{		
	public void indexWebPage(String url) throws IOException
	{
		
		Document doc = Jsoup.connect(url).get();
		Elements elements = doc.body().select("*");
		//TimeZone.setDefault(TimeZone.getTimeZone("GMT+2:00"));
		//SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		HashSet<Sentence> sentencesCollection = new HashSet<Sentence>();
		ProcessedUrl processedUrl = new ProcessedUrl(url, new Date(), sentencesCollection);
		for (Element element : elements) 
		{
			if (element.ownText().trim().length() > 1)
			{
				//Session session = HibernateUtils.getSession();
				//Transaction transaction = session.beginTransaction();
				
				//ProcessedUrl processedUrl = new ProcessedUrl(url, new Date());
				//session.persist(processedUrl);
				for (String sentenceContent : element.ownText().split("\\. "))
				{							
					Sentence sentence = new Sentence(sentenceContent);
					sentencesCollection.add(sentence);
					//session.persist(sentence);
				}
				//processedUrl.setSentences(sentencesCollection);
				//transaction.commit();
				//session.close();
			}
		}
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();

		//processedUrl.setSentences(sentencesCollection);
		session.persist(processedUrl);
		transaction.commit();
		session.close();		
	}	
	
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
	
	public List<Sentence> findSentenceByWordsNumber(int number) {
		Session session = HibernateUtils.getSession();
		Transaction transaction = session.beginTransaction();
		
		List<Sentence> result = session.createQuery("select s from Sentence s where length(s.content) > :number", Sentence.class).setParameter("number", number).getResultList();
		
		transaction.commit();
		session.close();
		return result;
		
	}
	
}
