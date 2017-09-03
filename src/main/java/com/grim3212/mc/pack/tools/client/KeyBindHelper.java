package com.grim3212.mc.pack.tools.client;

import org.lwjgl.input.Keyboard;

import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.tools.config.ToolsConfig;
import com.grim3212.mc.pack.tools.items.ItemBreakingWand;
import com.grim3212.mc.pack.tools.items.ItemBuildingWand;
import com.grim3212.mc.pack.tools.items.ItemMiningWand;
import com.grim3212.mc.pack.tools.items.ItemPowerStaff;
import com.grim3212.mc.pack.tools.items.ItemSlingshot;
import com.grim3212.mc.pack.tools.items.ItemStaff;
import com.grim3212.mc.pack.tools.items.ItemWand;
import com.grim3212.mc.pack.tools.network.MessagePowerStaffSwitchModes;
import com.grim3212.mc.pack.tools.network.MessageSlingshotSwitchModes;
import com.grim3212.mc.pack.tools.network.MessageStaffKey;
import com.grim3212.mc.pack.tools.network.MessageWandKeys;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyBindHelper {

	private final Minecraft mc;

	private KeyBinding Key_1 = new KeyBinding("Tools Key 1", Keyboard.KEY_X, GrimTools.partName);
	private KeyBinding Key_2 = new KeyBinding("Tools Key 2", Keyboard.KEY_C, GrimTools.partName);
	private KeyBinding Key_3 = new KeyBinding("Tools Key 3", Keyboard.KEY_V, GrimTools.partName);
	private KeyBinding Key_Help = new KeyBinding("Tools Help Key", Keyboard.KEY_LCONTROL, GrimTools.partName);

	private KeyBinding modeSwitch = new KeyBinding("GrimTools Switch Modes", Keyboard.KEY_M, GrimTools.partName);

	public KeyBindHelper() {
		mc = Minecraft.getMinecraft();

		if (ToolsConfig.subpartWands || ToolsConfig.subpartStaffs) {
			for (KeyBinding key : new KeyBinding[] { Key_1, Key_2, Key_3, Key_Help }) {
				ClientRegistry.registerKeyBinding(key);
			}
		}

		if (ToolsConfig.subpartPowerstaff || ToolsConfig.subpartSlingshots) {
			ClientRegistry.registerKeyBinding(modeSwitch);
		}
	}

	@SubscribeEvent
	public void tick(KeyInputEvent event) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (mc.inGameHasFocus) {
			if (player != null)
				if (player.getHeldItemMainhand() != null) {
					if (ToolsConfig.subpartPowerstaff) {
						if (player.getHeldItemMainhand().getItem() instanceof ItemPowerStaff) {
							if (modeSwitch.isPressed()) {
								PacketDispatcher.sendToServer(new MessagePowerStaffSwitchModes(EnumHand.MAIN_HAND));
							}
						}
					}

					if (ToolsConfig.subpartSlingshots) {
						if (player.getHeldItemMainhand().getItem() instanceof ItemSlingshot) {
							if (modeSwitch.isPressed()) {
								PacketDispatcher.sendToServer(new MessageSlingshotSwitchModes(EnumHand.MAIN_HAND));
							}
						}
					}

					if (ToolsConfig.subpartWands) {
						if (player.getHeldItemMainhand().getItem() instanceof ItemWand) {
							if (Key_Help.isKeyDown()) {
								printHelp(player, (ItemWand) player.getHeldItemMainhand().getItem());
								return;
							}

							int keys = (Key_1.isKeyDown() ? 100 : 0) + (Key_2.isKeyDown() ? 10 : 0) + (Key_3.isKeyDown() ? 1 : 0);
							PacketDispatcher.sendToServer(new MessageWandKeys(EnumHand.MAIN_HAND, keys));
						}
					}

					if (ToolsConfig.subpartStaffs) {
						if (player.getHeldItemMainhand().getItem() instanceof ItemStaff) {

							int keys = (Key_1.isKeyDown() ? 1 : 0) + (Key_2.isKeyDown() ? 10 : 0);
							PacketDispatcher.sendToServer(new MessageStaffKey(EnumHand.MAIN_HAND, keys));
						}
					}
				}

			if (player.getHeldItemOffhand() != null) {
				if (ToolsConfig.subpartPowerstaff) {
					if (player.getHeldItemOffhand().getItem() instanceof ItemPowerStaff) {
						if (modeSwitch.isPressed()) {
							PacketDispatcher.sendToServer(new MessagePowerStaffSwitchModes(EnumHand.OFF_HAND));
						}
					}
				}

				if (ToolsConfig.subpartSlingshots) {
					if (player.getHeldItemOffhand().getItem() instanceof ItemSlingshot) {
						if (modeSwitch.isPressed()) {
							PacketDispatcher.sendToServer(new MessageSlingshotSwitchModes(EnumHand.OFF_HAND));
						}
					}
				}

				if (ToolsConfig.subpartWands) {
					if (player.getHeldItemOffhand().getItem() instanceof ItemWand) {
						if (Key_Help.isKeyDown()) {
							printHelp(player, (ItemWand) player.getHeldItemOffhand().getItem());
							return;
						}

						int keys = (Key_1.isKeyDown() ? 100 : 0) + (Key_2.isKeyDown() ? 10 : 0) + (Key_3.isKeyDown() ? 1 : 0);
						PacketDispatcher.sendToServer(new MessageWandKeys(EnumHand.OFF_HAND, keys));
					}
				}

				if (ToolsConfig.subpartStaffs) {
					if (player.getHeldItemOffhand().getItem() instanceof ItemStaff) {

						int keys = (Key_1.isKeyDown() ? 1 : 0) + (Key_2.isKeyDown() ? 10 : 0);
						PacketDispatcher.sendToServer(new MessageStaffKey(EnumHand.OFF_HAND, keys));
					}
				}
			}
		}

	}

	public void printHelp(EntityPlayer player, ItemWand wand) {
		if (wand instanceof ItemBuildingWand) {
			addChatMessage(player, "=== " + I18n.format(wand.reinforced ? "item.reinforced_building_wand.name" : "item.building_wand.name") + " ===");
			addChatMessage(player, Keyboard.getKeyName(Key_1.getKeyCode()) + " - " + I18n.format("help.build.key1"));
			addChatMessage(player, Keyboard.getKeyName(Key_2.getKeyCode()) + " - " + I18n.format("help.build.key2"));
			addChatMessage(player, Keyboard.getKeyName(Key_3.getKeyCode()) + " - " + I18n.format("help.build.key3"));
			if (wand.reinforced) {
				addChatMessage(player, Keyboard.getKeyName(Key_1.getKeyCode()) + "+" + Keyboard.getKeyName(Key_2.getKeyCode()) + " - " + I18n.format("help.rbuild.key1.2"));
				addChatMessage(player, Keyboard.getKeyName(Key_1.getKeyCode()) + "+" + Keyboard.getKeyName(Key_2.getKeyCode()) + "+" + Keyboard.getKeyName(Key_3.getKeyCode()) + " - " + I18n.format("help.rbuild.key1.2.3"));
			}
			addChatMessage(player, Keyboard.getKeyName(Key_1.getKeyCode()) + "+" + Keyboard.getKeyName(Key_3.getKeyCode()) + " - " + I18n.format("help.build.key1.3"));
			if (wand.reinforced) {
				addChatMessage(player, Keyboard.getKeyName(Key_2.getKeyCode()) + "+" + Keyboard.getKeyName(Key_3.getKeyCode()) + " - " + I18n.format("help.rbuild.key2.3"));
			}
		} else if (wand instanceof ItemBreakingWand) {
			addChatMessage(player, "=== " + I18n.format(wand.reinforced ? "item.reinforced_breaking_wand.name" : "item.breaking_wand.name") + " ===");
			addChatMessage(player, Keyboard.getKeyName(Key_1.getKeyCode()) + " - " + I18n.format("help.break.key1"));
			addChatMessage(player, Keyboard.getKeyName(Key_2.getKeyCode()) + " - " + I18n.format("help.break.key2"));
			addChatMessage(player, Keyboard.getKeyName(Key_3.getKeyCode()) + " - " + I18n.format("help.break.key3"));
		} else if (wand instanceof ItemMiningWand) {
			addChatMessage(player, "=== " + I18n.format(wand.reinforced ? "item.reinforced_mining_wand.name" : "item.mining_wand.name") + " ===");
			addChatMessage(player, Keyboard.getKeyName(Key_1.getKeyCode()) + " - " + I18n.format("help.mine.key1"));
			addChatMessage(player, Keyboard.getKeyName(Key_2.getKeyCode()) + " - " + I18n.format("help.mine.key2"));
			addChatMessage(player, Keyboard.getKeyName(Key_3.getKeyCode()) + " - " + I18n.format("help.mine.key3"));
			addChatMessage(player, Keyboard.getKeyName(Key_1.getKeyCode()) + "+" + Keyboard.getKeyName(Key_2.getKeyCode()) + " - " + I18n.format("help.mine.key1.2"));
		}
	}

	public static void addChatMessage(EntityPlayer player, String message) {
		player.sendMessage(new TextComponentString(message));
	}

}
