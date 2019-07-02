package com.grim3212.mc.pack.decor.crafting;

import com.grim3212.mc.pack.decor.crafting.GrillRecipeSerializer.GrillRecipe;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;

public class DecorRecipes {

	@ObjectHolder(DecorNames.RECIPE_SERIALIZER_GRILL)
	public static IRecipeSerializer<?> GRILL_SERIALIZER;
	
	public static IRecipeType<GrillRecipe> GRILL_TYPE = IRecipeType.register(DecorNames.RECIPE_TYPE_GRILL);

	@SubscribeEvent
	public void initRecipes(RegistryEvent.Register<IRecipeSerializer<?>> evt) {
		evt.getRegistry().register(new GrillRecipeSerializer().setRegistryName(DecorNames.RECIPE_SERIALIZER_GRILL));
	}

}
