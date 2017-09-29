package com.grim3212.mc.pack.util.network;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.core.util.Utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

public class MessageAutoTorch extends AbstractServerMessage<MessageAutoTorch> {

	private BlockPos pos;

	public MessageAutoTorch() {
	}

	public MessageAutoTorch(BlockPos pos) {
		this.pos = pos;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.pos = buffer.readBlockPos();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(this.pos);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if (Utils.consumePlayerItem(player, new ItemStack(Blocks.TORCH)) != ItemStack.EMPTY) {
			player.world.setBlockState(pos, Blocks.TORCH.getDefaultState());
		}

	}

}
