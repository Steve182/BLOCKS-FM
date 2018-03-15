package com.blocksfm.blocks;

import java.io.File;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RadioStation extends BlockBase
{
	private File audioFile = new File("C:/Users/Stefan/Desktop/test.mp3");

	public RadioStation()
	{
		super(Material.ROCK, "radiostation");
	}

	@Override
	public RadioStation setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		AudioPlayer.play(audioFile);

		return false;
    }

	//getter/setter
	public File getAudioFile() {
		return audioFile;
	}

	public void setAudioFile(File audioFile) {
		this.audioFile = audioFile;
	}


}
