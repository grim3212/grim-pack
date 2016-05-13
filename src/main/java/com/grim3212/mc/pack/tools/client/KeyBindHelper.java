package com.grim3212.mc.pack.tools.client;

import org.lwjgl.input.Keyboard;

import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.tools.items.ItemBreakingWand;
import com.grim3212.mc.pack.tools.items.ItemBuildingWand;
import com.grim3212.mc.pack.tools.items.ItemMiningWand;
import com.grim3212.mc.pack.tools.items.ItemPowerStaff;
import com.grim3212.mc.pack.tools.items.ItemWand;
import com.grim3212.mc.pack.tools.network.MessageSwitchModes;
import com.grim3212.mc.pack.tools.network.MessageWandKeys;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyBindHelper {

	private final Minecraft mc;

	private KeyBinding Key_1 = new KeyBinding("Wands Key 1", Keyboard.KEY_X, GrimTools.modName);
	private KeyBinding Key_2 = new KeyBinding("Wands Key 2", Keyboard.KEY_C, GrimTools.modName);
	private KeyBinding Key_3 = new KeyBinding("Wands Key 3", Keyboard.KEY_V, GrimTools.modName);
	private KeyBinding Key_Help = new KeyBinding("Wands Help Key", Keyboard.KEY_LCONTROL, GrimTools.modName);

	private KeyBinding modeSwitch = new KeyBinding("Powerstaff Switch Modes", Keyboard.KEY_M, GrimTools.modName);

	public KeyBindHelper() {
		mc = Minecraft.getMinecraft();

		for (KeyBinding key : new KeyBinding[] { Key_1, Key_2, Key_3, Key_Help, modeSwitch }) {
			ClientRegistry.registerKeyBinding(key);
		}
	}

	@SubscribeEvent
	public void tick(KeyInputEvent event) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if (mc.inGameHasFocus) {
			if (player != null && player.getCurrentEquippedItem() != null) {
				if (player.getCurrentEquippedItem().getItem() instanceof ItemPowerStaff) {
					if (modeSwitch.isPressed()) {
						PacketDispatcher.sendToServer(new MessageSwitchModes());
					}
				}

				if (player.getCurrentEquippedItem().getItem() instanceof ItemWand) {
					if (Key_Help.isKeyDown()) {
						printHelp(player, (ItemWand) player.getCurrentEquippedItem().getItem());
						return;
					}

					int keys = (Key_1.isKeyDown() ? 100 : 0) + (Key_2.isKeyDown() ? 10 : 0) + (Key_3.isKeyDown() ? 1 : 0);
					PacketDispatcher.sendToServer(new MessageWandKeys(keys));
				}
			}
		}
	}

	public void printHelp(EntityPlayer player, ItemWand wand) {
		if (wand instanceof ItemBuildingWand) {
			addChatMessage(player, "=== " + StatCollector.translateToLocal(wand.reinforced ? "item.reinforced_building_wand.name" : "item.building_wand.name") + " ===");
			addChatMessage(player, Keyboard.getKeyName(Key_1.getKeyCode()) + " - " + StatCollector.translateToLocal("help.build.key1"));
			addChatMessage(player, Keyboard.getKeyName(Key_2.getKeyCode()) + " - " + StatCollector.translateToLocal("help.build.key2"));
			addChatMessage(player, Keyboard.getKeyName(Key_3.getKeyCode()) + " - " + StatCollector.translateToLocal("help.build.key3"));
			if (wand.reinforced) {
				addChatMessage(player, Keyboard.getKeyName(Key_1.getKeyCode()) + "+" + Keyboard.getKeyName(Key_2.getKeyCode()) + " - " + StatCollector.translateToLocal("help.rbuild.key1.2"));
				addChatMessage(player, Keyboard.getKeyName(Key_1.getKeyCode()) + "+" + Keyboard.getKeyName(Key_2.getKeyCode()) + "+" + Keyboard.getKeyName(Key_3.getKeyCode()) + " - " + StatCollector.translateToLocal("help.rbuild.key1.2.3"));
			}
			addChatMessage(player, Keyboard.getKeyName(Key_1.getKeyCode()) + "+" + Keyboard.getKeyName(Key_3.getKeyCode()) + " - " + StatCollector.translateToLocal("help.build.key1.3"));
			if (wand.reinforced) {
				addChatMessage(player, Keyboard.getKeyName(Key_2.getKeyCode()) + "+" + Keyboard.getKeyName(Key_3.getKeyCode()) + " - " + StatCollector.translateToLocal("help.rbuild.key2.3"));
			}
		} else if (wand instanceof ItemBreakingWand) {
			addChatMessage(player, "=== " + StatCollector.translateToLocal(wand.reinforced ? "item.reinforced_breaking_wand.name" : "item.breaking_wand.name") + " ===");
			addChatMessage(player, Keyboard.getKeyName(Key_1.getKeyCode()) + " - " + StatCollector.translateToLocal("help.break.key1"));
			addChatMessage(player, Keyboard.getKeyName(Key_2.getKeyCode()) + " - " + StatCollector.translateToLocal("help.break.key2"));
			addChatMessage(player, Keyboard.getKeyName(Key_3.getKeyCode()) + " - " + StatCollector.translateToLocal("help.break.key3"));
		} else if (wand instanceof ItemMiningWand) {
			addChatMessage(player, "=== " + StatCollector.translateToLocal(wand.reinforced ? "item.reinforced_mining_wand.name" : "item.mining_wand.name") + " ===");
			addChatMessage(player, Keyboard.getKeyName(Key_1.getKeyCode()) + " - " + StatCollector.translateToLocal("help.mine.key1"));
			addChatMessage(player, Keyboard.getKeyName(Key_2.getKeyCode()) + " - " + StatCollector.translateToLocal("help.mine.key2"));
			addChatMessage(player, Keyboard.getKeyName(Key_3.getKeyCode()) + " - " + StatCollector.translateToLocal("help.mine.key3"));
			addChatMessage(player, Keyboard.getKeyName(Key_1.getKeyCode()) + "+" + Keyboard.getKeyName(Key_2.getKeyCode()) + " - " + StatCollector.translateToLocal("help.mine.key1.2"));
		}
	}

	public static void addChatMessage(EntityPlayer player, String message) {
		player.addChatComponentMessage(new ChatComponentText(message));
	}

}
