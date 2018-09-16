package ahmux.nutritionpoint;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Fragment2 extends Fragment implements View.OnClickListener {

    Button b1,b2,b3,b4,b5;
    FragmentsCommunicator fc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f2_layout, container, false);

        b1 = (Button) view.findViewById(R.id.button6);
        b2 = (Button) view.findViewById(R.id.button7);
        b3 = (Button) view.findViewById(R.id.button8);
        b4 = (Button) view.findViewById(R.id.button9);
        b5 = (Button) view.findViewById(R.id.button10);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        fc = (FragmentsCommunicator) getActivity();
        switch (view.getId()){
            case (R.id.button6):
                fc.respond("no activity", 0);
                break;
            case (R.id.button7):
                fc.respond("walking", 0);
                break;
            case (R.id.button8):
                fc.respond("exercize 1-2 days", 0);
                break;
            case (R.id.button9):
                fc.respond("exercize 3-5 days", 0);
                break;
            case (R.id.button10):
                fc.respond("everyday", 0);
                break;
        }
    }
}
