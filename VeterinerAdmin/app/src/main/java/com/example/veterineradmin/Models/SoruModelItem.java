package com.example.veterineradmin.Models;

public class SoruModelItem{
	private String musid;
	private Object konu;
	private boolean tf;
	private String soruid;
	private String telefon;
	private String kadi;
	private String soru;

	public void setMusid(String musid){
		this.musid = musid;
	}

	public String getMusid(){
		return musid;
	}

	public void setKonu(Object konu){
		this.konu = konu;
	}

	public Object getKonu(){
		return konu;
	}

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setSoruid(String soruid){
		this.soruid = soruid;
	}

	public String getSoruid(){
		return soruid;
	}

	public void setTelefon(String telefon){
		this.telefon = telefon;
	}

	public String getTelefon(){
		return telefon;
	}

	public void setKadi(String kadi){
		this.kadi = kadi;
	}

	public String getKadi(){
		return kadi;
	}

	public void setSoru(String soru){
		this.soru = soru;
	}

	public String getSoru(){
		return soru;
	}

	@Override
 	public String toString(){
		return 
			"SoruModelItem{" + 
			"musid = '" + musid + '\'' +
			",konu = '" + konu + '\'' +
			",tf = '" + tf + '\'' +
			",soruid = '" + soruid + '\'' + 
			",telefon = '" + telefon + '\'' + 
			",kadi = '" + kadi + '\'' + 
			",soru = '" + soru + '\'' + 
			"}";
		}
}
