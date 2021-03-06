package com.beetle.bauhinia;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import java.beans.PropertyChangeEvent;

import com.beetle.bauhinia.db.IMessage;
import com.beetle.imkit.R;

public class MessageAudioView extends MessageRowView {

    protected ProgressBar uploadingProgressBar;


    public MessageAudioView(Context context, boolean incomming, boolean isShowUserName) {
        super(context, incomming, isShowUserName);

        final int contentLayout;
        contentLayout = R.layout.chat_content_audio;

        ViewGroup group = (ViewGroup)findViewById(R.id.content);
        group.addView(inflater.inflate(contentLayout, group, false));
    }

    class AudioHolder  {
        ImageView control;
        TextView duration;

        AudioHolder(View view) {
            control = (ImageView)view.findViewById(R.id.play_control);
            duration = (TextView)view.findViewById(R.id.duration);
        }
    }

    @Override
    public void setMessage(IMessage msg, boolean incomming) {
        super.setMessage(msg, incomming);

        boolean playing = message.getPlaying();
        View convertView = this;

        final IMessage.Audio audio = (IMessage.Audio) msg.content;
        AudioHolder audioHolder =  new AudioHolder(convertView);

        if (playing) {
            AnimationDrawable voiceAnimation;
            if (incomming) {
                audioHolder.control.setImageResource(R.anim.voice_from_icon);
            } else {
                audioHolder.control.setImageResource(R.anim.voice_to_icon);
            }
            voiceAnimation = (AnimationDrawable) audioHolder.control.getDrawable();
            voiceAnimation.start();
        } else {
            if (incomming) {
                audioHolder.control.setImageResource(R.drawable.ease_chatfrom_voice_playing);
            } else {
                audioHolder.control.setImageResource(R.drawable.ease_chatto_voice_playing);
            }
        }

        Period period = new Period().withSeconds((int) audio.duration);
        PeriodFormatter periodFormatter = new PeriodFormatterBuilder()
                .appendMinutes()
                .appendSeparator(":")
                .appendSeconds()
                .appendSuffix("\"")
                .toFormatter();
        audioHolder.duration.setText(periodFormatter.print(period));


//        boolean uploading = msg.getUploading();
//        if (uploading) {
//            uploadingProgressBar.setVisibility(View.VISIBLE);
//        } else {
//            uploadingProgressBar.setVisibility(View.GONE);
//        }
        convertView.requestLayout();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        super.propertyChange(event);
        if (event.getPropertyName().equals("playing")) {
            Log.i("gobelieve", "playing changed");
            boolean playing = this.message.getPlaying();
            AudioHolder audioHolder =  new AudioHolder(this);
            if (playing) {
                AnimationDrawable voiceAnimation;
                if (incomming) {
                    audioHolder.control.setImageResource(R.anim.voice_from_icon);
                } else {
                    audioHolder.control.setImageResource(R.anim.voice_to_icon);
                }
                voiceAnimation = (AnimationDrawable) audioHolder.control.getDrawable();
                voiceAnimation.start();
                Log.i("gobelieve", "start animation");
            } else {
                if (incomming) {
                    audioHolder.control.setImageResource(R.drawable.ease_chatfrom_voice_playing);
                } else {
                    audioHolder.control.setImageResource(R.drawable.ease_chatto_voice_playing);
                }
            }
        }
    }

}