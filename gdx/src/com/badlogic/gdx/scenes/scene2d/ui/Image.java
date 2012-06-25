
package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Scaling;

/** Displays a {@link TextureRegion} or {@link NinePatch}, scaled various way within the widgets bounds. The preferred size is the
 * actual size of the region or ninepatch. Only when using a TextureRegion will the actor's scale, rotation, and origin be used
 * when drawing.
 * @author Nathan Sweet */
public class Image extends Widget {
	private TextureRegion region;
	private NinePatch patch;
	private Scaling scaling;
	private int align = Align.CENTER;
	private float imageX, imageY, imageWidth, imageHeight;

	/** Creates an image with no region or patch, stretched, and aligned center. */
	public Image () {
		this((TextureRegion)null);
	}

	/** Creates an image stretched, and aligned center. */
	public Image (Texture texture) {
		this(new TextureRegion(texture));
	}

	/** Creates an image aligned center. */
	public Image (Texture texture, Scaling scaling) {
		this(new TextureRegion(texture), scaling);
	}

	public Image (Texture texture, Scaling scaling, int align) {
		this(new TextureRegion(texture), scaling, align);
	}

	/** Creates an image stretched, and aligned center.
	 * @param region May be null. */
	public Image (TextureRegion region) {
		this(region, Scaling.stretch, Align.CENTER);
	}

	/** Creates an image aligned center.
	 * @param region May be null. */
	public Image (TextureRegion region, Scaling scaling) {
		this(region, scaling, Align.CENTER);
	}

	/** @param region May be null. */
	public Image (TextureRegion region, Scaling scaling, int align) {
		setRegion(region);
		this.scaling = scaling;
		this.align = align;
		setWidth(getPrefWidth());
		setHeight(getPrefHeight());
	}

	/** Creates an image stretched, and aligned center.
	 * @param patch May be null. */
	public Image (NinePatch patch) {
		this(patch, Scaling.stretch, Align.CENTER);
	}

	/** Creates an image aligned center.
	 * @param patch May be null. */
	public Image (NinePatch patch, Scaling scaling) {
		this(patch, scaling, Align.CENTER);
	}

	/** @param patch May be null. */
	public Image (NinePatch patch, Scaling scaling, int align) {
		setPatch(patch);
		this.scaling = scaling;
		this.align = align;
		setWidth(getPrefWidth());
		setHeight(getPrefHeight());
	}

	public void layout () {
		float regionWidth, regionHeight;
		if (patch != null) {
			regionWidth = patch.getTotalWidth();
			regionHeight = patch.getTotalHeight();
		} else if (region != null) {
			regionWidth = Math.abs(region.getRegionWidth());
			regionHeight = Math.abs(region.getRegionHeight());
		} else
			return;

		float width = getWidth();
		float height = getHeight();

		Vector2 size = scaling.apply(regionWidth, regionHeight, width, height);
		imageWidth = size.x;
		imageHeight = size.y;

		if ((align & Align.LEFT) != 0)
			imageX = 0;
		else if ((align & Align.RIGHT) != 0)
			imageX = (int)(width - imageWidth);
		else
			imageX = (int)(width / 2 - imageWidth / 2);

		if ((align & Align.TOP) != 0)
			imageY = (int)(height - imageHeight);
		else if ((align & Align.BOTTOM) != 0)
			imageY = 0;
		else
			imageY = (int)(height / 2 - imageHeight / 2);
	}

	public void draw (SpriteBatch batch, float parentAlpha) {
		validate();

		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		float x = getX();
		float y = getY();
		float scaleX = getScaleX();
		float scaleY = getScaleY();

		if (patch != null)
			patch.draw(batch, x + imageX, y + imageY, imageWidth * scaleX, imageHeight * scaleY);
		else if (region != null) {
			float rotation = getRotation();
			if (scaleX == 1 && scaleY == 1 && rotation == 0)
				batch.draw(region, x + imageX, y + imageY, imageWidth, imageHeight);
			else {
				batch.draw(region, x + imageX, y + imageY, getOriginX() - imageX, getOriginY() - imageY, imageWidth, imageHeight,
					scaleX, scaleY, rotation);
			}
		}
	}

	/** @param region May be null. */
	public void setRegion (TextureRegion region) {
		if (region != null) {
			if (this.region == region) return;
			if (getPrefWidth() != Math.abs(region.getRegionWidth()) || getPrefHeight() != Math.abs(region.getRegionHeight()))
				invalidateHierarchy();
		} else {
			if (getPrefWidth() != 0 || getPrefHeight() != 0) invalidateHierarchy();
		}
		this.region = region;
		patch = null;
	}

	public TextureRegion getRegion () {
		return region;
	}

	/** @param patch May be null. */
	public void setPatch (NinePatch patch) {
		if (patch != null) {
			if (this.patch == patch) return;
			if (getPrefWidth() != patch.getTotalWidth() || getPrefHeight() != patch.getTotalHeight()) invalidateHierarchy();
		} else {
			if (getPrefWidth() != 0 || getPrefHeight() != 0) invalidateHierarchy();
		}
		this.patch = patch;
		region = null;
	}

	public NinePatch getPatch () {
		return patch;
	}

	public void setScaling (Scaling scaling) {
		if (scaling == null) throw new IllegalArgumentException("scaling cannot be null.");
		this.scaling = scaling;
	}

	public void setAlign (int align) {
		this.align = align;
	}

	public float getMinWidth () {
		return 0;
	}

	public float getMinHeight () {
		return 0;
	}

	public float getPrefWidth () {
		if (region != null) return Math.abs(region.getRegionWidth());
		if (patch != null) return patch.getTotalWidth();
		return 0;
	}

	public float getPrefHeight () {
		if (region != null) return Math.abs(region.getRegionHeight());
		if (patch != null) return patch.getTotalHeight();
		return 0;
	}

	public float getImageX () {
		return imageX;
	}

	public float getImageY () {
		return imageY;
	}

	public float getImageWidth () {
		return imageWidth;
	}

	public float getImageHeight () {
		return imageHeight;
	}
}
