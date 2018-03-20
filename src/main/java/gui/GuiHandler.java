package gui;

import com.blocksfm.blocks.radio.RadioGUI;
import com.blocksfm.blocks.radio.TileEntityRadio;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
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
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

		switch(GuiTypes.values()[ID])
		{
			case RADIO:
				TileEntityRadio teR = (TileEntityRadio)te;
				if(te == null)
					return null;
				return new RadioGUI(teR);
			default:
				break;
		}
		return null;
	}
}
