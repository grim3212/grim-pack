package com.grim3212.mc.pack.decor.item;

import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManualPage;
import com.grim3212.mc.pack.core.part.IPartItems;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.decor.GrimDecor;
import com.grim3212.mc.pack.decor.block.DecorBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class DecorItems implements IPartItems {

	public static Item glass_shard;
	public static Item frame;
	public static Item wallpaper;
	public static Item unfired_craft;
	public static Item unfired_pot;
	public static Item lamp_item;
	public static Item brush;
	public static Item pruners;
	public static Item flat_item_frame;

	public static List<IRecipe> frames;

	@Override
	public void initItems() {
		pruners = (new ItemPruners()).setUnlocalizedName("pruners");
		brush = (new ItemBrush()).setUnlocalizedName("brush");
		glass_shard = (new ItemManualPage("decor:deco.lights")).setUnlocalizedName("glass_shard").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		frame = new ItemFrame().setUnlocalizedName("frame").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		wallpaper = new ItemWallpaper().setUnlocalizedName("wallpaper").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		unfired_pot = (new ItemManualPage("decor:deco.crafts")).setUnlocalizedName("unfired_pot").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		unfired_craft = (new ItemManualPage("decor:deco.crafts")).setUnlocalizedName("unfired_craft").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		lamp_item = new ItemLampPost().setUnlocalizedName("lamp_item").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		flat_item_frame = new ItemFlatItemFrame().setUnlocalizedName("flat_item_frame").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());

		Utils.registerItem(flat_item_frame, "flat_item_frame");
		Utils.registerItem(pruners, "pruners");
		Utils.registerItem(brush, "brush");
		Utils.registerItem(lamp_item, "lamp_item");
		Utils.registerItem(glass_shard, "glass_shard");
		Utils.registerItem(frame, "frame");
		Utils.registerItem(wallpaper, "wallpaper");
		Utils.registerItem(unfired_craft, "unfired_craft");
		Utils.registerItem(unfired_pot, "unfired_pot");
	}

	@Override
	public void addRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(flat_item_frame, 4), new Object[] { "###", "#L#", "###", '#', DecorBlocks.hardened_wood, 'L', "leather" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pruners, 1), new Object[] { "  #", " ##", "SS ", '#', "ingotIron", 'S', "stickWood" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.GLASS, 1), new Object[] { "##", "##", '#', glass_shard }));
		DecorBlocks.lights.add(RecipeHelper.getLatestIRecipe());

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(frame, 2, 0), new Object[] { "  #", " # ", "#  ", '#', "plankWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(frame, 2, 1), new Object[] { "  #", " # ", "#  ", '#', "ingotIron" }));
		frames = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wallpaper, 6), new Object[] { "#A", "#A", "#A", '#', Items.PAPER, 'A', Blocks.WOOL }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(brush, 1), new Object[] { "  S", " # ", "#  ", '#', "stickWood", 'S', "string" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(unfired_pot, 1), new Object[] { "# #", "###", '#', Items.CLAY_BALL }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(unfired_craft, 1), new Object[] { " # ", "###", "###", '#', Items.CLAY_BALL }));
		DecorBlocks.crafts.addAll(RecipeHelper.getLatestIRecipes(2));

		GameRegistry.addSmelting(unfired_craft, new ItemStack(DecorBlocks.craft_clay, 1), 0.35F);
		GameRegistry.addSmelting(unfired_pot, new ItemStack(DecorBlocks.pot, 1), 0.35F);
	}

}
