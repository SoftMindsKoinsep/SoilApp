package eu.soilErasmus.soil;
import android.nfc.Tag;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

import androidx.recyclerview.widget.RecyclerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.YouTubePlayerViewHolder> {

    private final List<String> playlistIds;
    public Lifecycle lifecycle ;

    @NonNull
    public YouTubePlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.youtube_player_item,viewGroup,false);
        return new YouTubePlayerViewHolder(lifecycle, itemView);
    }


    public void onBindViewHolder(@NonNull YouTubePlayerViewHolder holder, int position) {
        holder.cuePlaylist(this.playlistIds.get(position));
    }

    public int getItemCount() {
        return playlistIds.size();
    }

    public RecyclerViewAdapter( List<String> playlistIds,  Lifecycle lifecycle) {
        super();
        this.playlistIds = playlistIds;
        this.lifecycle = lifecycle;
    }
    public class YouTubePlayerViewHolder extends RecyclerView.ViewHolder{
        private boolean flag;

        public YouTubePlayerViewHolder( Lifecycle lifecycle, View itemView) {
            super(itemView);

            YouTubePlayerView youTubePlayerView = itemView.findViewById(R.id.youtube_player_view);
            lifecycle.addObserver(youTubePlayerView);

        }

        public void cuePlaylist(String s) {
            if (!flag) {


                YouTubePlayerView youTubePlayerView = itemView.findViewById(R.id.youtube_player_view);
                lifecycle.addObserver(youTubePlayerView);

                IFramePlayerOptions iFramePlayerOptions = new IFramePlayerOptions.Builder()
                        .controls(1)
                        .listType("playlist")
                        .list(s)
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
