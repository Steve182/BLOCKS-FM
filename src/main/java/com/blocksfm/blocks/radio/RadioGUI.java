package com.blocksfm.blocks.radio;

import java.io.IOException;

import com.blocksfm.main.BlocksFM;
import com.blocksfm.net.RadioGuiPacket;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;

public class RadioGUI extends GuiScreen
{
    private TileEntityRadio te;

    private GuiButton inVol;
    private GuiButton deVol;
    private GuiButton inFrequency;
    private GuiButton deFrequency;
    private GuiButton isOn;

    private GuiLabel volLabel;
    private GuiLabel freqLabel;

    int labelID = 0;

    int offset = 30;
    int buttonGroupID = 0;

    public RadioGUI(TileEntityRadio te)
    {
        this.te = te;
    }

    @Override
    public void initGui()
    {
        int buttonID = 0;

        int buttonWidth = 20;

        //volume
        this.inVol = new GuiButton(buttonID++, this.width / 2 + 50, this.height / 4 + offset * buttonGroupID, "+");
        this.inVol.setWidth(buttonWidth);

        this.deVol = new GuiButton(buttonID++, this.width / 2, this.height / 4 + offset * buttonGroupID, "-");
        this.deVol.setWidth(buttonWidth);

        this.volLabel = new GuiLabel(this.fontRenderer, this.labelID++, this.width / 2 - 100, this.height / 4 + offset * buttonGroupID,  300, 25, 0xFFFFFF);
        this.volLabel.addLine("Volume: " + this.te.getVolume());
        buttonGroupID++;

        //frequency
        this.inFrequency = new GuiButton(buttonID++, this.width / 2 + 50, this.height / 4 + offset * buttonGroupID, "+");
        this.inFrequency.setWidth(buttonWidth);

        this.deFrequency = new GuiButton(buttonID++, this.width / 2, this.height / 4 + offset * buttonGroupID, "-");
        this.deFrequency.setWidth(buttonWidth);

        this.freqLabel = new GuiLabel(this.fontRenderer, this.labelID++, this.width / 2 - 100, this.height / 4 + offset * buttonGroupID,   300, 25, 0xFFFFFF);
        this.freqLabel.addLine("Frequency: " + this.te.formatPlayedFrequency());
        buttonGroupID++;

        //on - off
        this.isOn = new GuiButton(buttonID++, this.width / 4, this.height / 4 + 2 *(offset * buttonGroupID), this.te.formatOnOff());
        this.isOn.setWidth(this.width / 4);
        buttonGroupID++;

        this.buttonList.add(this.inVol);
        this.buttonList.add(this.deVol);
        this.buttonList.add(this.inFrequency);
        this.buttonList.add(this.deFrequency);
        this.buttonList.add(this.isOn);

        this.labelList.add(this.volLabel);
        this.labelList.add(this.freqLabel);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
       if(button == this.inVol)
        {
        	this.te.increaseVolume();
        }
       else if(button == this.deVol)
       {
    	   this.te.decreaseVolume();
       }
       else if(button == this.inFrequency)
       {
    	   this.te.increaseFrequency();
       }
       else if(button == this.deFrequency)
       {
    	   this.te.decreaseFrequency();
       }
       else if(button == this.isOn)
       {
    	   this.te.setOn(!this.te.isOn());
       }

       //alles an Server senden
        BlocksFM.net.sendToServer(new RadioGuiPacket(this.te.getPos(), this.te.getVolume(), this.te.getPlayedFreq(), this.te.isOn()));

        //refresh GUI
		this.updateScreen();
    }

    @Override
    public void updateScreen()
    {
    	this.labelList.clear();
    	this.buttonList.remove(4);
    	this.buttonGroupID = 0;
    	this.labelID = 0;

    	this.volLabel = new GuiLabel(this.fontRenderer,  labelID++, this.width / 2 - 100, this.height / 4 + offset * buttonGroupID,  300, 25, 0xFFFFFF);
    	this.volLabel.addLine("Volume: " + this.te.getVolume());
    	this.buttonGroupID++;

    	this.freqLabel = new GuiLabel(this.fontRenderer, this.labelID++, this.width / 2 - 100, this.height / 4 + offset * buttonGroupID,  300, 25, 0xFFFFFF);
        this.freqLabel.addLine("Frequency: " + this.te.formatPlayedFrequency());
        this.buttonGroupID++;

        //buttonID = 4
        this.isOn = new GuiButton(4, this.width / 4, this.height / 4 + 2 * (offset * buttonGroupID), this.te.formatOnOff());
        this.isOn.setWidth((this.width / 4) * 2);

        this.labelList.add(this.volLabel);
        this.labelList.add(this.freqLabel);
        this.buttonList.add(this.isOn);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        // Enter or ESC
        if (keyCode == 1) {
        	{
                this.mc.displayGuiScreen(null);
                if (this.mc.currentScreen == null)
                    this.mc.setIngameFocus();
        	}

        }
    }

    @Override
    public void drawBackground(int tint)
    {

    }

}
