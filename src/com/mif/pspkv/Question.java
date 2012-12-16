package com.mif.pspkv;

import java.util.List;

public class Question {

	private List<Answer> mAnswers;
	private String mContent, mDetails;
	
	public Question() {
		
	}
	
	public Question(String content, String details) {
		Content(content);
		Details(details);
	}
	
	public List<Answer> Answers() {
		return mAnswers;
	}
	public void Answers(List<Answer> mAnswers) {
		this.mAnswers = mAnswers;
	}
	public String Details() {
		return mDetails;
	}
	public void Details(String mDetails) {
		this.mDetails = mDetails;
	}
	public String Content() {
		return mContent;
	}
	public void Content(String mContent) {
		this.mContent = mContent;
	}
	
}
