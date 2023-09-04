package eu.soilErasmus.soil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.VideoViewHolder> {
    private final Context context;
    private final List<Video> videoList;
    private final RecyclerViewVideoListPass recyclerViewVideoListPass;

    public VideoRecyclerViewAdapter(Context context, List<Video> videoList,RecyclerViewVideoListPass recyclerViewVideoListPass) {
        this.context = context;
        this.videoList = videoList;
        this.recyclerViewVideoListPass = recyclerViewVideoListPass;
    }

    @NonNull
    @Override
    public VideoRecyclerViewAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.default_player_controller,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoRecyclerViewAdapter.VideoViewHolder holder, int position) {
        Video video = videoList.get(position);
        ExoPlayer exoPlayer = holder.exoPlayer = new ExoPlayer.Builder(context).build();
        holder.playerView.setPlayer(exoPlayer);
        holder.videoTitle.setText(video.getName());

        exoPlayer.addListener(new Player.Listener() {

            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == Player.STATE_BUFFERING) {
                    holder.progressBar.setVisibility(View.VISIBLE);
                }

                else if(playbackState == Player.STATE_IDLE) {
                    holder.progressBar.setVisibility(View.GONE);
                }

                else if(playbackState == Player.STATE_READY) {
                    holder.progressBar.setVisibility(View.GONE);
                    exoPlayer.play();
                    holder.videoTitle.setVisibility(View.GONE);
                }

                else if (playbackState == Player.STATE_ENDED){
                    exoPlayer.seekTo(0);
                    exoPlayer.stop();
                }
            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying){
                if (isPlaying) holder.videoTitle.setVisibility(View.GONE);
                else holder.videoTitle.setVisibility(View.VISIBLE);
            }
        });

        MediaItem mediaItem = MediaItem.fromUri(video.getUri());
        exoPlayer.setMediaItem(mediaItem);

        recyclerViewVideoListPass.pass(exoPlayer);

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }


    public static class VideoViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        PlayerView playerView;
        ExoPlayer exoPlayer;
        TextView videoTitle;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            playerView = itemView.findViewById(R.id.playerView);
            progressBar = itemView.findViewById(R.id.progressBar);
            videoTitle = itemView.findViewById(R.id.videoTitle);
            playerView.setOnClickListener(view -> exoPlayer.prepare());

        }
    }
}
