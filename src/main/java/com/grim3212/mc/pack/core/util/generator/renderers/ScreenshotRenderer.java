package com.grim3212.mc.pack.core.util.generator.renderers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ScreenShotHelper;

@SuppressWarnings("deprecation")
public class ScreenshotRenderer {

	public static void saveTrimmedScreenshot(File target, int width, int height) {
		saveTrimmedScreenshot(target, width, height, null);
	}

	public static void saveTrimmedScreenshot(File target, int width, int height, Color color) {
		saveTrimmedScreenshot(target, 0, 0, width, height, color);
	}

	public static void saveTrimmedScreenshot(File target, int x, int y, int width, int height) {
		saveTrimmedScreenshot(target, x, y, width, height, null);
	}

	public static void saveTrimmedScreenshot(File target, int x, int y, int width, int height, Color transparencyColor) {
		Minecraft mc = Minecraft.getInstance();
		NativeImage screenshot = ScreenShotHelper.createScreenshot(mc.mainWindow.getWidth(), mc.mainWindow.getHeight(), mc.getFramebuffer());
		NativeImage copy = new NativeImage(width, height, false);
		screenshot.resizeSubRectTo(x, y, width, height, copy);

		int[] pixels = screenshot.makePixelArray();

		BufferedImage bufferedimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		final int[] a = ((DataBufferInt) bufferedimage.getRaster().getDataBuffer()).getData();
		System.arraycopy(pixels, 0, a, 0, pixels.length);

		// Get screenshot of desired size by getting a subimage

		try {
			// Make transparent
			if (transparencyColor != null) {
				bufferedimage = makeColorTransparent(bufferedimage, transparencyColor);
			}

			// Write the file
			ImageIO.write(bufferedimage, "PNG", target.getCanonicalFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static BufferedImage makeColorTransparent(BufferedImage im, final Color color) {
		ImageFilter filter = new RGBImageFilter() {

			// the color we are looking for... Alpha bits are set to opaque
			public int markerRGB = color.getRGB() | 0xFF000000;

			public final int filterRGB(int x, int y, int rgb) {
				if ((rgb | 0xFF000000) == markerRGB) {
					// Mark the alpha bits as zero - transparent
					return 0x00FFFFFF & rgb;
				} else {
					return rgb;
				}
			}
		};

		// TODO: Possibly look for doing this all within a NATIVE IMAGE

		ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
		return imageToBufferedImage(Toolkit.getDefaultToolkit().createImage(ip));
	}

	private static BufferedImage imageToBufferedImage(Image image) {
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();

		return bufferedImage;
	}
}
