package com.blocksfm.blocks;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks
{
	public static RadioStation rs = new RadioStation().setCreativeTab(CreativeTabs.MATERIALS);
	public static Radio radio = new Radio().setCreativeTab(CreativeTabs.MATERIALS);

	public static void register(IForgeRegistry<Block> registry)
	{
		registry.registerAll(rs, radio);
	}

	public static void registerItemBlocks(IForgeRegistry<Item> registry)
	{
		registry.registerAll(rs.createItemBlock(), radio.createItemBlock());
	}

	public static void registerModels()
	{
		rs.registerItemModel(Item.getItemFromBlock(rs));
		radio.registerItemModel(Item.getItemFromBlock(radio));
	}
}
