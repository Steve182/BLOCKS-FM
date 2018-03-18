package com.blocksfm.blocks.radio;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;


public class RadioGUI extends GuiScreen
{

	@Override
	public void initGui()
	{
		GuiButton b = new GuiButton(0, this.width / 2 - 100, this.height / 4 - 24, "Test");
		this.buttonList.add(b);
	}

}
