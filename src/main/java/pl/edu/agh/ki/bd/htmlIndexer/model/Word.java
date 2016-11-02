package pl.edu.agh.ki.bd.htmlIndexer.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Word {

	private long id;
	private String content;
	private Set<Sentence> sentences=new HashSet<Sentence>();
	
	public Word(){};
	
	public Word(String content) {
		setContent(content);
	}
	
	public Word(String content, Set<Sentence> sentences) {
		setContent(content);
		setSentences(sentences);
	}
	
	public void addSentence(Sentence sentence) {
		this.sentences.add(sentence);
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
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
		if(!(obj instanceof Word)) return false;
		
		Word that = (Word) obj;
		EqualsBuilder eb = new EqualsBuilder();
		eb.append(this.content, that.content);
		return eb.isEquals();
	}
	
	@Override
	public int hashCode() {
    	HashCodeBuilder hcb = new HashCodeBuilder();
	    hcb.append(this.content);
	    return hcb.toHashCode();
	    }	
	
}
