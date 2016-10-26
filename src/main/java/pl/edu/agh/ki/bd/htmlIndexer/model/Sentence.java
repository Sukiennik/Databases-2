package pl.edu.agh.ki.bd.htmlIndexer.model;

public class Sentence {
	
	private long id;
	private ProcessedUrl processedurl;
	private String content;
	//private String url;
	
	public Sentence() 
	{
	}
	
	public Sentence(String content)
	{
		this.setContent(content);
		//this.setUrl(url);
	}
	
	public Sentence(String content, ProcessedUrl processed_url) {
		this.setContent(content);
		this.setProcessedurl(processed_url);
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
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

	
	/////////old//////////////
	/*
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	*/
}
