package com.grevoltec.cosecha.views.secure.fragments.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grevoltec.cosecha.R;
import com.grevoltec.cosecha.entities.CosechaEntity;
import com.grevoltec.cosecha.entities.core.AbsRegEntity;
import com.grevoltec.cosecha.views.secure.fragments.core.holder.AbsViewHolder;

import java.util.List;

public abstract class AbsRecyclerViewAdapter<E extends AbsRegEntity, H extends AbsViewHolder> extends RecyclerView.Adapter<H> {

    protected List<E> mEntities;
    protected Context mContext;

    public AbsRecyclerViewAdapter(Context context, List<E> entities) {
        this.mEntities = entities;
        this.mContext = context;
    }

    protected View inflaterLayout(ViewGroup viewGroup, int layout) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
    }

    @Override
    public abstract H onCreateViewHolder(ViewGroup viewGroup, int i);

    @Override
    public abstract void onBindViewHolder(H viewHolder, int i);

    @Override
    public int getItemCount() {
        return (null != mEntities ? mEntities.size() : 0);
    }

    public void removeItem(int position) {
        mEntities.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItem(E position) {
        int pos = mEntities.indexOf(position);
        mEntities.remove(position);
        notifyItemRemoved(pos);
    }

    public E getItemByPosition(int position) {
        return mEntities.get(position);
    }

    public void restoreItem(E item, int position) {
        mEntities.add(position, item);
        notifyItemInserted(position);
    }

}