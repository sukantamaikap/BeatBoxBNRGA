package com.bignerdranch.android.beatboxbnrga;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smaikap on 4/8/16.
 */
public class BeatBox {
    private static final String TAG = "BeatBox";
    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;

    private final List<Sound> mSounds = new ArrayList<>();
    private AssetManager mAssets;
    private SoundPool mSoundPool;

    public BeatBox(final Context context) {
        this.mAssets = context.getAssets();
        this.mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    private void loadSounds() {
        String[] soundNames;

        try {
            soundNames = this.mAssets.list(BeatBox.SOUNDS_FOLDER);
            Log.i(TAG, "Found " + soundNames.length + " sounds");
        } catch (final IOException exc) {
            Log.e(TAG, "Could not list assets");
            return;
        }

        for (final String fileName : soundNames) {
            try {
                final String assetPath = BeatBox.SOUNDS_FOLDER + "/" + fileName;
                final Sound sound = new Sound(assetPath);
                load(sound);
                this.mSounds.add(sound);
            } catch (final IOException ioe) {
                Log.e(TAG, "Could not load sound " + fileName, ioe);
            }
        }
    }

    private void load(final Sound sound) throws IOException {
        AssetFileDescriptor assetFileDescriptor = this.mAssets.openFd(sound.getAssetPath());
        final int soundID = this.mSoundPool.load(assetFileDescriptor, 1);
        sound.setSoundId(soundID);
    }

    public List<Sound> getSounds() {
        return this.mSounds;
    }

    public void play(final Sound sound) {
        final Integer soundId = sound.getSoundId();
        if (soundId == null) {
            Log.e(TAG, "sound id is null, something is wrong with this asset");
        }
        this.mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void release() {
        this.mSoundPool.release();
    }
}
