package com.grim3212.mc.pack.decor.tile;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerGrill;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.crafting.GrillRecipeSerializer;
import com.grim3212.mc.pack.decor.inventory.ContainerGrill;
import com.grim3212.mc.pack.decor.network.MessageParticles;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.INameable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntityGrill extends TileEntityColorizer implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity, INamedContainerProvider, INameable {

	private static final int[] SLOTS_TOP = new int[] { 0, 1, 2, 3 };
	private static final int[] SLOTS = new int[] { 4 };

	private LockCode code = LockCode.EMPTY_CODE;
	public NonNullList<ItemStack> inventory = NonNullList.withSize(5, ItemStack.EMPTY);
	public int[] cookTimes = new int[4];
	public int grillCoal = 0;
	private ITextComponent customName;
	private int nextUpdate = 0;
	private final Map<ResourceLocation, Integer> recipeUseCounts = Maps.newHashMap();

	public final IIntArray progress = new IIntArray() {
		public int get(int index) {
			switch (index) {
			case 0:
				return TileEntityGrill.this.grillCoal;
			case 1:
				return TileEntityGrill.this.cookTimes[0];
			case 2:
				return TileEntityGrill.this.cookTimes[1];
			case 3:
				return TileEntityGrill.this.cookTimes[2];
			case 4:
				return TileEntityGrill.this.cookTimes[3];
			case 5:
				return TileEntityGrill.this.getTier();
			default:
				return 0;
			}
		}

		public void set(int index, int value) {
			switch (index) {
			case 0:
				TileEntityGrill.this.grillCoal = value;
				break;
			case 1:
				TileEntityGrill.this.cookTimes[0] = value;
				break;
			case 2:
				TileEntityGrill.this.cookTimes[1] = value;
				break;
			case 3:
				TileEntityGrill.this.cookTimes[2] = value;
				break;
			case 4:
				TileEntityGrill.this.cookTimes[3] = value;
				break;
			case 5:
				break;
			}

		}

		public int size() {
			return 5;
		}
	};

	public TileEntityGrill() {
		super(DecorTileEntities.GRILL);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		if (this.code != null) {
			this.code.write(compound);
		}

		compound.putInt("GrillCoal", this.grillCoal);
		compound.putInt("CookTimes0", this.cookTimes[0]);
		compound.putInt("CookTimes1", this.cookTimes[1]);
		compound.putInt("CookTimes2", this.cookTimes[2]);
		compound.putInt("CookTimes3", this.cookTimes[3]);
		ItemStackHelper.saveAllItems(compound, this.inventory);
		compound.putShort("RecipesUsedSize", (short) this.recipeUseCounts.size());

		int i = 0;
		for (Entry<ResourceLocation, Integer> entry : this.recipeUseCounts.entrySet()) {
			compound.putString("RecipeLocation" + i, entry.getKey().toString());
			compound.putInt("RecipeAmount" + i, entry.getValue());
			++i;
		}

		if (this.hasCustomName()) {
			compound.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
		}

		return compound;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		this.code = LockCode.read(compound);
		ItemStackHelper.loadAllItems(compound, this.inventory);

		this.grillCoal = compound.getInt("GrillCoal");
		this.cookTimes[0] = compound.getInt("CookTimes0");
		this.cookTimes[1] = compound.getInt("CookTimes1");
		this.cookTimes[2] = compound.getInt("CookTimes2");
		this.cookTimes[3] = compound.getInt("CookTimes3");

		int i = compound.getShort("RecipesUsedSize");

		for (int j = 0; j < i; ++j) {
			ResourceLocation resourcelocation = new ResourceLocation(compound.getString("RecipeLocation" + j));
			int k = compound.getInt("RecipeAmount" + j);
			this.recipeUseCounts.put(resourcelocation, k);
		}

		if (compound.contains("CustomName", 8)) {
			this.customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
		}
	}

	@Override
	public void tick() {
		if (this.nextUpdate <= 0) {
			this.nextUpdate = 50;
		} else {
			this.nextUpdate -= 1;
		}

		if (DecorConfig.infiniteGrillFuel.get())
			this.grillCoal = 4000;

		if ((this.grillCoal <= 1) && (getWorld().getBlockState(getPos()).get(BlockColorizerGrill.ACTIVE))) {
			if (!getStackInSlot(4).isEmpty() && (getStackInSlot(4).getItem() == Items.COAL)) {
				this.grillCoal = 4001;

				if (getStackInSlot(4).getCount() > 1) {
					getStackInSlot(4).shrink(1);
				} else
					setInventorySlotContents(4, ItemStack.EMPTY);
			}
		}

		if ((this.grillCoal <= 0) && (getWorld().getBlockState(getPos()).get(BlockColorizerGrill.ACTIVE)) && this.nextUpdate == 50) {
			if (!world.isRemote) {
				PacketDispatcher.send(PacketDistributor.DIMENSION.with(() -> world.getDimension().getType()), new MessageParticles(pos));
				world.setBlockState(getPos(), getWorld().getBlockState(getPos()).with(BlockColorizerGrill.ACTIVE, false));
			}
			world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.4F + 0.8F, false);
		}

		if (isGrillBurning()) {
			this.grillCoal -= 1;

			int tiertime = (int) getTierTime(getTier());

			for (int i = 0; i < 4; i++) {
				if (!getStackInSlot(i).isEmpty() && (GrillRecipeSerializer.grillRecipesContain(getStackInSlot(i)))) {
					this.cookTimes[i] += 1;

					if (this.cookTimes[i] > tiertime) {
						this.inventory.set(i, GrillRecipeSerializer.getOutput(this.inventory.get(i)));
						this.cookTimes[i] = 0;
					} else {
						this.cookTimes[i] += 1;
					}
				} else {
					this.cookTimes[i] = 0;
				}
			}
		} else {
			for (int i = 0; i < 4; i++) {
				if (this.cookTimes[i] > 0) {
					this.cookTimes[i] -= this.world.rand.nextInt(2);
				}
			}
		}
	}

	@Override
	public ITextComponent getName() {
		if (this.hasCustomName()) {
			return this.getCustomName();
		} else {
			ItemStack toPlaceStack = new ItemStack(getStoredBlockState().getBlock(), 1);

			return new TranslationTextComponent(toPlaceStack.getDisplayName() + " " + I18n.format("container.grill"));
		}
	}

	@Override
	public ITextComponent getCustomName() {
		return this.customName;
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && this.customName.getString().length() > 0;
	}

	public void setCustomName(ITextComponent customName) {
		this.customName = customName;
	}

	@Override
	public int getSizeInventory() {
		return this.inventory.size();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.inventory.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (!this.inventory.get(index).isEmpty()) {
			if (this.inventory.get(index).getCount() <= count) {
				ItemStack stack = this.inventory.get(index);
				this.inventory.set(index, ItemStack.EMPTY);
				return stack;
			}

			ItemStack stack = this.inventory.get(index).split(count);

			if (this.inventory.get(index).getCount() == 0) {
				this.inventory.set(index, ItemStack.EMPTY);
			}

			return stack;
		}

		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
		this.inventory.set(index, stack);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(PlayerEntity player) {
	}

	@Override
	public void closeInventory(PlayerEntity player) {
	}

	public boolean isGrillBurning() {
		if (getWorld().getBlockState(getPos()).get(BlockColorizerGrill.ACTIVE) && (this.grillCoal > 0))
			return true;
		return false;
	}

	public int getTier() {
		BlockState grillType = this.getStoredBlockState();

		if (DecorConfig.consumeBlock.get()) {
			if (grillType.getBlock() == Blocks.DIAMOND_BLOCK || grillType.getBlock() == Blocks.EMERALD_BLOCK) {
				return 6;
			} else if (grillType.getMaterial() == Material.IRON) {
				return 5;
			} else if (grillType.getBlock() == Blocks.OBSIDIAN || grillType.getBlock() == Blocks.END_STONE || grillType.getBlock() == Blocks.LAPIS_BLOCK) {
				return 4;
			} else if (grillType.getMaterial() == Material.ROCK) {
				return 3;
			} else if (grillType.getMaterial() == Material.SAND) {
				return 1;
			}
			return 2;
		} else {
			// If you don't have to consume blocks then use a flat rate
			return 3;
		}
	}

	public static float getTierTime(int tier) {
		return 1000 + (6 - tier) * 500;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.inventory.size(); ++i) {
			this.inventory.set(i, ItemStack.EMPTY);
		}
	}

	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerIn) {
		return this.canOpen(playerIn) ? this.createMenu(id, playerInventory) : null;
	}

	protected Container createMenu(int id, PlayerInventory playerInventory) {
		return new ContainerGrill(id, playerInventory, this, this.progress, this.getPos());
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}

	public boolean canOpen(PlayerEntity player) {
		return LockableTileEntity.canUnlock(player, this.code, this.getDisplayName());
	}

	/**
	 * Get the formatted ChatComponent that will be used for the sender's username
	 * in chat
	 */
	@Override
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? this.getCustomName() : this.getName();
	}

	LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		if (!this.removed && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == Direction.UP)
				return handlers[0].cast();
			else if (facing == Direction.DOWN)
				return handlers[1].cast();
			else
				return handlers[2].cast();
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.inventory) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void fillStackedContents(RecipeItemHelper helper) {
		for (ItemStack itemstack : this.inventory) {
			helper.accountStack(itemstack);
		}
	}

	@Override
	public void setRecipeUsed(IRecipe<?> recipe) {
		if (this.recipeUseCounts.containsKey(recipe.getId())) {
			this.recipeUseCounts.put(recipe.getId(), this.recipeUseCounts.get(recipe.getId()) + 1);
		} else {
			this.recipeUseCounts.put(recipe.getId(), 1);
		}
	}

	@Override
	public IRecipe<?> getRecipeUsed() {
		return null;
	}

	@Override
	public boolean canUseRecipe(World worldIn, ServerPlayerEntity player, @Nullable IRecipe<?> recipe) {
		if (recipe != null) {
			this.setRecipeUsed(recipe);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		if (side == Direction.UP) {
			return SLOTS_TOP;
		} else {
			return SLOTS;
		}
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		return true;
	}
}