package com.grim3212.mc.pack.industry.client.model;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelItemTower extends ModelBase {
	public boolean topBlock = false;
	public boolean bottomBlock = false;
	public double nextRender = 0.0D;
	public ModelRenderer towerMain;
	public ModelRenderer towerMainInt;
	public ModelRenderer[] towerPost = new ModelRenderer[4];
	public ModelRenderer[] towerCap = new ModelRenderer[2];
	public ModelRenderer[] towerSideBar = new ModelRenderer[8];
	public ModelRenderer[] towerMidBar = new ModelRenderer[5];
	public ModelRenderer[] towerShelf1 = new ModelRenderer[5];
	public ModelRenderer[] towerShelf2 = new ModelRenderer[5];
	public ModelRenderer[] towerShelf3 = new ModelRenderer[5];
	public ModelRenderer[] towerShelf4 = new ModelRenderer[5];

	public int frame = 0;
	public boolean framedir = false;
	public int FramesCount = 100;

	public float[] BlankFrames = new float[100];

	public float[] TopSectionFramesY = new float[100];
	public float[] TopSectionFramesZ = new float[100];

	public float[] MidSectionFramesY = new float[100];
	public float[] MidSectionFramesZ = new float[100];

	public float[] BottomSectionFramesY = new float[100];
	public float[] BottomSectionFramesZ = new float[100];

	public ModelItemTower() {
		this.towerMain = new ModelRenderer(this, 0, 0).setTextureSize(128, 128);
		this.towerMainInt = new ModelRenderer(this, 0, 0).setTextureSize(128, 128);

		this.towerPost[0] = new ModelRenderer(this, 0, 80).setTextureSize(128, 128);
		this.towerPost[1] = new ModelRenderer(this, 12, 80).setTextureSize(128, 128);
		this.towerPost[2] = new ModelRenderer(this, 0, 99).setTextureSize(128, 128);
		this.towerPost[3] = new ModelRenderer(this, 12, 99).setTextureSize(128, 128);

		this.towerCap[0] = new ModelRenderer(this, 64, 0).setTextureSize(128, 128);
		this.towerCap[1] = new ModelRenderer(this, 64, 0).setTextureSize(128, 128);

		this.towerSideBar[0] = new ModelRenderer(this, 0, 32).setTextureSize(128, 128);
		this.towerSideBar[1] = new ModelRenderer(this, 0, 34).setTextureSize(128, 128);
		this.towerSideBar[2] = new ModelRenderer(this, 0, 32).setTextureSize(128, 128);
		this.towerSideBar[3] = new ModelRenderer(this, 0, 34).setTextureSize(128, 128);
		this.towerSideBar[4] = new ModelRenderer(this, 0, 32).setTextureSize(128, 128);
		this.towerSideBar[5] = new ModelRenderer(this, 0, 34).setTextureSize(128, 128);
		this.towerSideBar[6] = new ModelRenderer(this, 0, 32).setTextureSize(128, 128);
		this.towerSideBar[7] = new ModelRenderer(this, 0, 34).setTextureSize(128, 128);

		this.towerMidBar[0] = new ModelRenderer(this, 0, 45).setTextureSize(128, 128);
		this.towerMidBar[1] = new ModelRenderer(this, 48, 32).setTextureSize(128, 128);
		this.towerMidBar[2] = new ModelRenderer(this, 48, 32).setTextureSize(128, 128);
		this.towerMidBar[3] = new ModelRenderer(this, 48, 32).setTextureSize(128, 128);
		this.towerMidBar[4] = new ModelRenderer(this, 48, 32).setTextureSize(128, 128);

		this.towerMain.addBox(0.0F, 0.0F, 0.0F, 16, 16, 16);
		this.towerMainInt.addBox(16.01F, 16.01F, 16.01F, -16, -16, -16);

		this.towerPost[0].addBox(0.0F, 0.0F, 0.0F, 3, 16, 3);
		this.towerPost[1].addBox(0.0F, 0.0F, 13.0F, 3, 16, 3);
		this.towerPost[2].addBox(13.0F, 0.0F, 0.0F, 3, 16, 3);
		this.towerPost[3].addBox(13.0F, 0.0F, 13.0F, 3, 16, 3);

		this.towerCap[0].addBox(0.0F, 0.0F, 0.0F, 16, 1, 16);
		this.towerCap[1].addBox(0.0F, 15.0F, 0.0F, 16, 1, 16);

		this.towerSideBar[0].addBox(3.0F, 0.0F, 0.0F, 10, 1, 1);
		this.towerSideBar[1].addBox(0.0F, 0.0F, 3.0F, 1, 1, 10);
		this.towerSideBar[2].addBox(3.0F, 0.0F, 15.0F, 10, 1, 1);
		this.towerSideBar[3].addBox(15.0F, 0.0F, 3.0F, 1, 1, 10);
		this.towerSideBar[4].addBox(3.0F, 15.0F, 0.0F, 10, 1, 1);
		this.towerSideBar[5].addBox(0.0F, 15.0F, 3.0F, 1, 1, 10);
		this.towerSideBar[6].addBox(3.0F, 15.0F, 15.0F, 10, 1, 1);
		this.towerSideBar[7].addBox(15.0F, 15.0F, 3.0F, 1, 1, 10);

		this.towerMidBar[0].addBox(0.0F, 7.0F, 7.0F, 16, 2, 2);
		this.towerMidBar[1].addBox(6.0F, 0.0F, 6.0F, 4, 12, 4);
		this.towerMidBar[2].addBox(6.0F, 0.0F, 6.0F, 4, 16, 4);
		this.towerMidBar[3].addBox(6.0F, 4.0F, 6.0F, 4, 12, 4);
		this.towerMidBar[4].addBox(6.0F, 1.0F, 6.0F, 4, 14, 4);

		makeShelf(this.towerShelf1);
		makeShelf(this.towerShelf2);
		makeShelf(this.towerShelf3);
		makeShelf(this.towerShelf4);

		List<Float> bottomy = Lists.newArrayList();
		List<Float> bottomz = Lists.newArrayList();
		List<Float> midy = Lists.newArrayList();
		List<Float> midz = Lists.newArrayList();
		List<Float> topy = Lists.newArrayList();
		List<Float> topz = Lists.newArrayList();
		float height = 0.0F;
		float length = 0.0F;

		for (int i = 0; i < 100; i++) {
			height = i - 50.0F;
			height /= 50.0F;
			height = Math.abs(height);
			height = 1.0F - height;
			midy.add(height * 13.0F);

			if (i < 50) {
				midz.add(10.0F);
			} else
				midz.add(3.0F);

		}

		for (int i = 0; i < 100; i++) {
			height = i - 50.0F;
			height /= 50.0F;
			height = Math.abs(height);
			height = 1.0F - height;
			topy.add(height * 13.0F);

			if (i < 30) {
				topz.add(10.0F);
			}

			if ((i >= 30) && (i <= 70)) {
				length = i - 30;
				length /= 40.0F;

				topz.add(10.0F - length * 7.0F);
			}

			if (i > 70) {
				topz.add(3.0F);
			}

		}

		for (int i = 0; i < 100; i++) {
			height = i - 50.0F;
			height /= 50.0F;
			height = Math.abs(height);
			bottomy.add(height * 13.0F);

			if (i < 30) {
				bottomz.add(3.0F);
			}

			if ((i >= 30) && (i <= 70)) {
				length = i - 30;
				length /= 40.0F;

				bottomz.add(3.0F + length * 7.0F);
			}

			if (i > 70) {
				bottomz.add(10.0F);
			}

		}

		for (int i = 0; i < this.FramesCount; i++) {
			this.BottomSectionFramesY[i] = ((Float) bottomy.get(i)).floatValue();
			this.BottomSectionFramesZ[i] = ((Float) bottomz.get(i)).floatValue();

			this.MidSectionFramesY[i] = ((Float) midy.get(i)).floatValue();
			this.MidSectionFramesZ[i] = ((Float) midz.get(i)).floatValue();

			this.TopSectionFramesY[i] = ((Float) topy.get(i)).floatValue();
			this.TopSectionFramesZ[i] = ((Float) topz.get(i)).floatValue();
		}
	}

	public void makeShelf(ModelRenderer[] model) {
		model[0] = new ModelRenderer(this, 0, 64).setTextureSize(128, 128);
		model[1] = new ModelRenderer(this, 0, 68).setTextureSize(128, 128);
		model[2] = new ModelRenderer(this, 0, 68).setTextureSize(128, 128);
		model[3] = new ModelRenderer(this, 0, 72).setTextureSize(128, 128);
		model[4] = new ModelRenderer(this, 0, 72).setTextureSize(128, 128);

		model[0].addBox(1.0F, 0.0F, 0.0F, 14, 1, 3);
		model[1].addBox(1.0F, 1.0F, 0.0F, 1, 1, 3);
		model[2].addBox(14.0F, 1.0F, 0.0F, 1, 1, 3);
		model[3].addBox(2.0F, 1.0F, 0.0F, 12, 1, 1);
		model[4].addBox(2.0F, 1.0F, 2.0F, 12, 1, 1);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		callRender(f5);
	}

	public void renderModel(float f1) {
		callRender(f1);
	}

	public void renderModelInventory(float f1) {
		this.towerMain.render(f1);
		this.towerMainInt.render(f1);
		this.towerMidBar[0].render(f1);
		this.towerMidBar[4].render(f1);

		for (ModelRenderer model : this.towerPost) {
			model.render(f1);
		}

		for (ModelRenderer model : this.towerCap) {
			model.render(f1);
		}
	}

	public void callRender(float f1) {
		if ((this.bottomBlock) && (this.topBlock)) {
			this.towerMain.render(f1);
			this.towerMainInt.render(f1);
			this.towerMidBar[0].render(f1);
			this.towerMidBar[2].render(f1);
		}

		if ((!this.bottomBlock) && (!this.topBlock)) {
			this.towerMain.render(f1);
			this.towerMainInt.render(f1);
			this.towerMidBar[0].render(f1);
			this.towerMidBar[4].render(f1);
		}

		if ((this.bottomBlock) && (this.topBlock)) {
			for (ModelRenderer model : this.towerSideBar) {
				model.render(f1);
			}

		}

		if ((this.bottomBlock) && (!this.topBlock)) {
			this.towerSideBar[0].render(f1);
			this.towerSideBar[1].render(f1);
			this.towerSideBar[2].render(f1);
			this.towerSideBar[3].render(f1);
			this.towerMidBar[1].render(f1);
		}

		if ((!this.bottomBlock) && (this.topBlock)) {
			this.towerSideBar[4].render(f1);
			this.towerSideBar[5].render(f1);
			this.towerSideBar[6].render(f1);
			this.towerSideBar[7].render(f1);
			this.towerMidBar[3].render(f1);
		}

		for (ModelRenderer model : this.towerPost) {
			model.render(f1);
		}

		if (!this.bottomBlock)
			this.towerCap[0].render(f1);
		if (!this.topBlock)
			this.towerCap[1].render(f1);

		processFrame(f1);
	}

	public void setAnimation(int animation) {
		if (animation > 0) {
			this.framedir = true;
			this.frame = 0;
		}

		if (animation < 0) {
			this.framedir = false;
			this.frame = 25;
		}
	}

	public void processFrame(float f1) {
		if ((System.currentTimeMillis() > this.nextRender) && ((this.topBlock) || (this.bottomBlock))) {
			if (this.framedir) {
				if (this.frame < 25) {
					this.frame += 1;
				}
			} else if (this.frame > 0) {
				this.frame -= 1;
			}

		}

		renderFramePosition(this.towerShelf1, this.frame, 5, f1);
		renderFramePosition(this.towerShelf2, this.frame, 30, f1);
		renderFramePosition(this.towerShelf3, this.frame, 55, f1);
		renderFramePosition(this.towerShelf4, this.frame, 80, f1);
		this.nextRender = (System.currentTimeMillis() + 1.0D);
	}

	public void renderFramePosition(ModelRenderer[] model, int frame, int i, float f1) {
		int i2 = frame;
		i2 += i;
		while (i2 > 100)
			i2 -= 100;
		if (i2 > 0)
			i2--;

		float x = 0.0F;
		float y = getFrameYTable()[i2] / 16.0F;
		float z = getFrameZTable()[i2] / 16.0F;

		GL11.glTranslatef(x, y, z);
		for (ModelRenderer section : model) {
			section.render(f1);
		}
		GL11.glTranslatef(-x, -y, -z);
	}

	public float[] getFrameYTable() {
		if ((this.topBlock) && (!this.bottomBlock)) {
			return this.BottomSectionFramesY;
		}

		if ((!this.topBlock) && (this.bottomBlock)) {
			return this.TopSectionFramesY;
		}

		if (((this.topBlock) && (this.bottomBlock)) || ((!this.topBlock) && (!this.bottomBlock))) {
			return this.MidSectionFramesY;
		}

		return this.BlankFrames;
	}

	public float[] getFrameZTable() {
		if ((this.topBlock) && (!this.bottomBlock)) {
			return this.BottomSectionFramesZ;
		}

		if ((!this.topBlock) && (this.bottomBlock)) {
			return this.TopSectionFramesZ;
		}

		if (((this.topBlock) && (this.bottomBlock)) || ((!this.topBlock) && (!this.bottomBlock))) {
			return this.MidSectionFramesZ;
		}

		return this.BlankFrames;
	}
}