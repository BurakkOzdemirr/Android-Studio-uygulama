package com.example.veterineradmin.Models;

import java.util.List;

public class KullanicilarModel{
	private List<KullanicilarModelItem> kullanicilarModel;

	public void setKullanicilarModel(List<KullanicilarModelItem> kullanicilarModel){
		this.kullanicilarModel = kullanicilarModel;
	}

	public List<KullanicilarModelItem> getKullanicilarModel(){
		return kullanicilarModel;
	}

	@Override
 	public String toString(){
		return 
			"KullanicilarModel{" + 
			"kullanicilarModel = '" + kullanicilarModel + '\'' + 
			"}";
		}
}