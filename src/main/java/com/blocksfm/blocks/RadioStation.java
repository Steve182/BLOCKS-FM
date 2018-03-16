package com.blocksfm.blocks;

import java.io.File;
import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class RadioStation extends BlockBase
{

	private static ArrayList<RadioStation> stations = new ArrayList<>();

	private File audioFile = new File("C:/Users/Stefan/Desktop/test.mp3");
	private int sendingDistance = 100;		//default 100;
	private BlockPos pos;

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

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
	{
		synchronized(RadioStation.stations)
		{
			stations.remove(this);
		}
	}

	@Override
	public void onBlockExploded(World worldIn, BlockPos pos, Explosion explosion)
	{
		synchronized(RadioStation.stations)
		{
			stations.remove(this);
		}
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn)
	{
		synchronized(RadioStation.stations)
		{
			stations.remove(this);
		}
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		synchronized(RadioStation.stations)
		{
			stations.remove(this);
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		synchronized(RadioStation.stations)
		{
			stations.add(this);
		}

		this.pos = pos;
	}

	//getter/setter
	public File getAudioFile() {
		return audioFile;
	}

	public void setAudioFile(File audioFile) {
		this.audioFile = audioFile;
	}

	public int getSendingDistance() {
		return sendingDistance;
	}

	public void setSendingDistance(int sendingDistance) {
		this.sendingDistance = sendingDistance;
	}

	public static ArrayList<RadioStation> getStations() {
		return stations;
	}

	public static void setStations(ArrayList<RadioStation> stations) {
		RadioStation.stations = stations;
	}

	public BlockPos getPos() {
		return pos;
	}

	public void setPos(BlockPos pos) {
		this.pos = pos;
	}



}
