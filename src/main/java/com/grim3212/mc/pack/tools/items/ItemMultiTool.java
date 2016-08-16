package com.grim3212.mc.pack.tools.items;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.tools.client.ManualTools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ItemMultiTool extends ItemTool implements IManualItem {

	private float attackDamage;

	private static HashSet<Block> blocksEffectiveAgainst = Sets.newHashSet(new Block[] { Blocks.GRASS, Blocks.DIRT, Blocks.SAND, Blocks.GRAVEL, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.CLAY, Blocks.FARMLAND, Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.COBBLESTONE, Blocks.STONE, Blocks.SANDSTONE, Blocks.MOSSY_COBBLESTONE, Blocks.IRON_ORE, Blocks.IRON_BLOCK, Blocks.COAL_ORE, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.ICE, Blocks.NETHERRACK, Blocks.LAPIS_ORE, Blocks.LAPIS_BLOCK, Blocks.BRICK_STAIRS,
			Blocks.NETHER_BRICK_STAIRS, Blocks.SANDSTONE_STAIRS, Blocks.BIRCH_STAIRS, Blocks.JUNGLE_STAIRS, Blocks.STONE_STAIRS, Blocks.STONE_SLAB, Blocks.OBSIDIAN, Blocks.STONE_STAIRS, Blocks.QUARTZ_STAIRS, Blocks.STONE_BRICK_STAIRS, Blocks.DARK_OAK_STAIRS, Blocks.ACACIA_STAIRS });

	protected ItemMultiTool(ToolMaterial toolMaterial) {
		super(4.0f + toolMaterial.getDamageVsEntity(), -2.8f, toolMaterial, blocksEffectiveAgainst);
		this.setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.multiTool_page;
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state) {
		if (state.getBlock() == Blocks.WEB || state.getBlock() == Blocks.LEAVES || state.getBlock() == Blocks.LEAVES2) {
			return 15F;
		}
		if (state.getBlock() == Blocks.WOOL) {
			return 5F;
		}

		return super.getStrVsBlock(stack, state);
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1) {
		itemstack.damageItem(1, entityliving1);
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		if (state.getBlock() == Blocks.LEAVES || state.getBlock() == Blocks.WEB || state.getBlock() == Blocks.LEAVES2) {
			stack.damageItem(1, entityLiving);
		}
		stack.damageItem(1, entityLiving);
		return true;
	}

	@Override
	public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
		Block block = state.getBlock();

		if (block == Blocks.WEB) {
			return true;
		}
		if (block == Blocks.SNOW) {
			return true;
		}
		if (block == Blocks.OBSIDIAN) {
			return toolMaterial.getHarvestLevel() == 3;
		}
		if (block == Blocks.DIAMOND_BLOCK || block == Blocks.DIAMOND_ORE) {
			return toolMaterial.getHarvestLevel() >= 2;
		}
		if (block == Blocks.GOLD_BLOCK || block == Blocks.GOLD_ORE) {
			return toolMaterial.getHarvestLevel() >= 2;
		}
		if (block == Blocks.IRON_BLOCK || block == Blocks.IRON_ORE) {
			return toolMaterial.getHarvestLevel() >= 1;
		}
		if (block == Blocks.LAPIS_BLOCK || block == Blocks.LAPIS_ORE) {
			return toolMaterial.getHarvestLevel() >= 1;
		}
		if (block == Blocks.REDSTONE_ORE || block == Blocks.LIT_REDSTONE_ORE) {
			return toolMaterial.getHarvestLevel() >= 2;
		}
		if (block == Blocks.EMERALD_BLOCK || block == Blocks.EMERALD_ORE) {
			return toolMaterial.getHarvestLevel() >= 2;
		}
		if (state.getMaterial() == Material.ROCK) {
			return true;
		} else {
			return state.getMaterial() == Material.IRON;
		}
	}

	@Override
	@SuppressWarnings("incomplete-switch")
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack)) {
			return EnumActionResult.FAIL;
		} else {
			int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(stack, playerIn, worldIn, pos);
			if (hook != 0)
				return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;

			IBlockState iblockstate = worldIn.getBlockState(pos);
			Block block = iblockstate.getBlock();

			if (facing != EnumFacing.DOWN && worldIn.isAirBlock(pos.up())) {
				if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) {
					this.setBlock(stack, playerIn, worldIn, pos, Blocks.FARMLAND.getDefaultState());
					return EnumActionResult.SUCCESS;
				}

				if (block == Blocks.DIRT) {
					switch ((BlockDirt.DirtType) iblockstate.getValue(BlockDirt.VARIANT)) {
					case DIRT:
						this.setBlock(stack, playerIn, worldIn, pos, Blocks.FARMLAND.getDefaultState());
						return EnumActionResult.SUCCESS;
					case COARSE_DIRT:
						this.setBlock(stack, playerIn, worldIn, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
						return EnumActionResult.SUCCESS;
					}
				}
			}

			return EnumActionResult.PASS;
		}
	}

	protected void setBlock(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, IBlockState state) {
		worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

		if (!worldIn.isRemote) {
			worldIn.setBlockState(pos, state, 11);
			stack.damageItem(1, player);
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		ItemStack mat = this.toolMaterial.getRepairItemStack();
		if (mat != null && OreDictionary.itemMatches(mat, repair, false))
			return true;
		return super.getIsRepairable(toRepair, repair);
	}

	@Override
	public int getItemEnchantability() {
		return toolMaterial.getEnchantability();
	}

	@Override
	public Set<String> getToolClasses(ItemStack stack) {
		return ImmutableSet.<String> of("pickaxe", "shovel", "axe");
	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double) this.attackDamage, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.8000000953674316D, 0));
		}

		return multimap;
	}
}