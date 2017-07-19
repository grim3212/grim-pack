package com.grim3212.mc.pack.core.item;

import com.grim3212.mc.pack.core.manual.ItemInstructionManual;
import com.grim3212.mc.pack.core.util.CreateRecipes;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CoreItems {

	public static final Item instruction_manual = new ItemInstructionManual();

	@SubscribeEvent
	public static void initItems(RegistryEvent.Register<Item> evt) {
		evt.getRegistry().register(instruction_manual);
	}

	public static void addRecipe() {
		CreateRecipes.addShapelessRecipe(new ItemStack(instruction_manual), new Object[] { Items.BOOK, "dyeBlack", "dyeBrown" });
	}

}
