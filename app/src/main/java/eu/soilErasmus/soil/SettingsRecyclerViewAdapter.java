package eu.soilErasmus.soil;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SettingsRecyclerViewAdapter extends RecyclerView.Adapter<SettingsRecyclerViewAdapter.SettingsViewHolder> {
    Context context;
    List<SettingsItem> itemList;

    public SettingsRecyclerViewAdapter(Context context, List<SettingsItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public SettingsRecyclerViewAdapter.SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SettingsViewHolder(LayoutInflater.from(context).inflate(R.layout.settings_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsRecyclerViewAdapter.SettingsViewHolder holder, int position) {
        SettingsItem settingsItem = itemList.get(position);
        holder.itemName.setText(settingsItem.getName());
        holder.itemImage.setImageResource(settingsItem.getImgResource());

        holder.settingsItem = settingsItem;
        holder.itemList = itemList;
        holder.context = context;

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public static class SettingsViewHolder extends RecyclerView.ViewHolder {
        Context context;
        List<SettingsItem> itemList;
        SettingsItem settingsItem;
        ConstraintLayout clickableSpace;
        ImageView itemImage;
        TextView itemName;

        public SettingsViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);

            clickableSpace = itemView.findViewById(R.id.settingsItem);
            clickableSpace.setOnClickListener(view -> {
                settingsItem = itemList.get(getAbsoluteAdapterPosition());
                String[] itemNames = context.getResources().getStringArray(R.array.settings_items);
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if (settingsItem.getName().equals(itemNames[0])) {
                    if (firebaseUser != null) openAccount();
                    else Toast.makeText(context, "You haven't logged in yet.", Toast.LENGTH_SHORT).show();

                } else if (settingsItem.getName().equals(itemNames[1])) {
                    openAbout();

                } else if (settingsItem.getName().equals(itemNames[2])) {
                    if (firebaseUser != null) deleteUserData(firebaseAuth,firebaseUser);
                    else Toast.makeText(context, "You haven't logged in yet.", Toast.LENGTH_SHORT).show();

                } else if (settingsItem.getName().equals(itemNames[3])) {
                    if (firebaseUser != null) logOut(firebaseAuth);
                    else Toast.makeText(context, "You haven't logged in yet.", Toast.LENGTH_SHORT).show();

                }
            });
        }

        private void openAccount(){
            Intent intent = new Intent(context, account_info_page.class);
            context.startActivity(intent);
        }

        private void openAbout(){
            //privacy and security
        }

        private void deleteUserData(FirebaseAuth firebaseAuth,FirebaseUser firebaseUser){

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
            databaseReference.child(firebaseUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    deleteUser(firebaseAuth,firebaseUser);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG,e.getMessage());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();


                }
            });
        }

        private void deleteUser(FirebaseAuth firebaseAuth,FirebaseUser firebaseUser) {

            firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        firebaseAuth.signOut();
                        Toast.makeText(context, "Account deleted successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);

                    } else {
                        try {
                            throw task.getException();
                        } catch (Exception e){
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        private void logOut(FirebaseAuth firebaseAuth){
            firebaseAuth.signOut();
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);

        }
    }

}
    /*
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Are you sure?");
            builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {

                    databaseHelper.deleteData();
                    pref = getSharedPreferences("Data",MODE_PRIVATE);
                    editor = pref.edit();
                    editor.clear();
                    editor.apply();

                    Toast.makeText(context, "Account Deleted", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }


     */
