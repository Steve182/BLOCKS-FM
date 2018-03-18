package gui;

import com.blocksfm.blocks.radio.RadioGUI;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(GuiTypes.values()[ID])
		{
			case RADIO:
				return new RadioGUI();
			default:
				break;
		}
		return null;
	}

}
