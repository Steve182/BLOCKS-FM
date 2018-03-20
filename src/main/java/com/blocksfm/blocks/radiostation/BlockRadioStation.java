package com.blocksfm.blocks.radiostation;

import com.blocksfm.blocks.BlockTileEntity;
import com.blocksfm.main.BlocksFM;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRadioStation extends BlockTileEntity<TileEntityRadioStation>
{
	private static TileEntityRadioStation[] registeredStations = new TileEntityRadioStation[BlocksFM.maxFreq - BlocksFM.minFreq];

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
		return true;
    }

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{

	}

	@Override
	public TileEntityRadioStation createTileEntity(World world, IBlockState state)
	{
		TileEntityRadioStation rs = new TileEntityRadioStation();

		return rs;
	}

	@Override
	public Class<TileEntityRadioStation> getTileEntityClass()
	{
		return TileEntityRadioStation.class;
	}

	//getter/setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static TileEntityRadioStation[] getRegisteredStations() {
		return registeredStations;
	}

	public static void setRegisteredStations(TileEntityRadioStation[] registeredStations) {
		BlockRadioStation.registeredStations = registeredStations;
	}

}
