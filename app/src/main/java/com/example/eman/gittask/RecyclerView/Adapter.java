package com.example.eman.gittask.RecyclerView;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import com.example.eman.gittask.Fonts.TypefaceUtil;
import com.example.eman.gittask.R;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eman on 1/16/2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private List<Model> displayList;
    Context context;

    public Adapter(List<Model> displayList) {
        this.displayList = displayList;
    }

    @Override
    public Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        context = itemView.getContext();
        TypefaceUtil.overrideFonts(parent.getContext(), itemView);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Adapter.MyViewHolder holder, int position) {
        final Model DisModel = displayList.get(position);
        if (DisModel.getName() != null) {
            holder.Repos.setText(DisModel.getName());
        }
        if (DisModel.getLogin() != null) {
            holder.Log.setText(DisModel.getLogin());
        }
        holder.Description.setText(DisModel.getDescription());
        if (DisModel.getFork() == false || DisModel.getFork() == null) {
            holder.Back.setBackgroundResource(R.drawable.green);
        } else {
            holder.Back.setBackgroundResource(R.drawable.withe);
        }
        holder.Back.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog);
                View Repostoryd = dialog.findViewById(R.id.Repostiry);
                View Ownerd = dialog.findViewById(R.id.owner);
                Repostoryd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(DisModel.getHtml_url()!=null){
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(DisModel.getHtml_url()));
                       context. startActivity(browserIntent);}

                    }
                });
                Ownerd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(DisModel.getHtml_url_owner()!=null){
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(DisModel.getHtml_url_owner()));
                            context. startActivity(browserIntent);}

                    }
                });
                dialog.show();
                dialog.getWindow().setLayout((6 * DisModel.getWidth()) / 6, ViewGroup.LayoutParams.WRAP_CONTENT);

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.Repos)
        TextView Repos;
        @Bind(R.id.Description)
        TextView Description;
        @Bind(R.id.log)
        TextView Log;
        @Bind(R.id.Back)
        View Back;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
