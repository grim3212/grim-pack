package com.grim3212.mc.core.manual.event;

import com.grim3212.mc.core.config.CoreConfig;
import com.grim3212.mc.core.item.CoreItems;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class LoginEvent {

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		if (CoreConfig.giveManualOnJoin) {
			NBTTagCompound tag = event.player.getEntityData();
			if (tag.hasKey("giveManual")) {
				return;
			} else {
				event.player.inventory.addItemStackToInventory(new ItemStack(CoreItems.instruction_manual));
				tag.setBoolean("giveManual", false);
			}
		}
	}
}