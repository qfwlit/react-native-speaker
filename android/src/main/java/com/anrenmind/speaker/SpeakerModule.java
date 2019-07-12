package com.anrenmind.speaker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.telecom.Call;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by lcg on 2016/10/10.
 */

public class SpeakerModule extends ReactContextBaseJavaModule {
    private final String SPEAKER = "speaker";
    private final String EARPIECE = "earpiece";
    private final String UNKNOWN = "unknown";

    public SpeakerModule(ReactApplicationContext reactApplicationContext){
        super(reactApplicationContext);
    }

    @Override
    public String getName() {
        return "Speaker";
    }

    @ReactMethod
    public void switchSpeaker(String speaker){
        Context context = getCurrentActivity().getBaseContext();
        AudioManager audioManager = (AudioManager)context.getSystemService(context.AUDIO_SERVICE);

        if (speaker.equals(SPEAKER)){
            audioManager.setSpeakerphoneOn(true);
            audioManager.setMode(AudioManager.MODE_NORMAL);
        }
        else if (speaker.equals(EARPIECE)){
            audioManager.setSpeakerphoneOn(false);
            audioManager.setMode(AudioManager.MODE_IN_CALL);
        }
        else {

        }
    }

    @ReactMethod
    public void getSpeaker(final Callback callback){
        ReactApplicationContext context = getReactApplicationContext();
        AudioManager audioManager = (AudioManager)context.getSystemService(context.AUDIO_SERVICE);
        int mode = audioManager.getMode();

        if (mode == AudioManager.MODE_IN_CALL){
            callback.invoke(null,EARPIECE);
        }
        else if (mode == AudioManager.MODE_NORMAL){
            callback.invoke(null,SPEAKER);
        }
        else {
            callback.invoke(null,UNKNOWN);
        }
    }

    @ReactMethod
    public void hasRecordPermission(final Callback callback){
        callback.invoke(null, selfPermissionGranted(Manifest.permission.RECORD_AUDIO));
    }

    @ReactMethod
    public void requestRecordPermission(final Callback callback){
        ActivityCompat.requestPermissions(getCurrentActivity(),
                new String[]{Manifest.permission.RECORD_AUDIO},
                0);

        callback.invoke(null,null);
    }

    public boolean selfPermissionGranted(String permission) {
        boolean result = true;
        int targetSdkVersion = 22;
        try {
            final PackageInfo info = getReactApplicationContext().getPackageManager().getPackageInfo(
                    getReactApplicationContext().getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ( targetSdkVersion >= Build.VERSION_CODES.M) {
                result = getReactApplicationContext().checkSelfPermission(permission)
                        == PackageManager.PERMISSION_GRANTED;
            } else {
                result = PermissionChecker.checkSelfPermission(getReactApplicationContext(), permission)
                        == PermissionChecker.PERMISSION_GRANTED;
            }
        }

        return result;
    }
}
