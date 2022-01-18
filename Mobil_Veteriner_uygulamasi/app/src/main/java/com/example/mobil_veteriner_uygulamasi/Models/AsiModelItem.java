package com.example.mobil_veteriner_uygulamasi.Models;

public class AsiModelItem{
	private boolean tf;
	private Object petresim;
	private Object pettur;
	private Object asitarih;
	private Object asiisim;
	private Object asiism;
	private Object petisim;
	private Object petcins;

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setPetresim(Object petresim){
		this.petresim = petresim;
	}

	public Object getPetresim(){
		return petresim;
	}

	public void setPettur(Object pettur){
		this.pettur = pettur;
	}

	public Object getPettur(){
		return pettur;
	}

	public void setAsitarih(Object asitarih){
		this.asitarih = asitarih;
	}

	public Object getAsitarih(){
		return asitarih;
	}

	public void setAsiisim(Object asiisim){
		this.asiisim = asiisim;
	}

	public Object getAsiisim(){
		return asiisim;
	}

	public void setAsiism(Object asiism){
		this.asiism = asiism;
	}

	public Object getAsiism(){
		return asiism;
	}

	public void setPetisim(Object petisim){
		this.petisim = petisim;
	}

	public Object getPetisim(){
		return petisim;
	}

	public void setPetcins(Object petcins){
		this.petcins = petcins;
	}

	public Object getPetcins(){
		return petcins;
	}

	@Override
 	public String toString(){
		return 
			"AsiModelItem{" + 
			"tf = '" + tf + '\'' + 
			",petresim = '" + petresim + '\'' + 
			",pettur = '" + pettur + '\'' + 
			",asitarih = '" + asitarih + '\'' + 
			",asiisim = '" + asiisim + '\'' + 
			",asiism = '" + asiism + '\'' + 
			",petisim = '" + petisim + '\'' + 
			",petcins = '" + petcins + '\'' + 
			"}";
		}
}
