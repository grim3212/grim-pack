package com.grim3212.mc.pack.industry.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.item.IndustryItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.IStringSerializable;

public class MachineRecipes {

	public static final MachineRecipes INSTANCE = new MachineRecipes();
	private List<Triple<ItemStack, ItemStack, Float>> modernFurnaceRecipes = new ArrayList<Triple<ItemStack, ItemStack, Float>>();
	private List<Triple<ItemStack, ItemStack, Float>> derrickRecipes = new ArrayList<Triple<ItemStack, ItemStack, Float>>();
	private List<Triple<ItemStack, ItemStack, Float>> refineryRecipes = new ArrayList<Triple<ItemStack, ItemStack, Float>>();

	private MachineRecipes() {
		// Modern furnace recipes
		addRecipeForBlock(Blocks.GLASS, new ItemStack(IndustryBlocks.tempered_glass), 0.25f, MachineType.MODERN_FURNACE);
		addRecipeForBlock(Blocks.CLAY, new ItemStack(IndustryBlocks.modern_tile), 0.25f, MachineType.MODERN_FURNACE);
		
		//Minecraft furnace transmutations
		addRecipeForBlock(Blocks.BRICK_BLOCK, new ItemStack(Blocks.NETHERRACK), 0.25f, MachineType.MODERN_FURNACE);
		addRecipeForBlock(Blocks.MYCELIUM, new ItemStack(Blocks.END_STONE), 0.25f, MachineType.MODERN_FURNACE);
		addRecipeForBlock(Blocks.MOSSY_COBBLESTONE, new ItemStack(Blocks.PRISMARINE), 0.25f, MachineType.MODERN_FURNACE);
		addRecipeForBlock(Blocks.GLOWSTONE, new ItemStack(Blocks.SEA_LANTERN), 0.25f, MachineType.MODERN_FURNACE);
		addRecipeForBlock(Blocks.CACTUS, new ItemStack(Blocks.MAGMA), 0.25f, MachineType.MODERN_FURNACE);
		addRecipeForBlock(Blocks.RED_MUSHROOM_BLOCK, new ItemStack(Blocks.NETHER_WART_BLOCK), 0.25f, MachineType.MODERN_FURNACE);
		
		// Add vanilla furnace recipes to modern furnace
		for (Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet())
			addModernFurnaceRecipe(entry.getKey(), entry.getValue(), FurnaceRecipes.instance().getSmeltingExperience(entry.getValue()));

		// Derrick recipes
		addRecipe(IndustryItems.aluminum_can, new ItemStack(IndustryItems.crude_oil), 0.1f, MachineType.DERRICK);

		// Refinery recipes
		addRecipe(IndustryItems.crude_oil, new ItemStack(IndustryItems.fuel), 0.25f, MachineType.REFINERY);
		addRecipe(IndustryItems.super_crude_oil, new ItemStack(IndustryItems.crude_oil), 0.1f, MachineType.REFINERY);
		addRecipeForBlock(Blocks.LOG, new ItemStack(IndustryItems.rubber), 0.1f, MachineType.REFINERY);
		addRecipeForBlock(Blocks.LOG2, new ItemStack(IndustryItems.rubber), 0.1f, MachineType.REFINERY);
		addRecipeForBlock(Blocks.DIRT, new ItemStack(Items.CLAY_BALL), 0.25f, MachineType.REFINERY);
		
		//Refinery Transmutations
		addRecipe(Items.ROTTEN_FLESH, new ItemStack(Items.LEATHER), 0.25f, MachineType.REFINERY);
		addRecipe(Items.POISONOUS_POTATO, new ItemStack(Items.POTATO), 0.25f, MachineType.REFINERY);
		addRecipe(Items.SPIDER_EYE, new ItemStack(Items.NETHER_WART), 0.25f, MachineType.REFINERY);
		addRecipe(Items.PAPER, new ItemStack(Items.PAINTING), 0.25f, MachineType.REFINERY);
		addRecipe(Items.EGG, new ItemStack(Items.FEATHER), 0.25f, MachineType.REFINERY);
		addRecipe(Items.WATER_BUCKET, new ItemStack(Items.FISH), 0.25f, MachineType.REFINERY);
		addRecipe(Items.LAVA_BUCKET, new ItemStack(Items.FIRE_CHARGE), 0.25f, MachineType.REFINERY);
		addRecipe(Items.RABBIT_FOOT, new ItemStack(Items.NAME_TAG), 0.25f, MachineType.REFINERY);
		addRecipe(Items.CARROT_ON_A_STICK, new ItemStack(Items.LEAD), 0.25f, MachineType.REFINERY);
		//addRecipe(Items.BED, new ItemStack(Items.SADDLE), 0.25f, MachineType.REFINERY);
		//addRecipe(Items.EMERALD, new ItemStack(Items.GHAST_TEAR), 0.25f, MachineType.REFINERY);
		addRecipe(Items.GUNPOWDER, new ItemStack(Items.BLAZE_POWDER), 0.25f, MachineType.REFINERY);
		addRecipeForBlock(Blocks.VINE, new ItemStack(Items.STRING), 0.25f, MachineType.REFINERY);
		addRecipe(Items.ENDER_PEARL, new ItemStack(Items.ENDER_EYE), 0.25f, MachineType.REFINERY);
	}

	/**
	 * 
	 * @param input
	 *            Recipe input
	 * @param output
	 *            Recipe output
	 * @param experience
	 *            The experience when finished
	 * @param recipeList
	 *            0 = modern furnace, 1 = derrick, 2 = refinery
	 */
	public void addRecipeForBlock(Block input, ItemStack output, float experience, MachineType type) {
		this.addRecipe(Item.getItemFromBlock(input), output, experience, type);
	}

	/**
	 * 
	 * @param input
	 *            Recipe in
	 * @param output
	 *            Recipe out
	 * @param experience
	 *            The experience when finished
	 * @param recipeList
	 *            0 = modern furnace, 1 = derrick, 2 = refinery
	 */
	public void addRecipe(Item input, ItemStack output, float experience, MachineType type) {
		if (type == MachineType.MODERN_FURNACE)
			this.addModernFurnaceRecipe(new ItemStack(input, 1, 32767), output, experience);
		else if (type == MachineType.DERRICK)
			this.addDerrickRecipe(new ItemStack(input, 1, 32767), output, experience);
		else if (type == MachineType.REFINERY)
			this.addRefineryRecipe(new ItemStack(input, 1, 32767), output, experience);
	}

	public void addModernFurnaceRecipe(ItemStack input, ItemStack stack, float experience) {
		if (getResult(input, MachineType.MODERN_FURNACE) != null)
			return;

		this.getModernFurnaceList().add(new ImmutableTriple<ItemStack, ItemStack, Float>(input, stack, experience));
	}

	public void addDerrickRecipe(ItemStack input, ItemStack stack, float experience) {
		if (getResult(input, MachineType.DERRICK) != null)
			return;

		this.getDerrickList().add(new ImmutableTriple<ItemStack, ItemStack, Float>(input, stack, experience));
	}

	public void addRefineryRecipe(ItemStack input, ItemStack stack, float experience) {
		if (getResult(input, MachineType.REFINERY) != null)
			return;

		this.getRefineryList().add(new ImmutableTriple<ItemStack, ItemStack, Float>(input, stack, experience));
	}

	public ItemStack getResult(ItemStack stack, MachineType type) {
		List<Triple<ItemStack, ItemStack, Float>> recipeList = null;

		if (type == MachineType.MODERN_FURNACE) {
			recipeList = this.getModernFurnaceList();
		} else if (type == MachineType.DERRICK) {
			recipeList = this.getDerrickList();
		} else if (type == MachineType.REFINERY) {
			recipeList = this.getRefineryList();
		}

		if (recipeList != null) {
			for (Triple<ItemStack, ItemStack, Float> triple : recipeList) {
				if (this.compareItemStacks(stack, triple.getLeft())) {
					return triple.getMiddle();
				}
			}
		}

		return null;
	}

	/**
	 * Compares two itemstacks to ensure that they are the same. This checks
	 * both the item and the metadata of the item.
	 */
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}

	public List<Triple<ItemStack, ItemStack, Float>> getModernFurnaceList() {
		return this.modernFurnaceRecipes;
	}

	public List<Triple<ItemStack, ItemStack, Float>> getDerrickList() {
		return this.derrickRecipes;
	}

	public List<Triple<ItemStack, ItemStack, Float>> getRefineryList() {
		return this.refineryRecipes;
	}

	public float getSmeltingExperience(ItemStack stack, List<Triple<ItemStack, ItemStack, Float>> recipeList) {
		float ret = stack.getItem().getSmeltingExperience(stack);
		if (ret != -1)
			return ret;

		for (Triple<ItemStack, ItemStack, Float> triple : recipeList) {
			if (this.compareItemStacks(stack, triple.getMiddle())) {
				return triple.getRight();
			}
		}

		return 0.0F;
	}

	public enum MachineType  implements IStringSerializable {
		MODERN_FURNACE, DERRICK, REFINERY;

		public static final MachineType values[] = MachineType.values();
		
		@Override
		public String getName() {
			return this.name();
		}
	}
}
