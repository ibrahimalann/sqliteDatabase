package alan.software.sqlitedatabase;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import alan.software.sqlitedatabase.databinding.RecyclerviewRowBinding;

public class Adapter extends RecyclerView.Adapter<Adapter.DataHolder> {

    ArrayList<Model> arrayList;
    public  Adapter(ArrayList<Model> arrayList){
        this.arrayList=arrayList;
    }

    @Override
    public DataHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        RecyclerviewRowBinding recyclerviewRowBinding=RecyclerviewRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new DataHolder(recyclerviewRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter.DataHolder holder, int position) {
        holder.binding.nameTextview.setText(arrayList.get(position).name);
        holder.binding.lessonTextview.setText(arrayList.get(position).lesson);
        holder.binding.noteTexyview.setText(String.valueOf(arrayList.get(position).note));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.itemView.getContext(),AddDataActivity.class);
                intent.putExtra("dataId",arrayList.get(position).id);
                intent.putExtra("info","old");
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class  DataHolder extends RecyclerView.ViewHolder{
        private RecyclerviewRowBinding binding;

        public DataHolder(RecyclerviewRowBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
