package com.blocksfm.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class RadioStation extends BlockBase
{
	public RadioStation()
	{
		super(Material.ROCK, "radiostation");
	}

	@Override
	public RadioStation setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}
}
