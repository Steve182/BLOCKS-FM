package com.blocksfm.blocks.radio;

import com.blocksfm.main.BlocksFM;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRadio extends TileEntity
{
	private boolean isOn;
	private int playedFreq;
	private int volume;

	public TileEntityRadio()
	{
		this.isOn = true;
		this.playedFreq = BlocksFM.minFreq;
		this.volume = 5;
	}

	//für Speicherung auf Platte
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound comp)
	{
		comp.setBoolean("isOn", this.isOn);
		comp.setInteger("playedFreq", this.playedFreq);
		comp.setInteger("volume", this.volume);

		return super.writeToNBT(comp);
	}

	public void increaseVolume()
	{
		this.volume++;
	}

	public void decreaseVolume()
	{
		this.volume--;
	}

	public void increaseFrequency()
	{
		this.playedFreq++;
	}

	public void decreaseFrequency()
	{
		this.playedFreq--;
	}

	public double formatPlayedFrequency()
	{
		return this.playedFreq / 10.0;
	}

	public String formatOnOff()
	{
		if(isOn)
			return "ON";
		else
			return "OFF";
	}

	//getter/setter
	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}

	public int getPlayedFreq() {
		return playedFreq;
	}

	public void setPlayedFreq(int playedFreq) {
		this.playedFreq = playedFreq;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}
}
