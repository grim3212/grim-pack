package com.grim3212.mc.pack.util.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AutoItemTickHandler {

	private ItemStack prevItemStack = null;
	private int prevSlot = 0;
	private int ticks = 0;

	@SubscribeEvent
	public void tick(ClientTickEvent event) {
		GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
		if (guiscreen != null) {
			return;
		} else {
			onTickInGame();
		}
	}

	public void onTickInGame() {
		if (ticks != 0) {
			ticks++;
			if (ticks > 64)
				ticks = 0;
		} else {
			Minecraft mc = Minecraft.getMinecraft();
			EntityPlayer player = mc.thePlayer;

			ItemStack currentItemStack = player.getCurrentEquippedItem();
			int currentSlot = player.inventory.currentItem;

			if (mc.currentScreen == null) {
				ItemStack[] inventory = player.inventory.mainInventory;
				currentItemStack = inventory[currentSlot];

				if (currentItemStack == null && prevItemStack != null && currentSlot == prevSlot) {
					Item item = prevItemStack.getItem();
					boolean subTypes = prevItemStack.getHasSubtypes();
					boolean stackable = prevItemStack.isStackable();
					boolean damageable = prevItemStack.isItemStackDamageable();
					int damage = prevItemStack.getItemDamage();

					boolean hotbar = false;
					int found = -1;
					for (int i = 0; i < inventory.length; i++) {
						ItemStack foundItemStack = inventory[i];
						if (foundItemStack != null && item == foundItemStack.getItem() && (!subTypes || damage == foundItemStack.getItemDamage()) && foundItemStack.stackSize > 0) {
							if (found < 0) {
								found = i;
							} else if (!stackable) {
								if (!damageable) {
									found = i;
								} else if (foundItemStack.getItemDamage() > inventory[found].getItemDamage()) {
									found = i;
								}
							} else if (foundItemStack.stackSize > inventory[found].stackSize) {
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
							playerController.windowClick(windowId, found + inventory.length, 0, 0, player);
						else
							playerController.windowClick(windowId, found, 0, 0, player);
						playerController.windowClick(windowId, currentSlot + inventory.length, 0, 0, player);

						currentItemStack = inventory[currentSlot];
						ticks++;
					}

				}
			}
			prevSlot = currentSlot;
			prevItemStack = currentItemStack;
		}
	}
}
