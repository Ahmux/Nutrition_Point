package ahmux.nutritionpoint;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment4 extends Fragment implements View.OnClickListener{

    Button b1, b2, b3;
    String age, weight, height;
    TextView tv1,tv2, tv3, tv4, tv5, tv6;
    ImageView iv1, iv2;
    FragmentsCommunicator fc;
    SeekBar seekBar;
    double bmi, water;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f4_layout,container,false);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Questv1-Bold.otf");

        MainActivity activity = (MainActivity) getActivity();
        age = activity.getMyData("age");
        weight = activity.getMyData("weight");
        height = activity.getMyData("height");


        iv1 = (ImageView)view.findViewById(R.id.imageView1);
        iv1.setOnClickListener(this);
        iv2 = (ImageView)view.findViewById(R.id.imageView2);
        iv2.setOnClickListener(this);

        b1 = (Button)view.findViewById(R.id.okBtn);
        b1.setOnClickListener(this);
        b2 = (Button)view.findViewById(R.id.waterBtn);
        b2.setOnClickListener(this);
        b3 = (Button)view.findViewById(R.id.caloriesBtn);
        b3.setOnClickListener(this);

        tv1 = (TextView)view.findViewById(R.id.bmiTV1);
        tv2 = (TextView)view.findViewById(R.id.bmiTV2);
        tv3 = (TextView)view.findViewById(R.id.genderTV);
        tv4 = (TextView)view.findViewById(R.id.ageTV);
        tv5 = (TextView)view.findViewById(R.id.weightTV);
        tv6 = (TextView)view.findViewById(R.id.waterTV);


        tv3.setText(activity.getMyData("gender"));
        tv3.setTypeface(tf);
        tv4.setText(age +" "+ getString(R.string.years));
        tv4.setTypeface(tf);
        tv5.setText(weight +" " + getString(R.string.kg));
        tv5.setTypeface(tf);


        //BMI Calculations
        bmi = (Integer.valueOf(weight) * 10000) / (Integer.valueOf(height) * Integer.valueOf(height));
        tv1.setText("Your BMI: " + Math.round(bmi * 10d) / 10d);
        tv1.setTypeface(tf);

        if (bmi >= 30){
            tv2.setText("Obesity");
        }else if ((bmi >= 25) && (bmi < 30)){
            tv2.setText("Overweight");
        }else if (bmi <= 18){
            tv2.setText("Under Weight");
        }else if((bmi > 18) && (bmi < 25) ){
            tv2.setText("Normal");
        }

        seekBar = view.findViewById(R.id.seekBar);
        seekBar.setProgress((int)Math.round(bmi));



        //Calculate Body water
        if (Integer.valueOf(age) <= 30){
            water = (Integer.valueOf(weight) * 42 * 2.95) / (28.3 * 100);

        }else if (Integer.valueOf(age) > 30 && Integer.valueOf(age) <= 35){
            water = (Integer.valueOf(weight) * 37 * 2.95) / (28.3 * 100);

        }else if (Integer.valueOf(age) > 35){
            water = (Integer.valueOf(weight) * 32 * 2.95) / (28.3 * 100);
        }

        tv6.setText("You need: " + (Math.round(water * 10d) / 10d) + " L/day");


        return view;
    }

    @Override
    public void onClick(View view) {
        fc = (FragmentsCommunicator) getActivity();
        if(view.getId() == R.id.okBtn){
            fc.respond("metabolic", 40);
            fc.respond("ok",0);
        }else if(view.getId() == R.id.imageView1){
            Toast.makeText(getActivity(), "BMI = Body Mass Index", Toast.LENGTH_LONG).show();
        }else if(view.getId() == R.id.imageView2) {
            Toast.makeText(getActivity(), "Water Body needs", Toast.LENGTH_LONG).show();
        }else if(view.getId() == R.id.imageView3) {
            Toast.makeText(getActivity(), "Calories Analysis", Toast.LENGTH_LONG).show();
        }else if(view.getId() == R.id.waterBtn){
            Intent RemindersIntent = new Intent(getActivity(), RemindersActivity.class);
            startActivity(RemindersIntent);
        }else if(view.getId() == R.id.caloriesBtn) {
            Intent apiIntent = new Intent(getActivity(), ApiActivity.class);
            startActivity(apiIntent);
        }
    }
}
