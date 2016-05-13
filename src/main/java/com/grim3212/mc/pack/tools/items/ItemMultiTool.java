package com.grim3212.mc.pack.tools.items;

import java.util.HashSet;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.grim3212.mc.pack.tools.GrimTools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ItemMultiTool extends ItemTool {

	private float attackDamage;

	private static HashSet<Block> blocksEffectiveAgainst = Sets.newHashSet(new Block[] { Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow, Blocks.snow_layer, Blocks.clay, Blocks.farmland, Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.cobblestone, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_block, Blocks.diamond_ore, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.brick_stairs,
			Blocks.nether_brick_stairs, Blocks.sandstone_stairs, Blocks.birch_stairs, Blocks.jungle_stairs, Blocks.spruce_stairs, Blocks.stone_slab, Blocks.obsidian, Blocks.stone_stairs, Blocks.quartz_stairs, Blocks.stone_brick_stairs, Blocks.dark_oak_stairs, Blocks.acacia_stairs });

	protected ItemMultiTool(ToolMaterial toolMaterial) {
		super(4, toolMaterial, blocksEffectiveAgainst);
		this.setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		this.attackDamage = 4.0F + toolMaterial.getDamageVsEntity();
	}

	@Override
	public float getStrVsBlock(ItemStack itemstack, Block block) {
		if (block == Blocks.web || block == Blocks.leaves || block == Blocks.leaves2) {
			return 15F;
		}
		if (block == Blocks.wool) {
			return 5F;
		} else {
			return super.getStrVsBlock(itemstack, block);
		}
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1) {
		itemstack.damageItem(1, entityliving1);
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
		if (blockIn == Blocks.leaves || blockIn == Blocks.web || blockIn == Blocks.leaves2) {
			stack.damageItem(1, playerIn);
		}
		stack.damageItem(1, playerIn);
		return true;
	}

	@Override
	public boolean canHarvestBlock(Block block, ItemStack itemStack) {
		if (block == Blocks.web) {
			return true;
		}
		if (block == Blocks.snow) {
			return true;
		}
		if (block == Blocks.obsidian) {
			return toolMaterial.getHarvestLevel() == 3;
		}
		if (block == Blocks.diamond_block || block == Blocks.diamond_ore) {
			return toolMaterial.getHarvestLevel() >= 2;
		}
		if (block == Blocks.gold_block || block == Blocks.gold_ore) {
			return toolMaterial.getHarvestLevel() >= 2;
		}
		if (block == Blocks.iron_block || block == Blocks.iron_ore) {
			return toolMaterial.getHarvestLevel() >= 1;
		}
		if (block == Blocks.lapis_block || block == Blocks.lapis_ore) {
			return toolMaterial.getHarvestLevel() >= 1;
		}
		if (block == Blocks.redstone_ore || block == Blocks.lit_redstone_ore) {
			return toolMaterial.getHarvestLevel() >= 2;
		}
		if (block == Blocks.emerald_block || block == Blocks.emerald_ore) {
			return toolMaterial.getHarvestLevel() >= 2;
		}
		if (block.getMaterial() == Material.rock) {
			return true;
		} else {
			return block.getMaterial() == Material.iron;
		}
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!playerIn.canPlayerEdit(pos.offset(side), side, stack)) {
			return false;
		} else {
			int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(stack, playerIn, worldIn, pos);
			if (hook != 0)
				return hook > 0;

			IBlockState iblockstate = worldIn.getBlockState(pos);
			Block block = iblockstate.getBlock();

			if (side != EnumFacing.DOWN && worldIn.isAirBlock(pos.up())) {
				if (block == Blocks.grass) {
					return this.useHoe(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState());
				}

				if (block == Blocks.dirt) {
					switch ((BlockDirt.DirtType) iblockstate.getValue(BlockDirt.VARIANT)) {
					case DIRT:
						return this.useHoe(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState());
					case COARSE_DIRT:
						return this.useHoe(stack, playerIn, worldIn, pos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
					default:
						break;
					}
				}
			}

			return false;
		}
	}

	protected boolean useHoe(ItemStack stack, EntityPlayer player, World worldIn, BlockPos target, IBlockState newState) {
		worldIn.playSoundEffect((double) ((float) target.getX() + 0.5F), (double) ((float) target.getY() + 0.5F), (double) ((float) target.getZ() + 0.5F), newState.getBlock().stepSound.getStepSound(), (newState.getBlock().stepSound.getVolume() + 1.0F) / 2.0F, newState.getBlock().stepSound.getFrequency() * 0.8F);

		if (worldIn.isRemote) {
			return true;
		} else {
			worldIn.setBlockState(target, newState);
			stack.damageItem(1, player);
			return true;
		}
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.BLOCK;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
		return itemstack;
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
	public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", (double) this.attackDamage, 0));
		return multimap;
	}
}