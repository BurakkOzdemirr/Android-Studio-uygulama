package com.example.veterineradmin.Models;

import java.util.List;

public class SoruModel{
	private List<SoruModelItem> soruModel;

	public void setSoruModel(List<SoruModelItem> soruModel){
		this.soruModel = soruModel;
	}

	public List<SoruModelItem> getSoruModel(){
		return soruModel;
	}

	@Override
 	public String toString(){
		return 
			"SoruModel{" + 
			"soruModel = '" + soruModel + '\'' + 
			"}";
		}
}