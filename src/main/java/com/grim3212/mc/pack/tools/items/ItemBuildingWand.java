package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.tools.config.ToolsConfig;
import com.grim3212.mc.pack.tools.util.WandCoord3D;

import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class ItemBuildingWand extends ItemWand {

	private static final int BUILD_BOX = 100;
	private static final int BUILD_ROOM = 10;
	private static final int BUILD_FRAME = 1;
	private static final int BUILD_WATER = 110;
	private static final int BUILD_TORCHES = 101;
	private static final int BUILD_CAVES = 11;
	private static final int BUILD_LAVA = 111;

	public ItemBuildingWand(boolean reinforced) {
		super(reinforced);
		this.setMaxDamage(reinforced ? 200 : 30);
	}

	@Override
	protected boolean canBreak(int keys, World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);

		if (state.getBlock().isReplaceable(worldIn, pos))
			return true;

		if (state.getBlock() == Blocks.AIR || state.getBlock() == Blocks.LEAVES || state.getBlock() == Blocks.LEAVES2 || state.getBlock() == Blocks.SNOW || state.getBlock() == Blocks.FIRE || state.getBlock() == Blocks.VINE || state.getBlock() instanceof BlockCrops || state.getBlock() instanceof BlockFlower) {
			return true;
		}
		switch (keys) {
		case BUILD_BOX:
		case BUILD_ROOM:
		case BUILD_FRAME:
		case BUILD_CAVES:
			return (state.getBlock() instanceof BlockLiquid);
		case BUILD_WATER:
		case BUILD_LAVA:
			return (state.getBlock() == Blocks.TORCH || state.getBlock() instanceof BlockLiquid);
		}
		return false;
	}

	@Override
	protected boolean isTooFar(int range, int maxDiff, int range2D, int keys) {
		switch (keys) {
		case BUILD_BOX:
		case BUILD_FRAME:
		case BUILD_ROOM:
		case BUILD_WATER:
		case BUILD_LAVA:
		case BUILD_TORCHES:
			return range - 400 > maxDiff;
		case BUILD_CAVES:
			return range2D - 1600 > maxDiff;
		}
		return true;
	}

	@Override
	protected double[] getParticleColor() {
		return new double[] { 1.0D, 0.8D, 0.0D };
	}

	@Override
	protected boolean isIncompatible(IBlockState state) {
		return state.getBlock() == Blocks.AIR || state.getBlock() == Blocks.PISTON_EXTENSION || state.getBlock() == Blocks.PISTON_HEAD || state.getBlock() instanceof BlockBed || state.getBlock() instanceof BlockDoor || state.getBlock() instanceof BlockSign;
	}

	private boolean canPlace(World world, BlockPos pos, IBlockState state, int keys) {
		if (canBreak(keys, world, pos)) {
			if (state.getBlock().canPlaceBlockAt(world, pos))
				return true;
			if (state.getBlock() == Blocks.CACTUS || state.getBlock() == Blocks.REEDS || state.getBlock() == Blocks.REDSTONE_WIRE || state.getBlock() instanceof BlockPressurePlate || state.getBlock() == Blocks.SNOW) {
				return false;
			}
			if (state.getBlock() instanceof BlockTorch || state.getBlock() instanceof BlockFlower) {
				return false;
			}
			return true;
		}
		return false;
	}

	protected boolean consumeItems(ItemStack neededStack, EntityPlayer entityplayer, int neededItems, WandCoord3D clicked) {
		if (ToolsConfig.ENABLE_free_build_mode || entityplayer.capabilities.isCreativeMode) {
			return true;
		}
		int invItems = 0;
		// count items in inv.
		for (int t = 0; t < entityplayer.inventory.getSizeInventory(); t++) {
			ItemStack currentItem = entityplayer.inventory.getStackInSlot(t);
			if (currentItem != null && currentItem.isItemEqual(neededStack)) {
				invItems += currentItem.stackSize;
				if (invItems == neededItems)
					break; // enough, no need to continue counting.
			}
		}
		if (neededItems > invItems) {
			sendMessage(entityplayer, I18n.format("error.wand.toofewitems") + "(" + I18n.format("error.wand.toofewitems.needed") + " " + neededItems + ", " + I18n.format("error.wand.toofewitems.have") + " " + invItems + ").", false);
			return false; // abort
		}
		// remove blocks from inventory, highest positions first (quickbar last)
		for (int t = entityplayer.inventory.getSizeInventory() - 1; t >= 0; t--) {
			ItemStack currentItem = entityplayer.inventory.getStackInSlot(t);
			if (currentItem != null && currentItem.isItemEqual(neededStack)) {
				int stackSize = currentItem.stackSize;
				if (stackSize < neededItems) {
					entityplayer.inventory.setInventorySlotContents(t, null);
					neededItems -= stackSize;
				} else if (stackSize >= neededItems) {
					entityplayer.inventory.decrStackSize(t, neededItems);
					neededItems = 0;
					break;
				}
			}
		}
		return true;
	}

	private boolean emptyBuckets(EntityPlayer entityplayer, int neededItems, boolean lava) {
		Item vanillaBucket = lava ? Items.LAVA_BUCKET : Items.WATER_BUCKET;

		if (ToolsConfig.ENABLE_free_build_mode || entityplayer.capabilities.isCreativeMode) {
			return true;
		}
		int itemsInInventory = 0;
		for (int t = 0; t < entityplayer.inventory.getSizeInventory(); t++) {
			ItemStack currentItem = entityplayer.inventory.getStackInSlot(t);
			if (currentItem != null) {
				if (currentItem.getItem() == vanillaBucket) {
					itemsInInventory++;
				} else if (currentItem.getItem() instanceof ItemBetterBucket) {
					if (ItemBetterBucket.isEmptyOrContains(currentItem, lava ? "lava" : "water")) {
						itemsInInventory += NBTHelper.getInt(currentItem, "Amount") / Fluid.BUCKET_VOLUME;
					}
				}
			}
		}
		// ? error - not enough items!
		if (itemsInInventory < neededItems) {
			return false;
		}
		// remove buckets from inventory, highest positions first (quickbar
		// last)
		for (int t = entityplayer.inventory.getSizeInventory() - 1; t >= 0; t--) {
			ItemStack currentItem = entityplayer.inventory.getStackInSlot(t);
			if (currentItem != null) {
				if (currentItem.getItem() == vanillaBucket) {
					entityplayer.inventory.setInventorySlotContents(t, null);
					entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.BUCKET));
					if (--neededItems == 0)
						return true;
				} else if (currentItem.getItem() instanceof ItemBetterBucket) {
					if (ItemBetterBucket.isEmptyOrContains(currentItem, lava ? "lava" : "water")) {
						int bucketAmount = NBTHelper.getInt(currentItem, "Amount") / Fluid.BUCKET_VOLUME;
						if (bucketAmount > 0) {
							int amount = neededItems - bucketAmount;

							if (amount >= 0) {
								NBTHelper.setString(currentItem, "FluidName", "empty");
								NBTHelper.setInteger(currentItem, "Amount", 0);
								neededItems -= bucketAmount;
							} else {
								amount *= -1;
								if ((NBTHelper.getInt(currentItem, "Amount") - (amount * Fluid.BUCKET_VOLUME)) <= 0) {
									NBTHelper.setString(currentItem, "FluidName", "empty");
									NBTHelper.setInteger(currentItem, "Amount", 0);
									return true;
								} else {
									NBTHelper.setInteger(currentItem, "Amount", amount * Fluid.BUCKET_VOLUME);
									return true;
								}
							}
						}

						if (neededItems <= 0)
							return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	protected boolean doEffect(World world, EntityPlayer entityplayer, WandCoord3D start, WandCoord3D end, WandCoord3D clicked, int keys, IBlockState state) {
		if (state != this.stateClicked && (keys == BUILD_BOX || keys == BUILD_ROOM || keys == BUILD_FRAME || keys == BUILD_TORCHES)) {
			error(entityplayer, clicked, "notsamecorner");
			return false;
		}
		boolean flag = do_Building(world, start, end, clicked, keys, entityplayer, state);
		if (flag && keys != BUILD_WATER && keys != BUILD_LAVA)
			world.playSound((EntityPlayer) null, clicked.pos, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.BLOCKS, (world.rand.nextFloat() + 0.7F) / 2.0F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4F);
		if (flag && keys == BUILD_WATER)
			world.playSound((EntityPlayer) null, clicked.pos, SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.BLOCKS, (world.rand.nextFloat() + 0.7F) / 2.0F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4F);
		if (flag && keys == BUILD_WATER)
			world.playSound((EntityPlayer) null, clicked.pos, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, (world.rand.nextFloat() + 0.7F) / 2.0F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4F);

		return flag;
	}

	private boolean do_Building(World world, WandCoord3D start, WandCoord3D end, WandCoord3D clicked, int keys, EntityPlayer entityplayer, IBlockState state) {
		int X = 0;
		int Y = 0;
		int Z = 0;
		IBlockState stateAt = Blocks.AIR.getDefaultState();
		ItemStack neededStack = getNeededItem(state);
		int multiplier = getNeededCount(state);
		int neededItems = 0;
		int affected = 0;
		switch (keys) {
		case BUILD_BOX:
			neededItems = 0;

			for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
				for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
					for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
						if (canPlace(world, new BlockPos(X, Y, Z), state, keys)) {
							neededItems += multiplier;
						}
					}
				}

			}

			if (neededItems == 0) {
				if (!world.isRemote)
					error(entityplayer, clicked, "nowork");
				return false;
			}

			if (consumeItems(neededStack, entityplayer, neededItems, clicked)) {
				for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							BlockPos newPos = new BlockPos(X, Y, Z);

							if (canPlace(world, newPos, state, keys)) {
								world.setBlockState(newPos, state, 3);
								if (this.rand.nextInt(neededItems / 50 + 1) == 0)
									particles(world, newPos, 0);
								affected++;
							}
						}
					}
				}

				if ((this.stateOrig.getBlock() == Blocks.GRASS) && (affected > 0)) {
					for (int run = 0; run <= 1; run++) {
						if (run == 0)
							Y = start.pos.getY();
						if (run == 1) {
							Y = end.pos.getY();
						}
						for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
							for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
								BlockPos newPos = new BlockPos(X, Y, Z);

								if ((world.getBlockState(newPos).getBlock() == Blocks.DIRT) && ((world.getBlockState(newPos.up()).getBlock() == null) || (!world.getBlockState(newPos.up()).isOpaqueCube()))) {
									world.setBlockState(newPos, Blocks.GRASS.getDefaultState());
								}
							}
						}
					}
				}

				return affected > 0;
			}
			return false;
		case BUILD_ROOM:
			neededItems = 0;

			for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
				for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
					for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
						if (((X == start.pos.getX()) || (Y == start.pos.getY()) || (Z == start.pos.getZ()) || (X == end.pos.getX()) || (Y == end.pos.getY()) || (Z == end.pos.getZ())) && (canPlace(world, new BlockPos(X, Y, Z), state, keys))) {
							neededItems += multiplier;
						}
					}
				}

			}

			if (neededItems == 0) {
				if (!world.isRemote)
					error(entityplayer, clicked, "nowork");
				return false;
			}

			if (consumeItems(neededStack, entityplayer, neededItems, clicked)) {
				for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							BlockPos newPos = new BlockPos(X, Y, Z);
							if (((X == start.pos.getX()) || (Y == start.pos.getY()) || (Z == start.pos.getZ()) || (X == end.pos.getX()) || (Y == end.pos.getY()) || (Z == end.pos.getZ())) && (canPlace(world, newPos, state, keys))) {
								world.setBlockState(newPos, state, 3);
								if (this.rand.nextInt(neededItems / 50 + 1) == 0)
									particles(world, newPos, 0);
								affected++;
							}
						}
					}

				}

				if ((this.stateOrig == Blocks.GRASS) && (affected > 0)) {
					for (int run = 0; run <= 1; run++) {
						if (run == 0)
							Y = start.pos.getY();
						if (run == 1) {
							Y = end.pos.getY();
						}
						for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
							for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
								BlockPos newPos = new BlockPos(X, Y, Z);

								if ((world.getBlockState(newPos).getBlock() == Blocks.DIRT) && ((world.getBlockState(newPos.up()).getBlock() == null) || (!world.getBlockState(newPos.up()).isOpaqueCube()))) {
									world.setBlockState(newPos, Blocks.GRASS.getDefaultState());
								}
							}
						}
					}
				}
				return affected > 0;
			}
			return false;
		case BUILD_FRAME:
			neededItems = 0;

			for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
				for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
					for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
						if (((X == start.pos.getX()) && (Y == start.pos.getY())) || ((Y == start.pos.getY()) && (Z == start.pos.getZ())) || ((Z == start.pos.getZ()) && (X == start.pos.getX())) || ((X == start.pos.getX()) && (Y == end.pos.getY())) || ((X == end.pos.getX()) && (Y == start.pos.getY())) || ((Y == start.pos.getY()) && (Z == end.pos.getZ())) || ((Y == end.pos.getY()) && (Z == start.pos.getZ())) || ((Z == start.pos.getZ()) && (X == end.pos.getX())) || ((Z == end.pos.getZ()) && (X == start.pos.getX())) || ((X == end.pos.getX()) && (Y == end.pos.getY()))
								|| ((Y == end.pos.getY()) && (Z == end.pos.getZ())) || ((Z == end.pos.getZ()) && (X == end.pos.getX()) && (canPlace(world, new BlockPos(X, Y, Z), state, keys)))) {
							neededItems += multiplier;
						}
					}
				}

			}

			if (neededItems == 0) {
				if (!world.isRemote)
					error(entityplayer, clicked, "nowork");
				return false;
			}

			if (consumeItems(neededStack, entityplayer, neededItems, clicked)) {
				for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							if (((X == start.pos.getX()) && (Y == start.pos.getY())) || ((Y == start.pos.getY()) && (Z == start.pos.getZ())) || ((Z == start.pos.getZ()) && (X == start.pos.getX())) || ((X == start.pos.getX()) && (Y == end.pos.getY())) || ((X == end.pos.getX()) && (Y == start.pos.getY())) || ((Y == start.pos.getY()) && (Z == end.pos.getZ())) || ((Y == end.pos.getY()) && (Z == start.pos.getZ())) || ((Z == start.pos.getZ()) && (X == end.pos.getX())) || ((Z == end.pos.getZ()) && (X == start.pos.getX())) || ((X == end.pos.getX()) && (Y == end.pos.getY()))
									|| ((Y == end.pos.getY()) && (Z == end.pos.getZ())) || ((Z == end.pos.getZ()) && (X == end.pos.getX()))) {
								BlockPos newPos = new BlockPos(X, Y, Z);
								stateAt = world.getBlockState(newPos);

								if (canPlace(world, newPos, state, keys)) {
									world.setBlockState(newPos, state, 3);
									if (this.rand.nextInt(neededItems / 50 + 1) == 0)
										particles(world, newPos, 0);
									affected++;
								}
							}
						}
					}
				}

				if ((this.stateOrig == Blocks.GRASS) && (affected > 0)) {
					for (int run = 0; run <= 1; run++) {
						if (run == 0)
							Y = start.pos.getY();
						if (run == 1) {
							Y = end.pos.getY();
						}
						for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
							for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
								BlockPos newPos = new BlockPos(X, Y, Z);
								if (((X == start.pos.getX()) && (Y == start.pos.getY())) || ((Y == start.pos.getY()) && (Z == start.pos.getZ())) || ((Z == start.pos.getZ()) && (X == start.pos.getX())) || ((X == start.pos.getX()) && (Y == end.pos.getY())) || ((X == end.pos.getX()) && (Y == start.pos.getY())) || ((Y == start.pos.getY()) && (Z == end.pos.getZ())) || ((Y == end.pos.getY()) && (Z == start.pos.getZ())) || ((Z == start.pos.getZ()) && (X == end.pos.getX())) || ((Z == end.pos.getZ()) && (X == start.pos.getX())) || ((X == end.pos.getX()) && (Y == end.pos.getY()))
										|| ((Y == end.pos.getY()) && (Z == end.pos.getZ())) || ((Z == end.pos.getZ()) && (X == end.pos.getX()) && (world.getBlockState(newPos).getBlock() == Blocks.DIRT) && ((world.getBlockState(newPos.up()).getBlock() == null) || (!world.getBlockState(newPos.up()).isOpaqueCube())))) {
									world.setBlockState(newPos, Blocks.GRASS.getDefaultState());
								}
							}
						}
					}

				}

				return affected > 0;
			}
			return false;
		case BUILD_TORCHES:
			neededItems = 0;

			for (X = start.pos.getX(); X <= end.pos.getX(); X += 5) {
				for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z += 5) {
					for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
						BlockPos newPos = new BlockPos(X, Y, Z);
						stateAt = world.getBlockState(newPos);

						if (canPlace(world, newPos, state, keys)) {
							neededItems += multiplier;
						}
					}
				}
			}

			if (neededItems == 0) {
				if (!world.isRemote)
					error(entityplayer, clicked, "nowork");
				return false;
			}

			if (consumeItems(neededStack, entityplayer, neededItems, clicked)) {
				for (X = start.pos.getX(); X <= end.pos.getX(); X += 5) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z += 5) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							BlockPos newPos = new BlockPos(X, Y, Z);
							stateAt = world.getBlockState(newPos);

							if (canPlace(world, newPos, state, keys)) {
								world.setBlockState(newPos, state, 3);
								particles(world, newPos, 0);
								affected++;
							}
						}
					}
				}
				return affected > 0;
			}
			return false;
		case BUILD_WATER:
			if ((!this.reinforced) && (!FREE)) {
				error(entityplayer, clicked, "cantfillwater");
				return false;
			}

			if (!FREE) {
				neededItems = 0;

				for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							BlockPos newPos = new BlockPos(X, Y, Z);
							stateAt = world.getBlockState(newPos);

							if (canBreak(keys, world, newPos)) {
								neededItems++;
							}
						}
					}
				}

				if (neededItems == 0) {
					if (!world.isRemote)
						error(entityplayer, clicked, "nowork");
					return false;
				}
			}

			if (emptyBuckets(entityplayer, 2, false)) {
				for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							BlockPos newPos = new BlockPos(X, Y, Z);
							stateAt = world.getBlockState(newPos);

							if (canBreak(keys, world, newPos)) {
								world.setBlockState(newPos, Blocks.FLOWING_WATER.getDefaultState());
								affected++;
							}
						}
					}
				}

				if (affected == 0) {
					return false;
				}
				for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							BlockPos newPos = new BlockPos(X, Y, Z);
							stateAt = world.getBlockState(newPos);

							if (stateAt.getBlock() == Blocks.FLOWING_WATER) {
								world.notifyBlockOfStateChange(newPos, Blocks.FLOWING_WATER);
								if (world.getBlockState(newPos.up()).getBlock() == Blocks.AIR)
									particles(world, newPos, 2);
							}
						}
					}
				}
				return true;
			}
			error(entityplayer, clicked, "toofewwater");

			return false;
		case BUILD_LAVA:
			if ((!this.reinforced) && (!FREE)) {
				error(entityplayer, clicked, "cantfilllava");
				return false;
			}

			neededItems = 0;

			if (!FREE) {
				for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							BlockPos newPos = new BlockPos(X, Y, Z);
							stateAt = world.getBlockState(newPos);

							if (canBreak(keys, world, newPos)) {
								neededItems++;
							}
						}
					}
				}
				if (neededItems == 0) {
					if (!world.isRemote)
						error(entityplayer, clicked, "nowork");
					return false;
				}
			}

			if (emptyBuckets(entityplayer, neededItems, true)) {
				for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							BlockPos newPos = new BlockPos(X, Y, Z);
							stateAt = world.getBlockState(newPos);
							if (canBreak(keys, world, newPos)) {
								world.setBlockState(newPos, Blocks.FLOWING_LAVA.getDefaultState());
								affected++;
							}
						}
					}
				}

				if (affected == 0) {
					return false;
				}
				for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
					for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
						for (Y = start.pos.getY(); Y <= end.pos.getY(); Y++) {
							BlockPos newPos = new BlockPos(X, Y, Z);
							stateAt = world.getBlockState(newPos);
							if (stateAt.getBlock() == Blocks.FLOWING_LAVA) {
								world.notifyBlockOfStateChange(newPos, Blocks.FLOWING_LAVA);
							}
						}
					}
				}
				return true;
			}
			error(entityplayer, clicked, "toofewlava");

			return false;
		case BUILD_CAVES:
			if ((!this.reinforced) && (!FREE)) {
				error(entityplayer, clicked, "cantfillcave");
				return false;
			}

			boolean underground = false;
			long cnt = 0L;
			for (X = start.pos.getX(); X <= end.pos.getX(); X++) {
				for (Z = start.pos.getZ(); Z <= end.pos.getZ(); Z++) {
					underground = false;
					for (Y = 127; Y > 1; Y--) {
						BlockPos newPos = new BlockPos(X, Y, Z);
						stateAt = world.getBlockState(newPos);

						boolean surfaceBlock = isSurface(stateAt);

						if ((!underground) && (surfaceBlock)) {
							underground = true;
						} else if (underground) {
							if (canBreak(keys, world, newPos)) {
								world.setBlockState(newPos, Blocks.STONE.getDefaultState());
								cnt += 1L;
							}
						}
					}
				}
			}
			if (cnt > 0L) {
				if (!world.isRemote)
					sendMessage(entityplayer, cnt + I18n.format("result.wand.fill"), false);
				return true;
			} else {
				if (!world.isRemote)
					error(entityplayer, clicked, "nocave");
				return false;
			}
		}

		return false;
	}
}
