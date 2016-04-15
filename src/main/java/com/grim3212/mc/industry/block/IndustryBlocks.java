package com.grim3212.mc.industry.block;

import java.util.List;

import com.grim3212.mc.core.part.IPartItems;
import com.grim3212.mc.core.util.RecipeHelper;
import com.grim3212.mc.industry.GrimIndustry;
import com.grim3212.mc.industry.block.BlockElemental.ElementType;
import com.grim3212.mc.industry.item.IndustryItems;
import com.grim3212.mc.industry.item.ItemSensor;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
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
	public static Block attractor;
	public static Block repulsor;
	public static Block gravitor;
	public static Block direction_attractor;
	public static Block direction_repulsor;
	public static Block direction_gravitor;
	public static Block reactor;
	public static Block bomb_shell;
	public static Block c4;
	public static Block nuclear_bomb;
	public static Block uranium_ore;

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
		attractor = (new BlockGravity(-1)).setHardness(0.3F).setUnlocalizedName("attractor");
		repulsor = (new BlockGravity(1)).setHardness(0.3F).setUnlocalizedName("repulsor");
		gravitor = (new BlockGravity(0)).setHardness(0.3F).setUnlocalizedName("gravitor");
		direction_attractor = (new BlockDGravity(-1)).setHardness(0.3F).setUnlocalizedName("direction_attractor");
		direction_repulsor = (new BlockDGravity(1)).setHardness(0.3F).setUnlocalizedName("direction_repulsor");
		direction_gravitor = (new BlockDGravity(0)).setHardness(0.3F).setUnlocalizedName("direction_gravitor");
		nuclear_bomb = (new BlockNuclearBomb()).setHardness(1.0F).setStepSound(Block.soundTypeMetal).setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab()).setUnlocalizedName("nuclear_bomb");
		reactor = (new BlockNuclearReactor()).setHardness(1.0F).setStepSound(Block.soundTypeMetal).setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab()).setUnlocalizedName("reactor");
		bomb_shell = (new Block(Material.iron)).setHardness(1.0F).setStepSound(Block.soundTypeMetal).setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab()).setUnlocalizedName("bomb_shell");
		c4 = (new BlockC4()).setHardness(1.0F).setStepSound(Block.soundTypeMetal).setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab()).setUnlocalizedName("c4");
		uranium_ore = (new BlockUraniumOre()).setHardness(1.0F).setStepSound(Block.soundTypeStone).setLightLevel(0.55F).setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab()).setUnlocalizedName("uranium_ore");

		GameRegistry.registerBlock(attractor, "attractor");
		GameRegistry.registerBlock(repulsor, "repulsor");
		GameRegistry.registerBlock(gravitor, "gravitor");
		GameRegistry.registerBlock(direction_attractor, "direction_attractor");
		GameRegistry.registerBlock(direction_repulsor, "direction_repulsor");
		GameRegistry.registerBlock(direction_gravitor, "direction_gravitor");
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
		GameRegistry.registerBlock(uranium_ore, "uranium_ore");
		GameRegistry.registerBlock(bomb_shell, "bomb_shell");
		GameRegistry.registerBlock(c4, "c4");
		GameRegistry.registerBlock(nuclear_bomb, "nuclear_bomb");
		GameRegistry.registerBlock(reactor, "reactor");
	}

	public static List<IRecipe> sensors;
	public static List<IRecipe> workbenches;
	public static List<IRecipe> attracting;
	public static List<IRecipe> repulsing;
	public static List<IRecipe> gravitoring;

	@Override
	public void addRecipes() {
		OreDictionary.registerOre("oreUranium", uranium_ore);

		GameRegistry.addSmelting(uranium_ore, new ItemStack(IndustryItems.uranium_ingot), 0.7F);

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

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(attractor, 1), new Object[] { "SZS", "ZRZ", "SZS", 'S', "ingotIron", 'R', Items.compass, 'Z', "dustRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(direction_attractor, 1), new Object[] { "SZS", "ZRZ", 'S', "ingotIron", 'R', Items.compass, 'Z', "dustRedstone" }));
		attracting = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(repulsor, 1), new Object[] { "ZSZ", "SRS", "ZSZ", 'Z', "dustRedstone", 'S', "ingotIron", 'R', Items.compass }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(direction_repulsor, 1), new Object[] { "ZSZ", "SRS", 'Z', "dustRedstone", 'S', "ingotIron", 'R', Items.compass }));
		repulsing = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(gravitor, 1), new Object[] { "S S", " R ", "SZS", 'S', "ingotIron", 'R', Items.compass, 'Z', "dustRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(direction_gravitor, 1), new Object[] { " R ", "SZS", 'S', "ingotIron", 'R', Items.compass, 'Z', "dustRedstone" }));
		gravitoring = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(uranium_ore, 1), new Object[] { "RDR", "DGD", "RDR", 'R', "dustRedstone", 'G', "glowstone", 'D', "gemDiamond" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(reactor, 1), new Object[] { "GMG", "CRC", "GMG", 'G', IndustryItems.graphite, 'M', IndustryItems.iron_parts, 'C', IndustryItems.reactor_core_case, 'R', IndustryItems.reactor_core }));

		// No possible way to obtain aluminum
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bomb_shell, 1), new Object[] { "A A", " A ", "A A", 'A', "ingotAluminum" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(c4, 1), new Object[] { "GGG", "RRR", "GGG", 'G', Items.gunpowder, 'R', "dustRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(nuclear_bomb, 1), new Object[] { "BCB", "CRC", "BCB", 'B', bomb_shell, 'C', c4, 'R', reactor }));
	}

}
