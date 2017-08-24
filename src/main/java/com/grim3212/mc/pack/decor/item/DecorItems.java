package com.grim3212.mc.pack.decor.item;

import com.grim3212.mc.pack.core.item.ItemManualPage;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.config.DecorConfig;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class DecorItems {

	public static final Item glass_shard = (new ItemManualPage("glass_shard", "decor:deco.lights")).setCreativeTab(GrimCreativeTabs.GRIM_DECOR);
	public static final Item frame = new ItemFrame();
	public static final Item wallpaper = new ItemWallpaper();
	public static final Item unfired_craft = (new ItemManualPage("unfired_craft", "decor:deco.crafts")).setCreativeTab(GrimCreativeTabs.GRIM_DECOR);
	public static final Item unfired_pot = (new ItemManualPage("unfired_pot", "decor:deco.crafts")).setCreativeTab(GrimCreativeTabs.GRIM_DECOR);
	public static final Item lamp_item = new ItemLampPost();
	public static final Item brush = new ItemBrush();
	public static final Item pruners = new ItemPruners();
	public static final Item flat_item_frame = new ItemFlatItemFrame();

	@SubscribeEvent
	public void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		if (DecorConfig.subpartLightBulbs)
			r.register(glass_shard);

		if (DecorConfig.subpartFrames)
			r.register(frame);

		if (DecorConfig.subpartWallpaper)
			r.register(wallpaper);

		if (DecorConfig.subpartDecorations) {
			r.register(unfired_craft);
			r.register(unfired_pot);
		}

		if (DecorConfig.subpartFlatItemFrame)
			r.register(flat_item_frame);

		if (DecorConfig.subpartColorizer) {
			r.register(brush);

			if (DecorConfig.subpartLampPosts)
				r.register(lamp_item);

			if (DecorConfig.subpartSlopes)
				r.register(pruners);
		}
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		if (DecorConfig.subpartDecorations) {
			GameRegistry.addSmelting(unfired_craft, new ItemStack(DecorBlocks.craft_clay, 1), 0.35F);
			GameRegistry.addSmelting(unfired_pot, new ItemStack(DecorBlocks.pot, 1), 0.35F);
		}
	}
}