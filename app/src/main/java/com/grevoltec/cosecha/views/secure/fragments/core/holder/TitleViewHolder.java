package com.grevoltec.cosecha.views.secure.fragments.core.holder;

import android.view.View;
import android.widget.TextView;

import com.grevoltec.cosecha.R;

public class TitleViewHolder extends AbsViewHolder {

    public TextView lblTitle;

    public TitleViewHolder(View itemView) {
        super(itemView);
        lblTitle = itemView.findViewById(R.id.lblTitle);
    }

}