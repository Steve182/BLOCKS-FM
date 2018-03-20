package com.blocksfm.main;

import com.blocksfm.blocks.ModBlocks;
import com.blocksfm.item.ModItems;
import com.blocksfm.net.RadioGuiPacket;
import com.blocksfm.proxy.CommonProxy;

import gui.GuiHandler;
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
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = BlocksFM.MODID, name = BlocksFM.NAME, version = BlocksFM.VERSION)
public class BlocksFM
{
    public static final String MODID = "blocksfm";
    public static final String NAME = "BLOCKS-FM";
    public static final String VERSION = "1.0";

    public static final int minFreq = 876;
    public static final int maxFreq = 1079;

    @Mod.Instance(MODID)
    public static BlocksFM instance;

    //Proxy
    @SidedProxy(serverSide = "com.blocksfm.proxy.CommonProxy", clientSide = "com.blocksfm.proxy.ClientProxy")
    public static CommonProxy proxy;

    public static GuiHandler guiHandler = new GuiHandler();

    public static final SimpleNetworkWrapper net = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

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
    	//Radio GUI
    	BlocksFM.net.registerMessage(RadioGuiPacket.Handler.class, RadioGuiPacket.class, 0, Side.CLIENT);
    	BlocksFM.net.registerMessage(RadioGuiPacket.Handler.class, RadioGuiPacket.class, 0, Side.SERVER);

    	//RadioStation GUI

    	NetworkRegistry.INSTANCE.registerGuiHandler(BlocksFM.instance, guiHandler);
    }

    @EventHandler
    public void postInit(FMLPreInitializationEvent event)
    {

    }
}
