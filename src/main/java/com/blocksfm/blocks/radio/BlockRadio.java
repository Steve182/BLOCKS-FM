package com.blocksfm.blocks.radio;

import java.util.ArrayList;

import com.blocksfm.blocks.BlockTileEntity;
import com.blocksfm.main.BlocksFM;

import gui.GuiTypes;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
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

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		//TileEntityRadio te = this.getTileEntity(worldIn, pos);

		playerIn.openGui(BlocksFM.instance, GuiTypes.RADIO.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());

		return true;
    }

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
	}

	 @Override
	 public boolean hasTileEntity(IBlockState state)
	 {
		 return true;
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
