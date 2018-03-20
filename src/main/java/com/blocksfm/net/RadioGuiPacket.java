package com.blocksfm.net;

import com.blocksfm.blocks.radio.TileEntityRadio;
import com.blocksfm.utils.Utils;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class RadioGuiPacket implements IMessage
{
	private BlockPos pos;
	private int volume;
	private int playedFrequency;
	private boolean isOn;

	public RadioGuiPacket()
	{
		//für reflections
	}

	public RadioGuiPacket(BlockPos pos, int vol, int freq, boolean isOn)
	{
		this.volume = vol;
		this.pos = pos;
		this.playedFrequency = freq;
		this.isOn = isOn;
	}
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.pos = new BlockPos(new Vec3i(buf.readInt(), buf.readInt(), buf.readInt()));
		this.volume = buf.readInt();
		this.playedFrequency = buf.readInt();
		this.isOn = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		Utils.writeVec3i(buf, this.pos);
		buf.writeInt(this.volume);
		buf.writeInt(this.playedFrequency);
		buf.writeBoolean(this.isOn);
	}

	public static class Handler implements IMessageHandler<RadioGuiPacket, IMessage>
	{
		@Override
		public IMessage onMessage(RadioGuiPacket message, MessageContext ctx)
		{
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(RadioGuiPacket message, MessageContext ctx)
		{
			TileEntityRadio tile = (TileEntityRadio) ctx.getServerHandler().player.world.getTileEntity(message.pos);

			if (tile == null)
				return;

			tile.setVolume(message.volume);
			tile.setPlayedFreq(message.playedFrequency);
			tile.setOn(message.isOn);
		}
	}

}
