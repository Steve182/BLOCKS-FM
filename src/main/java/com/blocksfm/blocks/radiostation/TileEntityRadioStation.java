package com.blocksfm.blocks.radiostation;

import net.minecraft.tileentity.TileEntity;

public class TileEntityRadioStation extends TileEntity
{
	private int sendingDistance = 10;		//default 100;


	//getter/setter
	public int getSendingDistance() {
		return sendingDistance;
	}

	public void setSendingDistance(int sendingDistance) {
		this.sendingDistance = sendingDistance;
	}


}
