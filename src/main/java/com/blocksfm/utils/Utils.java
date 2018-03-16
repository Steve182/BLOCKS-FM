package com.blocksfm.utils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Utils
{
	public static double calcDistance(BlockPos p1, BlockPos p2)
	{
		 return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2) + Math.pow(p1.getZ() - p2.getZ(), 2));
	}

	public static Block getBlockAt(BlockPos bp, World world)
	{
		IBlockState ibs = world.getBlockState(bp);
		return ibs.getBlock();
	}
}
