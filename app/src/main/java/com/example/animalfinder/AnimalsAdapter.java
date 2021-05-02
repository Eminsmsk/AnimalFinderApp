package com.example.animalfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AnimalsAdapter extends RecyclerView.Adapter<AnimalsAdapter.CardViewHolder> {
    private Context mContext;
    private List<Animal> animalList;
    private CallBackInterface listener;



    public AnimalsAdapter(Context mContext, List<Animal> animalList) {
        this.mContext = mContext;
        this.animalList = animalList;
        listener = ((CallBackInterface) mContext);
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_animal, parent, false);
        return new AnimalsAdapter.CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

        Animal a = animalList.get(position);
        holder.imageViewAnimal.setImageResource(mContext.getResources().getIdentifier(a.getImageName(),"drawable",mContext.getPackageName()));
        holder.imageViewAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean flag = listener.checkAnswer(a.getAnimalName());
                if(flag)
                    listener.countConsecutive();
            }
        });



    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        private CardView animalCard;
        private ImageView imageViewAnimal;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            this.animalCard = itemView.findViewById(R.id.animalCard);
            this.imageViewAnimal = itemView.findViewById(R.id.imageViewAnimal);;
        }
    }




}
