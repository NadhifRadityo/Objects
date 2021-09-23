package io.github.NadhifRadityo.Objects.$Utilizations;

import sun.awt.image.ByteComponentRaster;
import sun.awt.image.IntegerComponentRaster;
import sun.awt.image.ShortComponentRaster;

import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.ComponentSampleModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferDouble;
import java.awt.image.DataBufferFloat;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferShort;
import java.awt.image.DataBufferUShort;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import static io.github.NadhifRadityo.Objects.$Utilizations.NumberUtils.clamp;

public class ImageUtils {
	private static final Field FIELD_IntegerComponentRaster_data;
	private static final Field FIELD_IntegerComponentRaster_dataOffsets;
	private static final Field FIELD_ShortComponentRaster_data;
	private static final Field FIELD_ShortComponentRaster_dataOffsets;
	private static final Field FIELD_ByteComponentRaster_data;
	private static final Field FIELD_ByteComponentRaster_dataOffsets;
	private static final Field FIELD_DataBuffer_offsets;
	private static final Field FIELD_DataBufferInt_data;
	private static final Field FIELD_DataBufferInt_bankdata;
	private static final Field FIELD_DataBufferShort_data;
	private static final Field FIELD_DataBufferShort_bankdata;
	private static final Field FIELD_DataBufferUShort_data;
	private static final Field FIELD_DataBufferUShort_bankdata;
	private static final Field FIELD_DataBufferFloat_data;
	private static final Field FIELD_DataBufferFloat_bankdata;
	private static final Field FIELD_DataBufferDouble_data;
	private static final Field FIELD_DataBufferDouble_bankdata;
	private static final Field FIELD_DataBufferByte_data;
	private static final Field FIELD_DataBufferByte_bankdata;

	// TODO Cross platform. Like BufferUtils.deallocate
	static { try {
		FIELD_IntegerComponentRaster_data = IntegerComponentRaster.class.getDeclaredField("data");
		FIELD_IntegerComponentRaster_dataOffsets = IntegerComponentRaster.class.getDeclaredField("dataOffsets");
		FIELD_ShortComponentRaster_data = ShortComponentRaster.class.getDeclaredField("data");
		FIELD_ShortComponentRaster_dataOffsets = ShortComponentRaster.class.getDeclaredField("dataOffsets");
		FIELD_ByteComponentRaster_data = ByteComponentRaster.class.getDeclaredField("data");
		FIELD_ByteComponentRaster_dataOffsets = ByteComponentRaster.class.getDeclaredField("dataOffsets");
		FIELD_DataBuffer_offsets = DataBuffer.class.getDeclaredField("offsets");
		FIELD_DataBufferInt_data = DataBufferInt.class.getDeclaredField("data");
		FIELD_DataBufferInt_bankdata = DataBufferInt.class.getDeclaredField("bankdata");
		FIELD_DataBufferShort_data = DataBufferShort.class.getDeclaredField("data");;
		FIELD_DataBufferShort_bankdata = DataBufferShort.class.getDeclaredField("bankdata");
		FIELD_DataBufferUShort_data = DataBufferUShort.class.getDeclaredField("data");;
		FIELD_DataBufferUShort_bankdata = DataBufferUShort.class.getDeclaredField("bankdata");
		FIELD_DataBufferFloat_data = DataBufferFloat.class.getDeclaredField("data");;
		FIELD_DataBufferFloat_bankdata = DataBufferFloat.class.getDeclaredField("bankdata");
		FIELD_DataBufferDouble_data = DataBufferDouble.class.getDeclaredField("data");;
		FIELD_DataBufferDouble_bankdata = DataBufferDouble.class.getDeclaredField("bankdata");
		FIELD_DataBufferByte_data = DataBufferByte.class.getDeclaredField("data");;
		FIELD_DataBufferByte_bankdata = DataBufferByte.class.getDeclaredField("bankdata");

		FIELD_IntegerComponentRaster_data.setAccessible(true);
		FIELD_IntegerComponentRaster_dataOffsets.setAccessible(true);
		FIELD_ShortComponentRaster_data.setAccessible(true);
		FIELD_ShortComponentRaster_dataOffsets.setAccessible(true);
		FIELD_ByteComponentRaster_data.setAccessible(true);
		FIELD_ByteComponentRaster_dataOffsets.setAccessible(true);
		FIELD_DataBuffer_offsets.setAccessible(true);
		FIELD_DataBufferInt_data.setAccessible(true);
		FIELD_DataBufferInt_bankdata.setAccessible(true);
		FIELD_DataBufferShort_data.setAccessible(true);
		FIELD_DataBufferShort_bankdata.setAccessible(true);
		FIELD_DataBufferUShort_data.setAccessible(true);
		FIELD_DataBufferUShort_bankdata.setAccessible(true);
		FIELD_DataBufferFloat_data.setAccessible(true);
		FIELD_DataBufferFloat_bankdata.setAccessible(true);
		FIELD_DataBufferDouble_data.setAccessible(true);
		FIELD_DataBufferDouble_bankdata.setAccessible(true);
		FIELD_DataBufferByte_data.setAccessible(true);
		FIELD_DataBufferByte_bankdata.setAccessible(true);
	} catch(Exception e) { throw new RuntimeException(e); } }

	public static void copyToBufferedImage(float[] data, int[] imageRaster, int channels, int sx, int sy, int rw, int rh, int w, int h, int stride) {
		if(channels <= 0 || channels > 4) throw new IllegalArgumentException("Invalid channel!");
		int offset = sy * rw + sx;
		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x++) {
				int i = y * w + x; int j = channels * i; int r, g, b, a = 255;
				r = g = b = (int) clamp(data[j] * 256, 0, 255);
				if(channels >= 2) g = b = (int) clamp(data[j + 1] * 256, 0, 255);
				if(channels >= 3) b = (int) clamp(data[j + 2] * 256, 0, 255);
				if(channels >= 4) a = (int) clamp(data[j + 3] * 256, 0, 255);
				imageRaster[offset + stride * y + i] = ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF));
			}
		}
	}
	public static void copyToBufferedImage(double[] data, int[] imageRaster, int channels, int sx, int sy, int rw, int rh, int w, int h, int stride) {
		if(channels <= 0 || channels > 4) throw new IllegalArgumentException("Invalid channel!");
		int offset = sy * rw + sx;
		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x++) {
				int i = y * w + x; int j = channels * i; int r, g, b, a = 255;
				r = g = b = (int) clamp(data[j] * 256, 0, 255);
				if(channels >= 2) g = b = (int) clamp(data[j + 1] * 256, 0, 255);
				if(channels >= 3) b = (int) clamp(data[j + 2] * 256, 0, 255);
				if(channels >= 4) a = (int) clamp(data[j + 3] * 256, 0, 255);
				imageRaster[offset + stride * y + i] = ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF));
			}
		}
	}

	public static void clampArray(int[] data, int minVal, int maxVal, int sx, int sy, int rw, int rh, int w, int h, int stride) {
		int fit = stride * ((w / 8) * 8);
		int full = stride * w;
		int offset = sy * rw + sx;
		for(int y = 0; y < h; y++) {
			int current = stride * (offset + y * rw);
			int i = 0;
			for(; i < fit; i += 8) {
				int j = current + i;
				data[j    ] = clamp(data[j    ], minVal, maxVal);
				data[j + 1] = clamp(data[j + 1], minVal, maxVal);
				data[j + 2] = clamp(data[j + 2], minVal, maxVal);
				data[j + 3] = clamp(data[j + 3], minVal, maxVal);
				data[j + 4] = clamp(data[j + 4], minVal, maxVal);
				data[j + 5] = clamp(data[j + 5], minVal, maxVal);
				data[j + 6] = clamp(data[j + 6], minVal, maxVal);
				data[j + 7] = clamp(data[j + 7], minVal, maxVal);
			}
			for(; i < full; i++) {
				int j = current + i;
				data[j] = clamp(data[j], minVal, maxVal);
			}
		}
	}
	public static void clampArray(short[] data, short minVal, short maxVal, int sx, int sy, int rw, int rh, int w, int h, int stride) {
		int fit = stride * ((w / 8) * 8);
		int full = stride * w;
		int offset = sy * rw + sx;
		for(int y = 0; y < h; y++) {
			int current = stride * (offset + y * rw);
			int i = 0;
			for(; i < fit; i += 8) {
				int j = current + i;
				data[j    ] = clamp(data[j    ], minVal, maxVal);
				data[j + 1] = clamp(data[j + 1], minVal, maxVal);
				data[j + 2] = clamp(data[j + 2], minVal, maxVal);
				data[j + 3] = clamp(data[j + 3], minVal, maxVal);
				data[j + 4] = clamp(data[j + 4], minVal, maxVal);
				data[j + 5] = clamp(data[j + 5], minVal, maxVal);
				data[j + 6] = clamp(data[j + 6], minVal, maxVal);
				data[j + 7] = clamp(data[j + 7], minVal, maxVal);
			}
			for(; i < full; i++) {
				int j = current + i;
				data[j] = clamp(data[j], minVal, maxVal);
			}
		}
	}
	public static void clampArray(float[] data, float minVal, float maxVal, int sx, int sy, int rw, int rh, int w, int h, int stride) {
		int fit = stride * ((w / 8) * 8);
		int full = stride * w;
		int offset = sy * rw + sx;
		for(int y = 0; y < h; y++) {
			int current = stride * (offset + y * rw);
			int i = 0;
			for(; i < fit; i += 8) {
				int j = current + i;
				data[j    ] = clamp(data[j    ], minVal, maxVal);
				data[j + 1] = clamp(data[j + 1], minVal, maxVal);
				data[j + 2] = clamp(data[j + 2], minVal, maxVal);
				data[j + 3] = clamp(data[j + 3], minVal, maxVal);
				data[j + 4] = clamp(data[j + 4], minVal, maxVal);
				data[j + 5] = clamp(data[j + 5], minVal, maxVal);
				data[j + 6] = clamp(data[j + 6], minVal, maxVal);
				data[j + 7] = clamp(data[j + 7], minVal, maxVal);
			}
			for(; i < full; i++) {
				int j = current + i;
				data[j] = clamp(data[j], minVal, maxVal);
			}
		}
	}
	public static void clampArray(double[] data, double minVal, double maxVal, int sx, int sy, int rw, int rh, int w, int h, int stride) {
		int fit = stride * ((w / 8) * 8);
		int full = stride * w;
		int offset = sy * rw + sx;
		for(int y = 0; y < h; y++) {
			int current = stride * (offset + y * rw);
			int i = 0;
			for(; i < fit; i += 8) {
				int j = current + i;
				data[j    ] = clamp(data[j    ], minVal, maxVal);
				data[j + 1] = clamp(data[j + 1], minVal, maxVal);
				data[j + 2] = clamp(data[j + 2], minVal, maxVal);
				data[j + 3] = clamp(data[j + 3], minVal, maxVal);
				data[j + 4] = clamp(data[j + 4], minVal, maxVal);
				data[j + 5] = clamp(data[j + 5], minVal, maxVal);
				data[j + 6] = clamp(data[j + 6], minVal, maxVal);
				data[j + 7] = clamp(data[j + 7], minVal, maxVal);
			}
			for(; i < full; i++) {
				int j = current + i;
				data[j] = clamp(data[j], minVal, maxVal);
			}
		}
	}
//	public static void clampArray(byte[] data, byte minVal, byte maxVal, int sx, int sy, int rw, int rh, int w, int h, int stride) {
//		int fit = stride * ((w / 8) * 8);
//		int full = stride * w;
//		int offset = sy * rw + sx;
//		for(int y = 0; y < h; y++) {
//			int current = stride * (offset + y * rw);
//			int i = 0;
//			for(; i < fit; i += 8) {
//				int j = current + i;
//				data[j    ] = clamp(data[j    ], minVal, maxVal);
//				data[j + 1] = clamp(data[j + 1], minVal, maxVal);
//				data[j + 2] = clamp(data[j + 2], minVal, maxVal);
//				data[j + 3] = clamp(data[j + 3], minVal, maxVal);
//				data[j + 4] = clamp(data[j + 4], minVal, maxVal);
//				data[j + 5] = clamp(data[j + 5], minVal, maxVal);
//				data[j + 6] = clamp(data[j + 6], minVal, maxVal);
//				data[j + 7] = clamp(data[j + 7], minVal, maxVal);
//			}
//			for(; i < full; i++) {
//				int j = current + i;
//				data[j] = clamp(data[j], minVal, maxVal);
//			}
//		}
//	}

	@SuppressWarnings("SuspiciousSystemArraycopy")
	protected static void flipImageImpl(Object data, int sx, int sy, int rw, int rh, int w, int h, int stride) {
		ArrayUtils.assertArray(data);
		Object temp = Array.newInstance(data.getClass().getComponentType(), stride * w);
		int tempLen = Array.getLength(temp);
		int middle = h / 2;
		for(int i = 0; i < middle; i++) {
			int top = stride * (sx + (sy + i) * rw);
			int bottom = stride * (sx + (sy + h - i - 1) * rw);
			System.arraycopy(data, top, temp, 0, tempLen);
			System.arraycopy(data, bottom, data, top, tempLen);
			System.arraycopy(temp, 0, data, bottom, tempLen);
		}
	}
	public static void flipImage(int[] data, int sx, int sy, int rw, int rh, int w, int h, int stride) { flipImageImpl(data, sx, sy, rw, rh, w, h, stride); }
	public static void flipImage(short[] data, int sx, int sy, int rw, int rh, int w, int h, int stride) { flipImageImpl(data, sx, sy, rw, rh, w, h, stride); }
	public static void flipImage(float[] data, int sx, int sy, int rw, int rh, int w, int h, int stride) { flipImageImpl(data, sx, sy, rw, rh, w, h, stride); }
	public static void flipImage(double[] data, int sx, int sy, int rw, int rh, int w, int h, int stride) { flipImageImpl(data, sx, sy, rw, rh, w, h, stride); }
	public static void flipImage(byte[] data, int sx, int sy, int rw, int rh, int w, int h, int stride) { flipImageImpl(data, sx, sy, rw, rh, w, h, stride); }

	@SuppressWarnings("SuspiciousSystemArraycopy")
	protected static void cutImageImpl(Object source, int ssx, int ssy, int srw, int srh, Object dest, int dsx, int dsy, int drw, int drh, int w, int h, int stride, Object filler) {
		ArrayUtils.assertArray(filler, source, dest);
		if(w > srw || w > drw || h > srh || h > drh)
			throw new IllegalArgumentException("Width or height exceeds source or destination bounds");
		Object temp = Array.newInstance(source.getClass().getComponentType(), stride * w);
		int tempLen = Array.getLength(temp);
		int offset = ssy * srw + ssx;
		for(int y = 0; y < h; y++) {
			System.arraycopy(source, stride * (offset + (y * srw)), temp, 0, tempLen);
			ArrayUtils.fillArray(source, stride * (offset + (y * srw)), tempLen, filler, 0);
			System.arraycopy(temp, 0, dest, stride * ((dsy * drw + dsx) + (y * srw)), tempLen);
		}
	}
	public static void cutImage(int[] source, int ssx, int ssy, int srw, int srh, int[] dest, int dsx, int dsy, int drw, int drh, int w, int h, int stride, int[] filler) { cutImageImpl(source, ssx, ssy, srw, srh, dest, dsx, dsy, drw, drh, w, h, stride, filler); }
	public static void cutImage(short[] source, int ssx, int ssy, int srw, int srh, short[] dest, int dsx, int dsy, int drw, int drh, int w, int h, int stride, short[] filler) { cutImageImpl(source, ssx, ssy, srw, srh, dest, dsx, dsy, drw, drh, w, h, stride, filler); }
	public static void cutImage(float[] source, int ssx, int ssy, int srw, int srh, float[] dest, int dsx, int dsy, int drw, int drh, int w, int h, int stride, float[] filler) { cutImageImpl(source, ssx, ssy, srw, srh, dest, dsx, dsy, drw, drh, w, h, stride, filler); }
	public static void cutImage(double[] source, int ssx, int ssy, int srw, int srh, double[] dest, int dsx, int dsy, int drw, int drh, int w, int h, int stride, double[] filler) { cutImageImpl(source, ssx, ssy, srw, srh, dest, dsx, dsy, drw, drh, w, h, stride, filler); }
	public static void cutImage(byte[] source, int ssx, int ssy, int srw, int srh, byte[] dest, int dsx, int dsy, int drw, int drh, int w, int h, int stride, byte[] filler) { cutImageImpl(source, ssx, ssy, srw, srh, dest, dsx, dsy, drw, drh, w, h, stride, filler); }

	protected static void setPixelsImpl(Object data, Object filler, int sx, int sy, int rw, int rh, int w, int h, int stride) {
		ArrayUtils.assertArray(filler, data);
		int offset = sy * rw + sx;
		if(w != rw) for(int y = 0; y < h; y++)
			ArrayUtils.fillArray(data, stride * (offset + y * rw), stride * w, filler, 0);
		else ArrayUtils.fillArray(data, stride * offset, stride * w * h, filler, 0);
	}
	public static void setPixels(int[] data, int[] filler, int sx, int sy, int rw, int rh, int w, int h, int stride) { setPixelsImpl(data, filler, sx, sy, rw, rh, w, h, stride); }
	public static void setPixels(short[] data, short[] filler, int sx, int sy, int rw, int rh, int w, int h, int stride) { setPixelsImpl(data, filler, sx, sy, rw, rh, w, h, stride); }
	public static void setPixels(float[] data, float[] filler, int sx, int sy, int rw, int rh, int w, int h, int stride) { setPixelsImpl(data, filler, sx, sy, rw, rh, w, h, stride); }
	public static void setPixels(double[] data, double[] filler, int sx, int sy, int rw, int rh, int w, int h, int stride) { setPixelsImpl(data, filler, sx, sy, rw, rh, w, h, stride); }
	public static void setPixels(byte[] data, byte[] filler, int sx, int sy, int rw, int rh, int w, int h, int stride) { setPixelsImpl(data, filler, sx, sy, rw, rh, w, h, stride); }

	public static void clearPixels(int[] data, int sx, int sy, int rw, int rh, int w, int h, int stride) { setPixels(data, ArrayUtils.getEmptyIntegerArray(-1), sx, sy, rw, rh, w, h, stride); }
	public static void clearPixels(short[] data, int sx, int sy, int rw, int rh, int w, int h, int stride) { setPixels(data, ArrayUtils.getEmptyShortArray(-1), sx, sy, rw, rh, w, h, stride); }
	public static void clearPixels(float[] data, int sx, int sy, int rw, int rh, int w, int h, int stride) { setPixels(data, ArrayUtils.getEmptyFloatArray(-1), sx, sy, rw, rh, w, h, stride); }
	public static void clearPixels(double[] data, int sx, int sy, int rw, int rh, int w, int h, int stride) { setPixels(data, ArrayUtils.getEmptyDoubleArray(-1), sx, sy, rw, rh, w, h, stride); }
	public static void clearPixels(byte[] data, int sx, int sy, int rw, int rh, int w, int h, int stride) { setPixels(data, ArrayUtils.getEmptyByteArray(-1), sx, sy, rw, rh, w, h, stride); }

	public static void scaleBands(int[] data, double factor, int sx, int sy, int rw, int rh, int w, int h, int stride) {
		int fit = stride * ((w / 8) * 8);
		int full = stride * w;
		int offset = sy * rw + sx;
		for(int y = 0; y < h; y++) {
			int current = stride * (offset + y * rw);
			int i = 0;
			for(; i < fit; i += 8) {
				int j = current + i;
				data[j    ] *= factor;
				data[j + 1] *= factor;
				data[j + 2] *= factor;
				data[j + 3] *= factor;
				data[j + 4] *= factor;
				data[j + 5] *= factor;
				data[j + 6] *= factor;
				data[j + 7] *= factor;
			}
			for(; i < full; i++) {
				int j = current + i;
				data[j] *= factor;
			}
		}
	}
	public static void scaleBands(short[] data, double factor, int sx, int sy, int rw, int rh, int w, int h, int stride) {
		int fit = stride * ((w / 8) * 8);
		int full = stride * w;
		int offset = sy * rw + sx;
		for(int y = 0; y < h; y++) {
			int current = stride * (offset + y * rw);
			int i = 0;
			for(; i < fit; i += 8) {
				int j = current + i;
				data[j    ] *= factor;
				data[j + 1] *= factor;
				data[j + 2] *= factor;
				data[j + 3] *= factor;
				data[j + 4] *= factor;
				data[j + 5] *= factor;
				data[j + 6] *= factor;
				data[j + 7] *= factor;
			}
			for(; i < full; i++) {
				int j = current + i;
				data[j] *= factor;
			}
		}
	}
	public static void scaleBands(float[] data, double factor, int sx, int sy, int rw, int rh, int w, int h, int stride) {
		int fit = stride * ((w / 8) * 8);
		int full = stride * w;
		int offset = sy * rw + sx;
		for(int y = 0; y < h; y++) {
			int current = stride * (offset + y * rw);
			int i = 0;
			for(; i < fit; i += 8) {
				int j = current + i;
				data[j    ] *= factor;
				data[j + 1] *= factor;
				data[j + 2] *= factor;
				data[j + 3] *= factor;
				data[j + 4] *= factor;
				data[j + 5] *= factor;
				data[j + 6] *= factor;
				data[j + 7] *= factor;
			}
			for(; i < full; i++) {
				int j = current + i;
				data[j] *= factor;
			}
		}
	}
	public static void scaleBands(double[] data, double factor, int sx, int sy, int rw, int rh, int w, int h, int stride) {
		int fit = stride * ((w / 8) * 8);
		int full = stride * w;
		int offset = sy * rw + sx;
		for(int y = 0; y < h; y++) {
			int current = stride * (offset + y * rw);
			int i = 0;
			for(; i < fit; i += 8) {
				int j = current + i;
				data[j    ] *= factor;
				data[j + 1] *= factor;
				data[j + 2] *= factor;
				data[j + 3] *= factor;
				data[j + 4] *= factor;
				data[j + 5] *= factor;
				data[j + 6] *= factor;
				data[j + 7] *= factor;
			}
			for(; i < full; i++) {
				int j = current + i;
				data[j] *= factor;
			}
		}
	}
	public static void scaleBands(byte[] data, byte factor, int sx, int sy, int rw, int rh, int w, int h, int stride) {
		int fit = stride * ((w / 8) * 8);
		int full = stride * w;
		int offset = sy * rw + sx;
		for(int y = 0; y < h; y++) {
			int current = stride * (offset + y * rw);
			int i = 0;
			for(; i < fit; i += 8) {
				int j = current + i;
				data[j    ] *= factor;
				data[j + 1] *= factor;
				data[j + 2] *= factor;
				data[j + 3] *= factor;
				data[j + 4] *= factor;
				data[j + 5] *= factor;
				data[j + 6] *= factor;
				data[j + 7] *= factor;
			}
			for(; i < full; i++) {
				int j = current + i;
				data[j] *= factor;
			}
		}
	}

	public static BufferedImage newShortBufferedImage(int w, int h) {
		int[] bands = new int[] { 0, 1, 2, 3 };
		SampleModel sampleModel = new ComponentSampleModel(DataBuffer.TYPE_SHORT, w, h, bands.length, w * bands.length, bands);
		WritableRaster raster = Raster.createWritableRaster(sampleModel, new DataBufferShort(new short[w * h * bands.length], w * h * bands.length), null);
		ColorModel colorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), true, false, Transparency.TRANSLUCENT, DataBuffer.TYPE_SHORT);
		BufferedImage result = new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), null);
		ArrayUtils.fill(getRasterShort(result), ArrayUtils.getEmptyShortArray(-1));
		return result;
	}
	public static BufferedImage newFloatBufferedImage(int w, int h) {
		int[] bands = new int[] { 0, 1, 2, 3 };
		SampleModel sampleModel = new ComponentSampleModel(DataBuffer.TYPE_FLOAT, w, h, bands.length, w * bands.length, bands);
		WritableRaster raster = Raster.createWritableRaster(sampleModel, new DataBufferFloat(new float[w * h * bands.length], w * h * bands.length), null);
		ColorModel colorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), true, false, Transparency.TRANSLUCENT, DataBuffer.TYPE_FLOAT);
		BufferedImage result = new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), null);
		ArrayUtils.fill(getRasterFloat(result), ArrayUtils.getEmptyFloatArray(-1));
		return result;
	}
	public static BufferedImage newByteBufferedImage(int w, int h) {
		int[] bands = new int[] { 0, 1, 2, 3 };
		SampleModel sampleModel = new ComponentSampleModel(DataBuffer.TYPE_BYTE, w, h, bands.length, w * bands.length, bands);
		WritableRaster raster = Raster.createWritableRaster(sampleModel, new DataBufferByte(new byte[w * h * bands.length], w * h * bands.length), null);
		ColorModel colorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), true, false, Transparency.TRANSLUCENT, DataBuffer.TYPE_BYTE);
		BufferedImage result = new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), null);
		ArrayUtils.fill(getRasterByte(result), ArrayUtils.getEmptyByteArray(-1));
		return result;
	}

	public static int[] getRasterInt(BufferedImage image) { DataBuffer dataBuffer = image.getRaster().getDataBuffer(); if(!(dataBuffer instanceof DataBufferInt)) return null; return ((DataBufferInt) dataBuffer).getData(); }
	public static short[] getRasterShort(BufferedImage image) { DataBuffer dataBuffer = image.getRaster().getDataBuffer(); if(!(dataBuffer instanceof DataBufferShort)) return null; return ((DataBufferShort) dataBuffer).getData(); }
	public static short[] getRasterUShort(BufferedImage image) { DataBuffer dataBuffer = image.getRaster().getDataBuffer(); if(!(dataBuffer instanceof DataBufferUShort)) return null; return ((DataBufferUShort) dataBuffer).getData(); }
	public static float[] getRasterFloat(BufferedImage image) { DataBuffer dataBuffer = image.getRaster().getDataBuffer(); if(!(dataBuffer instanceof DataBufferFloat)) return null; return ((DataBufferFloat) dataBuffer).getData(); }
	public static double[] getRasterDouble(BufferedImage image) { DataBuffer dataBuffer = image.getRaster().getDataBuffer(); if(!(dataBuffer instanceof DataBufferDouble)) return null; return ((DataBufferDouble) dataBuffer).getData(); }
	public static byte[] getRasterByte(BufferedImage image) { DataBuffer dataBuffer = image.getRaster().getDataBuffer(); if(!(dataBuffer instanceof DataBufferByte)) return null; return ((DataBufferByte) dataBuffer).getData(); }
	public static Object getRaster(BufferedImage image) {
		DataBuffer buffer = image.getRaster().getDataBuffer();
		if(buffer instanceof DataBufferInt) return getRasterInt(image);
		if(buffer instanceof DataBufferShort) return getRasterShort(image);
		if(buffer instanceof DataBufferUShort) return getRasterUShort(image);
		if(buffer instanceof DataBufferFloat) return getRasterFloat(image);
		if(buffer instanceof DataBufferDouble) return getRasterDouble(image);
		if(buffer instanceof DataBufferByte) return getRasterByte(image);
		return null;
	}

	public static void free(BufferedImage image) { ExceptionUtils.doSilentThrowsRunnable(ExceptionUtils.silentException, () -> {
		WritableRaster raster = image.getRaster(); DataBuffer buffer = raster.getDataBuffer();
		Field rasterDataField = null; Field rasterDataOffsetsField = null; Field dataField = null; Field bankDataField = null;
		if(raster instanceof IntegerComponentRaster) { rasterDataField = FIELD_IntegerComponentRaster_data; rasterDataOffsetsField = FIELD_IntegerComponentRaster_dataOffsets; }
		if(raster instanceof ShortComponentRaster) { rasterDataField = FIELD_ShortComponentRaster_data; rasterDataOffsetsField = FIELD_ShortComponentRaster_dataOffsets; }
		if(raster instanceof ByteComponentRaster) { rasterDataField = FIELD_ByteComponentRaster_data; rasterDataOffsetsField = FIELD_ByteComponentRaster_dataOffsets; }
		if(buffer instanceof DataBufferInt) { dataField = FIELD_DataBufferInt_data; bankDataField = FIELD_DataBufferInt_bankdata; }
		if(buffer instanceof DataBufferShort) { dataField = FIELD_DataBufferShort_data; bankDataField = FIELD_DataBufferShort_bankdata; }
		if(buffer instanceof DataBufferUShort) { dataField = FIELD_DataBufferUShort_data; bankDataField = FIELD_DataBufferUShort_bankdata; }
		if(buffer instanceof DataBufferFloat) { dataField = FIELD_DataBufferFloat_data; bankDataField = FIELD_DataBufferFloat_bankdata; }
		if(buffer instanceof DataBufferDouble) { dataField = FIELD_DataBufferDouble_data; bankDataField = FIELD_DataBufferDouble_bankdata; }
		if(buffer instanceof DataBufferByte) { dataField = FIELD_DataBufferByte_data; bankDataField = FIELD_DataBufferByte_bankdata; }
		if(dataField == null || bankDataField == null) throw new IllegalArgumentException("Data not supported");
		if(rasterDataField != null) rasterDataField.set(raster, null); if(rasterDataOffsetsField != null) rasterDataOffsetsField.set(raster, null);
		FIELD_DataBuffer_offsets.set(buffer, null); dataField.set(buffer, null); bankDataField.set(buffer, null);
	}); }
}
