package com.grim3212.mc.pack.core.util.generator;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.util.text.TextFormatting;

public class GeneratorUtil {

	/**
	 * Converts an instruction manual description and the various color codes
	 * into html tags instead
	 * 
	 * @param s
	 *            String to remove format codes from
	 * @return
	 */
	public static String changeFormatCodes(String s) {
		String combined = s;

		// For the 15 colors in TextFormatting
		// Start at one because we don't replace black
		for (int i = 1; i < 16; i++) {
			String[] matches = StringUtils.substringsBetween(combined, "<f>" + stripControl(TextFormatting.fromColorIndex(i)), "<f>0");

			if (matches != null) {
				String[] search = new String[matches.length];
				for (int x = 0; x < search.length; x++) {
					search[x] = "<f>" + stripControl(TextFormatting.fromColorIndex(i)) + matches[x] + "<f>0";
				}

				String[] replacements = new String[matches.length];
				for (int j = 0; j < replacements.length; j++) {
					replacements[j] = "<font color='" + getColorNames(TextFormatting.fromColorIndex(i)) + "'>" + matches[j] + "</font>";
				}

				combined = StringUtils.replaceEach(combined, search, replacements);
			}
		}

		String[] bolds = StringUtils.substringsBetween(combined, "<f>l", "<f>r");
		if (bolds != null) {
			String[] search = new String[bolds.length];
			for (int i = 0; i < search.length; i++) {
				search[i] = "<f>l" + bolds[i] + "<f>r";
			}

			String[] breplacements = new String[bolds.length];
			for (int j = 0; j < breplacements.length; j++) {
				breplacements[j] = "<strong>" + bolds[j] + "</strong>";
			}

			combined = StringUtils.replaceEach(combined, search, breplacements);
		}

		String[] strikethroughs = StringUtils.substringsBetween(combined, "<f>m", "<f>r");
		if (strikethroughs != null) {
			String[] search = new String[strikethroughs.length];
			for (int i = 0; i < search.length; i++) {
				search[i] = "<f>m" + strikethroughs[i] + "<f>r";
			}

			String[] sreplacements = new String[bolds.length];
			for (int j = 0; j < search.length; j++) {
				sreplacements[j] = "<del>" + strikethroughs[j] + "</del>";
			}
			combined = StringUtils.replaceEach(combined, search, sreplacements);
		}

		String[] underlines = StringUtils.substringsBetween(combined, "<f>n", "<f>r");
		if (underlines != null) {
			String[] search = new String[underlines.length];
			for (int i = 0; i < search.length; i++) {
				search[i] = "<f>n" + underlines[i] + "<f>r";
			}

			String[] ureplacements = new String[bolds.length];
			for (int j = 0; j < ureplacements.length; j++) {
				ureplacements[j] = "<u>" + underlines[j] + "</u>";
			}
			combined = StringUtils.replaceEach(combined, search, ureplacements);
		}

		String[] italics = StringUtils.substringsBetween(combined, "<f>o", "<f>r");
		if (italics != null) {
			String[] search = new String[italics.length];
			for (int i = 0; i < search.length; i++) {
				search[i] = "<f>o" + italics[i] + "<f>r";
			}

			String[] ireplacements = new String[bolds.length];
			for (int j = 0; j < ireplacements.length; j++) {
				ireplacements[j] = "<i>" + italics[j] + "</i>";
			}
			combined = StringUtils.replaceEach(combined, search, ireplacements);
		}

		return combined;
	}

	private static String stripControl(TextFormatting format) {
		return format.toString().substring(1);
	}

	public static String nameToHtml(String s) {
		if (s.contains("§")) {
			int num = StringUtils.countMatches(s, "§");

			for (int i = 0; i < num; i++) {
				char color = s.charAt(s.indexOf("§") + 1);

				TextFormatting text = fromColorCode(color);
				if (text != null) {
					if (text.isColor()) {
						s = s.replaceFirst("[§].{1}", "<font color='" + getColorNames(text) + "'>");
						s = s + "</font>";
					} else {
						if (text == TextFormatting.BOLD) {
							s = s.replaceFirst("[§].{1}", "<strong>");
							s = s + "</strong>";
						} else if (text == TextFormatting.UNDERLINE) {
							s = s.replaceFirst("[§].{1}", "<strong>");
							s = s + "</strong>";
						} else if (text == TextFormatting.ITALIC) {
							s = s.replaceFirst("[§].{1}", "<i>");
							s = s + "</i>";
						} else if (text == TextFormatting.STRIKETHROUGH) {
							s = s.replaceFirst("[§].{1}", "<del>");
							s = s + "</del>";
						} else {
							// If not a supported code then remove it
							s = s.replaceFirst("[§].{1}", "");
						}
					}
				} else {
					// If not a color then remove it
					s = s.replaceFirst("[§].{1}", "");
				}
			}
		}

		return s;
	}

	public static TextFormatting fromColorCode(char code) {
		for (TextFormatting textformatting : TextFormatting.values()) {
			if (stripControl(textformatting).charAt(0) == code) {
				return textformatting;
			}
		}

		return null;
	}

	private static String getColorNames(TextFormatting format) {
		switch (format) {
		case DARK_BLUE:
		case DARK_GRAY:
		case DARK_GREEN:
		case DARK_RED:
			return format.getFriendlyName().replaceFirst("_", "");
		case DARK_AQUA:
			return "darkcyan";
		case DARK_PURPLE:
			return "purple";
		case LIGHT_PURPLE:
			return "pink";
		case RED:
		case WHITE:
		case YELLOW:
		case AQUA:
		case BLACK:
		case BLUE:
		case GOLD:
		case GRAY:
		case GREEN:
		default:
			return format.getFriendlyName();
		}
	}
}
