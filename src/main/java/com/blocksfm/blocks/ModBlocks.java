package com.blocksfm.blocks;

import com.blocksfm.blocks.radio.BlockRadio;
import com.blocksfm.blocks.radiostation.BlockRadioStation;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks
{
	public static BlockRadioStation rs = new BlockRadioStation().setCreativeTab(CreativeTabs.MATERIALS);
	public static BlockRadio radio = new BlockRadio().setCreativeTab(CreativeTabs.MATERIALS);

	public static void register(IForgeRegistry<Block> registry)
	{
		registry.registerAll(rs, radio);

		GameRegistry.registerTileEntity(rs.getTileEntityClass(), rs.getRegistryName().toString());
		GameRegistry.registerTileEntity(radio.getTileEntityClass(), radio.getRegistryName().toString());
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
