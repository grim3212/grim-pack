package com.grim3212.mc.pack.tools.crafting;

import com.grim3212.mc.pack.GrimPack;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class ToolsRecipes {

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		IForgeRegistry<IRecipe> r = evt.getRegistry();

		r.register(new BackpackRecipe().setRegistryName(new ResourceLocation(GrimPack.modID, "backpack_recipe")));
		r.register(new PelletBagRecipe().setRegistryName(new ResourceLocation(GrimPack.modID, "pellet_bag_recipe")));
	}

}
