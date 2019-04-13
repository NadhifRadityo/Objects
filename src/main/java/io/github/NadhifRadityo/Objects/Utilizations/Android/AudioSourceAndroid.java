package io.github.NadhifRadityo.Objects.Utilizations.Android;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines the audio source.
 * An audio source defines both a default physical source of audio signal, and a recording
 * configuration. These constants are for instance used
 * in {@link MediaRecorder#setAudioSource(int)} or
 * {@link AudioRecord.Builder#setAudioSource(int)}.
 */
public final class AudioSourceAndroid {
    private AudioSourceAndroid() {}
    /** @hide */
    public final static int AUDIO_SOURCE_INVALID = -1;
  /* Do not change these values without updating their counterparts
   * in system/media/audio/include/system/audio.h!
   */
    /** Default audio source **/
    public static final int DEFAULT = 0;
    /** Microphone audio source */
    public static final int MIC = 1;
    /** Voice call uplink (Tx) audio source.
     * <p>
     * Capturing from <code>VOICE_UPLINK</code> source requires the
     * {@link android.Manifest.permission#CAPTURE_AUDIO_OUTPUT} permission.
     * This permission is reserved for use by system components and is not available to
     * third-party applications.
     * </p>
     */
    public static final int VOICE_UPLINK = 2;
    /** Voice call downlink (Rx) audio source.
     * <p>
     * Capturing from <code>VOICE_DOWNLINK</code> source requires the
     * {@link android.Manifest.permission#CAPTURE_AUDIO_OUTPUT} permission.
     * This permission is reserved for use by system components and is not available to
     * third-party applications.
     * </p>
     */
    public static final int VOICE_DOWNLINK = 3;
    /** Voice call uplink + downlink audio source
     * <p>
     * Capturing from <code>VOICE_CALL</code> source requires the
     * {@link android.Manifest.permission#CAPTURE_AUDIO_OUTPUT} permission.
     * This permission is reserved for use by system components and is not available to
     * third-party applications.
     * </p>
     */
    public static final int VOICE_CALL = 4;
    /** Microphone audio source tuned for video recording, with the same orientation
     *  as the camera if available. */
    public static final int CAMCORDER = 5;
    /** Microphone audio source tuned for voice recognition. */
    public static final int VOICE_RECOGNITION = 6;
    /** Microphone audio source tuned for voice communications such as VoIP. It
     *  will for instance take advantage of echo cancellation or automatic gain control
     *  if available.
     */
    public static final int VOICE_COMMUNICATION = 7;
    /**
     * Audio source for a submix of audio streams to be presented remotely.
     * <p>
     * An application can use this audio source to capture a mix of audio streams
     * that should be transmitted to a remote receiver such as a Wifi display.
     * While recording is active, these audio streams are redirected to the remote
     * submix instead of being played on the device speaker or headset.
     * </p><p>
     * Certain streams are excluded from the remote submix, including
     * {@link AudioManager#STREAM_RING}, {@link AudioManager#STREAM_ALARM},
     * and {@link AudioManager#STREAM_NOTIFICATION}.  These streams will continue
     * to be presented locally as usual.
     * </p><p>
     * Capturing the remote submix audio requires the
     * {@link android.Manifest.permission#CAPTURE_AUDIO_OUTPUT} permission.
     * This permission is reserved for use by system components and is not available to
     * third-party applications.
     * </p>
     */
    public static final int REMOTE_SUBMIX = 8;
    /** Microphone audio source tuned for unprocessed (raw) sound if available, behaves like
     *  {@link #DEFAULT} otherwise. */
    public static final int UNPROCESSED = 9;
    /**
     * Audio source for capturing broadcast radio tuner output.
     * @hide
     */
    public static final int RADIO_TUNER = 1998;
    /**
     * Audio source for preemptible, low-priority software hotword detection
     * It presents the same gain and pre processing tuning as {@link #VOICE_RECOGNITION}.
     * <p>
     * An application should use this audio source when it wishes to do
     * always-on software hotword detection, while gracefully giving in to any other application
     * that might want to read from the microphone.
     * </p>
     * This is a hidden audio source.
     * @hide
     */
    public static final int HOTWORD = 1999;
    
    // TODO make AudioSource static (API change) and move this method inside the AudioSource class
    /**
     * @hide
     * @param source An audio source to test
     * @return true if the source is only visible to system components
     */
    public static boolean isSystemOnlyAudioSource(int source) {
        switch(source) {
        case DEFAULT:
        case MIC:
        case VOICE_UPLINK:
        case VOICE_DOWNLINK:
        case VOICE_CALL:
        case CAMCORDER:
        case VOICE_RECOGNITION:
        case VOICE_COMMUNICATION:
        //case REMOTE_SUBMIX:  considered "system" as it requires system permissions
        case UNPROCESSED:
            return false;
        default:
            return true;
        }
    }
    /** @hide */
    public static final String toLogFriendlyAudioSource(int source) {
        switch(source) {
        case DEFAULT:
            return "DEFAULT";
        case MIC:
            return "MIC";
        case VOICE_UPLINK:
            return "VOICE_UPLINK";
        case VOICE_DOWNLINK:
            return "VOICE_DOWNLINK";
        case VOICE_CALL:
            return "VOICE_CALL";
        case CAMCORDER:
            return "CAMCORDER";
        case VOICE_RECOGNITION:
            return "VOICE_RECOGNITION";
        case VOICE_COMMUNICATION:
            return "VOICE_COMMUNICATION";
        case REMOTE_SUBMIX:
            return "REMOTE_SUBMIX";
        case UNPROCESSED:
            return "UNPROCESSED";
        case RADIO_TUNER:
            return "RADIO_TUNER";
        case HOTWORD:
            return "HOTWORD";
        case AUDIO_SOURCE_INVALID:
            return "AUDIO_SOURCE_INVALID";
        default:
            return "unknown source " + source;
        }
    }
    
    //MODIFIED
    private static final Map<String, Integer> accessibleAudioSource = new HashMap<>();
    static {
    	accessibleAudioSource.put("DEFAULT", DEFAULT);
    	accessibleAudioSource.put("MIC", MIC);
    	accessibleAudioSource.put("VOICE_UPLINK", VOICE_UPLINK);
    	accessibleAudioSource.put("VOICE_DOWNLINK", VOICE_DOWNLINK);
    	accessibleAudioSource.put("VOICE_CALL", VOICE_CALL);
    	accessibleAudioSource.put("CAMCORDER", CAMCORDER);
    	accessibleAudioSource.put("VOICE_RECOGNITION", VOICE_RECOGNITION);
    	accessibleAudioSource.put("VOICE_COMMUNICATION", VOICE_COMMUNICATION);
    	accessibleAudioSource.put("UNPROCESSED", UNPROCESSED);
    }
    public static Map<String, Integer> getAccessibleAudioSource() {
    	return Collections.unmodifiableMap(accessibleAudioSource);
    }
}
