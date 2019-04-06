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
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


/**
 * Created by Belal on 2/26/2017.
 */

public class DiaryModel extends ArrayAdapter<AddEntry> {
    private Activity context;
    List<AddEntry> artists;
    FirebaseDatabase mRef;
    FirebaseDatabase m1;
    FirebaseDatabase m2;
    public DiaryModel(Activity context, List<AddEntry> artists) {
        super(context, R.layout.diarymodel, artists);
        this.context = context;
        this.artists = artists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.diarymodel, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.mtext);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.mDate);
        TextView textViewTag = (TextView) listViewItem.findViewById(R.id.Tags);
        ImageView imageView= (ImageView) listViewItem.findViewById(R.id.imageL);

        AddEntry artist = artists.get(position);
        textViewName.setText(artist.getmBDescription());
        textViewGenre.setText(artist.getmCDate());
        textViewTag.setText(artist.getmETags());
        Glide.with(context).load(artist.getmATitle()).into(imageView);
        return listViewItem;
    }

}
