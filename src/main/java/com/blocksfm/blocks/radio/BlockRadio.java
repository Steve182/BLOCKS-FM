package com.blocksfm.blocks.radio;

import java.util.ArrayList;

import com.blocksfm.audio.AudioPlaybackThread;
import com.blocksfm.blocks.BlockTileEntity;
import com.blocksfm.blocks.radiostation.BlockRadioStation;
import com.blocksfm.blocks.radiostation.TileEntityRadioStation;
import com.blocksfm.utils.Utils;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRadio extends BlockTileEntity<TileEntityRadio>
{
	private static ArrayList<TileEntityRadio> allRadios = new ArrayList<>();

	public BlockRadio()
	{
		super(Material.ROCK, "radio");

	}

	@Override
	public BlockRadio setCreativeTab(CreativeTabs tab)
	{
		super.setCreativeTab(tab);
		return this;
	}

	public static void updateRadios(World world)
	{
		if(!world.isRemote)
		{
			synchronized (allRadios)
			{
				synchronized (BlockRadioStation.getStations())
				{
					//alle radios durchgehen
					for(TileEntityRadio r : BlockRadio.allRadios)
					{
						r.getRecievedStations().clear();

						//alle platzierten RadioStations durchgehen
						for(TileEntityRadioStation rs : BlockRadioStation.getStations())
						{
							//Utils.chat(Utils.calcDistance(rs.getPos(), r.getPos()) + "");
							//wenn Distanz zwischen Radio und Station <= maximale Sendedistanz der Station, kann Station empfangen werden
							if(Utils.calcDistance(rs.getPos(), r.getPos()) <= rs.getSendingDistance() && !r.getRecievedStations().contains(rs))
							{
								r.getRecievedStations().add(rs);
							}
						}
					}
				}
			}
		}
	}


	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if(!AudioPlaybackThread.getInstance().isAlive())
			AudioPlaybackThread.getInstance().start();
		else
			AudioPlaybackThread.getInstance().shutdown();
		return true;
    }

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		synchronized (BlockRadio.allRadios)
		{
			allRadios.remove(this.getTileEntity(worldIn, pos));
			Minecraft.getMinecraft().player.sendChatMessage("amount of stations in game: " + allRadios.size());
		}
	}


	@Override
	public Class<TileEntityRadio> getTileEntityClass()
	{
		return TileEntityRadio.class;
	}

	@Override
	public TileEntityRadio createTileEntity(World world, IBlockState state)
	{
		TileEntityRadio r = new TileEntityRadio();

		if(!world.isRemote)
		{
			synchronized (allRadios)
			{
				//neue Station registrieren
				if(!allRadios.contains(r))
					allRadios.add(r);

				Utils.chat("Radio: " + r.getPos());
				Minecraft.getMinecraft().player.sendChatMessage("amount of radios in game: " + allRadios.size());
			}
		}

		updateRadios(world);

		return r;
	}


	//getter/setter
	public static ArrayList<TileEntityRadio> getAllRadios() {
		return allRadios;
	}

	public static void setAllRadios(ArrayList<TileEntityRadio> allRadios) {
		BlockRadio.allRadios = allRadios;
	}

}
