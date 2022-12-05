package com.example.ecommerce.ViewHolder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ecommerce.Interface.itemClickListener;
import com.example.ecommerce.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductPrice, txtProductQuantity;
    private itemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity = itemView.findViewById(R.id.cart_product_quantity);
    }

    @Override
    public void onClick(View view)
    {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }

    public void setItemClickListener(com.example.ecommerce.Interface.itemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }
}
