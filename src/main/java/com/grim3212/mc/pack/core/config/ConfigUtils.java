package com.grim3212.mc.pack.core.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.GrimCore;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.core.util.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class ConfigUtils {

	private static final String ORE_DICT = "oredict";
	private static String currentPart = GrimCore.partName;

	public static void setCurrentPart(String currentPart) {
		ConfigUtils.currentPart = currentPart;
	}

	/**
	 * A list of fuels that contain an itemstack and number for a type of fuel.
	 * 
	 * @param fromConfig
	 *            Config entry to pull string away from
	 */
	public static List<Pair<ItemStack, Integer>> loadConfigurableFuel(String[] fromConfig) {
		List<Pair<ItemStack, Integer>> recipes = Lists.newArrayList();

		if (fromConfig.length > 0) {
			for (int i = 0; i < fromConfig.length; i++) {
				String[] rawids = fromConfig[i].split(">");

				Object input = getInput(rawids[0], fromConfig[i]);
				int burnTime = 0;

				if (rawids.length > 1) {
					burnTime = getBurnTime(rawids[1], fromConfig[i]);
				} else {
					GrimLog.debugInfo(currentPart, "No burn time argument found for '" + rawids[0] + "'");
				}

				// Make sure input and output are valid
				if (input != null) {

					// Change recipe depending on input
					if (input instanceof ItemStack) {

						// Add finished recipe
						recipes.add(Pair.of((ItemStack) input, burnTime));
					} else if (input instanceof String) {
						String oreDict = (String) input;

						if (oreDict.contains(":")) {
							String[] split = oreDict.split(":");

							if (OreDictionary.doesOreNameExist(split[0])) {
								NonNullList<ItemStack> inputs = OreDictionary.getOres(split[0]);

								if (Utils.isInteger(split[1])) {
									for (ItemStack ore : inputs) {
										// Add recipe for every OreDictionary
										recipes.add(Pair.of(new ItemStack(ore.getItem(), 1, Integer.parseInt(split[1])), burnTime));
									}
								} else if (split[1].equals("?")) {
									for (ItemStack ore : inputs) {

										// Add recipe for every OreDictionary
										recipes.add(Pair.of(new ItemStack(ore.getItem(), 1, OreDictionary.WILDCARD_VALUE), burnTime));
									}
								} else {
									GrimLog.error(currentPart, "Something unknown happened with input: '" + fromConfig[i] + "'!");
								}
							} else {
								GrimLog.error(currentPart, "Ore name: '" + split[0] + "' does not exist!");
							}
						} else {
							if (OreDictionary.doesOreNameExist(oreDict)) {
								NonNullList<ItemStack> inputs = OreDictionary.getOres(oreDict);

								for (ItemStack ore : inputs) {

									// Add recipe for every OreDictionary
									recipes.add(Pair.of(ore, burnTime));
								}
							} else {
								GrimLog.error(currentPart, "Ore name: '" + oreDict + "' does not exist!");
							}
						}
					} else {
						GrimLog.error(currentPart, "Something unknown happened with input: '" + fromConfig[i] + "'!");
					}
				}
			}
		}

		return recipes;
	}

	/**
	 * A list of recipes. Each sublist contains a valid recipe. If xp is true
	 * then this list will be of size 3 and otherwise of size 2
	 * 
	 * @param fromConfig
	 *            Config entry to pull string away from
	 * @param xp
	 *            Should xp be considered in this recipe
	 * @return
	 */
	public static List<BasicRecipe> loadConfigurableRecipes(String[] fromConfig, boolean xp) {
		List<BasicRecipe> recipes = Lists.newArrayList();

		if (fromConfig.length > 0) {
			for (int i = 0; i < fromConfig.length; i++) {
				String[] rawids = fromConfig[i].split(">");

				Object input = getInput(rawids[0], fromConfig[i]);
				ItemStack output = getOutput(rawids[1], fromConfig[i]);

				// Make sure input and output are valid
				if (input != null && !output.isEmpty()) {
					BasicRecipe recipe = new BasicRecipe();

					// Set recipe since it is unchanging
					recipe.output = output;

					// Set xp since it is unchanging
					if (xp) {
						if (rawids.length == 3) {
							// Try and parse experience
							recipe.experience = getExperience(rawids[2], fromConfig[i]);
						}
					} else if (rawids.length == 3) {
						GrimLog.error(currentPart, "Ignoring XP in recipe without experience: '" + recipe + "'!");
					}

					// Change recipe depending on input
					if (input instanceof ItemStack) {
						recipe.input = (ItemStack) input;

						// Add finished recipe
						recipes.add(recipe);
					} else if (input instanceof String) {
						String oreDict = (String) input;

						if (oreDict.contains(":")) {
							String[] split = oreDict.split(":");

							if (OreDictionary.doesOreNameExist(split[0])) {
								NonNullList<ItemStack> inputs = OreDictionary.getOres(split[0]);

								if (Utils.isInteger(split[1])) {
									for (ItemStack ore : inputs) {
										BasicRecipe oreRecipe = new BasicRecipe();
										oreRecipe.input = new ItemStack(ore.getItem(), 1, Integer.parseInt(split[1]));
										oreRecipe.output = recipe.output;
										oreRecipe.experience = recipe.experience;

										// Add recipe for every OreDictionary
										// item
										recipes.add(oreRecipe);
									}
								} else if (split[1].equals("?")) {
									for (ItemStack ore : inputs) {
										BasicRecipe oreRecipe = new BasicRecipe();
										oreRecipe.input = new ItemStack(ore.getItem(), 1, OreDictionary.WILDCARD_VALUE);
										oreRecipe.output = recipe.output;
										oreRecipe.experience = recipe.experience;

										// Add recipe for every OreDictionary
										// item
										recipes.add(oreRecipe);
									}
								} else {
									GrimLog.error(currentPart, "Something unknown happened with input: '" + recipe + "'!");
								}
							} else {
								GrimLog.error(currentPart, "Ore name: '" + split[0] + "' does not exist!");
							}
						} else {
							if (OreDictionary.doesOreNameExist(oreDict)) {
								NonNullList<ItemStack> inputs = OreDictionary.getOres(oreDict);

								for (ItemStack ore : inputs) {
									BasicRecipe oreRecipe = new BasicRecipe();
									oreRecipe.input = ore;
									oreRecipe.output = recipe.output;
									oreRecipe.experience = recipe.experience;

									// Add recipe for every OreDictionary item
									recipes.add(oreRecipe);
								}
							} else {
								GrimLog.error(currentPart, "Ore name: '" + oreDict + "' does not exist!");
							}
						}
					} else {
						GrimLog.error(currentPart, "Something unknown happened with input: '" + recipe + "'!");
					}
				}
			}
		}

		return recipes;
	}

	private static Object getInput(String s, String recipe) {
		Object input = getItemStackFromString(s);

		if (input instanceof ItemStack) {
			ItemStack in = (ItemStack) input;
			if (!in.isEmpty()) {
				return in;
			} else {
				GrimLog.error(currentPart, "Couldn't add recipe: '" + recipe + "' input is empty!");
			}
		} else if (input instanceof String) {
			return input;
		} else {
			GrimLog.error(currentPart, "Couldn't parse input in recipe: '" + recipe + "'!");
		}

		return null;
	}

	private static ItemStack getOutput(String s, String recipe) {
		Object output = getItemStackFromString(s);

		if (output instanceof ItemStack) {
			ItemStack out = (ItemStack) output;
			if (!out.isEmpty()) {
				return out;
			} else {
				GrimLog.error(currentPart, "Couldn't add recipe: '" + recipe + "' output is empty!");
			}

		} else if (output instanceof String) {
			GrimLog.error(currentPart, "Output cannot be an OreDictionary item in recipe: '" + recipe + "'!");
		} else {
			GrimLog.error(currentPart, "Couldn't parse output in recipe: '" + recipe + "'!");
		}

		return ItemStack.EMPTY;
	}

	private static int getBurnTime(String s, String recipe) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			GrimLog.error(currentPart, "Burn time couldn't be parsed as an integer: '" + recipe + "'!");
		}
		return 0;
	}

	private static float getExperience(String s, String recipe) {
		try {
			return Float.parseFloat(s);
		} catch (Exception e) {
			GrimLog.error(currentPart, "Experience couldn't be parsed as a float: '" + recipe + "'!");
		}
		return 0.0F;
	}

	public static void loadItemsOntoList(String[] config, List<ItemStack> list) {
		if (list == null) {
			list = Lists.newArrayList();
		} else {
			list.clear();
		}

		list.addAll(loadConfigurableItems(config));
	}

	public static List<ItemStack> loadConfigurableItems(String[] fromConfig) {
		List<ItemStack> itemlist = Lists.newArrayList();

		if (fromConfig.length > 0) {
			for (String u : fromConfig) {
				if (u.contains(":")) {
					String[] parts = u.split(":");
					if (parts.length == 2) {
						if (parts[0].equals(ORE_DICT)) {
							if (OreDictionary.doesOreNameExist(parts[1])) {
								itemlist.addAll(OreDictionary.getOres(parts[1]));
							} else {
								GrimLog.error(currentPart, "Ore name does not exist: '" + parts[1] + "'!");
							}
						} else {
							if (Utils.isInteger(parts[1])) {
								Item item = Item.REGISTRY.getObject(new ResourceLocation(parts[0]));
								if (item != null) {
									itemlist.add(new ItemStack(item, 1, Integer.parseInt(parts[1])));
								} else {
									GrimLog.error(currentPart, "Can't find item that matches '" + u + "'!");
								}
							} else if (parts[1].equals("?")) {
								Item item = Item.REGISTRY.getObject(new ResourceLocation(parts[0]));
								if (item != null) {
									itemlist.add(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE));
								} else {
									GrimLog.error(currentPart, "Can't find item that matches '" + u + "'!");
								}
							} else {
								Item item = Item.REGISTRY.getObject(new ResourceLocation(parts[0], parts[1]));
								if (item != null) {
									itemlist.add(new ItemStack(item));
								} else {
									GrimLog.error(currentPart, "Can't find item that matches '" + u + "'!");
								}
							}
						}
					} else if (parts.length == 3) {
						// OreDictionary Meta Override
						if (parts[0].equals(ORE_DICT)) {
							if (OreDictionary.doesOreNameExist(parts[1])) {
								if (Utils.isInteger(parts[2])) {
									// Use meta
									for (ItemStack stack : OreDictionary.getOres(parts[1])) {
										itemlist.add(new ItemStack(stack.getItem(), 1, Integer.parseInt(parts[2])));
									}
								} else if (parts[2].equals("?")) {
									// Ignore meta
									for (ItemStack stack : OreDictionary.getOres(parts[1])) {
										itemlist.add(new ItemStack(stack.getItem(), 1, OreDictionary.WILDCARD_VALUE));
									}
								} else {
									GrimLog.error(currentPart, "The third arg (meta) must be an int! Found '" + u + "'!");
								}

							} else {
								GrimLog.error(currentPart, "Ore name does not exist: '" + parts[1] + "'!");
							}
						} else {
							Item item = Item.REGISTRY.getObject(new ResourceLocation(parts[0], parts[1]));
							if (item != null) {
								if (Utils.isInteger(parts[2])) {
									// Use meta
									itemlist.add(new ItemStack(item, 1, Integer.parseInt(parts[2])));
								} else if (parts[2].equals("?")) {
									// Ignore meta
									itemlist.add(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE));
								} else {
									GrimLog.error(currentPart, "The third arg (meta) must be an int! Found '" + u + "'!");
								}
							} else {
								GrimLog.error(currentPart, "Can't find item that matches: " + u);
							}
						}
					}
				} else {
					// Without any colons
					Item item = Item.REGISTRY.getObject(new ResourceLocation(u));
					if (item != null) {
						itemlist.add(new ItemStack(item));
					} else {
						GrimLog.error(currentPart, "Can't find item with name: " + u);
					}
				}
			}
		}

		return itemlist;
	}

	/**
	 * Checks is a IBlockState is found within a map of states and if meta
	 * should be ignored
	 * 
	 * @param map
	 * @param state
	 * @return
	 */
	public static boolean isStateFound(Map<IBlockState, Boolean> map, IBlockState state) {
		if (map != null && !map.isEmpty()) {
			if (map.containsKey(state) || (map.containsKey(state.getBlock().getDefaultState()) && map.get(state.getBlock().getDefaultState()))) {
				return true;
			}
		}

		return false;
	}

	public static void loadBlocksOntoMap(String[] config, Map<IBlockState, Boolean> map) {
		if (map == null) {
			map = Maps.newHashMap();
		} else {
			map.clear();
		}

		map.putAll(loadConfigurableBlocks(config));
	}

	/**
	 * Returns a map of blocks with a state and if the meta should be ignored
	 * 
	 * @param fromConfig
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Map<IBlockState, Boolean> loadConfigurableBlocks(String[] fromConfig) {
		Map<IBlockState, Boolean> blocklist = Maps.newHashMap();

		if (fromConfig.length > 0) {
			for (String u : fromConfig) {
				if (u.contains(":")) {
					String[] parts = u.split(":");
					if (parts.length == 2) {
						if (parts[0].equals(ORE_DICT)) {
							if (OreDictionary.doesOreNameExist(parts[1])) {
								for (ItemStack stack : OreDictionary.getOres(parts[1])) {
									if (stack.getItem() instanceof ItemBlock) {
										Block block = ((ItemBlock) stack.getItem()).getBlock();
										if (stack.getMetadata() == OreDictionary.WILDCARD_VALUE) {
											blocklist.put(block.getStateFromMeta(stack.getMetadata()), true);
										} else {
											blocklist.put(block.getStateFromMeta(stack.getMetadata()), false);
										}
									} else {
										GrimLog.debugInfo(currentPart, "Ignoring item '" + stack.getUnlocalizedName() + "' in OreDict '" + parts[1] + "' for blocks only list!");
									}
								}
							} else {
								GrimLog.error(currentPart, "OreDict name does not exist: '" + parts[1] + "'!");
							}
						} else {
							if (Utils.isInteger(parts[1])) {
								Block block = Block.REGISTRY.getObject(new ResourceLocation(parts[0]));
								if (block != null) {
									// Meta
									blocklist.put(block.getStateFromMeta(Integer.parseInt(parts[1])), false);
								} else {
									GrimLog.error(currentPart, "Can't find block that matches: " + u);
								}
							} else if (parts[1].equals("?")) {
								// Ignore meta
								Block block = Block.REGISTRY.getObject(new ResourceLocation(parts[0]));
								if (block != null) {
									blocklist.put(block.getDefaultState(), true);
								} else {
									GrimLog.error(currentPart, "Can't find block that matches: " + u);
								}
							} else {
								Block block = Block.REGISTRY.getObject(new ResourceLocation(parts[0], parts[1]));
								if (block != null) {
									blocklist.put(block.getDefaultState(), false);
								} else {
									GrimLog.error(currentPart, "Can't find block that matches: " + u);
								}
							}
						}
					} else if (parts.length == 3) {
						// OreDictionary Meta Override
						if (parts[0].equals(ORE_DICT)) {
							if (OreDictionary.doesOreNameExist(parts[1])) {
								if (Utils.isInteger(parts[2])) {
									for (ItemStack stack : OreDictionary.getOres(parts[1])) {
										if (stack.getItem() instanceof ItemBlock) {
											Block block = ((ItemBlock) stack.getItem()).getBlock();
											blocklist.put(block.getStateFromMeta(Integer.parseInt(parts[2])), false);
										} else {
											GrimLog.debugInfo(currentPart, "Ignoring item '" + stack.getUnlocalizedName() + "' in OreDict '" + parts[1] + "'!");
										}
									}
								} else if (parts[2].equals("?")) {
									for (ItemStack stack : OreDictionary.getOres(parts[1])) {
										if (stack.getItem() instanceof ItemBlock) {
											Block block = ((ItemBlock) stack.getItem()).getBlock();
											blocklist.put(block.getDefaultState(), true);
										} else {
											GrimLog.debugInfo(currentPart, "Ignoring item '" + stack.getUnlocalizedName() + "' in OreDict '" + parts[1] + "'!");
										}
									}
								} else {
									GrimLog.error(currentPart, "The third arg (meta) must be an int! Found '" + u + "'!");
								}
							} else {
								GrimLog.error(currentPart, "OreDict name does not exist: '" + parts[1] + "'!");
							}
						} else {
							Block block = Block.REGISTRY.getObject(new ResourceLocation(parts[0], parts[1]));
							if (block != null) {
								if (Utils.isInteger(parts[2])) {
									// Use meta
									blocklist.put(block.getStateFromMeta(Integer.parseInt(parts[2])), false);
								} else if (parts[2].equals("?")) {
									// Ignore meta
									blocklist.put(block.getDefaultState(), true);
								} else {
									GrimLog.error(currentPart, "The third arg (meta) must be an int! Found '" + u + "'!");
								}
							} else {
								GrimLog.error(currentPart, "Can't find block that matches: " + u);
							}
						}
					}
				} else {
					// Without any colons
					Block block = Block.REGISTRY.getObject(new ResourceLocation(u));
					if (block != null) {
						blocklist.put(block.getDefaultState(), false);
					} else {
						GrimLog.error(currentPart, "Can't find block with name: " + u);
					}
				}
			}
		}

		return blocklist;
	}

	/**
	 * Get an ItemStack or OreDictionary name from a string
	 * 
	 * @param s
	 * @return
	 */
	public static Object getItemStackFromString(String s) {
		if (s.contains(":")) {
			String[] split = s.split(":");

			if (split[0].equals(ORE_DICT)) {
				// Meta override
				if (split.length == 3) {
					if (Utils.isInteger(split[2]) || split[2].equals("?")) {
						return split[1] + ":" + split[2];
					} else {
						GrimLog.error(currentPart, "The third arg (meta) must be an int or '?'! Found in '" + s + "'!");
					}
				}

				return split[1];
			} else {
				if (split.length == 2) {
					return new ItemStack((Item) Item.REGISTRY.getObject(new ResourceLocation(s)));
				} else if (split.length == 3) {
					if (Utils.isInteger(split[2])) {
						// Use meta
						return new ItemStack((Item) Item.REGISTRY.getObject(new ResourceLocation(s)), 1, Integer.parseInt(split[2]));
					} else if (split[2].equals("?")) {
						// Ignore meta
						return new ItemStack((Item) Item.REGISTRY.getObject(new ResourceLocation(s)), 1, OreDictionary.WILDCARD_VALUE);
					} else {
						GrimLog.error(currentPart, "Not sure what '" + s + "' is!");
						return ItemStack.EMPTY;
					}
				}
			}
		} else {
			return new ItemStack((Item) Item.REGISTRY.getObject(new ResourceLocation(s)));
		}

		return ItemStack.EMPTY;
	}

	public static class ToolMaterialHolder {
		private final String name;
		private final int defaultHarvestLevel;
		private final int defaultMaxUses;
		private final float defaultEfficiency;
		private final float defaultDamage;
		private final int defaultEnchantability;

		private int harvestLevel;
		private int maxUses;
		private float efficiency;
		private float damage;
		private int enchantability;

		public ToolMaterialHolder(String name, int harvestLevel, int maxUses, float efficiency, float damage, int enchantability) {
			this.name = name;
			this.harvestLevel = harvestLevel;
			this.maxUses = maxUses;
			this.efficiency = efficiency;
			this.damage = damage;
			this.enchantability = enchantability;

			this.defaultHarvestLevel = harvestLevel;
			this.defaultMaxUses = maxUses;
			this.defaultEfficiency = efficiency;
			this.defaultDamage = damage;
			this.defaultEnchantability = enchantability;
		}

		public String getName() {
			return name;
		}

		public int getHarvestLevel() {
			return harvestLevel;
		}

		public int getMaxUses() {
			return maxUses;
		}

		public float getEfficiency() {
			return efficiency;
		}

		public float getDamage() {
			return damage;
		}

		public int getEnchantability() {
			return enchantability;
		}

		@Override
		public String toString() {
			return "[Name:" + getName() + ", HarvestLevel:" + getHarvestLevel() + ", MaxUses:" + getMaxUses() + ", Efficiency:" + getEfficiency() + ", Damage:" + getDamage() + ", Enchantability:" + getEnchantability() + "]";
		}

		public void load() {
			File directoryFile = new File(GrimPack.configDir, GrimPack.modID + "/materials");
			File toolFile = new File(GrimPack.configDir, GrimPack.modID + "/materials/" + this.name + "_tool_material.json");

			if (toolFile.exists()) {
				int harvestLevel = this.defaultHarvestLevel;
				int maxUses = this.defaultMaxUses;
				float efficiency = this.defaultEfficiency;
				float damage = this.defaultDamage;
				int enchantability = this.defaultEnchantability;

				try {
					JsonReader reader = new JsonReader(new FileReader(toolFile));
					reader.beginObject();

					while (reader.hasNext()) {
						String name = reader.nextName();

						if (name.equals("harvestLevel")) {
							harvestLevel = reader.nextInt();
						} else if (name.equals("maxUses")) {
							maxUses = reader.nextInt();
						} else if (name.equals("efficiency")) {
							efficiency = (float) reader.nextDouble();
						} else if (name.equals("damage")) {
							damage = (float) reader.nextDouble();
						} else if (name.equals("enchantability")) {
							enchantability = reader.nextInt();
						} else {
							reader.skipValue();
						}
					}

					reader.endObject();
					reader.close();

				} catch (IOException e) {
					throw new JsonSyntaxException("Tool material '" + this.name + "' had a problem loading.");
				}

				this.harvestLevel = harvestLevel;
				this.maxUses = maxUses;
				this.efficiency = efficiency;
				this.damage = damage;
				this.enchantability = enchantability;
			} else {
				try {
					if (!directoryFile.exists()) {
						directoryFile.mkdirs();
					}

					if (toolFile.createNewFile()) {
						JsonWriter writer = new JsonWriter(new FileWriter(toolFile));
						writer.setIndent("\t");
						writer.setHtmlSafe(true);
						writer.beginObject();

						writer.name("harvestLevel").value(this.defaultHarvestLevel);
						writer.name("maxUses").value(this.defaultMaxUses);
						writer.name("efficiency").value(this.defaultEfficiency);
						writer.name("damage").value(this.defaultDamage);
						writer.name("enchantability").value(this.defaultEnchantability);

						writer.endObject();
						writer.close();

						// Reset to defaults if the file was deleted and already
						// had values
						this.harvestLevel = defaultHarvestLevel;
						this.maxUses = defaultMaxUses;
						this.efficiency = defaultEfficiency;
						this.damage = defaultDamage;
						this.enchantability = defaultEnchantability;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			GrimLog.debugInfo(currentPart, "Loaded Tool Material - '" + this.name + "_tool_material.json'. " + this.toString());
		}
	}

	public static class ArmorMaterialHolder {
		private final String name;
		private final int defaultDurability;
		private final int[] defaultReductionAmounts;
		private final int defaultEnchantability;
		private final float defaultToughness;

		private int durability;
		private int[] reductionAmounts;
		private int enchantability;
		private float toughness;

		public ArmorMaterialHolder(String name, int durability, int[] reductionAmounts, int enchantability, float toughness) {
			this.name = name;
			this.durability = durability;
			this.reductionAmounts = reductionAmounts;
			this.enchantability = enchantability;
			this.toughness = toughness;

			this.defaultDurability = durability;
			this.defaultReductionAmounts = reductionAmounts.clone();
			this.defaultEnchantability = enchantability;
			this.defaultToughness = toughness;
		}

		public String getName() {
			return name;
		}

		public int getDurability() {
			return durability;
		}

		public int[] getReductionAmounts() {
			return reductionAmounts;
		}

		public int getEnchantability() {
			return enchantability;
		}

		public float getToughness() {
			return toughness;
		}

		@Override
		public String toString() {
			return "[Name:" + getName() + ", Durability:" + getDurability() + ", ReductionAmounts:" + Arrays.toString(getReductionAmounts()) + ", Enchantability:" + getEnchantability() + ", Toughness:" + getToughness() + "]";
		}

		public void load() {
			File directoryFile = new File(GrimPack.configDir, GrimPack.modID + "/materials");
			File armorFile = new File(GrimPack.configDir, GrimPack.modID + "/materials/" + this.name + "_armor_material.json");

			if (armorFile.exists()) {
				int durability = this.defaultDurability;
				int[] reductionAmounts = this.defaultReductionAmounts.clone();
				int enchantability = this.defaultEnchantability;
				float toughness = this.defaultToughness;

				try {
					JsonReader reader = new JsonReader(new FileReader(armorFile));
					reader.beginObject();

					while (reader.hasNext()) {
						String name = reader.nextName();

						if (name.equals("durability")) {
							durability = reader.nextInt();
						} else if (name.equals("reductionAmounts")) {
							reader.beginArray();
							for (int i = 0; i < reductionAmounts.length; i++) {
								reductionAmounts[i] = reader.nextInt();
							}
							reader.endArray();
						} else if (name.equals("enchantability")) {
							enchantability = reader.nextInt();
						} else if (name.equals("toughness")) {
							toughness = (float) reader.nextDouble();
						} else {
							reader.skipValue();
						}
					}

					reader.endObject();
					reader.close();
				} catch (IOException e) {
					throw new JsonSyntaxException("Armor material '" + this.name + "' had a problem loading.");
				}

				this.durability = durability;
				this.reductionAmounts = reductionAmounts;
				this.enchantability = enchantability;
				this.toughness = toughness;

			} else {
				try {
					if (!directoryFile.exists()) {
						directoryFile.mkdirs();
					}

					if (armorFile.createNewFile()) {
						JsonWriter writer = new JsonWriter(new FileWriter(armorFile));
						writer.setIndent("\t");
						writer.setHtmlSafe(true);
						writer.beginObject();

						writer.name("durability").value(this.defaultDurability);
						writer.name("reductionAmounts");
						writer.beginArray();
						for (int amnt : this.defaultReductionAmounts) {
							writer.value(amnt);
						}
						writer.endArray();
						writer.name("enchantability").value(this.defaultEnchantability);
						writer.name("toughness").value(this.defaultToughness);

						writer.endObject();
						writer.close();

						// Reset to defaults if the file was deleted and already
						// had values
						this.durability = defaultDurability;
						this.reductionAmounts = defaultReductionAmounts.clone();
						this.enchantability = defaultEnchantability;
						this.toughness = defaultToughness;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			GrimLog.debugInfo(currentPart, "Loaded Armor Material - '" + this.name + "_armor_material.json'. " + this.toString());
		}
	}
}
