package com.example.mobil_veteriner_uygulamasi.Models;

import java.util.List;

public class PetModel{
	private List<PetModelItem> petModel;

	public void setPetModel(List<PetModelItem> petModel){
		this.petModel = petModel;
	}

	public List<PetModelItem> getPetModel(){
		return petModel;
	}

	@Override
 	public String toString(){
		return 
			"PetModel{" + 
			"petModel = '" + petModel + '\'' + 
			"}";
		}
}