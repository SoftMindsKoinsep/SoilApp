package eu.soilErasmus.soil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

import androidx.recyclerview.widget.RecyclerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.YouTubePlayerViewHolder> {

    private final List<String> playlistIds;
    private final Lifecycle lifecycle ;

    RecyclerViewAdapter( List<String> playlistIds,  Lifecycle lifecycle) {
        this.playlistIds = playlistIds;
        this.lifecycle = lifecycle;
    }

    @NonNull
    public YouTubePlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.youtube_player_item,viewGroup,false);
        return new YouTubePlayerViewHolder(itemView);
    }


    public void onBindViewHolder(@NonNull YouTubePlayerViewHolder holder, int position) {

        lifecycle.addObserver(holder.youTubePlayerView);
        IFramePlayerOptions iFramePlayerOptions = new IFramePlayerOptions.Builder()
                .controls(1)
                //.rel(0)
                .listType("playlist")
                .list(playlistIds.get(position))
                .ccLoadPolicy(0)
                .ivLoadPolicy(0)
                .build();

        holder.youTubePlayerView.setEnableAutomaticInitialization(false);
        holder.youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
        }, true, iFramePlayerOptions);
    }

    public int getItemCount() {
        return playlistIds.size();
    }


    public class YouTubePlayerViewHolder extends RecyclerView.ViewHolder{
        private YouTubePlayerView youTubePlayerView;

        public YouTubePlayerViewHolder(View itemView) {
            super(itemView);
            youTubePlayerView = itemView.findViewById(R.id.youtube_player_view);

        }
    }
}
