package com.grim3212.mc.pack.decor.item;

import java.util.List;

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

	public static List<IRecipe> frames;

	@Override
	public void initItems() {
		glass_shard = (new Item()).setUnlocalizedName("glass_shard").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		frame = new ItemFrame().setUnlocalizedName("frame").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		wallpaper = new ItemWallpaper().setUnlocalizedName("wallpaper").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		unfired_pot = (new Item()).setUnlocalizedName("unfired_pot").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		unfired_craft = (new Item()).setUnlocalizedName("unfired_craft").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		lamp_item = new ItemLampPost().setUnlocalizedName("lamp_item").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());

		Utils.registerItem(lamp_item, "lamp_item");
		Utils.registerItem(glass_shard, "glass_shard");
		Utils.registerItem(frame, "frame");
		Utils.registerItem(wallpaper, "wallpaper");
		Utils.registerItem(unfired_craft, "unfired_craft");
		Utils.registerItem(unfired_pot, "unfired_pot");
	}

	@Override
	public void addRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.glass, 1), new Object[] { "##", "##", '#', glass_shard }));
		DecorBlocks.lights.add(RecipeHelper.getLatestIRecipe());

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(frame, 2, 0), new Object[] { "  #", " # ", "#  ", '#', "plankWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(frame, 2, 1), new Object[] { "  #", " # ", "#  ", '#', "ingotIron" }));
		frames = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wallpaper, 6), new Object[] { "#A", "#A", "#A", '#', Items.paper, 'A', Blocks.wool }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(unfired_pot, 1), new Object[] { "# #", "###", '#', Items.clay_ball }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(unfired_craft, 1), new Object[] { " # ", "###", "###", '#', Items.clay_ball }));
		DecorBlocks.crafts.addAll(RecipeHelper.getLatestIRecipes(2));

		GameRegistry.addSmelting(unfired_craft, new ItemStack(DecorBlocks.craft_clay, 1), 0.35F);
		GameRegistry.addSmelting(unfired_pot, new ItemStack(DecorBlocks.pot, 1), 0.35F);
	}

}
