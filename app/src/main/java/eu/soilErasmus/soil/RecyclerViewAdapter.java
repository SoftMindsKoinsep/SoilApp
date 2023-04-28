package eu.soilErasmus.soil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.recyclerview.widget.RecyclerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import eu.soilErasmus.soil.R;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.YouTubePlayerViewHolder> {

    private final List<String> playlistIds;
    private final Lifecycle lifecycle ;

    @NonNull
    public YouTubePlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_youtube_player,viewGroup,false);
        return new YouTubePlayerViewHolder(lifecycle, itemView);
    }


    public void onBindViewHolder( YouTubePlayerViewHolder holder, int position) {
        holder.cueVideo(this.playlistIds.get(position));
        //
    }

    public int getItemCount() {
        return this.playlistIds.size();
    }

    public RecyclerViewAdapter( List<String> playlistIds,  Lifecycle lifecycle) {
        super();
        this.playlistIds = playlistIds;
        this.lifecycle = lifecycle;
    }
    public static final class YouTubePlayerViewHolder extends RecyclerView.ViewHolder {

        private String currentId;
        private YouTubePlayer youTubePlayer;

        public void cueVideo(String playlistId){
            this.currentId = playlistId;
            youTubePlayer.cueVideo(playlistId,0.0f);

        }

        public YouTubePlayerViewHolder( Lifecycle lifecycle, View itemView) {
            super(itemView);

            YouTubePlayerView youTubePlayerView = itemView.findViewById(R.id.youtube_player_view);
            lifecycle.addObserver(youTubePlayerView);



            View overlayView = itemView.findViewById(R.id.overlay_view);
            overlayView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    YouTubePlayer player = YouTubePlayerViewHolder.this.youTubePlayer;
                    if (player != null) {
                        player.play();
                    }
                }
            });
            lifecycle.addObserver(youTubePlayerView);
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    YouTubePlayerViewHolder.this.youTubePlayer = youTubePlayer;
                    String playerId= YouTubePlayerViewHolder.this.currentId;
                    if(playerId != null){
                        youTubePlayer.cueVideo(playerId,0);

                    }
                }
            });

        }




    }
}