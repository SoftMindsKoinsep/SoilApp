package eu.soilErasmus.soil;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlantRecyclerViewAdapter extends RecyclerView.Adapter<PlantRecyclerViewAdapter.PlantViewHolder> {

    Context context;
    List<Plant> plantList;

    public PlantRecyclerViewAdapter(Context context, List<Plant> plantList) {
        this.context = context;
        this.plantList = plantList;
    }

    @NonNull
    @Override
    public PlantRecyclerViewAdapter.PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlantViewHolder(LayoutInflater.from(context).inflate(R.layout.plant_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlantRecyclerViewAdapter.PlantViewHolder holder, int position) {

        Plant plant = plantList.get(position);
        holder.plantName.setText(plant.getName());
        holder.plantDescription.setText(plant.getDescription());
        holder.plant = plant;
        holder.expandedLayoutVisibility = plant.isVisible();
        holder.arrow.setImageResource(holder.arrowChange());
        holder.expandedLayout.setVisibility(plant.isVisible()? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    public class PlantViewHolder extends RecyclerView.ViewHolder{

        Button artificialButton;
        TextView plantName,plantDescription;
        ConstraintLayout clickableSpace,expandedLayout;
        Boolean expandedLayoutVisibility;
        Plant plant;
        ImageView arrow;


        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);

            plantName = itemView.findViewById(R.id.plant_name);
            plantDescription = itemView.findViewById(R.id.plant_description);
            artificialButton = itemView.findViewById(R.id.artificial_button);
            expandedLayout = itemView.findViewById(R.id.expanded_layout);
            clickableSpace = itemView.findViewById(R.id.clickable_space);
            arrow = itemView.findViewById(R.id.arrow_down);

            clickableSpace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    plant = plantList.get(getAdapterPosition());
                    plant.setVisibility(!expandedLayoutVisibility);
                    notifyItemChanged(getAdapterPosition());
                }
            });

            artificialButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //plant.assetResource
                    Intent intent= new Intent(context, ARCamera.class);
                    intent.putExtra("modelForAR", plant.getAssetResource());
                    context.startActivity(intent);
                }
            });
        }

        public int arrowChange(){
            if (expandedLayoutVisibility) {return R.drawable.baseline_keyboard_arrow_up_24;}

            return R.drawable.baseline_keyboard_arrow_down_24;
        }
    }
}

