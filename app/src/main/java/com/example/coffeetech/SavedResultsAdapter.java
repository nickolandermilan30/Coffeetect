package com.example.coffeetech;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class SavedResultsAdapter extends RecyclerView.Adapter<SavedResultsAdapter.ViewHolder>  {

    private List<SavedResult> savedResults;

    public SavedResultsAdapter(List<SavedResult> savedResults) {
        this.savedResults = savedResults;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SavedResult result = savedResults.get(position);

        holder.resultImageView.setImageBitmap(result.getImageBitmap());
        holder.diseaseTextView.setText("Disease: " + result.getDiseaseName());
    }

    @Override
    public int getItemCount() {
        return savedResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView resultImageView;
        TextView diseaseTextView;
        ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            resultImageView = itemView.findViewById(R.id.resultImageView);
            diseaseTextView = itemView.findViewById(R.id.diseaseTextView);

            deleteButton = itemView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        SavedResult resultToDelete = savedResults.get(position);
                        deleteResult(resultToDelete);
                    }
                }

                private void deleteResult(SavedResult resultToDelete) {
                    int position = savedResults.indexOf(resultToDelete);
                    if (position != -1) {
                        savedResults.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });
        }
    }
}
