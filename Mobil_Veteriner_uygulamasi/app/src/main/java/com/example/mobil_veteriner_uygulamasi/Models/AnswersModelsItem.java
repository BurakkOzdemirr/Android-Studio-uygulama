package com.example.mobil_veteriner_uygulamasi.Models;

public class AnswersModelsItem{
	private Object cevapid;
	private Object cevap;
	private boolean tf;
	private Object soruid;
	private Object konu;
	private Object soru;

	public void setCevapid(Object cevapid){
		this.cevapid = cevapid;
	}

	public Object getCevapid(){
		return cevapid;
	}

	public void setCevap(Object cevap){
		this.cevap = cevap;
	}

	public Object getCevap(){
		return cevap;
	}

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setSoruid(Object soruid){
		this.soruid = soruid;
	}

	public Object getSoruid(){
		return soruid;
	}

	public void setKonu(Object konu){
		this.konu = konu;
	}

	public Object getKonu(){
		return konu;
	}

	public void setSoru(Object soru){
		this.soru = soru;
	}

	public Object getSoru(){
		return soru;
	}

	@Override
 	public String toString(){
		return 
			"AnswersModelsItem{" + 
			"cevapid = '" + cevapid + '\'' + 
			",cevap = '" + cevap + '\'' + 
			",tf = '" + tf + '\'' + 
			",soruid = '" + soruid + '\'' + 
			",konu = '" + konu + '\'' + 
			",soru = '" + soru + '\'' + 
			"}";
		}
}
