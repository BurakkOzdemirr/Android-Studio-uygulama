package com.example.mobil_veteriner_uygulamasi.Models;

public class LoginModel{
	private Boolean tf;
	private Object mailadres;
	private Object parola;
	private Object id;
	private String text;
	private Object username;

	public void setTf(Boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setMailadres(Object mailadres){
		this.mailadres = mailadres;
	}

	public Object getMailadres(){
		return mailadres;
	}

	public void setParola(Object parola){
		this.parola = parola;
	}

	public Object getParola(){
		return parola;
	}

	public void setId(Object id){
		this.id = id;
	}

	public Object getId(){
		return id;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setUsername(Object username){
		this.username = username;
	}

	public Object getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"LoginModel{" + 
			"tf = '" + tf + '\'' + 
			",mailadres = '" + mailadres + '\'' + 
			",parola = '" + parola + '\'' + 
			",id = '" + id + '\'' + 
			",text = '" + text + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}
