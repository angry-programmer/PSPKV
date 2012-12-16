package com.mif.pspkv;
public class Answer {

	private String mContent;
	private boolean mIsRight;
	
	public Answer() {
		
	}
	
	public Answer(String content, boolean isRight) {
		IsRight(isRight);
		Content(content);
	}

	public String Content() {
		return mContent;
	}

	public void Content(String mContent) {
		this.mContent = mContent;
	}

	public boolean IsRight() {
		return mIsRight;
	}

	public void IsRight(boolean mIsRight) {
		this.mIsRight = mIsRight;
	}
	
}