package com.grim3212.mc.cuisine.block;

import com.grim3212.mc.cuisine.item.CuisineItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockButterchurn extends Block {

	public static final PropertyInteger ACTIVE = PropertyInteger.create("active", 0, 1);

	protected BlockButterchurn() {
		super(Material.wood);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVE, 0));
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		for (int count = 0; count < OreDictionary.getOres("bowlMilk").size(); count++) {
			if (playerIn.getCurrentEquippedItem() == null) {
				return false;
			} else if (playerIn.getHeldItem().getItem() == OreDictionary.getOres("bowlMilk").get(count).getItem()) {
				if (((Integer) state.getValue(ACTIVE)) == 0) {
					worldIn.setBlockState(pos, state.cycleProperty(ACTIVE), 4);
					playerIn.inventory.consumeInventoryItem(playerIn.getHeldItem().getItem());
					playerIn.inventory.addItemStackToInventory(new ItemStack(Items.bowl));
				}
			}
		}
		return true;
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		if (!worldIn.isRemote) {
			if (((Integer) worldIn.getBlockState(pos).getValue(ACTIVE)) == 1) {
				worldIn.setBlockState(pos, this.getDefaultState());
				float f = 0.7F;
				double d = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
				double d1 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.20000000000000001D + 0.59999999999999998D;
				double d2 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;

				int amount = worldIn.rand.nextInt(4);
				if (amount == 0) {
					amount = 1;
				}

				EntityItem entityitem = new EntityItem(worldIn, (double) pos.getX() + d, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(CuisineItems.butter, amount));
				entityitem.setPickupDelay(10);
				worldIn.spawnEntityInWorld(entityitem);
			}
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ACTIVE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((Integer) state.getValue(ACTIVE));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { ACTIVE });
	}
}
