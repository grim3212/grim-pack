package com.grim3212.mc.pack.decor.block.colorizer;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.block.IColorizer;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;
import com.grim3212.mc.pack.decor.util.BlockHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.DiggingParticle;
import net.minecraft.client.particle.DiggingParticle.Factory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.FakePlayer;

public class BlockColorizer extends BlockManual implements IColorizer {

	public BlockColorizer(String name) {
		this(name, Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5f, 12f));
	}

	public BlockColorizer(String name, Block.Properties props) {
		super(name, props);
		setDefaultState(getState());
	}

	protected BlockState getState() {
		return stateContainer.getBaseState();
	}

	protected BlockState getStoredState(World worldIn, BlockPos pos) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityColorizer) {
			return ((TileEntityColorizer) te).getStoredBlockState();
		}
		return Blocks.AIR.getDefaultState();
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		if (DecorConfig.consumeBlock.get()) {
			if (!player.abilities.isCreativeMode) {
				if (this.getStoredState(worldIn, pos) != Blocks.AIR.getDefaultState()) {
					BlockState blockState = this.getStoredState(worldIn, pos);
					spawnAsEntity(worldIn, pos, new ItemStack(blockState.getBlock(), 1));
				}
			}
		}
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
		ItemStack itemstack = new ItemStack(this);
		NBTHelper.setTagCompound(itemstack, "stored_state", NBTUtil.writeBlockState(Blocks.AIR.getDefaultState()));
		return itemstack;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean doesSideBlockRendering(BlockState state, IEnviromentBlockReader world, BlockPos pos, Direction face) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityColorizer) {
			return ((TileEntityColorizer) te).getStoredBlockState().doesSideBlockRendering(world, pos, face);
		}
		return true;
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		ItemStack heldItem = player.getHeldItem(hand);

		if (!heldItem.isEmpty() && tileentity instanceof TileEntityColorizer) {

			if (heldItem.getItem() == DecorItems.brush) {
				if (this.tryUseBrush(worldIn, player, hand, pos)) {
					return true;
				}
			}

			TileEntityColorizer te = (TileEntityColorizer) tileentity;
			Block block = Block.getBlockFromItem(heldItem.getItem());

			if (block != null && !(block instanceof BlockColorizer)) {
				if (BlockHelper.getUsableBlocks().contains(block.getDefaultState())) {
					// Can only set blockstate if it contains nothing or if
					// in creative mode
					if (te.getStoredBlockState() == Blocks.AIR.getDefaultState() || player.abilities.isCreativeMode) {
						BlockState placeState = block.getStateForPlacement(new BlockItemUseContext(new ItemUseContext(player, hand, hit)));

						setColorizer(worldIn, pos, state, placeState, player, hand, true);

						SoundType placeSound = placeState.getSoundType(worldIn, pos, player);

						worldIn.playSound(player, pos, placeSound.getPlaceSound(), SoundCategory.BLOCKS, (placeSound.getVolume() + 1.0F) / 2.0F, placeSound.getPitch() * 0.8F);

						return true;
					} else if (te.getStoredBlockState() != Blocks.AIR.getDefaultState()) {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityColorizer();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean addHitEffects(BlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
		if (target instanceof BlockRayTraceResult) {
			BlockRayTraceResult hit = (BlockRayTraceResult) target;

			TileEntity te = worldObj.getTileEntity(hit.getPos());

			if (te instanceof TileEntityColorizer) {
				TileEntityColorizer tileentity = (TileEntityColorizer) te;
				BlockPos pos = hit.getPos();

				if (tileentity.getStoredBlockState().getRenderType() != BlockRenderType.INVISIBLE) {
					int i = pos.getX();
					int j = pos.getY();
					int k = pos.getZ();
					float f = 0.1F;
					AxisAlignedBB axisalignedbb = tileentity.getStoredBlockState().getShape(worldObj, pos).getBoundingBox();
					double d0 = (double) i + RANDOM.nextDouble() * (axisalignedbb.maxX - axisalignedbb.minX - (double) (f * 2.0F)) + (double) f + axisalignedbb.minX;
					double d1 = (double) j + RANDOM.nextDouble() * (axisalignedbb.maxY - axisalignedbb.minY - (double) (f * 2.0F)) + (double) f + axisalignedbb.minY;
					double d2 = (double) k + RANDOM.nextDouble() * (axisalignedbb.maxZ - axisalignedbb.minZ - (double) (f * 2.0F)) + (double) f + axisalignedbb.minZ;

					Direction side = hit.getFace();

					if (side == Direction.DOWN) {
						d1 = (double) j + axisalignedbb.minY - (double) f;
					}

					if (side == Direction.UP) {
						d1 = (double) j + axisalignedbb.maxY + (double) f;
					}

					if (side == Direction.NORTH) {
						d2 = (double) k + axisalignedbb.minZ - (double) f;
					}

					if (side == Direction.SOUTH) {
						d2 = (double) k + axisalignedbb.maxZ + (double) f;
					}

					if (side == Direction.WEST) {
						d0 = (double) i + axisalignedbb.minX - (double) f;
					}

					if (side == Direction.EAST) {
						d0 = (double) i + axisalignedbb.maxX + (double) f;
					}
					Factory particleFactory = new DiggingParticle.Factory();
					DiggingParticle digging = (DiggingParticle) particleFactory.makeParticle(new BlockParticleData(ParticleTypes.BLOCK, tileentity.getStoredBlockState()), worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D);
					digging.setBlockPos(hit.getPos()).multiplyVelocity(0.2f).multipleParticleScaleBy(0.6f);
					manager.addEffect(digging);
					return true;
				}
			}
		}

		return super.addHitEffects(state, worldObj, target, manager);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean addDestroyEffects(BlockState state, World world, BlockPos pos, ParticleManager manager) {
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileEntityColorizer) {
			TileEntityColorizer tileentity = (TileEntityColorizer) te;
			if (tileentity.getStoredBlockState() == Blocks.AIR.getDefaultState()) {
				return super.addDestroyEffects(state, world, pos, manager);
			} else {
				manager.clearEffects(world);
				manager.addBlockDestroyEffects(pos, tileentity.getStoredBlockState());
			}
		}

		return true;
	}

	@Override
	public boolean addLandingEffects(BlockState state, ServerWorld worldObj, BlockPos blockPosition, BlockState iblockstate, LivingEntity entity, int numberOfParticles) {
		TileEntity tileentity = (TileEntity) worldObj.getTileEntity(blockPosition);
		if (tileentity instanceof TileEntityColorizer) {
			TileEntityColorizer te = (TileEntityColorizer) tileentity;
			if (te.getStoredBlockState() == Blocks.AIR.getDefaultState()) {
				return super.addLandingEffects(state, worldObj, blockPosition, iblockstate, entity, numberOfParticles);
			} else {
				worldObj.spawnParticle(new BlockParticleData(ParticleTypes.BLOCK, te.getStoredBlockState()), entity.posX, entity.posY, entity.posZ, numberOfParticles, 0.0D, 0.0D, 0.0D, 0.15000000596046448D);
			}
		}
		return true;
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.colorizer_page;
	}

	@Override
	public boolean clearColorizer(World worldIn, BlockPos pos, BlockState state, PlayerEntity player, Hand hand) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityColorizer) {
			TileEntityColorizer tileColorizer = (TileEntityColorizer) te;
			BlockState storedState = tileColorizer.getStoredBlockState();

			// Can only clear a filled colorizer
			if (storedState != Blocks.AIR.getDefaultState()) {

				if (DecorConfig.consumeBlock.get() && !player.abilities.isCreativeMode) {
					ItemEntity blockDropped = new ItemEntity(worldIn, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), new ItemStack(tileColorizer.getStoredBlockState().getBlock(), 1));
					if (!worldIn.isRemote) {
						worldIn.addEntity(blockDropped);
						if (!(player instanceof FakePlayer)) {
							blockDropped.onCollideWithPlayer(player);
						}
					}
				}

				// Clear Self
				if (setColorizer(worldIn, pos, state, null, player, hand, false)) {
					SoundType placeSound = state.getSoundType(worldIn, pos, player);

					worldIn.playSound(player, pos, placeSound.getPlaceSound(), SoundCategory.BLOCKS, (placeSound.getVolume() + 1.0F) / 2.0F, placeSound.getPitch() * 0.8F);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean setColorizer(World worldIn, BlockPos pos, BlockState state, BlockState toSetState, PlayerEntity player, Hand hand, boolean consumeItem) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof TileEntityColorizer) {
			TileEntityColorizer te = (TileEntityColorizer) tileentity;
			te.setStoredBlockState(toSetState != null ? toSetState : Blocks.AIR.getDefaultState());

			// Remove an item if config allows and we are not resetting
			// colorizer
			if (DecorConfig.consumeBlock.get() && toSetState != null && consumeItem) {
				if (!player.abilities.isCreativeMode)
					player.getHeldItem(hand).shrink(1);
			}

			return true;
		}
		return false;
	}

	public boolean tryUseBrush(World world, PlayerEntity player, Hand hand, BlockPos pos) {
		if (player.isSneaking()) {
			if (world.isRemote) {
				TileEntity tileentity = world.getTileEntity(pos);

				if (tileentity instanceof TileEntityColorizer) {
					BlockState storedState = ((TileEntityColorizer) tileentity).getStoredBlockState();

					ItemStack storedstack = new ItemStack(storedState.getBlock(), 1);
					if (storedstack.getItem() != null)
						player.sendMessage(new TranslationTextComponent("grimpack.decor.brush.stored", storedstack.getDisplayName()));
					else
						player.sendMessage(new TranslationTextComponent("grimpack.decor.brush.empty"));
				}
				return true;
			}
		}

		BlockState targetBlockState = world.getBlockState(pos);

		if (targetBlockState.getBlock() instanceof IColorizer) {
			if (((IColorizer) targetBlockState.getBlock()).clearColorizer(world, pos, targetBlockState, player, hand)) {
				return true;
			}
		}

		return false;
	}
}
