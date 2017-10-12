package com.grim3212.mc.pack.world.blocks;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRune extends BlockManual {

	public static PropertyEnum<EnumRuneType> RUNETYPE = PropertyEnum.create("rune_type", EnumRuneType.class);

	public BlockRune() {
		super("rune", Material.ROCK, SoundType.STONE);
		setResistance(50F);
		setHardness(50F);
		setCreativeTab(GrimCreativeTabs.GRIM_WORLD);
	}

	@Override
	protected IBlockState getState() {
		return super.getState().withProperty(RUNETYPE, EnumRuneType.UR);
	}

	@Override
	public Page getPage(IBlockState state) {
		return null;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(RUNETYPE).ordinal();
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (EnumRuneType rune : EnumRuneType.values) {
			items.add(new ItemStack(this, 1, rune.ordinal()));
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		playerIn.addPotionEffect(getPotionEffect(state.getValue(RUNETYPE), playerIn.experienceLevel, 1.0F, 0.06F));
		return true;
	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		if (entityIn instanceof EntityLivingBase) {
			EntityLivingBase living = (EntityLivingBase) entityIn;
			int xpLevel = 1;
			if (living instanceof EntityPlayer) {
				xpLevel = ((EntityPlayer) living).experienceLevel;
			}
			living.addPotionEffect(getPotionEffect(worldIn.getBlockState(pos).getValue(RUNETYPE), xpLevel, 1.0F, 0.06F));
		}
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(RUNETYPE).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(RUNETYPE, EnumRuneType.values[meta]);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { RUNETYPE });
	}

	private PotionEffect getPotionEffect(EnumRuneType type, int xpLevel, float durationMod, float amplifierMod) {
		// TODO: Add in config options for modifiers
		//XPLVLMOD, DURATIONMOD, AMPLIFIERMOD
		int duration = (int) (1000F + (float) (500F + (xpLevel * 100)) * durationMod);
		int amplifier = (int) (0.0D + Math.floor(amplifierMod * (float) xpLevel));

		return new PotionEffect(type.getPotion(), duration, amplifier);
	}

	public static enum EnumRuneType implements IStringSerializable {
		UR("ur", "strength"), EOH("eoh", "poison"), HAGEL("hagel", "jump_boost"), EOLH("eolh", "resistance"), CEN("cen", "fire_resistance"), GER("ger", "haste"), RAD("rad", "speed"), IS("is", "slowness"), DAEG("daeg", "instant_health"), TYR("tyr", "nausea"), BEORC("beorc", "regeneration"), LAGU("lagu", "water_breathing"), ODAL("odal", "night_vision"), NYD("nyd", "hunger"), THORN("thorn", "weakness"), OS("os", "blindness");

		private final String runeName;
		private final String potion;
		private Potion potionType;
		public static final EnumRuneType values[] = values();

		private EnumRuneType(String runeName, String potion) {
			this.runeName = runeName;
			this.potion = potion;
		}

		public static String[] names() {
			EnumRuneType[] states = values;
			String[] names = new String[states.length];

			for (int i = 0; i < states.length; i++) {
				names[i] = states[i].getName();
			}

			return names;
		}

		public Potion getPotion() {
			if (potionType != null) {
				return potionType;
			} else {
				potionType = Potion.getPotionFromResourceLocation(potion);
				return potionType;
			}
		}

		@Override
		public String getName() {
			return this.runeName;
		}
	}
}
