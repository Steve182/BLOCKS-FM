package com.blocksfm.blocks.radio;

import java.util.ArrayList;

import com.blocksfm.blocks.radiostation.TileEntityRadioStation;

import net.minecraft.tileentity.TileEntity;

public class TileEntityRadio extends TileEntity
{
	private ArrayList<TileEntityRadioStation> recievedStations;

	public TileEntityRadio()
	{
		this.recievedStations = new ArrayList<>();
	}
	//getter/setter
	public ArrayList<TileEntityRadioStation> getRecievedStations() {
		return recievedStations;
	}

	public void setRecievedStations(ArrayList<TileEntityRadioStation> recievedStations) {
		this.recievedStations = recievedStations;
	}



}
