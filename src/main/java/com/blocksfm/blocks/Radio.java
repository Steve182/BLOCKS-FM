package com.blocksfm.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Radio extends BlockBase
{
	public Radio()
	{
		super(Material.ROCK, "radio");
	}

	@Override
	public Radio setCreativeTab(CreativeTabs tab)
	{
		super.setCreativeTab(tab);
		return this;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		int offset = 10;
		BlockPos startPos = new BlockPos(pos.getX() - offset, pos.getY(), pos.getZ() - offset);
		BlockPos endPos = new BlockPos(startPos.getX() + 2 * offset, startPos.getY(), startPos.getZ() + 2 * offset);
		Iterable<BlockPos> blockPoses = BlockPos.getAllInBox(startPos, endPos);

		Block bedrock = Blocks.BEDROCK;
		IBlockState ibs = bedrock.getDefaultState();

		worldIn.setBlockState(startPos, ibs);
		worldIn.setBlockState(endPos, ibs);

		for(BlockPos bp : blockPoses)
		{
			Block b = getBlockAt(bp, worldIn);

			if( b instanceof RadioStation)
			{
				AudioPlayer.play(((RadioStation) b).getAudioFile());

				break;
			}
		}


		return false;
    }

	public Block getBlockAt(BlockPos bp, World world)
	{
		IBlockState ibs = world.getBlockState(bp);
		return ibs.getBlock();
	}

}
