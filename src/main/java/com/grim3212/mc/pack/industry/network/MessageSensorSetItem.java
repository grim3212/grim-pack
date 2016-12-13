package com.grim3212.mc.pack.industry.network;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSensorSetItem extends AbstractServerMessage<MessageSensorSetItem> {

	private ItemStack stack;
	private BlockPos pos;

	public MessageSensorSetItem() {
	}

	public MessageSensorSetItem(BlockPos pos, ItemStack stack) {
		this.pos = pos;
		this.stack = stack;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.pos = buffer.readBlockPos();
		this.stack = buffer.readItemStack();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(pos);
		buffer.writeItemStack(stack);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		TileEntity te = player.world.getTileEntity(pos);

		if (te instanceof TileEntitySpecificSensor) {
			((TileEntitySpecificSensor) te).getSpecific().setItemStackSpecific(stack);
		}
	}
}
