package com.bignerdranch.android.beatboxbnrga;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by smaikap on 4/8/16.
 */
public class BeatBoxFragment extends Fragment {

    public static BeatBoxFragment newInstance() {
        return new BeatBoxFragment();
    }

    private BeatBox mBeatBox;

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        this.mBeatBox = new BeatBox(this.getContext());
    }

    @Override
    public View onCreateView(final LayoutInflater layoutInflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = layoutInflater.inflate(R.layout.fragment_beat_box, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_beat_box_recycle_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(new SoundAdapter(this.mBeatBox.getSounds()));
        return view;
    }

    /* ===================================== View Holder ==============================================*/
    private class SoundHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Button mButton;
        private Sound mSound;

        public SoundHolder(final LayoutInflater inflater, final ViewGroup container) {
            super(inflater.inflate(R.layout.list_item_sound, container, false));
            this.mButton = (Button) itemView.findViewById(R.id.list_item_sound_button);
            this.mButton.setOnClickListener(this);
        }

        public void bindSound(final Sound sound) {
            this.mSound = sound;
            this.mButton.setText(this.mSound.getName());
        }

        @Override
        public void onClick(View view) {
            BeatBoxFragment.this.mBeatBox.play(this.mSound);
        }
    }

    /*========================================== END OF VIEW HOLDER ======================================*/

    /*======================================View Adapter ================================================*/
    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder> {
        private List<Sound> mSounds;

        public SoundAdapter(final List<Sound> sounds) {
            this.mSounds = sounds;
        }

        @Override
        public SoundHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new SoundHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(final SoundHolder holder, final int position) {
            final Sound sound = this.mSounds.get(position);
            holder.bindSound(sound);
        }

        @Override
        public int getItemCount() {
            return this.mSounds.size();
        }
    }
    /*======================================================================================================*/

    public void onDestroy() {
        super.onDestroy();
        this.mBeatBox.release();
    }
}
