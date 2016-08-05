package com.bignerdranch.android.beatboxbnrga;

/**
 * Created by smaikap on 4/8/16.
 */
public class Sound {
    private String mAssetPath;
    private String mName;
    private Integer mSoundId;

    public Sound(final String assetPath) {
        this.mAssetPath = assetPath;
        final String[] components = assetPath.split("/");
        final String fileName = components[components.length -1];
        this.mName = fileName.replace(".wav", "");
    }

    public String getAssetPath() {
        return this.mAssetPath;
    }

    public String getName() {
        return this.mName;
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }
}
