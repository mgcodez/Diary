package online.diary;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


/**
 * Created by Belal on 2/26/2017.
 */

public class Photos extends ArrayAdapter<CalendarNav> {
    private Activity context;
    List<CalendarNav> artists;

    public Photos(Activity context, List<CalendarNav> artists) {
        super(context, R.layout.activity_photos, artists);
        this.context = context;
        this.artists = artists;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_photos, null, true);

        ImageView imageView= (ImageView) listViewItem.findViewById(R.id.imageView);

        CalendarNav artist = artists.get(position);
        Glide.with(context).load(artist.getmATitle()).into(imageView);
        return listViewItem;
    }
}
