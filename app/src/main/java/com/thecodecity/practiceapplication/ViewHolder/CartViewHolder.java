package com.thecodecity.practiceapplication.ViewHolder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thecodecity.practiceapplication.Interface.ItemClickListner;
import com.thecodecity.practiceapplication.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName,txtProductPrice, txtProductQuantity;
    private ItemClickListner itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtProductName= itemView.findViewById(R.id.cart_product_name);
        txtProductPrice= itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity= itemView.findViewById(R.id.cart_product_quantity);
Log.i("TAG","I am in cart view "+txtProductName );
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
   public void setItemClickListener(ItemClickListner itemClickListener){
        this.itemClickListener = itemClickListener;
   }
}
