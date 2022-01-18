package com.example.mobil_veteriner_uygulamasi.Models;

import java.util.List;

public class AnswersModels{
	private List<AnswersModelsItem> answersModels;

	public void setAnswersModels(List<AnswersModelsItem> answersModels){
		this.answersModels = answersModels;
	}

	public List<AnswersModelsItem> getAnswersModels(){
		return answersModels;
	}

	@Override
 	public String toString(){
		return 
			"AnswersModels{" + 
			"answersModels = '" + answersModels + '\'' + 
			"}";
		}
}