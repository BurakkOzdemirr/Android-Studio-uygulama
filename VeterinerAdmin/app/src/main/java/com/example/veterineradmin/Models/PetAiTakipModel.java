package com.example.veterineradmin.Models;

import java.util.List;

public class PetAiTakipModel{
	private List<PetAiTakipModelItem> petAiTakipModel;

	public void setPetAiTakipModel(List<PetAiTakipModelItem> petAiTakipModel){
		this.petAiTakipModel = petAiTakipModel;
	}

	public List<PetAiTakipModelItem> getPetAiTakipModel(){
		return petAiTakipModel;
	}

	@Override
 	public String toString(){
		return 
			"PetAiTakipModel{" + 
			"petAiTakipModel = '" + petAiTakipModel + '\'' + 
			"}";
		}
}