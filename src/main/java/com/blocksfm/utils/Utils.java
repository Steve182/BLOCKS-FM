package com.blocksfm.utils;

import com.blocksfm.main.BlocksFM;
import com.blocksfm.net.RadioGuiPacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

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

	public static boolean isSameBlockPos(BlockPos p1, BlockPos p2)
	{
		return p1.getX() == p2.getX() && p1.getY() == p2.getY() && p1.getZ() == p2.getZ();
	}

	public static void chat(String msg)
	{
		Minecraft.getMinecraft().player.sendChatMessage(msg);
	}

	public static void writeVec3i(ByteBuf buf, Vec3i pos)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}
}
