package com.grim3212.mc.pack.tools.event;

import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.tools.items.ItemChickenSuit;
import com.grim3212.mc.pack.tools.network.MessageChickenSuitUpdate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class ChickenSuitJumpEvent {

	private int numJumps;

	@SubscribeEvent
	public void tick(ClientTickEvent event) {
		GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
		if (guiscreen != null) {
			return;
		} else {
			if (event.phase == Phase.END)
				onTickInGame();
		}
	}

	public void onTickInGame() {
		Minecraft mc = Minecraft.getMinecraft();

		if (mc.player.onGround) {
			numJumps = 0;
		}

		if (!mc.player.isInWater() && !mc.player.isInLava() && mc.player.isAirBorne) {
			int jumpsAllowed = getMaxJumps(mc.player);

			// Must at least have 1 piece of the suit
			if (jumpsAllowed > 1) {
				if (mc.gameSettings.keyBindJump.isPressed() && numJumps < jumpsAllowed) {
					// Do not perform any calculation on the initial jump
					// This is handled by vanilla already
					if (numJumps > 0) {
						mc.player.jump();
						mc.player.fallDistance = 0.0f;

						// Only play sound to client player
						mc.world.playSound(mc.player, mc.player.getPosition(), SoundEvents.ENTITY_CHICKEN_AMBIENT, SoundCategory.PLAYERS, 1.0F, 1.0F);

						// Double jump on server
						PacketDispatcher.sendToServer(new MessageChickenSuitUpdate());
					} else {
						// Allow for resetting fall damage when falling
						// 'Flap those wings' :)
						if (mc.player.fallDistance > 0.4f) {
							mc.player.jump();
							mc.player.fallDistance = -this.numJumps;

							// Only play sound to client player
							mc.world.playSound(mc.player, mc.player.getPosition(), SoundEvents.ENTITY_CHICKEN_AMBIENT, SoundCategory.PLAYERS, 1.0F, 1.0F);

							// Double jump on server
							PacketDispatcher.sendToServer(new MessageChickenSuitUpdate());
						}
					}

					numJumps++;
				}

				if (!mc.gameSettings.keyBindSneak.isKeyDown() && mc.player.motionY < 0.0D) {
					double d = -0.14999999999999999D - 0.14999999999999999D * (1.0D - (double) numJumps / 5D);
					if (mc.player.motionY < d) {
						mc.player.motionY = d;
					}
					mc.player.fallDistance = 0.0F;

					// Glide on server
					PacketDispatcher.sendToServer(new MessageChickenSuitUpdate(this.numJumps));
				}

			} else {
				numJumps = 0;
			}
		} else {
			numJumps = 0;
		}
	}

	private int getMaxJumps(EntityPlayer player) {
		// Start at one for original jump
		int maxJumps = 1;
		for (ItemStack stack : player.getArmorInventoryList()) {
			if (stack.isEmpty())
				continue;

			if (stack.getItem() instanceof ItemChickenSuit) {
				maxJumps++;
			}
		}

		return maxJumps;
	}

}
