package com.blocksfm.blocks.radiostation;

import java.io.File;
import java.util.ArrayList;

import com.blocksfm.blocks.BlockTileEntity;
import com.blocksfm.blocks.radio.BlockRadio;
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

public class BlockRadioStation extends BlockTileEntity<TileEntityRadioStation>
{

	private static ArrayList<TileEntityRadioStation> allStations = new ArrayList<>();


	private File audioFile = new File("C:/Users/Stefan/Desktop/test.mp3");

	public BlockRadioStation()
	{
		super(Material.ROCK, "radiostation");

	}

	@Override
	public BlockRadioStation setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		//AudioPlayer.play(audioFile);
		return false;
    }

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		synchronized (BlockRadioStation.allStations)
		{
			allStations.remove(this.getTileEntity(worldIn, pos));
			Minecraft.getMinecraft().player.sendChatMessage("amount of stations in game: " + allStations.size());

			BlockRadio.updateRadios(worldIn);
		}
	}

	@Override
	public TileEntityRadioStation createTileEntity(World world, IBlockState state)
	{
		TileEntityRadioStation rs = new TileEntityRadioStation();

		if(!world.isRemote)
		{
			synchronized (allStations)
			{
				//neue Station registrieren
				if(!allStations.contains(rs))
					allStations.add(rs);

				Utils.chat("Station: " + rs.getPos());
				Minecraft.getMinecraft().player.sendChatMessage("amount of stations in game: " + allStations.size());
			}
		}

		//radios benchrichtigen, dass evtl neue station in reichweite
		//BlockRadio.updateRadios(world);

		return rs;
	}

	@Override
	public Class<TileEntityRadioStation> getTileEntityClass()
	{
		return TileEntityRadioStation.class;
	}

	//getter/setter
	public File getAudioFile() {
		return audioFile;
	}

	public void setAudioFile(File audioFile) {
		this.audioFile = audioFile;
	}

	public static ArrayList<TileEntityRadioStation> getStations() {
		return allStations;
	}

	public static void setStations(ArrayList<TileEntityRadioStation> stations) {
		BlockRadioStation.allStations = stations;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



}
