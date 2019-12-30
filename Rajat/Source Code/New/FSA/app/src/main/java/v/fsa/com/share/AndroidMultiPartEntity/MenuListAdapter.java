package v.fsa.com.share.AndroidMultiPartEntity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.HashMap;
import java.util.List;

import v.fsa.com.startup.fsa.LoginActivity;
import v.fsa.com.startup.fsa.R;
import v.fsa.com.user.NearestFireStation;

/**
 * Created by cursor on 4/30/2018.
 */

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MyViewHolder>{


    private Context mContext;
    private List<MenuList> mlist;


    HashMap<Integer,Class> classList;



    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, count;
        public ImageView thumbnail, overflow;
       public TextView cardDefault;
        public LinearLayout activity_home_page;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.cardTitle);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            activity_home_page=(LinearLayout)view.findViewById(R.id.activity_home_page);
            cardDefault=(TextView)view.findViewById(R.id.cardDefault);


        }


        @Override
        public void onClick(View view) {




        }
    }


    public MenuListAdapter(Context mContext, List<MenuList> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_page_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final MenuList album = mlist.get(position);
        holder.title.setText(album.getTitle());

        holder.activity_home_page.setTag(album.getTitle());
        holder.cardDefault.setText(album.getTitle_hr());

        holder.activity_home_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tag=view.getTag().toString();

                sendPageLink(tag);


                //Toast.makeText(mContext,"Click="+tag,Toast.LENGTH_LONG).show();

            }
        });



        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);


    }

    private void sendPageLink(String page){


        if(page.equalsIgnoreCase("Login")){

            Intent i=new Intent(mContext, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(i);

        }
        else if(page.equalsIgnoreCase("Nearest FireStation")){

            Intent i=new Intent(mContext, NearestFireStation.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(i);


        }
        else if(page.equalsIgnoreCase("Call 999")){

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:999"));
            mContext.startActivity(intent);


        }





     /*   if(page.equalsIgnoreCase("Requisition")){


            Intent i=new Intent(mContext, RequsitionActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(i);

        }
        else if(page.equalsIgnoreCase("Report")){


            Intent i=new Intent(mContext, Requsition_Report.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(i);

        }

        else if(page.equalsIgnoreCase("Profile")){


            Intent i=new Intent(mContext, MyProfile.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(i);

        }


        else if(page.equalsIgnoreCase("Logout")){


            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);

            sp.edit().clear().commit();

            Intent i=new Intent(mContext,LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(i);


        }*/


    }


}
