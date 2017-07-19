package com.grim3212.mc.pack.decor.item;

import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManualPage;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.core.util.CreateRecipes;
import com.grim3212.mc.pack.decor.block.DecorBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
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
	public static final Item decor_door_item = new ItemDecorDoor();

	@SubscribeEvent
	public static void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		r.register(glass_shard);
		r.register(frame);
		r.register(wallpaper);
		r.register(unfired_craft);
		r.register(unfired_pot);
		r.register(lamp_item);
		r.register(brush);
		r.register(pruners);
		r.register(flat_item_frame);
		r.register(decor_door_item);
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		GameRegistry.addSmelting(unfired_craft, new ItemStack(DecorBlocks.craft_clay, 1), 0.35F);
		GameRegistry.addSmelting(unfired_pot, new ItemStack(DecorBlocks.pot, 1), 0.35F);
	}

	public static List<IRecipe> frames;

	public static void addRecipes() {
		CreateRecipes.addShapedRecipe(new ItemStack(flat_item_frame, 4), new Object[] { "###", "#L#", "###", '#', DecorBlocks.hardened_wood, 'L', "leather" });
		CreateRecipes.addShapedRecipe(new ItemStack(pruners, 1), new Object[] { "  #", " ##", "SS ", '#', "ingotIron", 'S', "stickWood" });

		CreateRecipes.addShapedRecipe(new ItemStack(Blocks.GLASS, 1), new Object[] { "##", "##", '#', glass_shard });
		// DecorBlocks.lights.add(RecipeHelper.getLatestIRecipe());

		CreateRecipes.addShapedRecipe(new ItemStack(frame, 2, 0), new Object[] { "  #", " # ", "#  ", '#', "plankWood" });
		CreateRecipes.addShapedRecipe(new ItemStack(frame, 2, 1), new Object[] { "  #", " # ", "#  ", '#', "ingotIron" });
		// frames = RecipeHelper.getLatestIRecipes(2);

		CreateRecipes.addShapedRecipe(new ItemStack(wallpaper, 6), new Object[] { "#A", "#A", "#A", '#', Items.PAPER, 'A', Blocks.WOOL });
		CreateRecipes.addShapedRecipe(new ItemStack(brush, 1), new Object[] { "  S", " # ", "#  ", '#', "stickWood", 'S', "string" });

		CreateRecipes.addShapedRecipe(new ItemStack(unfired_pot, 1), new Object[] { "# #", "###", '#', Items.CLAY_BALL });
		CreateRecipes.addShapedRecipe(new ItemStack(unfired_craft, 1), new Object[] { " # ", "###", "###", '#', Items.CLAY_BALL });
		// DecorBlocks.crafts.addAll(RecipeHelper.getLatestIRecipes(2));
	}

}
