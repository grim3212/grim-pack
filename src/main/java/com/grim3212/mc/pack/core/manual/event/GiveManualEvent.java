package com.grim3212.mc.pack.core.manual.event;

import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.init.CoreInit;
import com.grim3212.mc.pack.core.util.NBTHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.items.ItemHandlerHelper;

public class GiveManualEvent {

	public static final String GAVE_MANUAL = "grimpack.gaveManual";

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		if (CoreConfig.giveManualOnJoin.get()) {
			NBTTagCompound tag = event.getPlayer().getEntityData();
			NBTTagCompound data = NBTHelper.getTagCompound(tag, EntityPlayer.PERSISTED_NBT_TAG);

			if (!data.getBoolean(GAVE_MANUAL)) {
				ItemHandlerHelper.giveItemToPlayer(event.getPlayer(), new ItemStack(CoreInit.instruction_manual));
				data.putBoolean(GAVE_MANUAL, true);
				tag.put(EntityPlayer.PERSISTED_NBT_TAG, data);
			}
		}
	}
}
