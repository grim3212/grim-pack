package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class BlockDrill extends BlockManual {

	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public BlockDrill() {
		super("drill", Material.IRON, SoundType.METAL);
		this.setTickRandomly(true);
		setHardness(1.0F);
		setResistance(200.0F);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	protected IBlockState getState() {
		return this.getBlockState().getBaseState().withProperty(POWERED, false);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.drill_page;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		worldIn.setBlockState(pos, state.cycleProperty(POWERED));
		worldIn.scheduleUpdate(pos, this, 10);
		worldIn.playSound((EntityPlayer) null, pos, state.getValue(POWERED) ? SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON : SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.6F);
		return true;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(POWERED) ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(POWERED, meta == 1);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { POWERED });
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		BlockPos headPos = new BlockPos(pos.getX(), 5, pos.getZ());
		if (worldIn.getBlockState(headPos).getBlock() == IndustryBlocks.drill_head) {
			worldIn.setBlockToAir(headPos);
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (state.getValue(POWERED)) {
			if (rand.nextInt(8) == 0) {
				for (int l = pos.getY() - 1; l >= 4; l--) {
					if (worldIn.getBlockState(new BlockPos(pos.getX(), 5, pos.getZ())).getBlock() == IndustryBlocks.drill_head) {
						for (int t = 5; t <= pos.getY() - 1; t++) {
							BlockPos newPos = new BlockPos(pos.getX(), t + 1, pos.getZ());
							if (worldIn.getBlockState(newPos).getBlock() == IndustryBlocks.drill_head) {
								worldIn.setBlockToAir(newPos);
							}
							newPos = new BlockPos(pos.getX(), t, pos.getZ());
							if (worldIn.getBlockState(newPos).getBlock() == IndustryBlocks.drill_head) {
								worldIn.setBlockToAir(newPos);
							}
						}
						worldIn.setBlockState(pos, IndustryBlocks.drill.getDefaultState());
						break;
					}
					BlockPos airPos = new BlockPos(pos.getX(), l, pos.getZ());
					if (worldIn.isAirBlock(airPos)) {

						worldIn.setBlockState(airPos, IndustryBlocks.drill_head.getDefaultState());
						if (l + 1 != pos.getY()) {
							worldIn.setBlockState(airPos.up(), IndustryBlocks.drill_head.getDefaultState().withProperty(BlockDrillHead.IS_SHAFT, true));
						}
						break;
					}
					if (!worldIn.isAirBlock(airPos) && worldIn.getBlockState(airPos).getBlock() != IndustryBlocks.drill_head) {
						// Drop or add broken blocks to inventory
						drillBlock(worldIn, airPos, pos);

						worldIn.setBlockState(airPos, IndustryBlocks.drill_head.getDefaultState());
						if (l + 1 != pos.getY()) {
							worldIn.setBlockState(airPos.up(), IndustryBlocks.drill_head.getDefaultState().withProperty(BlockDrillHead.IS_SHAFT, true));
						}
						break;
					}
				}
			}

			worldIn.scheduleUpdate(pos, this, rand.nextInt(20));
		}
	}

	/**
	 * Tries to add the mined items to an inventory first If not it drops them
	 * in the world
	 * 
	 * @param worldIn
	 *            World reference
	 * @param pos
	 *            Position of the block being mined
	 * @param drillPos
	 *            Position of the drill
	 */
	private void drillBlock(World worldIn, BlockPos pos, BlockPos drillPos) {
		NonNullList<ItemStack> ret = NonNullList.create();
		worldIn.getBlockState(pos).getBlock().getDrops(ret, worldIn, pos, worldIn.getBlockState(pos), 0);

		TileEntity te = worldIn.getTileEntity(drillPos.up());
		if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)) {
			IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

			for (ItemStack foundStack : ret) {
				ItemStack simulation = ItemHandlerHelper.insertItem(itemHandler, foundStack.copy(), true);

				if ((simulation.isEmpty() || foundStack.getCount() != simulation.getCount())) {

					ItemStack result = ItemHandlerHelper.insertItem(itemHandler, foundStack, false);
					if (!result.isEmpty()) {
						worldIn.spawnEntity(new EntityItem(worldIn, (double) drillPos.getX(), (double) drillPos.up(2).getY(), (double) drillPos.getZ(), result));
					}
				} else {
					worldIn.spawnEntity(new EntityItem(worldIn, (double) drillPos.getX(), (double) drillPos.up(2).getY(), (double) drillPos.getZ(), foundStack));
				}
			}
		} else {
			for (ItemStack foundStack : ret) {
				worldIn.spawnEntity(new EntityItem(worldIn, (double) drillPos.getX(), (double) drillPos.up().getY(), (double) drillPos.getZ(), foundStack));
			}
		}
	}
}
