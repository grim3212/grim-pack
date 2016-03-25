package com.grim3212.mc.decor.block;

import java.util.List;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.core.part.IPartItems;
import com.grim3212.mc.core.util.RecipeHelper;
import com.grim3212.mc.decor.GrimDecor;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class DecorBlocks implements IPartItems {

	public static Block calendar;
	public static Block wall_clock;

	public static List<IRecipe> clocks;

	@Override
	public void initItems() {
		calendar = (new BlockCalendar()).setHardness(1.0F).setStepSound(Block.soundTypeWood).setUnlocalizedName("calendar").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		wall_clock = new BlockWallClock().setHardness(0.75F).setStepSound(Block.soundTypeWood).setUnlocalizedName("wall_clock").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());

		GameRegistry.registerBlock(calendar, "calendar");
		GameRegistry.registerBlock(wall_clock, "wall_clock");
	}

	@Override
	public void addRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(calendar, 1), new Object[] { "##", "##", "##", '#', Items.paper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wall_clock, 1), new Object[] { "XIX", "IRI", "XIX", 'X', "plankWood", 'I', "ingotGold", 'R', "dustRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wall_clock, 1), new Object[] { "XXX", "XRX", "XXX", 'X', "plankWood", 'R', Items.clock }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.clock, 1), new Object[] { wall_clock }));
		clocks = RecipeHelper.getLatestIRecipes(3);
	}

	@Override
	public void renderItems() {
		RenderHelper.renderBlock(calendar);
		RenderHelper.renderBlock(wall_clock);
	}

}
