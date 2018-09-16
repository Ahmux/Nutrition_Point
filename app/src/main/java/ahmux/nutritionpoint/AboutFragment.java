package ahmux.nutritionpoint;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class AboutFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        ImageView facebook = (ImageView) view.findViewById(R.id.imageView7);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent fbIntent = new Intent(Intent.ACTION_VIEW);
                    fbIntent.setData(Uri.parse("https://www.facebook.com/supportnutritionpoit"));
                    startActivity(fbIntent);
                } catch (Exception e) {}
            }
        });

        ImageView twitter = (ImageView) view.findViewById(R.id.imageView8);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent tIntent = new Intent(Intent.ACTION_VIEW);
                    tIntent.setData(Uri.parse("https://twitter.com/dawlatyaktieen"));
                    startActivity(tIntent);
                } catch (Exception e) {}
            }
        });
        return view;
    }

}
