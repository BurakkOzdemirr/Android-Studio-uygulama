package com.example.mobil_veteriner_uygulamasi.Models;

import java.util.List;

public class KampanyaModel{
	private List<KampanyaModelItem> kampanyaModel;

	public void setKampanyaModel(List<KampanyaModelItem> kampanyaModel){
		this.kampanyaModel = kampanyaModel;
	}

	public List<KampanyaModelItem> getKampanyaModel(){
		return kampanyaModel;
	}

	@Override
 	public String toString(){
		return 
			"KampanyaModel{" + 
			"kampanyaModel = '" + kampanyaModel + '\'' + 
			"}";
		}
}