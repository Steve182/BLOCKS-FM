package com.blocksfm.main;

import com.blocksfm.blocks.ModBlocks;
import com.blocksfm.item.ModItems;
import com.blocksfm.proxy.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = BlocksFM.MODID, name = BlocksFM.NAME, version = BlocksFM.VERSION)
public class BlocksFM
{
    public static final String MODID = "blocksfm";
    public static final String NAME = "BLOCKS-FM";
    public static final String VERSION = "1.0";

    @Mod.Instance(MODID)
    public static BlocksFM instance;

    //Proxy
    @SidedProxy(serverSide = "com.blocksfm.proxy.CommonProxy", clientSide = "com.blocksfm.proxy.ClientProxy")
    public static CommonProxy proxy;
    
    //Network
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(BlocksFM.MODID);

    //for Item and Block registration
    @Mod.EventBusSubscriber
	public static class RegistrationHandler
	{
    	@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event)
    	{
			ModItems.register(event.getRegistry());
			ModBlocks.registerItemBlocks(event.getRegistry());
		}

    	@SubscribeEvent
    	public static void registerItems(ModelRegistryEvent event)
    	{
    		ModItems.registerModels();
    		ModBlocks.registerModels();
    	}

    	@SubscribeEvent
    	public static void registerBlocks(RegistryEvent.Register<Block> event) {
    		ModBlocks.register(event.getRegistry());
    	}
	}

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    @EventHandler
    public void postInit(FMLPreInitializationEvent event)
    {

    }
}
