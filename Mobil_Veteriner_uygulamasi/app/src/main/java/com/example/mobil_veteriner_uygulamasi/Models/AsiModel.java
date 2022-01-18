package com.example.mobil_veteriner_uygulamasi.Models;

import java.util.List;

public class AsiModel{
	private List<AsiModelItem> asiModel;

	public void setAsiModel(List<AsiModelItem> asiModel){
		this.asiModel = asiModel;
	}

	public List<AsiModelItem> getAsiModel(){
		return asiModel;
	}

	@Override
 	public String toString(){
		return 
			"AsiModel{" + 
			"asiModel = '" + asiModel + '\'' + 
			"}";
		}
}