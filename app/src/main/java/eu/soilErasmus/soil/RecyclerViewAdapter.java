package eu.soilErasmus.soil;
import android.annotation.SuppressLint;
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
    public Lifecycle lifecycle ;

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
        holder.cuePlaylist(this.playlistIds.get(position));
    }

    public int getItemCount() {
        return playlistIds.size();
    }


    public class YouTubePlayerViewHolder extends RecyclerView.ViewHolder{
        private boolean flag;

        @SuppressLint("ClickableViewAccessibility")
        public YouTubePlayerViewHolder(View itemView) {
            super(itemView);

        }
        public void cuePlaylist(String s) {
            if (!flag) {
                YouTubePlayerView youTubePlayerView = itemView.findViewById(R.id.youtube_player_view);
                lifecycle.addObserver(youTubePlayerView);

                IFramePlayerOptions iFramePlayerOptions = new IFramePlayerOptions.Builder()
                        .controls(1)
                        .listType("playlist")
                        .list(s)
                        .ccLoadPolicy(0)
                        .ivLoadPolicy(0)
                        .build();

                youTubePlayerView.setEnableAutomaticInitialization(false);
                lifecycle.addObserver(youTubePlayerView);
                youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
                }, true, iFramePlayerOptions);
                flag=true;

            }
        }
    }
}
