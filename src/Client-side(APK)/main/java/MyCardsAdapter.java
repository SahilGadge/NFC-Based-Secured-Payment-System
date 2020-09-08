package com.the43appmart.aniruddha.nfcpay;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class MyCardsAdapter extends RecyclerView.Adapter<MyCardsAdapter.ViewHolder> {

    private MyCards context;
    private List<MyCardsData> my_data;

    public MyCardsAdapter(MyCards context, List<MyCardsData> MyCardsData) {
        this.context = context;
        this.my_data = MyCardsData;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from( parent.getContext() ).inflate( R.layout.my_cards_card, parent, false );

        return new ViewHolder( itemView );
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.id.setText( my_data.get( position ).getId() );
//        holder.Course.setText( my_data.get( position ).getCourse() );
//        holder.Time.setText( my_data.get( position ).getTime() );
//        holder.Date.setText( my_data.get( position ).getStarting_date() );
        String CARDBRAND = my_data.get( position ).getBrand();
//                    holder.imgCardImage.setBackgroundTintList( R.drawable.master_card );
//            Resources res = getResources(); //resource handle
//            Drawable drawable = res.getDrawable(R.drawable.master_card); //new Image that was added to the res folder
//
//            holder.imgCardImage.setBackground(drawable);
        if (CARDBRAND.equals( "VISA" )) {
//            holder.imgCardImage.setBackgroundDrawable( R.drawable.master_card );
        }
        holder.CardHolerName.setText( my_data.get( position ).getName() );
        holder.Brand.setText( my_data.get( position ).getBrand() );
        holder.CardNumber.setText( my_data.get( position ).getCardNumber() );
        holder.CVV.setText( "CVV- " + my_data.get( position ).getCVV() );
        holder.Expiry.setText( "Exp-" + my_data.get( position ).getExpiry() );

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView CardHolerName, CardNumber, CVV, Expiry, Brand;
        public FrameLayout imgCardImage;

        public ViewHolder(final View itemView) {
            super( itemView );
            imgCardImage = (FrameLayout) itemView.findViewById( R.id.imgCardImage );
            Brand = (TextView) itemView.findViewById( R.id.txtBrand );
            CardHolerName = (TextView) itemView.findViewById( R.id.txtCardHolderName );
            CardNumber = (TextView) itemView.findViewById( R.id.txtCardNumber );
            CVV = (TextView) itemView.findViewById( R.id.txtCVV );
            Expiry = (TextView) itemView.findViewById( R.id.txtExpiry );

        }
    }
}
