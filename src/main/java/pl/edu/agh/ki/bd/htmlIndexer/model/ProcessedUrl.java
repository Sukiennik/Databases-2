package pl.edu.agh.ki.bd.htmlIndexer.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ProcessedUrl {

	private long id;
	private String url;
	private Date date;
	private Set sentences;
	
	public ProcessedUrl() {
	}
	
	public ProcessedUrl(String url, Date date) {
		this.setUrl(url);
		this.setDate(date);
	}
	public ProcessedUrl(String url, Date date, Set sentences) {
		this.setUrl(url);
		this.setDate(date);
		this.setSentences(sentences);
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Set getSentences() {
		return this.sentences;
	}
	
	public void setSentences(Set sentences) {
		this.sentences = sentences;
	}
	
	
}
