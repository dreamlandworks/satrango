package com.satrangolimitless.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.satrangolimitless.Interface.onDateclick;
import com.satrangolimitless.Interface.onFrom_timeclick;
import com.satrangolimitless.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.satrangolimitless.Utils.Base_Url.Image_url;

public class CalenderdateAdapter extends RecyclerView.Adapter<CalenderdateAdapter.HeroViewHolder> {

    private final Context context;
    private final List<String> arrayList;
    String odate;
 onDateclick onDateclick;
    public CalenderdateAdapter(Context mCtx, List<String> arrayList,onDateclick onDateclick) {
        this.context = mCtx;
        this.arrayList = arrayList;
        this.onDateclick = onDateclick  ;
    }


    @NonNull
    @Override
    public CalenderdateAdapter.HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_vendor_calendar, parent, false);
        return new CalenderdateAdapter.HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalenderdateAdapter.HeroViewHolder holder, final int position) {



try {
System.out.println("  CalenderdateAdapter   -----     "+arrayList.get(position));
    String[] splitStr = arrayList.get(position).split("\\s+");
  odate=splitStr[0];
    System.out.println("  CalenderdateAdapterodate   -----     "+odate);


    SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MMM-yyyy" );
    Date date;
    try {
        date = originalFormat.parse(odate);
        System.out.println("Old Format :   " + originalFormat.format(date));
        System.out.println("New Format :   " + targetFormat.format(date));


        String datettt= targetFormat.format(date);
        String[] splitdatettt = datettt.split("-");
        String onlydate=splitdatettt[0];
        String mnth=splitdatettt[1];
        System.out.println("onlydate :   " + onlydate);
        System.out.println("mnth :   " + mnth);
        holder.txtdate.setText(onlydate);
        holder.txtmnth.setText(mnth);
    } catch (ParseException ex) {
        // Handle Exception.
    }

}catch (Exception e ){e .printStackTrace();}


holder.txtdate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        onDateclick.ondateclick(arrayList.get(position));

    }
});
    }

    public void removeAt(int position) {
        arrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayList.size());
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class HeroViewHolder extends RecyclerView.ViewHolder {

         TextView txtdate,txtmnth;

        public HeroViewHolder(View itemView) {
            super(itemView);
            txtdate = itemView.findViewById(R.id.txtdate);
            txtmnth = itemView.findViewById(R.id.txtmnth);

        }
    }
}




