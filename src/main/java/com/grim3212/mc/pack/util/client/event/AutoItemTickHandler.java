package com.grim3212.mc.pack.util.client.event;

import com.grim3212.mc.pack.util.config.UtilConfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AutoItemTickHandler {

	private ItemStack prevItemStack = ItemStack.EMPTY;
	private int prevSlot = 0;
	private int ticks = 0;

	@SubscribeEvent
	public void tick(ClientTickEvent event) {
		if (UtilConfig.enableAutoReplace) {
			GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
			if (guiscreen != null) {
				return;
			} else {
				onTickInGame();
			}
		}
	}

	public void onTickInGame() {
		if (ticks != 0) {
			ticks++;
			if (ticks > 64)
				ticks = 0;
		} else {
			Minecraft mc = Minecraft.getMinecraft();
			EntityPlayer player = mc.player;

			ItemStack currentItemStack = player.getHeldItemMainhand();
			int currentSlot = player.inventory.currentItem;

			if (mc.currentScreen == null) {
				NonNullList<ItemStack> inventory = player.inventory.mainInventory;
				currentItemStack = inventory.get(currentSlot);

				if (currentItemStack.isEmpty() && !prevItemStack.isEmpty() && currentSlot == prevSlot) {
					Item item = prevItemStack.getItem();
					boolean subTypes = prevItemStack.getHasSubtypes();
					boolean stackable = prevItemStack.isStackable();
					boolean damageable = prevItemStack.isItemStackDamageable();
					int damage = prevItemStack.getItemDamage();

					boolean hotbar = false;
					int found = -1;
					for (int i = 0; i < inventory.size(); i++) {
						ItemStack foundItemStack = inventory.get(i);
						if (!foundItemStack.isEmpty() && item == foundItemStack.getItem() && (!subTypes || damage == foundItemStack.getItemDamage()) && foundItemStack.getCount() > 0) {
							if (found < 0) {
								found = i;
							} else if (!stackable) {
								if (!damageable) {
									found = i;
								} else if (foundItemStack.getItemDamage() > inventory.get(found).getItemDamage()) {
									found = i;
								}
							} else if (foundItemStack.getCount() > inventory.get(found).getCount()) {
								found = i;
							}

							if (i < 9)
								hotbar = true;
							else
								hotbar = false;
							break;
						}
					}

					if (found >= 0) {
						PlayerControllerMP playerController = mc.playerController;
						int windowId = player.inventoryContainer.windowId;

						if (hotbar)
							playerController.windowClick(windowId, found + inventory.size(), 0, ClickType.PICKUP, player);
						else
							playerController.windowClick(windowId, found, 0, ClickType.PICKUP, player);
						playerController.windowClick(windowId, currentSlot + inventory.size(), 0, ClickType.PICKUP, player);

						currentItemStack = inventory.get(currentSlot);
						ticks++;
					}

				}
			}
			prevSlot = currentSlot;
			prevItemStack = currentItemStack;
		}
	}
}
