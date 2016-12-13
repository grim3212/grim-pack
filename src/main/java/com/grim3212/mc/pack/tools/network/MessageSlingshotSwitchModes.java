package com.grim3212.mc.pack.tools.network;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.tools.items.ItemSlingshot;
import com.grim3212.mc.pack.tools.util.EnumSlingshotModes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSlingshotSwitchModes extends AbstractServerMessage<MessageSlingshotSwitchModes> {

	private EnumHand hand;

	public MessageSlingshotSwitchModes() {
	}

	public MessageSlingshotSwitchModes(EnumHand hand) {
		this.hand = hand;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.hand = buffer.readByte() == 1 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeByte(hand.ordinal());
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if (!player.getHeldItem(hand).isEmpty() && player.getHeldItem(hand).getItem() instanceof ItemSlingshot) {

			ItemStack stack = player.getHeldItem(hand);

			String mode = NBTHelper.getString(stack, "Mode");
			EnumSlingshotModes slingShotMode = EnumSlingshotModes.getFromString(mode);

			if (!mode.isEmpty()) {
				slingShotMode = slingShotMode.cycle();
			}
			NBTHelper.setString(stack, "Mode", slingShotMode.getUnlocalized());

			player.sendMessage(new TextComponentString(new TextComponentTranslation("grimtools.slingshot.modeSwitched").getFormattedText() + new TextComponentTranslation("grimtools.slingshot." + slingShotMode.getUnlocalized()).getFormattedText()));
		}
	}
}