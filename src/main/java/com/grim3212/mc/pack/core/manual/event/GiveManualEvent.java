package com.grim3212.mc.pack.core.manual.event;

import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.init.CoreInit;
import com.grim3212.mc.pack.core.util.NBTHelper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.ItemHandlerHelper;

public class GiveManualEvent {

	public static final String GAVE_MANUAL = "grimpack.gaveManual";

	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		if (CoreConfig.giveManualOnJoin.get()) {
			CompoundNBT tag = event.getPlayer().getPersistentData();
			CompoundNBT data = NBTHelper.getTagCompound(tag, PlayerEntity.PERSISTED_NBT_TAG);

			if (!data.getBoolean(GAVE_MANUAL)) {
				ItemHandlerHelper.giveItemToPlayer(event.getPlayer(), new ItemStack(CoreInit.instruction_manual));
				data.putBoolean(GAVE_MANUAL, true);
				tag.put(PlayerEntity.PERSISTED_NBT_TAG, data);
			}
		}
	}
}
