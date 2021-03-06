package pl.edu.agh.ki.bd.htmlIndexer.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ProcessedUrl {

	private long id;
	private String url;
	private Date date;
	private Set<Sentence> sentences=new HashSet<Sentence>();
	
	public ProcessedUrl() {
	}
	
	public ProcessedUrl(String url, Date date) {
		this.setUrl(url);
		this.setDate(date);
	}
	public ProcessedUrl(String url, Date date, Set<Sentence> sentences) {
		this.setUrl(url);
		this.setDate(date);
		this.setSentences(sentences);
	}
	
	public void addSentence(Sentence sentence) {
		this.sentences.add(sentence);
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
	
	public Set<Sentence> getSentences() {
		return this.sentences;
	}
	
	public void setSentences(Set<Sentence> sentences) {
		this.sentences = sentences;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof ProcessedUrl)) return false;
		
		ProcessedUrl that = (ProcessedUrl) obj;
		EqualsBuilder eb = new EqualsBuilder();
		eb.append(this.getUrl(), that.getUrl());
		return eb.isEquals();
	}
	
	@Override
	public int hashCode() {
    	HashCodeBuilder hcb = new HashCodeBuilder();
	    hcb.append(url);
	    return hcb.toHashCode();
	    }	
	
}
