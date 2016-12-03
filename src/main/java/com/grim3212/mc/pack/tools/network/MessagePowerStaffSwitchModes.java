package com.grim3212.mc.pack.tools.network;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.tools.items.ItemPowerStaff;
import com.grim3212.mc.pack.tools.util.EnumPowerStaffModes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;

public class MessagePowerStaffSwitchModes extends AbstractServerMessage<MessagePowerStaffSwitchModes> {

	private EnumHand hand;

	public MessagePowerStaffSwitchModes() {
	}

	public MessagePowerStaffSwitchModes(EnumHand hand) {
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

//	@Override
//	public void process(EntityPlayer player, Side side) {
//		if (player.getHeldItem(hand) != null && player.getHeldItem(hand).getItem() instanceof ItemPowerStaff) {
//			if (player.getHeldItem(hand).getItemDamage() == 0) {
//				player.getHeldItem(hand).setItemDamage(1);
//			} else {
//				player.getHeldItem(hand).setItemDamage(0);
//			}
//		}
//	}
	@Override
	public void process(EntityPlayer player, Side side) {
		if (player.getHeldItem(hand) != null && player.getHeldItem(hand).getItem() instanceof ItemPowerStaff) {
			
			if (player.getHeldItem(hand) != null && player.getHeldItem(hand).getItem() instanceof ItemPowerStaff) {
				if (player.getHeldItem(hand).getItemDamage() == 0) {
					player.getHeldItem(hand).setItemDamage(1);
				} else {
					player.getHeldItem(hand).setItemDamage(0);
				}
			}

			ItemStack stack = player.getHeldItem(hand);

			String mode = NBTHelper.getString(stack, "Mode");
			EnumPowerStaffModes powerStaffMode = EnumPowerStaffModes.getFromString(mode);

			if (!mode.isEmpty()) {
				powerStaffMode = powerStaffMode.cycle();
			}
			NBTHelper.setString(stack, "Mode", powerStaffMode.getUnlocalized());

			player.addChatMessage(new TextComponentString(new TextComponentTranslation("grimtools.powerstaff.modeSwitched").getFormattedText() + new TextComponentTranslation("grimtools.powerstaff." + powerStaffMode.getUnlocalized()).getFormattedText()));
		}
	}
}