package com.grim3212.mc.pack.core.manual.event;

import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.item.CoreItems;
import com.grim3212.mc.pack.core.util.NBTHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.items.ItemHandlerHelper;

public class GiveManualEvent {

	public static final String GAVE_MANUAL = "grimpack.gaveManual";

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		if (CoreConfig.giveManualOnJoin) {
			NBTTagCompound tag = event.player.getEntityData();
			NBTTagCompound data = NBTHelper.getTagCompound(tag, EntityPlayer.PERSISTED_NBT_TAG);

			if (!data.getBoolean(GAVE_MANUAL)) {
				ItemHandlerHelper.giveItemToPlayer(event.player, new ItemStack(CoreItems.instruction_manual));
				data.setBoolean(GAVE_MANUAL, true);
				tag.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
			}
		}
	}
}
