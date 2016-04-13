package com.grim3212.mc.industry.block;

import java.util.List;

import com.grim3212.mc.core.part.IPartItems;
import com.grim3212.mc.core.util.RecipeHelper;
import com.grim3212.mc.industry.GrimIndustry;
import com.grim3212.mc.industry.block.BlockElemental.ElementType;
import com.grim3212.mc.industry.item.IndustryItems;
import com.grim3212.mc.industry.item.ItemSensor;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class IndustryBlocks implements IPartItems {

	public static Block togglerack;
	public static Block iron_workbench;
	public static Block diamond_workbench;
	public static Block ice_maker;
	public static Block fire_block;
	public static Block water_block;
	public static Block lava_block;
	public static Block spike;
	public static Block wooden_sensor;
	public static Block stone_sensor;
	public static Block iron_sensor;
	public static Block netherrack_sensor;

	public static List<IRecipe> sensors;
	public static List<IRecipe> workbenches;

	@Override
	public void initItems() {
		togglerack = new BlockToggleRack().setUnlocalizedName("togglerack").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		iron_workbench = (new BlockIronWorkbench()).setHardness(3F).setResistance(5F).setUnlocalizedName("iron_workbench").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		diamond_workbench = (new BlockDiamondWorkbench()).setHardness(5F).setResistance(5F).setUnlocalizedName("diamond_workbench").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		ice_maker = (new BlockIceMaker()).setHardness(1.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("ice_maker").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		fire_block = (new BlockElemental(ElementType.FIRE)).setHardness(2.0F).setStepSound(Block.soundTypeMetal).setUnlocalizedName("fire_block").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		water_block = (new BlockElemental(ElementType.WATER)).setHardness(2.0F).setStepSound(Block.soundTypeMetal).setUnlocalizedName("water_block").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		lava_block = (new BlockElemental(ElementType.LAVA)).setHardness(2.0F).setStepSound(Block.soundTypeMetal).setUnlocalizedName("lava_block").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		spike = (new BlockSpike()).setHardness(1.5F).setResistance(10F).setStepSound(Block.soundTypeStone).setUnlocalizedName("spike").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		wooden_sensor = (new BlockSensor(0)).setHardness(0.5F).setResistance(10F).setStepSound(Block.soundTypeStone).setUnlocalizedName("wooden_sensor").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		stone_sensor = (new BlockSensor(1)).setHardness(0.5F).setResistance(10F).setStepSound(Block.soundTypeStone).setUnlocalizedName("stone_sensor").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		iron_sensor = (new BlockSensor(2)).setHardness(0.5F).setResistance(10F).setStepSound(Block.soundTypeStone).setUnlocalizedName("iron_sensor").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		netherrack_sensor = (new BlockSensor(3)).setHardness(0.5F).setResistance(10F).setStepSound(Block.soundTypeStone).setUnlocalizedName("netherrack_sensor").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());

		GameRegistry.registerBlock(wooden_sensor, ItemSensor.class, "wooden_sensor", 0);
		GameRegistry.registerBlock(stone_sensor, ItemSensor.class, "stone_sensor", 1);
		GameRegistry.registerBlock(iron_sensor, ItemSensor.class, "iron_sensor", 2);
		GameRegistry.registerBlock(netherrack_sensor, ItemSensor.class, "netherrack_sensor", 3);
		GameRegistry.registerBlock(spike, "spike");
		GameRegistry.registerBlock(water_block, "water_block");
		GameRegistry.registerBlock(lava_block, "lava_block");
		GameRegistry.registerBlock(fire_block, "fire_block");
		GameRegistry.registerBlock(iron_workbench, "iron_workbench");
		GameRegistry.registerBlock(diamond_workbench, "diamond_workbench");
		GameRegistry.registerBlock(togglerack, "togglerack");
		GameRegistry.registerBlock(ice_maker, "ice_maker");
	}

	@Override
	public void addRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(togglerack, 4), new Object[] { "QQQ", "QAQ", "QQQ", 'A', "dustRedstone", 'Q', Blocks.netherrack }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(iron_workbench), new Object[] { " X ", "X=X", " X ", 'X', "blockIron", '=', Blocks.crafting_table }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(diamond_workbench), new Object[] { " X ", "X=X", " X ", 'X', "blockDiamond", '=', Blocks.crafting_table }));
		workbenches = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ice_maker, 1), new Object[] { "X X", "I I", "XXX", 'X', "cobblestone", 'I', Blocks.snow }));
		IndustryItems.ice.add(RecipeHelper.getLatestIRecipe());

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fire_block, 1), new Object[] { "###", "#!#", "###", '#', "blockIron", '!', Items.flint_and_steel }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(water_block, 1), new Object[] { "###", "#!#", "###", '#', "blockIron", '!', Items.water_bucket }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(lava_block, 1), new Object[] { "###", "#!#", "###", '#', "blockIron", '!', Items.lava_bucket }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(spike, 8), new Object[] { "X X", " X ", "XXX", 'X', "ingotIron" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wooden_sensor), new Object[] { "IGI", "XYX", "IXI", 'X', "plankWood", 'Y', "dustRedstone", 'G', "blockGlass", 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stone_sensor), new Object[] { "IGI", "XYX", "IXI", 'X', "stone", 'Y', "dustRedstone", 'G', "blockGlass", 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(iron_sensor), new Object[] { "XGX", "XYX", "XXX", 'X', "ingotIron", 'Y', "dustRedstone", 'G', "blockGlass" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(netherrack_sensor), new Object[] { "IGI", "XYX", "IXI", 'X', Blocks.netherrack, 'Y', "dustRedstone", 'G', "blockGlass", 'I', "ingotIron" }));
		sensors = RecipeHelper.getLatestIRecipes(4);
	}

}
