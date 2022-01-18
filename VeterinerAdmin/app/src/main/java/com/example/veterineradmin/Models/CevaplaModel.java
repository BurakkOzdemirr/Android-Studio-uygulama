package com.example.veterineradmin.Models;

public class CevaplaModel{
	private boolean tf;
	private String text2;

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setText2(String text2){
		this.text2 = text2;
	}

	public String getText2(){
		return text2;
	}

	@Override
 	public String toString(){
		return 
			"CevaplaModel{" + 
			"tf = '" + tf + '\'' + 
			",text2 = '" + text2 + '\'' + 
			"}";
		}


}
