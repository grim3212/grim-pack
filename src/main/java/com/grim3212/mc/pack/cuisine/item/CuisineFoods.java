package com.grim3212.mc.pack.cuisine.item;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class CuisineFoods {
	public static final Food CHOCOLATE_BALL = (new Food.Builder()).hunger(2).saturation(0.2F).build();
	public static final Food CHOCOLATE_BAR = (new Food.Builder()).hunger(3).saturation(0.8F).build();
	public static final Food CHOCOLATE_BAR_WRAPPED = (new Food.Builder()).hunger(5).saturation(0.8F).build();
	
	public static final Food DRAGON_FRUIT = (new Food.Builder()).hunger(4).saturation(0.3F).build();
	public static final Food SWEETS = (new Food.Builder()).hunger(2).saturation(0.1F).build();
	public static final Food POWERED_SWEETS = (new Food.Builder()).hunger(6).saturation(0.3F).build();
	public static final Food DOUGH = (new Food.Builder()).hunger(1).saturation(0.2F).build();
	public static final Food PUMPKIN_SLICE = (new Food.Builder()).hunger(1).saturation(0.2F).build();
	public static final Food RAW_PIE = (new Food.Builder()).hunger(2).saturation(0.3F).effect(new EffectInstance(Effects.HUNGER, 300, 0), 0.1F).build();
	
	public static final Food BUTTER = (new Food.Builder()).hunger(2).saturation(0.4F).build();
	public static final Food CHEESE = (new Food.Builder()).hunger(3).saturation(0.6F).build();
	public static final Food BREAD_SLICE = (new Food.Builder()).hunger(2).saturation(0.4F).build();
	public static final Food CHEESE_BURGER = (new Food.Builder()).hunger(12).saturation(0.95F).build();
	public static final Food HOT_CHEESE = (new Food.Builder()).hunger(8).saturation(0.75F).build();
	public static final Food EGGS_MIXED = (new Food.Builder()).hunger(4).saturation(0.4F).effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.3F).build();
	public static final Food EGGS_UNMIXED = (new Food.Builder()).hunger(2).saturation(0.1F).effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.3F).build();
	public static final Food EGGS_COOKED = (new Food.Builder()).hunger(10).saturation(0.8F).build();
}
