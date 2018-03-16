package com.blocksfm.blocks;

import java.util.ArrayList;

import com.blocksfm.utils.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Radio extends BlockBase
{
	private ArrayList<RadioStation> recievedStations;

	public Radio()
	{
		super(Material.ROCK, "radio");

		this.recievedStations = new ArrayList<RadioStation>();
	}

	@Override
	public Radio setCreativeTab(CreativeTabs tab)
	{
		super.setCreativeTab(tab);
		return this;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		//alle platzierten RadioStations durchgehen
				for(RadioStation rs : RadioStation.getStations())
				{
					//wenn Distanz zwischen Radio und Station <= maximale Sendedistanz der Station, kann Station empfangen werden
					if(Utils.calcDistance(pos, rs.getPos()) >= rs.getSendingDistance())
					{
						synchronized (this.recievedStations)
						{
							this.recievedStations.add(rs);
						}
					}
				}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {

		Minecraft.getMinecraft().player.sendChatMessage("test");
		/*int offset = 10;
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
		}*/


		return false;
    }

}
