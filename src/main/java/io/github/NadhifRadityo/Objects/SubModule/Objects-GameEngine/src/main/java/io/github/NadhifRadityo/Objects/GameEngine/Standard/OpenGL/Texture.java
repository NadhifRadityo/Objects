package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL;

import com.sun.imageio.plugins.jpeg.JPEGImageReader;
import com.sun.imageio.plugins.png.PNGImageReader;
import com.twelvemonkeys.imageio.plugins.hdr.HDRImageReadParam;
import com.twelvemonkeys.imageio.plugins.hdr.HDRImageReader;
import com.twelvemonkeys.imageio.plugins.hdr.tonemap.NullToneMapper;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IVec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Size;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ByteUtils;
import io.github.NadhifRadityo.Objects.Utilizations.GenTypeUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ImageUtils;
import io.github.NadhifRadityo.Objects.Utilizations.PrivilegedUtils;
import io.github.NadhifRadityo.Objects.Utilizations.UnsafeUtils;
import org.newdawn.slick.opengl.LoadableImageData;
import sun.misc.Unsafe;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Iterator;

public class Texture<ID extends Number> extends OpenGLNativeHolder<ID> implements OpenGLNativeHolder.BindableNative, OpenGLNativeHolder.SlotableNative {
	public static final String TYPE = "TEXTURE";
	protected static final int clearSize = 512;
	protected static final ByteBuffer clearBuffer = BufferUtils.createByteBuffer(clearSize * clearSize * 4);
	protected final int target;
	protected final Size size;

	public Texture(GLContext<ID> gl, int target) {
		super(gl);
		this.target = target;
		this.size = new Size();
	}

	public int getTarget() { return target; }
	public Size getSize() { return size; }
	@Override public boolean isBind() { return getBindableNative(getGL(), getIdentifier(getCurrentSlot())) == this; }
	@Override public int getCurrentSlot() { return getSlotableNative(getGL(), TYPE, this); }

	public void setTextureFilter(int textureFilter) {
		assertNotDead(); assertCreated(); assertBind(); assertNotSlot();
		getGL().glTexParameteri(target, getGL().GL_TEXTURE_MAG_FILTER(), textureFilter);
		getGL().glTexParameteri(target, getGL().GL_TEXTURE_MIN_FILTER(), textureFilter);
	}
	public void setTextureWrap(int textureWrap) {
		assertNotDead(); assertCreated(); assertBind(); assertNotSlot();
		getGL().glTexParameteri(target, getGL().GL_TEXTURE_WRAP_S(), textureWrap);
		getGL().glTexParameteri(target, getGL().GL_TEXTURE_WRAP_T(), textureWrap);
	}

	public void asSize(int level, int internalFormat, int width, int height) {
		assertNotDead(); assertCreated(); assertBind(); assertNotSlot();
		int max = getGL().getMaxTextureSize();
		if(width > max || height > max) newException("Attempt to allocate a texture to big for the current hardware. Max: " + max);
		getGL().glTexStorage2D(target, level, internalFormat, width, height); size.set(width, height);
	}
	public void asSize(int level, int width, int height) { asSize(level, getGL().GL_RGBA8(), width, height); }
	public void asSize(int width, int height) { asSize(getGL().GL_RGBA8(), width, height); }
	public void asSize(int level, int internalFormat, Size size) { asSize(level, internalFormat, size.getWidth(), size.getHeight()); }
	public void asSize(int level, Size size) { asSize(level, getGL().GL_RGBA8(), size); }
	public void asSize(Size size) { asSize(0, size); }

	public void load(ByteBuffer buffer, int level, int internalFormat, int width, int height, int border, int format, int type) {
		assertNotDead(); assertCreated(); assertBind(); assertNotSlot();
		int max = getGL().getMaxTextureSize();
		if(width > max || height > max) newException("Attempt to allocate a texture to big for the current hardware. Max: " + max);
		getGL().glTexImage2D(target, level, internalFormat, width, height, border, format, type, buffer); size.set(width, height);
	}
	public void load(ByteBuffer buffer, int level, int internalFormat, int width, int height, int border, int format) { load(buffer, level, internalFormat, width, height, border, format, getGL().GL_UNSIGNED_BYTE()); }
	public void load(ByteBuffer buffer, int level, int internalFormat, int width, int height, int border) { load(buffer, level, internalFormat, width, height, border, getGL().GL_RGBA()); }
	public void load(ByteBuffer buffer, int level, int internalFormat, int width, int height) { load(buffer, level, internalFormat, width, height, 0); }
	public void load(ByteBuffer buffer, int level, int width, int height) { load(buffer, level, getGL().GL_RGBA8(), width, height); }
	public void load(ByteBuffer buffer, int width, int height) { load(buffer, 0, width, height); }
	public void load(ByteBuffer buffer, int level, int internalFormat, int width, int height, int border, boolean alpha, int type) { load(buffer, level, internalFormat, width, height, border, alpha ? getGL().GL_RGBA() : getGL().GL_RGB(), type); }
	public void load(ByteBuffer buffer, int level, int internalFormat, int width, int height, int border, boolean alpha) { load(buffer, level, internalFormat, width, height, border, alpha, getGL().GL_UNSIGNED_BYTE()); }
	public void load(ByteBuffer buffer, int level, int internalFormat, int width, int height, boolean alpha) { load(buffer, level, internalFormat, width, height, 0, alpha); }
	public void load(ByteBuffer buffer, int level, int width, int height, boolean alpha) { load(buffer, level, getGL().GL_RGBA8(), width, height, alpha); }
	public void load(ByteBuffer buffer, int width, int height, boolean alpha) { load(buffer, 0, width, height, alpha); }
	public void load(ByteBuffer buffer, int level, int internalFormat, Size size, int border, int format, int type) { load(buffer, level, internalFormat, size.getWidth(), size.getHeight(), border, format, type); }
	public void load(ByteBuffer buffer, int level, int internalFormat, Size size, int border, int format) { load(buffer, level, internalFormat, size, border, format, getGL().GL_UNSIGNED_BYTE()); }
	public void load(ByteBuffer buffer, int level, int internalFormat, Size size, int border) { load(buffer, level, internalFormat, size, border, getGL().GL_RGBA()); }
	public void load(ByteBuffer buffer, int level, int internalFormat, Size size) { load(buffer, level, internalFormat, size, 0); }
	public void load(ByteBuffer buffer, int level, Size size) { load(buffer, level, getGL().GL_RGBA8(), size); }
	public void load(ByteBuffer buffer, Size size) { load(buffer, 0, size); }
	public void load(ByteBuffer buffer, int level, int internalFormat, Size size, int border, boolean alpha, int type) { load(buffer, level, internalFormat, size, border, alpha ? getGL().GL_RGBA() : getGL().GL_RGB(), type); }
	public void load(ByteBuffer buffer, int level, int internalFormat, Size size, int border, boolean alpha) { load(buffer, level, internalFormat, size, border, alpha, getGL().GL_UNSIGNED_BYTE()); }
	public void load(ByteBuffer buffer, int level, int internalFormat, Size size, boolean alpha) { load(buffer, level, internalFormat, size, 0, alpha); }
	public void load(ByteBuffer buffer, int level, Size size, boolean alpha) { load(buffer, level, getGL().GL_RGBA8(), size, alpha); }
	public void load(ByteBuffer buffer, Size size, boolean alpha) { load(buffer, 0, size, alpha); }
	public void load(ByteBuffer buffer, int level, int internalFormat, int width, int height, int border, short depth, int type) { load(buffer, level, internalFormat, width, height, border, depth == 32, type); }
	public void load(ByteBuffer buffer, int level, int internalFormat, int width, int height, int border, short depth) { load(buffer, level, internalFormat, width, height, border, depth, getGL().GL_UNSIGNED_BYTE()); }
	public void load(ByteBuffer buffer, int level, int internalFormat, int width, int height, short depth) { load(buffer, level, internalFormat, width, height, 0, depth); }
	public void load(ByteBuffer buffer, int level, int width, int height, short depth) { load(buffer, level, getGL().GL_RGBA8(), width, height, depth); }
	public void load(ByteBuffer buffer, int width, int height, short depth) { load(buffer, 0, width, height, depth); }
	public void load(ByteBuffer buffer, int level, int internalFormat, Size size, int border, short depth, int type) { load(buffer, level, internalFormat, size.getWidth(), size.getHeight(), border, depth == 32, type); }
	public void load(ByteBuffer buffer, int level, int internalFormat, Size size, int border, short depth) { load(buffer, level, internalFormat, size, border, depth, getGL().GL_UNSIGNED_BYTE()); }
	public void load(ByteBuffer buffer, int level, int internalFormat, Size size, short depth) { load(buffer, level, internalFormat, size, 0, depth); }
	public void load(ByteBuffer buffer, int level, Size size, short depth) { load(buffer, level, getGL().GL_RGBA8(), size, depth); }
	public void load(ByteBuffer buffer, Size size, short depth) { load(buffer, 0, size, depth); }
	public void load(LoadableImageData imageData, int level, int internalFormat, int border, int format, int type) { load(imageData.getImageBufferData(), level, internalFormat, imageData.getWidth(), imageData.getHeight(), border, format < 0 ? imageData.getDepth() == 32 ? getGL().GL_RGBA() : getGL().GL_RGB() : format, type); BufferUtils.deallocate(imageData.getImageBufferData()); }
	public void load(LoadableImageData imageData, int level, int internalFormat, int border, int format) { load(imageData, level, internalFormat, border, format, getGL().GL_UNSIGNED_BYTE()); }
	public void load(LoadableImageData imageData, int level, int internalFormat, int border) { load(imageData, level, internalFormat, border, -1); }
	public void load(LoadableImageData imageData, int level, int internalFormat) { load(imageData, level, internalFormat, 0); }
	public void load(LoadableImageData imageData, int level) { load(imageData, level, getGL().GL_RGBA8()); }
	public void load(LoadableImageData imageData) { load(imageData, 0); }
	public void _load(LoadableImageData imageData, int level, int internalFormat, int border, boolean alpha, int type) { load(imageData, level, internalFormat, border, alpha ? getGL().GL_RGBA() : getGL().GL_RGB(), type); }
	public void _load(LoadableImageData imageData, int level, int internalFormat, int border, boolean alpha) { _load(imageData, level, internalFormat, border, alpha, getGL().GL_UNSIGNED_BYTE()); }
	public void _load(LoadableImageData imageData, int level, int internalFormat, boolean alpha) { _load(imageData, level, internalFormat, 0, alpha); }
	public void _load(LoadableImageData imageData, int level, boolean alpha) { _load(imageData, level, getGL().GL_RGBA8(), alpha); }
	public void _load(LoadableImageData imageData, boolean alpha) { _load(imageData, 0, alpha); }
	public void _load(LoadableImageData imageData) { _load(imageData, imageData.getDepth() == 32); }
	public void load(InputStream inputStream, boolean flipped, boolean forceAlpha, int[] transparent/**/, int level, int internalFormat, int border, int format, int type) { load(getImageData(inputStream, flipped, forceAlpha, transparent), level, internalFormat, border, format, type); }
	public void load(InputStream inputStream, boolean flipped, boolean forceAlpha, int[] transparent/**/, int level, int internalFormat, int border, int format) { load(inputStream, flipped, forceAlpha, transparent, level, internalFormat, border, format, getGL().GL_UNSIGNED_BYTE()); }
	public void load(InputStream inputStream, boolean flipped, boolean forceAlpha, int[] transparent/**/, int level, int internalFormat, int border) { load(inputStream, flipped, forceAlpha, transparent, level, internalFormat, border, -1); }
	public void load(InputStream inputStream, boolean flipped, boolean forceAlpha, int[] transparent/**/, int level, int internalFormat) { load(inputStream, flipped, forceAlpha, transparent, level, internalFormat, 0); }
	public void load(InputStream inputStream, boolean flipped, boolean forceAlpha, int[] transparent/**/, int level) { load(inputStream, flipped, forceAlpha, transparent, level, getGL().GL_RGBA8()); }
	public void load(InputStream inputStream, boolean flipped, boolean forceAlpha, int[] transparent/**/) { load(inputStream, flipped, forceAlpha, transparent, 0); }
	public void _load(InputStream inputStream, boolean flipped, boolean forceAlpha, int[] transparent/**/, int level, int internalFormat, int border, boolean alpha, int type) { load(inputStream, flipped, forceAlpha, transparent, level, internalFormat, border, alpha ? getGL().GL_RGBA() : getGL().GL_RGB(), type); }
	public void _load(InputStream inputStream, boolean flipped, boolean forceAlpha, int[] transparent/**/, int level, int internalFormat, int border, boolean alpha) { _load(inputStream, flipped, forceAlpha, transparent, level, internalFormat, border, alpha, getGL().GL_UNSIGNED_BYTE()); }
	public void _load(InputStream inputStream, boolean flipped, boolean forceAlpha, int[] transparent/**/, int level, int internalFormat, boolean alpha) { _load(inputStream, flipped, forceAlpha, transparent, level, internalFormat, 0, alpha); }
	public void _load(InputStream inputStream, boolean flipped, boolean forceAlpha, int[] transparent/**/, int level, boolean alpha) { _load(inputStream, flipped, forceAlpha, transparent, level, getGL().GL_RGBA8(), alpha); }
	public void _load(InputStream inputStream, boolean flipped, boolean forceAlpha, int[] transparent/**/, boolean alpha) { _load(inputStream, flipped, forceAlpha, transparent, 0, alpha); }
	public void _load(InputStream inputStream, boolean flipped, boolean forceAlpha, int[] transparent) { _load(getImageData(inputStream, flipped, forceAlpha, transparent)); }
	public void _load(InputStream inputStream, boolean flipped, boolean forceAlpha) { _load(inputStream, flipped, forceAlpha, null); }
	public void _load(InputStream inputStream, boolean forceAlpha) { _load(inputStream, forceAlpha, false); }
	public void _load(InputStream inputStream) { _load(inputStream, false); }
	public void __load(InputStream inputStream/**/, int level, int internalFormat, int border, int format, int type) { load(getImageData(inputStream, false, false, null), level, internalFormat, border, format, type); }
	public void __load(InputStream inputStream/**/, int level, int internalFormat, int border, int format) { __load(inputStream, level, internalFormat, border, format, getGL().GL_UNSIGNED_BYTE()); }
	public void __load(InputStream inputStream/**/, int level, int internalFormat, int border) { __load(inputStream, level, internalFormat, border, -1); }
	public void __load(InputStream inputStream/**/, int level, int internalFormat) { __load(inputStream, level, internalFormat, 0); }
	public void __load(InputStream inputStream/**/, int level) { __load(inputStream, level, getGL().GL_RGBA8()); }
	public void __load(InputStream inputStream/**/) { __load(inputStream, 0); }
	public void __load(InputStream inputStream/**/, int level, int internalFormat, int border, boolean alpha, int type) { __load(inputStream, level, internalFormat, border, alpha ? getGL().GL_RGBA() : getGL().GL_RGB(), type); }
	public void __load(InputStream inputStream/**/, int level, int internalFormat, int border, boolean alpha) { __load(inputStream, level, internalFormat, border, alpha, getGL().GL_UNSIGNED_BYTE()); }
	public void __load(InputStream inputStream/**/, int level, int internalFormat, boolean alpha) { __load(inputStream, level, internalFormat, 0, alpha); }
	public void __load(InputStream inputStream/**/, int level, boolean alpha) { __load(inputStream, level, getGL().GL_RGBA8(), alpha); }
	public void __load(InputStream inputStream/**/, boolean alpha) { __load(inputStream, 0, alpha); }
	public void load(File file, boolean flipped, boolean forceAlpha, int[] transparent/**/, int level, int internalFormat, int border, int format, int type) throws FileNotFoundException { load(new FileInputStream(file), flipped, forceAlpha, transparent, level, internalFormat, border, format, type); }
	public void load(File file, boolean flipped, boolean forceAlpha, int[] transparent/**/, int level, int internalFormat, int border, int format) throws FileNotFoundException { load(file, flipped, forceAlpha, transparent, level, internalFormat, border, format, getGL().GL_UNSIGNED_BYTE()); }
	public void load(File file, boolean flipped, boolean forceAlpha, int[] transparent/**/, int level, int internalFormat, int border) throws FileNotFoundException { load(file, flipped, forceAlpha, transparent, level, internalFormat, border, -1); }
	public void load(File file, boolean flipped, boolean forceAlpha, int[] transparent/**/, int level, int internalFormat) throws FileNotFoundException { load(file, flipped, forceAlpha, transparent, level, internalFormat, 0); }
	public void load(File file, boolean flipped, boolean forceAlpha, int[] transparent/**/, int level) throws FileNotFoundException { load(file, flipped, forceAlpha, transparent, level, getGL().GL_RGBA8()); }
	public void load(File file, boolean flipped, boolean forceAlpha, int[] transparent/**/) throws FileNotFoundException { load(file, flipped, forceAlpha, transparent, 0); }
	public void _load(File file, boolean flipped, boolean forceAlpha, int[] transparent/**/, int level, int internalFormat, int border, boolean alpha, int type) throws FileNotFoundException { load(file, flipped, forceAlpha, transparent, level, internalFormat, border, alpha ? getGL().GL_RGBA() : getGL().GL_RGB(), type); }
	public void _load(File file, boolean flipped, boolean forceAlpha, int[] transparent/**/, int level, int internalFormat, int border, boolean alpha) throws FileNotFoundException { _load(file, flipped, forceAlpha, transparent, level, internalFormat, border, alpha, getGL().GL_UNSIGNED_BYTE()); }
	public void _load(File file, boolean flipped, boolean forceAlpha, int[] transparent/**/, int level, int internalFormat, boolean alpha) throws FileNotFoundException { _load(file, flipped, forceAlpha, transparent, level, internalFormat, 0, alpha); }
	public void _load(File file, boolean flipped, boolean forceAlpha, int[] transparent/**/, int level, boolean alpha) throws FileNotFoundException { _load(file, flipped, forceAlpha, transparent, level, getGL().GL_RGBA8(), alpha); }
	public void _load(File file, boolean flipped, boolean forceAlpha, int[] transparent/**/, boolean alpha) throws FileNotFoundException { _load(file, flipped, forceAlpha, transparent, 0, alpha); }
	public void _load(File file, boolean flipped, boolean forceAlpha, int[] transparent) throws FileNotFoundException { _load(new FileInputStream(file), flipped, forceAlpha, transparent); }
	public void _load(File file, boolean flipped, boolean forceAlpha) throws FileNotFoundException { _load(file, flipped, forceAlpha, null); }
	public void _load(File file, boolean forceAlpha) throws FileNotFoundException { _load(file, forceAlpha, false); }
	public void _load(File file) throws FileNotFoundException { _load(file, false); }
	public void __load(File file/**/, int level, int internalFormat, int border, int format, int type) throws FileNotFoundException { load(new FileInputStream(file), false, false, null, level, internalFormat, border, format, type); }
	public void __load(File file/**/, int level, int internalFormat, int border, int format) throws FileNotFoundException { __load(file, level, internalFormat, border, format, getGL().GL_UNSIGNED_BYTE()); }
	public void __load(File file/**/, int level, int internalFormat, int border) throws FileNotFoundException { __load(file, level, internalFormat, border, -1); }
	public void __load(File file/**/, int level, int internalFormat) throws FileNotFoundException { __load(file, level, internalFormat, 0); }
	public void __load(File file/**/, int level) throws FileNotFoundException { __load(file, level, getGL().GL_RGBA8()); }
	public void __load(File file/**/) throws FileNotFoundException { __load(file, 0); }
	public void __load(File file/**/, int level, int internalFormat, int border, boolean alpha, int type) throws FileNotFoundException { __load(file, level, internalFormat, border, alpha ? getGL().GL_RGBA() : getGL().GL_RGB(), type); }
	public void __load(File file/**/, int level, int internalFormat, int border, boolean alpha) throws FileNotFoundException { __load(file, level, internalFormat, border, alpha, getGL().GL_UNSIGNED_BYTE()); }
	public void __load(File file/**/, int level, int internalFormat, boolean alpha) throws FileNotFoundException { __load(file, level, internalFormat, 0, alpha); }
	public void __load(File file/**/, int level, boolean alpha) throws FileNotFoundException { __load(file, level, getGL().GL_RGBA8(), alpha); }
	public void __load(File file/**/, boolean alpha) throws FileNotFoundException { __load(file, 0, alpha); }

	public void reload(ByteBuffer buffer, int level, int x, int y, int width, int height, int format, int type) {
		assertNotDead(); assertCreated(); assertBind(); assertNotSlot();
		int max = getGL().getMaxTextureSize();
		if(width > max || height > max) newException("Attempt to allocate a texture to big for the current hardware. Max: " + max);
		if(size.x() == 0 || size.y() == 0) newException("Size not initialized");
		getGL().glTexSubImage2D(target, level, x, y, width, height, format, type, buffer);
	}
	public void reload(ByteBuffer buffer, int level, int x, int y, int width, int height, int format) { reload(buffer, level, x, y, width, height, format, getGL().GL_UNSIGNED_BYTE()); }
	public void reload(ByteBuffer buffer, int level, int x, int y, int width, int height, boolean alpha) { reload(buffer, level, x, y, width, height, alpha ? getGL().GL_RGBA() : getGL().GL_RGB()); }
	public void reload(ByteBuffer buffer, int x, int y, int width, int height, boolean alpha) { reload(buffer, 0, x, y, width, height, alpha); }
	public void reload(ByteBuffer buffer, int x, int y, Size size, boolean alpha) { reload(buffer, 0, x, y, size.getWidth(), size.getHeight(), alpha); }
	public void reload(ByteBuffer buffer, IVec2 pos, Size size, boolean alpha) { reload(buffer, pos.x(), pos.y(), size, alpha); }
	public void reload(ByteBuffer buffer, int level, int x, int y, int width, int height, short depth) { reload(buffer, level, x, y, width, height, depth == 32); }
	public void reload(ByteBuffer buffer, int x, int y, int width, int height, short depth) { reload(buffer, 0, x, y, width, height, depth); }
	public void reload(ByteBuffer buffer, int x, int y, Size size, short depth) { reload(buffer, 0, x, y, size.getWidth(), size.getHeight(), depth); }
	public void reload(ByteBuffer buffer, IVec2 pos, Size size, short depth) { reload(buffer, pos.x(), pos.y(), size, depth); }

	public void clear() {
		assertNotDead(); assertCreated(); assertBind(); assertNotSlot();
		int width = size.getWidth(); int height = size.getHeight();
		int clearWidth = Math.min(width, clearSize);
		int clearHeight = Math.min(height, clearSize);
		for(int y = 0; y < height; y += clearSize) {
			int h = Math.min(clearHeight, height - y);
			for(int x = 0; x < width; x += clearSize) {
				int w = Math.min(clearWidth, width - x);
				reload(clearBuffer, x, y, w, h, true);
			}
		}
	}

	public void generateMipmap() {
		assertNotDead(); assertCreated(); assertBind(); assertNotSlot();
		getGL().glGenerateMipmap(target);
	}

	@Override public void arrange() {
		getInstance().setId(getGL().createTexture());
	}
	@Override public void disarrange() {
		if(isBind()) unbind();
		if(getCurrentSlot() != SLOT_NULL) deallocateSlot();
		getGL().destroyTexture(getId());
	}

	@Override public void bind() {
		assertNotDead(); assertCreated(); assertNotBind();
		int slot = getCurrentSlot();
		BindableNative bindableNative = getBindableNative(getGL(), getIdentifier(slot));
		if(bindableNative != null) bindableNative.unbind();
		getGL().glBindTexture(target, getId());
		setBindableNative(getGL(), getIdentifier(slot), this);
	}
	@Override public void unbind() {
		assertNotDead(); assertCreated(); assertBind();
		getGL().glBindTexture(target, null);
		setBindableNative(getGL(), getIdentifier(getCurrentSlot()), null);
	}

	@Override public void allocateSlot() {
		assertNotDead(); assertCreated(); assertNotSlot();
		int slot = setSlotableNative(getGL(), TYPE, this);
		getGL().glActiveTexture(getGL().GL_TEXTURE()[slot]);
	}
	@Override public void deallocateSlot() {
		assertNotDead(); assertCreated(); assertSlot();
		getGL().glDisable(target); setSlotableNative(getGL(), TYPE, getCurrentSlot(), null);
	}

	protected String identifier;
	protected String[] identifiers;
	protected String getIdentifier(int slot) {
		if(slot == SlotableNative.SLOT_NULL) {
			if(identifier == null) identifier = TYPE + ":" + target + ":" + slot;
			return identifier;
		}
		if(identifiers == null) identifiers = new String[SlotableNative.MAX_SLOTS];
		if(identifiers[slot] == null) identifiers[slot] = TYPE + ":" + target + ":" + slot;
		return identifiers[slot];
	}

	protected static LoadableImageData getImageData(InputStream inputStream, boolean flipped, boolean forceAlpha, int[] transparent) {
		LoadableImageData result = new ImageData();
		try { result.loadImage(inputStream, flipped, forceAlpha, transparent); } catch(Exception e) { throw newException(e); }
		return result;
	}

	public static class ImageData implements LoadableImageData {
		public static final int TYPE_UNDEFINED = -1;
		public static final int TYPE_JPG = 0;
		public static final int TYPE_PNG = 1;
		public static final int TYPE_HDR = 2;

		protected int type = TYPE_UNDEFINED;
		protected ByteBuffer imageBuffer;
		protected BufferedImage image;

		@Override public void configureEdging(boolean edging) { throw new UnsupportedOperationException("Not yet implemented"); }
		@Override public ByteBuffer loadImage(InputStream fis) throws IOException { return loadImage(fis, false, null); }
		@Override public ByteBuffer loadImage(InputStream fis, boolean flipped, int[] transparent) throws IOException { return loadImage(fis, flipped, false, transparent); }
		@Override public ByteBuffer loadImage(InputStream fis, boolean flipped, boolean forceAlpha, int[] transparent) throws IOException {
			if(transparent != null) throw new UnsupportedOperationException("Not yet implemented");
			ImageInputStream input = null; ImageReader reader = null; try {
				input = ImageIO.createImageInputStream(fis);
				Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
				if(!readers.hasNext()) throw new IllegalArgumentException("No reader!");
				reader = readers.next(); reader.setInput(input);
				ImageReadParam param = reader.getDefaultReadParam();
				if(reader instanceof JPEGImageReader) {
					type = TYPE_JPG;
				} else if(reader instanceof PNGImageReader) {
					type = TYPE_PNG;
				} else if(reader instanceof HDRImageReader) {
					type = TYPE_HDR;
					((HDRImageReadParam) param).setToneMapper(new NullToneMapper());
				} else type = TYPE_UNDEFINED;
				image = reader.read(0, param);
			} finally { if(input != null) input.close(); if(reader != null) reader.dispose(); }
			if(image.getSampleModel().getNumBands() == 3 && forceAlpha || type == TYPE_JPG || type == TYPE_PNG) {
				BufferedImage oldImage = image;
				switch(type) {
					case TYPE_JPG: case TYPE_PNG: image = ImageUtils.newByteBufferedImage(oldImage.getWidth(), oldImage.getHeight()); break;
					case TYPE_HDR: image = ImageUtils.newFloatBufferedImage(oldImage.getWidth(), oldImage.getHeight()); break;
				}
				Graphics2D graphics = (Graphics2D) image.getGraphics();
				graphics.drawImage(oldImage, 0, 0, null);
				ImageUtils.free(oldImage);
			}
			switch(type) {
				case TYPE_JPG: case TYPE_PNG: {
					int stride = image.getSampleModel().getNumBands();
					byte[] raster = ImageUtils.getRasterByte(image);
					if(flipped) ImageUtils.flipImage(raster, 0, 0, image.getWidth(), image.getHeight(), image.getWidth(), image.getHeight(), stride);
					imageBuffer = BufferUtils.createByteBuffer(raster.length * Byte.BYTES);
					PrivilegedUtils.doPrivileged(() -> UnsafeUtils.copyMemory(raster, Unsafe.ARRAY_BYTE_BASE_OFFSET, null, BufferUtils.__getAddress(imageBuffer), imageBuffer.capacity()));
					break;
				}
				case TYPE_HDR: {
					int stride = image.getSampleModel().getNumBands();
					short[] raster = ByteUtils.toHalf(ImageUtils.getRasterFloat(image));
					if(flipped) ImageUtils.flipImage(raster, 0, 0, image.getWidth(), image.getHeight(), image.getWidth(), image.getHeight(), stride);
					imageBuffer = BufferUtils.createByteBuffer(raster.length * Short.BYTES);
					PrivilegedUtils.doPrivileged(() -> UnsafeUtils.copyMemory(raster, Unsafe.ARRAY_FLOAT_BASE_OFFSET, null, BufferUtils.__getAddress(imageBuffer), imageBuffer.capacity()));
					break;
				}
			}
			ImageUtils.free(image); return imageBuffer;
		}
		@Override public int getDepth() { return image.getSampleModel().getNumBands() * 8; }
		@Override public int getWidth() { return image.getWidth(); }
		@Override public int getHeight() { return image.getHeight(); }
		@Override public int getTexWidth() { return GenTypeUtils.fold2(getWidth()); }
		@Override public int getTexHeight() { return GenTypeUtils.fold2(getHeight()); }
		@Override public ByteBuffer getImageBufferData() { return imageBuffer; }
	}
}
