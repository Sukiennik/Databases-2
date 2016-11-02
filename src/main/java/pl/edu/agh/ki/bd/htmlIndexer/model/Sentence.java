package pl.edu.agh.ki.bd.htmlIndexer.model;

import java.util.HashSet;
import java.util.Set;

public class Sentence {
	
	private long id;
	private ProcessedUrl processedurl;
	//private String content;
	private Set<Word> words = new HashSet<Word>();
	
	public Sentence() 
	{
	}
	
	public Sentence(ProcessedUrl processed_url)
	{
		this.setProcessedurl(processed_url);
	}
	
	public Sentence(Set<Word> words, ProcessedUrl processed_url) {
		this.setWords(words);
		this.setProcessedurl(processed_url);
	}
	
	/*public Sentence(String content, ProcessedUrl processed_url) {
		this.setContent(content);
		this.setProcessedurl(processed_url);
	}*/
	
	public void addWord(Word word) {
		this.words.add(word);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public ProcessedUrl getProcessedurl() {
		return processedurl;
	}
	
	public void setProcessedurl(ProcessedUrl processed_url) {
		this.processedurl = processed_url;
	}
	
	/*public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}*/
	
	public Set<Word> getWords() {
		return this.words;
	}
	
	public void setWords(Set<Word> words) {
		this.words = words;
	}
}
