package com.grim3212.mc.pack.compat.jei;

import javax.annotation.Nonnull;

import com.grim3212.mc.pack.compat.jei.recipes.backpacks.BackpackRecipeMaker;
import com.grim3212.mc.pack.compat.jei.recipes.chisel.ChiselRecipeCategory;
import com.grim3212.mc.pack.compat.jei.recipes.chisel.ChiselRecipeMaker;
import com.grim3212.mc.pack.compat.jei.recipes.grill.GrillRecipeCategory;
import com.grim3212.mc.pack.compat.jei.recipes.grill.GrillRecipeMaker;
import com.grim3212.mc.pack.compat.jei.recipes.machines.MachineRecipeCategory;
import com.grim3212.mc.pack.compat.jei.recipes.machines.MachineRecipeMaker;
import com.grim3212.mc.pack.compat.jei.recipes.machines.mfurnace.MFurnaceFuelCategory;
import com.grim3212.mc.pack.compat.jei.recipes.machines.mfurnace.MFurnaceFuelRecipeMaker;
import com.grim3212.mc.pack.compat.jei.recipes.machines.mfurnace.MFurnaceSmeltingCategory;
import com.grim3212.mc.pack.compat.jei.recipes.pelletbags.PelletBagRecipeMaker;
import com.grim3212.mc.pack.compat.jei.transfers.DiamondWorkbenchTransferInfo;
import com.grim3212.mc.pack.compat.jei.transfers.IronWorkbenchTransferInfo;
import com.grim3212.mc.pack.compat.jei.transfers.PortableWorkbenchTransferInfo;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.client.gui.GuiGrill;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.inventory.ContainerGrill;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.client.gui.GuiDerrick;
import com.grim3212.mc.pack.industry.client.gui.GuiMFurnace;
import com.grim3212.mc.pack.industry.client.gui.GuiRefinery;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.inventory.ContainerDerrick;
import com.grim3212.mc.pack.industry.inventory.ContainerMFurnace;
import com.grim3212.mc.pack.industry.inventory.ContainerRefinery;
import com.grim3212.mc.pack.industry.item.IndustryItems;
import com.grim3212.mc.pack.industry.util.MachineRecipes.MachineType;
import com.grim3212.mc.pack.tools.config.ToolsConfig;
import com.grim3212.mc.pack.tools.items.ToolsItems;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

@mezz.jei.api.JEIPlugin
public class JEIGrimPack implements IModPlugin {

	private static IJeiRuntime jeiRuntime;
	private static IJeiHelpers jeiHelpers;
	private static IIngredientRegistry ingredientRegistry;

	@Override
	public void register(@Nonnull IModRegistry registry) {
		JEIGrimPack.ingredientRegistry = registry.getIngredientRegistry();
		final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();

		if (CoreConfig.useDecor) {
			if (DecorConfig.subpartColorizer && DecorConfig.subpartFireplaces) {
				registry.addRecipes(GrillRecipeMaker.getGrillRecipes(jeiHelpers), GrillRecipeCategory.UID);
				registry.addRecipeClickArea(GuiGrill.class, 117, 36, 16, 16, GrillRecipeCategory.UID);
				recipeTransferRegistry.addRecipeTransferHandler(ContainerGrill.class, GrillRecipeCategory.UID, 0, 4, 5, 36);
				registry.addRecipeCatalyst(new ItemStack(DecorBlocks.grill), GrillRecipeCategory.UID);
			}
		}

		if (CoreConfig.useIndustry) {
			if (IndustryConfig.subpartWorkbenchUpgrades) {
				// crafting table shiftclicking
				registry.getRecipeTransferRegistry().addRecipeTransferHandler(new IronWorkbenchTransferInfo());
				registry.getRecipeTransferRegistry().addRecipeTransferHandler(new DiamondWorkbenchTransferInfo());

				// add our crafting table to the list with the vanilla crafting
				// table
				registry.addRecipeCatalyst(new ItemStack(IndustryBlocks.iron_workbench, 1), VanillaRecipeCategoryUid.CRAFTING);
				registry.addRecipeCatalyst(new ItemStack(IndustryBlocks.diamond_workbench, 1), VanillaRecipeCategoryUid.CRAFTING);
				registry.addRecipeCatalyst(new ItemStack(IndustryItems.portable_iron_workbench, 1), VanillaRecipeCategoryUid.CRAFTING);
				registry.addRecipeCatalyst(new ItemStack(IndustryItems.portable_diamond_workbench, 1), VanillaRecipeCategoryUid.CRAFTING);
			}

			if (IndustryConfig.subpartMachines) {
				registry.addRecipes(MFurnaceFuelRecipeMaker.getFuelRecipes(jeiHelpers), MFurnaceFuelCategory.UID);
				registry.addRecipes(MachineRecipeMaker.getMachineRecipes(jeiHelpers, MachineType.MODERN_FURNACE), MFurnaceSmeltingCategory.UID);
				registry.addRecipeClickArea(GuiMFurnace.class, 78, 32, 28, 23, MFurnaceSmeltingCategory.UID, MFurnaceFuelCategory.UID);
				recipeTransferRegistry.addRecipeTransferHandler(ContainerMFurnace.class, MFurnaceSmeltingCategory.UID, 0, 1, 3, 36);
				recipeTransferRegistry.addRecipeTransferHandler(ContainerMFurnace.class, MFurnaceFuelCategory.UID, 1, 1, 3, 36);
				registry.addRecipeCatalyst(new ItemStack(IndustryBlocks.modern_furnace), MFurnaceSmeltingCategory.UID, MFurnaceFuelCategory.UID);

				registry.addRecipes(MachineRecipeMaker.getMachineRecipes(jeiHelpers, MachineType.REFINERY), MachineRecipeCategory.REFINERY_UID);
				registry.addRecipeClickArea(GuiRefinery.class, 78, 32, 28, 23, MachineRecipeCategory.REFINERY_UID);
				recipeTransferRegistry.addRecipeTransferHandler(ContainerRefinery.class, MachineRecipeCategory.REFINERY_UID, 0, 1, 2, 36);
				registry.addRecipeCatalyst(new ItemStack(IndustryBlocks.refinery), MachineRecipeCategory.REFINERY_UID);

				registry.addRecipes(MachineRecipeMaker.getMachineRecipes(jeiHelpers, MachineType.DERRICK), MachineRecipeCategory.DERRICK_UID);
				registry.addRecipeClickArea(GuiDerrick.class, 78, 32, 28, 23, MachineRecipeCategory.DERRICK_UID);
				recipeTransferRegistry.addRecipeTransferHandler(ContainerDerrick.class, MachineRecipeCategory.DERRICK_UID, 0, 1, 2, 36);
				registry.addRecipeCatalyst(new ItemStack(IndustryBlocks.derrick), MachineRecipeCategory.DERRICK_UID);
			}
		}

		if (CoreConfig.useTools) {
			if (ToolsConfig.subpartBackpacks)
				// Backpack recipe
				registry.addRecipes(BackpackRecipeMaker.getBackpackRecipes(), VanillaRecipeCategoryUid.CRAFTING);

			if (ToolsConfig.subpartSlingshots)
				// Pellet Bag recipe
				registry.addRecipes(PelletBagRecipeMaker.getPelletBagRecipes(), VanillaRecipeCategoryUid.CRAFTING);

			if (ToolsConfig.subpartPortableWorkbench) {
				// portable workbench shiftclicking
				registry.getRecipeTransferRegistry().addRecipeTransferHandler(new PortableWorkbenchTransferInfo());

				// add our portable workbench to the list with the vanilla
				// crafting
				registry.addRecipeCatalyst(new ItemStack(ToolsItems.portable_workbench, 1), VanillaRecipeCategoryUid.CRAFTING);
			}

			if (ToolsConfig.subpartChisel) {
				registry.addRecipes(ChiselRecipeMaker.getChiselRecipes(jeiHelpers), ChiselRecipeCategory.UID);
				registry.addRecipeCatalyst(new ItemStack(ToolsItems.iron_chisel), ChiselRecipeCategory.UID);
				registry.addRecipeCatalyst(new ItemStack(ToolsItems.diamond_chisel), ChiselRecipeCategory.UID);
			}
		}
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		JEIGrimPack.jeiHelpers = registry.getJeiHelpers();
		final IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();

		if (CoreConfig.useDecor) {
			if (DecorConfig.subpartColorizer && DecorConfig.subpartFireplaces) {
				registry.addRecipeCategories(new GrillRecipeCategory(guiHelper));
			}
		}

		if (CoreConfig.useIndustry) {
			if (IndustryConfig.subpartMachines) {
				registry.addRecipeCategories(new MFurnaceFuelCategory(guiHelper), new MFurnaceSmeltingCategory(guiHelper), new MachineRecipeCategory(guiHelper, MachineType.DERRICK), new MachineRecipeCategory(guiHelper, MachineType.REFINERY));
			}
		}

		if (CoreConfig.useTools) {
			if (ToolsConfig.subpartChisel) {
				registry.addRecipeCategories(new ChiselRecipeCategory(guiHelper));
			}
		}
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
		if (CoreConfig.useTools) {
			if (ToolsConfig.subpartBackpacks)
				subtypeRegistry.useNbtForSubtypes(ToolsItems.backpack);

			if (ToolsConfig.subpartSlingshots)
				subtypeRegistry.useNbtForSubtypes(ToolsItems.pellet_bag);
		}
	}

	@Override
	public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {
		JEIGrimPack.jeiRuntime = jeiRuntime;
	}

	public static IJeiHelpers getJeiHelpers() {
		return jeiHelpers;
	}

	public static IJeiRuntime getJeiRuntime() {
		return jeiRuntime;
	}

	public static IIngredientRegistry getIngredientRegistry() {
		return ingredientRegistry;
	}
}
