package io.github.NadhifRadityo.Objects.Utilizations.Android;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@link AudioFormatAndroid} class is used to access a number of audio format and
 * channel configuration constants. They are for instance used
 * in {@link AudioTrack} and {@link AudioRecord}, as valid values in individual parameters of
 * constructors like {@link AudioTrack#AudioTrack(int, int, int, int, int, int)}, where the fourth
 * parameter is one of the <code>AudioFormatAndroid.ENCODING_*</code> constants.
 * The <code>AudioFormatAndroid</code> constants are also used in {@link MediaFormat} to specify
 * audio related values commonly used in media, such as for {@link MediaFormat#KEY_CHANNEL_MASK}.
 * <p>The {@link AudioFormatAndroid.Builder} class can be used to create instances of
 * the <code>AudioFormatAndroid</code> format class.
 * Refer to
 * {@link AudioFormatAndroid.Builder} for documentation on the mechanics of the configuration and building
 * of such instances. Here we describe the main concepts that the <code>AudioFormatAndroid</code> class
 * allow you to convey in each instance, they are:
 * <ol>
 * <li><a href="#sampleRate">sample rate</a>
 * <li><a href="#encoding">encoding</a>
 * <li><a href="#channelMask">channel masks</a>
 * </ol>
 * <p>Closely associated with the <code>AudioFormatAndroid</code> is the notion of an
 * <a href="#audioFrame">audio frame</a>, which is used throughout the documentation
 * to represent the minimum size complete unit of audio data.
 *
 * <h4 id="sampleRate">Sample rate</h4>
 * <p>Expressed in Hz, the sample rate in an <code>AudioFormatAndroid</code> instance expresses the number
 * of audio samples for each channel per second in the content you are playing or recording. It is
 * not the sample rate
 * at which content is rendered or produced. For instance a sound at a media sample rate of 8000Hz
 * can be played on a device operating at a sample rate of 48000Hz; the sample rate conversion is
 * automatically handled by the platform, it will not play at 6x speed.
 *
 * <p>As of API {@link android.os.Build.VERSION_CODES#M},
 * sample rates up to 192kHz are supported
 * for <code>AudioRecord</code> and <code>AudioTrack</code>, with sample rate conversion
 * performed as needed.
 * To improve efficiency and avoid lossy conversions, it is recommended to match the sample rate
 * for <code>AudioRecord</code> and <code>AudioTrack</code> to the endpoint device
 * sample rate, and limit the sample rate to no more than 48kHz unless there are special
 * device capabilities that warrant a higher rate.
 *
 * <h4 id="encoding">Encoding</h4>
 * <p>Audio encoding is used to describe the bit representation of audio data, which can be
 * either linear PCM or compressed audio, such as AC3 or DTS.
 * <p>For linear PCM, the audio encoding describes the sample size, 8 bits, 16 bits, or 32 bits,
 * and the sample representation, integer or float.
 * <ul>
 * <li> {@link #ENCODING_PCM_8BIT}: The audio sample is a 8 bit unsigned integer in the
 * range [0, 255], with a 128 offset for zero. This is typically stored as a Java byte in a
 * byte array or ByteBuffer. Since the Java byte is <em>signed</em>,
 * be careful with math operations and conversions as the most significant bit is inverted.
 * </li>
 * <li> {@link #ENCODING_PCM_16BIT}: The audio sample is a 16 bit signed integer
 * typically stored as a Java short in a short array, but when the short
 * is stored in a ByteBuffer, it is native endian (as compared to the default Java big endian).
 * The short has full range from [-32768, 32767],
 * and is sometimes interpreted as fixed point Q.15 data.
 * </li>
 * <li> {@link #ENCODING_PCM_FLOAT}: Introduced in
 * API {@link android.os.Build.VERSION_CODES#LOLLIPOP}, this encoding specifies that
 * the audio sample is a 32 bit IEEE single precision float. The sample can be
 * manipulated as a Java float in a float array, though within a ByteBuffer
 * it is stored in native endian byte order.
 * The nominal range of <code>ENCODING_PCM_FLOAT</code> audio data is [-1.0, 1.0].
 * It is implementation dependent whether the positive maximum of 1.0 is included
 * in the interval. Values outside of the nominal range are clamped before
 * sending to the endpoint device. Beware that
 * the handling of NaN is undefined; subnormals may be treated as zero; and
 * infinities are generally clamped just like other values for <code>AudioTrack</code>
 * &ndash; try to avoid infinities because they can easily generate a NaN.
 * <br>
 * To achieve higher audio bit depth than a signed 16 bit integer short,
 * it is recommended to use <code>ENCODING_PCM_FLOAT</code> for audio capture, processing,
 * and playback.
 * Floats are efficiently manipulated by modern CPUs,
 * have greater precision than 24 bit signed integers,
 * and have greater dynamic range than 32 bit signed integers.
 * <code>AudioRecord</code> as of API {@link android.os.Build.VERSION_CODES#M} and
 * <code>AudioTrack</code> as of API {@link android.os.Build.VERSION_CODES#LOLLIPOP}
 * support <code>ENCODING_PCM_FLOAT</code>.
 * </li>
 * </ul>
 * <p>For compressed audio, the encoding specifies the method of compression,
 * for example {@link #ENCODING_AC3} and {@link #ENCODING_DTS}. The compressed
 * audio data is typically stored as bytes in
 * a byte array or ByteBuffer. When a compressed audio encoding is specified
 * for an <code>AudioTrack</code>, it creates a direct (non-mixed) track
 * for output to an endpoint (such as HDMI) capable of decoding the compressed audio.
 * For (most) other endpoints, which are not capable of decoding such compressed audio,
 * you will need to decode the data first, typically by creating a {@link MediaCodec}.
 * Alternatively, one may use {@link MediaPlayer} for playback of compressed
 * audio files or streams.
 * <p>When compressed audio is sent out through a direct <code>AudioTrack</code>,
 * it need not be written in exact multiples of the audio access unit;
 * this differs from <code>MediaCodec</code> input buffers.
 *
 * <h4 id="channelMask">Channel mask</h4>
 * <p>Channel masks are used in <code>AudioTrack</code> and <code>AudioRecord</code> to describe
 * the samples and their arrangement in the audio frame. They are also used in the endpoint (e.g.
 * a USB audio interface, a DAC connected to headphones) to specify allowable configurations of a
 * particular device.
 * <br>As of API {@link android.os.Build.VERSION_CODES#M}, there are two types of channel masks:
 * channel position masks and channel index masks.
 *
 * <h5 id="channelPositionMask">Channel position masks</h5>
 * Channel position masks are the original Android channel masks, and are used since API
 * {@link android.os.Build.VERSION_CODES#BASE}.
 * For input and output, they imply a positional nature - the location of a speaker or a microphone
 * for recording or playback.
 * <br>For a channel position mask, each allowed channel position corresponds to a bit in the
 * channel mask. If that channel position is present in the audio frame, that bit is set,
 * otherwise it is zero. The order of the bits (from lsb to msb) corresponds to the order of that
 * position's sample in the audio frame.
 * <br>The canonical channel position masks by channel count are as follows:
 * <br><table>
 * <tr><td>channel count</td><td>channel position mask</td></tr>
 * <tr><td>1</td><td>{@link #CHANNEL_OUT_MONO}</td></tr>
 * <tr><td>2</td><td>{@link #CHANNEL_OUT_STEREO}</td></tr>
 * <tr><td>3</td><td>{@link #CHANNEL_OUT_STEREO} | {@link #CHANNEL_OUT_FRONT_CENTER}</td></tr>
 * <tr><td>4</td><td>{@link #CHANNEL_OUT_QUAD}</td></tr>
 * <tr><td>5</td><td>{@link #CHANNEL_OUT_QUAD} | {@link #CHANNEL_OUT_FRONT_CENTER}</td></tr>
 * <tr><td>6</td><td>{@link #CHANNEL_OUT_5POINT1}</td></tr>
 * <tr><td>7</td><td>{@link #CHANNEL_OUT_5POINT1} | {@link #CHANNEL_OUT_BACK_CENTER}</td></tr>
 * <tr><td>8</td><td>{@link #CHANNEL_OUT_7POINT1_SURROUND}</td></tr>
 * </table>
 * <br>These masks are an ORed composite of individual channel masks. For example
 * {@link #CHANNEL_OUT_STEREO} is composed of {@link #CHANNEL_OUT_FRONT_LEFT} and
 * {@link #CHANNEL_OUT_FRONT_RIGHT}.
 *
 * <h5 id="channelIndexMask">Channel index masks</h5>
 * Channel index masks are introduced in API {@link android.os.Build.VERSION_CODES#M}. They allow
 * the selection of a particular channel from the source or sink endpoint by number, i.e. the first
 * channel, the second channel, and so forth. This avoids problems with artificially assigning
 * positions to channels of an endpoint, or figuring what the i<sup>th</sup> position bit is within
 * an endpoint's channel position mask etc.
 * <br>Here's an example where channel index masks address this confusion: dealing with a 4 channel
 * USB device. Using a position mask, and based on the channel count, this would be a
 * {@link #CHANNEL_OUT_QUAD} device, but really one is only interested in channel 0
 * through channel 3. The USB device would then have the following individual bit channel masks:
 * {@link #CHANNEL_OUT_FRONT_LEFT},
 * {@link #CHANNEL_OUT_FRONT_RIGHT}, {@link #CHANNEL_OUT_BACK_LEFT}
 * and {@link #CHANNEL_OUT_BACK_RIGHT}. But which is channel 0 and which is
 * channel 3?
 * <br>For a channel index mask, each channel number is represented as a bit in the mask, from the
 * lsb (channel 0) upwards to the msb, numerically this bit value is
 * <code>1 << channelNumber</code>.
 * A set bit indicates that channel is present in the audio frame, otherwise it is cleared.
 * The order of the bits also correspond to that channel number's sample order in the audio frame.
 * <br>For the previous 4 channel USB device example, the device would have a channel index mask
 * <code>0xF</code>. Suppose we wanted to select only the first and the third channels; this would
 * correspond to a channel index mask <code>0x5</code> (the first and third bits set). If an
 * <code>AudioTrack</code> uses this channel index mask, the audio frame would consist of two
 * samples, the first sample of each frame routed to channel 0, and the second sample of each frame
 * routed to channel 2.
 * The canonical channel index masks by channel count are given by the formula
 * <code>(1 << channelCount) - 1</code>.
 *
 * <h5>Use cases</h5>
 * <ul>
 * <li><i>Channel position mask for an endpoint:</i> <code>CHANNEL_OUT_FRONT_LEFT</code>,
 *  <code>CHANNEL_OUT_FRONT_CENTER</code>, etc. for HDMI home theater purposes.
 * <li><i>Channel position mask for an audio stream:</i> Creating an <code>AudioTrack</code>
 *  to output movie content, where 5.1 multichannel output is to be written.
 * <li><i>Channel index mask for an endpoint:</i> USB devices for which input and output do not
 *  correspond to left or right speaker or microphone.
 * <li><i>Channel index mask for an audio stream:</i> An <code>AudioRecord</code> may only want the
 *  third and fourth audio channels of the endpoint (i.e. the second channel pair), and not care the
 *  about position it corresponds to, in which case the channel index mask is <code>0xC</code>.
 *  Multichannel <code>AudioRecord</code> sessions should use channel index masks.
 * </ul>
 * <h4 id="audioFrame">Audio Frame</h4>
 * <p>For linear PCM, an audio frame consists of a set of samples captured at the same time,
 * whose count and
 * channel association are given by the <a href="#channelMask">channel mask</a>,
 * and whose sample contents are specified by the <a href="#encoding">encoding</a>.
 * For example, a stereo 16 bit PCM frame consists of
 * two 16 bit linear PCM samples, with a frame size of 4 bytes.
 * For compressed audio, an audio frame may alternately
 * refer to an access unit of compressed data bytes that is logically grouped together for
 * decoding and bitstream access (e.g. {@link MediaCodec}),
 * or a single byte of compressed data (e.g. {@link AudioTrack#getBufferSizeInFrames()
 * AudioTrack.getBufferSizeInFrames()}),
 * or the linear PCM frame result from decoding the compressed data
 * (e.g.{@link AudioTrack#getPlaybackHeadPosition()
 * AudioTrack.getPlaybackHeadPosition()}),
 * depending on the context where audio frame is used.
 */
public final class AudioFormatAndroid {
    //---------------------------------------------------------
    // Constants
    //--------------------
    /** Invalid audio data format */
    public static final int ENCODING_INVALID = 0;
    /** Default audio data format */
    public static final int ENCODING_DEFAULT = 1;
    // These values must be kept in sync with core/jni/android_media_AudioFormatAndroid.h
    // Also sync av/services/audiopolicy/managerdefault/ConfigParsingUtils.h
    /** Audio data format: PCM 16 bit per sample. Guaranteed to be supported by devices. */
    public static final int ENCODING_PCM_16BIT = 2;
    /** Audio data format: PCM 8 bit per sample. Not guaranteed to be supported by devices. */
    public static final int ENCODING_PCM_8BIT = 3;
    /** Audio data format: single-precision floating-point per sample */
    public static final int ENCODING_PCM_FLOAT = 4;
    /** Audio data format: AC-3 compressed */
    public static final int ENCODING_AC3 = 5;
    /** Audio data format: E-AC-3 compressed */
    public static final int ENCODING_E_AC3 = 6;
    /** Audio data format: DTS compressed */
    public static final int ENCODING_DTS = 7;
    /** Audio data format: DTS HD compressed */
    public static final int ENCODING_DTS_HD = 8;
    /** Audio data format: MP3 compressed */
    public static final int ENCODING_MP3 = 9;
    /** Audio data format: AAC LC compressed */
    public static final int ENCODING_AAC_LC = 10;
    /** Audio data format: AAC HE V1 compressed */
    public static final int ENCODING_AAC_HE_V1 = 11;
    /** Audio data format: AAC HE V2 compressed */
    public static final int ENCODING_AAC_HE_V2 = 12;
    /** Audio data format: compressed audio wrapped in PCM for HDMI
     * or S/PDIF passthrough.
     * IEC61937 uses a stereo stream of 16-bit samples as the wrapper.
     * So the channel mask for the track must be {@link #CHANNEL_OUT_STEREO}.
     * Data should be written to the stream in a short[] array.
     * If the data is written in a byte[] array then there may be endian problems
     * on some platforms when converting to short internally.
     */
    public static final int ENCODING_IEC61937 = 13;
    /** Audio data format: DOLBY TRUEHD compressed
     **/
    public static final int ENCODING_DOLBY_TRUEHD = 14;
    /** Audio data format: AAC ELD compressed */
    public static final int ENCODING_AAC_ELD = 15;
    /** Audio data format: AAC xHE compressed */
    public static final int ENCODING_AAC_XHE = 16;
    /** Audio data format: AC-4 sync frame transport format */
    public static final int ENCODING_AC4 = 17;
    /** Audio data format: E-AC-3-JOC compressed
     * E-AC-3-JOC streams can be decoded by downstream devices supporting {@link #ENCODING_E_AC3}.
     * Use {@link #ENCODING_E_AC3} as the AudioTrack encoding when the downstream device
     * supports {@link #ENCODING_E_AC3} but not {@link #ENCODING_E_AC3_JOC}.
     **/
    public static final int ENCODING_E_AC3_JOC = 18;
    /** @hide */
    public static String toLogFriendlyEncoding(int enc) {
        switch(enc) {
            case ENCODING_INVALID:
                return "ENCODING_INVALID";
            case ENCODING_PCM_16BIT:
                return "ENCODING_PCM_16BIT";
            case ENCODING_PCM_8BIT:
                return "ENCODING_PCM_8BIT";
            case ENCODING_PCM_FLOAT:
                return "ENCODING_PCM_FLOAT";
            case ENCODING_AC3:
                return "ENCODING_AC3";
            case ENCODING_E_AC3:
                return "ENCODING_E_AC3";
            case ENCODING_DTS:
                return "ENCODING_DTS";
            case ENCODING_DTS_HD:
                return "ENCODING_DTS_HD";
            case ENCODING_MP3:
                return "ENCODING_MP3";
            case ENCODING_AAC_LC:
                return "ENCODING_AAC_LC";
            case ENCODING_AAC_HE_V1:
                return "ENCODING_AAC_HE_V1";
            case ENCODING_AAC_HE_V2:
                return "ENCODING_AAC_HE_V2";
            case ENCODING_IEC61937:
                return "ENCODING_IEC61937";
            case ENCODING_DOLBY_TRUEHD:
                return "ENCODING_DOLBY_TRUEHD";
            case ENCODING_AAC_ELD:
                return "ENCODING_AAC_ELD";
            case ENCODING_AAC_XHE:
                return "ENCODING_AAC_XHE";
            case ENCODING_AC4:
                return "ENCODING_AC4";
            default :
                return "invalid encoding " + enc;
        }
    }
    /** Invalid audio channel configuration */
    /** @deprecated Use {@link #CHANNEL_INVALID} instead.  */
    @Deprecated    public static final int CHANNEL_CONFIGURATION_INVALID   = 0;
    /** Default audio channel configuration */
    /** @deprecated Use {@link #CHANNEL_OUT_DEFAULT} or {@link #CHANNEL_IN_DEFAULT} instead.  */
    @Deprecated    public static final int CHANNEL_CONFIGURATION_DEFAULT   = 1;
    /** Mono audio configuration */
    /** @deprecated Use {@link #CHANNEL_OUT_MONO} or {@link #CHANNEL_IN_MONO} instead.  */
    @Deprecated    public static final int CHANNEL_CONFIGURATION_MONO      = 2;
    /** Stereo (2 channel) audio configuration */
    /** @deprecated Use {@link #CHANNEL_OUT_STEREO} or {@link #CHANNEL_IN_STEREO} instead.  */
    @Deprecated    public static final int CHANNEL_CONFIGURATION_STEREO    = 3;
    /** Invalid audio channel mask */
    public static final int CHANNEL_INVALID = 0;
    /** Default audio channel mask */
    public static final int CHANNEL_OUT_DEFAULT = 1;
    // Output channel mask definitions below are translated to the native values defined in
    //  in /system/media/audio/include/system/audio.h in the JNI code of AudioTrack
    public static final int CHANNEL_OUT_FRONT_LEFT = 0x4;
    public static final int CHANNEL_OUT_FRONT_RIGHT = 0x8;
    public static final int CHANNEL_OUT_FRONT_CENTER = 0x10;
    public static final int CHANNEL_OUT_LOW_FREQUENCY = 0x20;
    public static final int CHANNEL_OUT_BACK_LEFT = 0x40;
    public static final int CHANNEL_OUT_BACK_RIGHT = 0x80;
    public static final int CHANNEL_OUT_FRONT_LEFT_OF_CENTER = 0x100;
    public static final int CHANNEL_OUT_FRONT_RIGHT_OF_CENTER = 0x200;
    public static final int CHANNEL_OUT_BACK_CENTER = 0x400;
    public static final int CHANNEL_OUT_SIDE_LEFT =         0x800;
    public static final int CHANNEL_OUT_SIDE_RIGHT =       0x1000;
    /** @hide */
    public static final int CHANNEL_OUT_TOP_CENTER =       0x2000;
    /** @hide */
    public static final int CHANNEL_OUT_TOP_FRONT_LEFT =   0x4000;
    /** @hide */
    public static final int CHANNEL_OUT_TOP_FRONT_CENTER = 0x8000;
    /** @hide */
    public static final int CHANNEL_OUT_TOP_FRONT_RIGHT = 0x10000;
    /** @hide */
    public static final int CHANNEL_OUT_TOP_BACK_LEFT =   0x20000;
    /** @hide */
    public static final int CHANNEL_OUT_TOP_BACK_CENTER = 0x40000;
    /** @hide */
    public static final int CHANNEL_OUT_TOP_BACK_RIGHT =  0x80000;
    public static final int CHANNEL_OUT_MONO = CHANNEL_OUT_FRONT_LEFT;
    public static final int CHANNEL_OUT_STEREO = (CHANNEL_OUT_FRONT_LEFT | CHANNEL_OUT_FRONT_RIGHT);
    // aka QUAD_BACK
    public static final int CHANNEL_OUT_QUAD = (CHANNEL_OUT_FRONT_LEFT | CHANNEL_OUT_FRONT_RIGHT |
            CHANNEL_OUT_BACK_LEFT | CHANNEL_OUT_BACK_RIGHT);
    /** @hide */
    public static final int CHANNEL_OUT_QUAD_SIDE = (CHANNEL_OUT_FRONT_LEFT | CHANNEL_OUT_FRONT_RIGHT |
            CHANNEL_OUT_SIDE_LEFT | CHANNEL_OUT_SIDE_RIGHT);
    public static final int CHANNEL_OUT_SURROUND = (CHANNEL_OUT_FRONT_LEFT | CHANNEL_OUT_FRONT_RIGHT |
            CHANNEL_OUT_FRONT_CENTER | CHANNEL_OUT_BACK_CENTER);
    // aka 5POINT1_BACK
    public static final int CHANNEL_OUT_5POINT1 = (CHANNEL_OUT_FRONT_LEFT | CHANNEL_OUT_FRONT_RIGHT |
            CHANNEL_OUT_FRONT_CENTER | CHANNEL_OUT_LOW_FREQUENCY | CHANNEL_OUT_BACK_LEFT | CHANNEL_OUT_BACK_RIGHT);
    /** @hide */
    public static final int CHANNEL_OUT_5POINT1_SIDE = (CHANNEL_OUT_FRONT_LEFT | CHANNEL_OUT_FRONT_RIGHT |
            CHANNEL_OUT_FRONT_CENTER | CHANNEL_OUT_LOW_FREQUENCY |
            CHANNEL_OUT_SIDE_LEFT | CHANNEL_OUT_SIDE_RIGHT);
    // different from AUDIO_CHANNEL_OUT_7POINT1 used internally, and not accepted by AudioRecord.
    /** @deprecated Not the typical 7.1 surround configuration. Use {@link #CHANNEL_OUT_7POINT1_SURROUND} instead. */
    @Deprecated    public static final int CHANNEL_OUT_7POINT1 = (CHANNEL_OUT_FRONT_LEFT | CHANNEL_OUT_FRONT_RIGHT |
            CHANNEL_OUT_FRONT_CENTER | CHANNEL_OUT_LOW_FREQUENCY | CHANNEL_OUT_BACK_LEFT | CHANNEL_OUT_BACK_RIGHT |
            CHANNEL_OUT_FRONT_LEFT_OF_CENTER | CHANNEL_OUT_FRONT_RIGHT_OF_CENTER);
    // matches AUDIO_CHANNEL_OUT_7POINT1
    public static final int CHANNEL_OUT_7POINT1_SURROUND = (
            CHANNEL_OUT_FRONT_LEFT | CHANNEL_OUT_FRONT_CENTER | CHANNEL_OUT_FRONT_RIGHT |
            CHANNEL_OUT_SIDE_LEFT | CHANNEL_OUT_SIDE_RIGHT |
            CHANNEL_OUT_BACK_LEFT | CHANNEL_OUT_BACK_RIGHT |
            CHANNEL_OUT_LOW_FREQUENCY);
    // CHANNEL_OUT_ALL is not yet defined; if added then it should match AUDIO_CHANNEL_OUT_ALL
    /** Minimum value for sample rate,
     *  assuming AudioTrack and AudioRecord share the same limitations.
     * @hide
     */
    // never unhide
    public static final int SAMPLE_RATE_HZ_MIN = 4000;
    /** Maximum value for sample rate,
     *  assuming AudioTrack and AudioRecord share the same limitations.
     * @hide
     */
    // never unhide
    public static final int SAMPLE_RATE_HZ_MAX = 192000;
    /** Sample rate will be a route-dependent value.
     * For AudioTrack, it is usually the sink sample rate,
     * and for AudioRecord it is usually the source sample rate.
     */
    public static final int SAMPLE_RATE_UNSPECIFIED = 0;
    /**
     * @hide
     * Return the input channel mask corresponding to an output channel mask.
     * This can be used for submix rerouting for the mask of the recorder to map to that of the mix.
     * @param outMask a combination of the CHANNEL_OUT_* definitions, but not CHANNEL_OUT_DEFAULT
     * @return a combination of CHANNEL_IN_* definitions matching an output channel mask
     * @throws IllegalArgumentException
     */
    public static int inChannelMaskFromOutChannelMask(int outMask) throws IllegalArgumentException {
        if (outMask == CHANNEL_OUT_DEFAULT) {
            throw new IllegalArgumentException(
                    "Illegal CHANNEL_OUT_DEFAULT channel mask for input.");
        }
        switch (channelCountFromOutChannelMask(outMask)) {
            case 1:
                return CHANNEL_IN_MONO;
            case 2:
                return CHANNEL_IN_STEREO;
            default:
                throw new IllegalArgumentException("Unsupported channel configuration for input.");
        }
    }
    /**
     * @hide
     * Return the number of channels from an input channel mask
     * @param mask a combination of the CHANNEL_IN_* definitions, even CHANNEL_IN_DEFAULT
     * @return number of channels for the mask
     */
    public static int channelCountFromInChannelMask(int mask) {
        return Integer.bitCount(mask);
    }
    /**
     * @hide
     * Return the number of channels from an output channel mask
     * @param mask a combination of the CHANNEL_OUT_* definitions, but not CHANNEL_OUT_DEFAULT
     * @return number of channels for the mask
     */
    public static int channelCountFromOutChannelMask(int mask) {
        return Integer.bitCount(mask);
    }
    /**
     * @hide
     * Return a channel mask ready to be used by native code
     * @param mask a combination of the CHANNEL_OUT_* definitions, but not CHANNEL_OUT_DEFAULT
     * @return a native channel mask
     */
    public static int convertChannelOutMaskToNativeMask(int javaMask) {
        return (javaMask >> 2);
    }
    /**
     * @hide
     * Return a java output channel mask
     * @param mask a native channel mask
     * @return a combination of the CHANNEL_OUT_* definitions
     */
    public static int convertNativeChannelMaskToOutMask(int nativeMask) {
        return (nativeMask << 2);
    }
    public static final int CHANNEL_IN_DEFAULT = 1;
    // These directly match native
    public static final int CHANNEL_IN_LEFT = 0x4;
    public static final int CHANNEL_IN_RIGHT = 0x8;
    public static final int CHANNEL_IN_FRONT = 0x10;
    public static final int CHANNEL_IN_BACK = 0x20;
    public static final int CHANNEL_IN_LEFT_PROCESSED = 0x40;
    public static final int CHANNEL_IN_RIGHT_PROCESSED = 0x80;
    public static final int CHANNEL_IN_FRONT_PROCESSED = 0x100;
    public static final int CHANNEL_IN_BACK_PROCESSED = 0x200;
    public static final int CHANNEL_IN_PRESSURE = 0x400;
    public static final int CHANNEL_IN_X_AXIS = 0x800;
    public static final int CHANNEL_IN_Y_AXIS = 0x1000;
    public static final int CHANNEL_IN_Z_AXIS = 0x2000;
    public static final int CHANNEL_IN_VOICE_UPLINK = 0x4000;
    public static final int CHANNEL_IN_VOICE_DNLINK = 0x8000;
    public static final int CHANNEL_IN_MONO = CHANNEL_IN_FRONT;
    public static final int CHANNEL_IN_STEREO = (CHANNEL_IN_LEFT | CHANNEL_IN_RIGHT);
    /** @hide */
    public static final int CHANNEL_IN_FRONT_BACK = CHANNEL_IN_FRONT | CHANNEL_IN_BACK;
    // CHANNEL_IN_ALL is not yet defined; if added then it should match AUDIO_CHANNEL_IN_ALL
    /** @hide */
    public static int getBytesPerSample(int AudioFormatAndroid)
    {
        switch (AudioFormatAndroid) {
        case ENCODING_PCM_8BIT:
            return 1;
        case ENCODING_PCM_16BIT:
        case ENCODING_IEC61937:
        case ENCODING_DEFAULT:
            return 2;
        case ENCODING_PCM_FLOAT:
            return 4;
        case ENCODING_INVALID:
        default:
            throw new IllegalArgumentException("Bad audio format " + AudioFormatAndroid);
        }
    }
    /** @hide */
    public static boolean isValidEncoding(int AudioFormatAndroid)
    {
        switch (AudioFormatAndroid) {
        case ENCODING_PCM_8BIT:
        case ENCODING_PCM_16BIT:
        case ENCODING_PCM_FLOAT:
        case ENCODING_AC3:
        case ENCODING_E_AC3:
        case ENCODING_E_AC3_JOC:
        case ENCODING_DTS:
        case ENCODING_DTS_HD:
        case ENCODING_MP3:
        case ENCODING_AAC_LC:
        case ENCODING_AAC_HE_V1:
        case ENCODING_AAC_HE_V2:
        case ENCODING_IEC61937:
        case ENCODING_AAC_ELD:
        case ENCODING_AAC_XHE:
        case ENCODING_AC4:
            return true;
        default:
            return false;
        }
    }
    /** @hide */
    public static boolean isPublicEncoding(int AudioFormatAndroid)
    {
        switch (AudioFormatAndroid) {
        case ENCODING_PCM_8BIT:
        case ENCODING_PCM_16BIT:
        case ENCODING_PCM_FLOAT:
        case ENCODING_AC3:
        case ENCODING_E_AC3:
        case ENCODING_E_AC3_JOC:
        case ENCODING_DTS:
        case ENCODING_DTS_HD:
        case ENCODING_IEC61937:
        case ENCODING_MP3:
        case ENCODING_AAC_LC:
        case ENCODING_AAC_HE_V1:
        case ENCODING_AAC_HE_V2:
        case ENCODING_AAC_ELD:
        case ENCODING_AAC_XHE:
        case ENCODING_AC4:
            return true;
        default:
            return false;
        }
    }
    /** @hide */
    public static boolean isEncodingLinearPcm(int AudioFormatAndroid)
    {
        switch (AudioFormatAndroid) {
        case ENCODING_PCM_8BIT:
        case ENCODING_PCM_16BIT:
        case ENCODING_PCM_FLOAT:
        case ENCODING_DEFAULT:
            return true;
        case ENCODING_AC3:
        case ENCODING_E_AC3:
        case ENCODING_E_AC3_JOC:
        case ENCODING_DTS:
        case ENCODING_DTS_HD:
        case ENCODING_MP3:
        case ENCODING_AAC_LC:
        case ENCODING_AAC_HE_V1:
        case ENCODING_AAC_HE_V2:
        case ENCODING_IEC61937: // wrapped in PCM but compressed
        case ENCODING_AAC_ELD:
        case ENCODING_AAC_XHE:
        case ENCODING_AC4:
            return false;
        case ENCODING_INVALID:
        default:
            throw new IllegalArgumentException("Bad audio format " + AudioFormatAndroid);
        }
    }
    /** @hide */
    public static boolean isEncodingLinearFrames(int AudioFormatAndroid)
    {
        switch (AudioFormatAndroid) {
        case ENCODING_PCM_8BIT:
        case ENCODING_PCM_16BIT:
        case ENCODING_PCM_FLOAT:
        case ENCODING_IEC61937: // same size as stereo PCM
        case ENCODING_DEFAULT:
            return true;
        case ENCODING_AC3:
        case ENCODING_E_AC3:
        case ENCODING_E_AC3_JOC:
        case ENCODING_DTS:
        case ENCODING_DTS_HD:
        case ENCODING_MP3:
        case ENCODING_AAC_LC:
        case ENCODING_AAC_HE_V1:
        case ENCODING_AAC_HE_V2:
        case ENCODING_AAC_ELD:
        case ENCODING_AAC_XHE:
        case ENCODING_AC4:
            return false;
        case ENCODING_INVALID:
        default:
            throw new IllegalArgumentException("Bad audio format " + AudioFormatAndroid);
        }
    }
    /**
     * Returns an array of public encoding values extracted from an array of
     * encoding values.
     * @hide
     */
    public static int[] filterPublicFormats(int[] formats) {
        if (formats == null) {
            return null;
        }
        int[] myCopy = Arrays.copyOf(formats, formats.length);
        int size = 0;
        for (int i = 0; i < myCopy.length; i++) {
            if (isPublicEncoding(myCopy[i])) {
                if (size != i) {
                    myCopy[size] = myCopy[i];
                }
                size++;
            }
        }
        return Arrays.copyOf(myCopy, size);
    }
    
    /** @hide */
    public final static int AUDIO_FORMAT_HAS_PROPERTY_NONE = 0x0;
    /** @hide */
    public final static int AUDIO_FORMAT_HAS_PROPERTY_ENCODING = 0x1 << 0;
    /** @hide */
    public final static int AUDIO_FORMAT_HAS_PROPERTY_SAMPLE_RATE = 0x1 << 1;
    /** @hide */
    public final static int AUDIO_FORMAT_HAS_PROPERTY_CHANNEL_MASK = 0x1 << 2;
    /** @hide */
    public final static int AUDIO_FORMAT_HAS_PROPERTY_CHANNEL_INDEX_MASK = 0x1 << 3;
    
    //MODIFIED
    private final static Map<String, Integer> supportedEncoding = new HashMap<>();
    private final static Map<Integer, Integer> encodingBit = new HashMap<>();
    private final static Map<String, Integer> channelList = new HashMap<>();
    static {
    	supportedEncoding.put("ENCODING_PCM_16BIT", ENCODING_PCM_16BIT);
    	supportedEncoding.put("ENCODING_PCM_8BIT", ENCODING_PCM_8BIT);
    	
    	encodingBit.put(ENCODING_PCM_16BIT, 16);
    	encodingBit.put(ENCODING_PCM_8BIT, 8);

		channelList.put("CHANNEL_IN_DEFAULT", CHANNEL_IN_DEFAULT);
		channelList.put("CHANNEL_IN_LEFT", CHANNEL_IN_LEFT);
		channelList.put("CHANNEL_IN_RIGHT", CHANNEL_IN_RIGHT);
		channelList.put("CHANNEL_IN_FRONT", CHANNEL_IN_FRONT);
    	channelList.put("CHANNEL_IN_BACK", CHANNEL_IN_BACK);
		channelList.put("CHANNEL_IN_LEFT_PROCESSED", CHANNEL_IN_LEFT_PROCESSED);
		channelList.put("CHANNEL_IN_RIGHT_PROCESSED", CHANNEL_IN_RIGHT_PROCESSED);
		channelList.put("CHANNEL_IN_FRONT_PROCESSED", CHANNEL_IN_FRONT_PROCESSED);
    	channelList.put("CHANNEL_IN_BACK_PROCESSED", CHANNEL_IN_BACK_PROCESSED);
		channelList.put("CHANNEL_IN_PRESSURE", CHANNEL_IN_PRESSURE);
		channelList.put("CHANNEL_IN_VOICE_UPLINK", CHANNEL_IN_VOICE_UPLINK);
		channelList.put("CHANNEL_IN_VOICE_DNLINK", CHANNEL_IN_VOICE_DNLINK);
		channelList.put("CHANNEL_IN_MONO", CHANNEL_IN_MONO);
		channelList.put("CHANNEL_IN_STEREO", CHANNEL_IN_STEREO);
		channelList.put("CHANNEL_IN_FRONT_BACK", CHANNEL_IN_FRONT_BACK);
    }
    
    public static Map<String, Integer> getSupportedEncoding() {
    	return Collections.unmodifiableMap(supportedEncoding);
    }
    public static int getEncodingBitsPerSample(int code) {
    	return encodingBit.get(code) == null ? -1 : encodingBit.get(code);
    }
    public static Map<String, Integer> getChannelList() {
    	return Collections.unmodifiableMap(channelList);
    }
}
