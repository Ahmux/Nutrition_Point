package ahmux.nutritionpoint;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class Fragment3 extends Fragment implements View.OnClickListener{

    Button b1;
    NumberPicker noPicker = null;
    FragmentsCommunicator fc;
    EditText et1, et2;
    int age;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f3_layout, container, false);

        fc = (FragmentsCommunicator) getActivity();
        b1 = (Button)view.findViewById(R.id.button);
        b1.setOnClickListener(this);

        et1 =(EditText) view.findViewById(R.id.editText1);
        et2 =(EditText) view.findViewById(R.id.editText2);

        noPicker = (NumberPicker)view.findViewById(R.id.numberPicker);
        noPicker.setMaxValue(100);
        noPicker.setMinValue(10);
        //selector wheel wraps when reaching the min/max value.
        noPicker.setWrapSelectorWheel(false);
        // special format --> noPicker.setFormatter();
        noPicker.setValue(30);
        noPicker.setSaveEnabled(false);
        noPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                age = newValue;
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        fc = (FragmentsCommunicator) getActivity();

        if (age == 0){
            Toast.makeText(getActivity(), "Please Enter your AGE!", Toast.LENGTH_SHORT).show();
        }else  if (et1.getText().toString().matches("")) {
            Toast.makeText(getActivity(), "Please Enter your WEIGHT in kg!", Toast.LENGTH_SHORT).show();
        }else if(Integer.valueOf(et1.getText().toString()) < 20 || Integer.valueOf(et1.getText().toString()) > 250){
            Toast.makeText(getActivity(), "Please Enter valid WEIGHT in kg!", Toast.LENGTH_SHORT).show();
        }else if (et2.getText().toString().matches("")){
            Toast.makeText(getActivity(), "Please Enter your Height in cm!", Toast.LENGTH_SHORT).show();
        }else if(Integer.valueOf(et2.getText().toString()) < 60 || Integer.valueOf(et2.getText().toString()) > 220){
            Toast.makeText(getActivity(), "Please Enter valid Height in cm!", Toast.LENGTH_SHORT).show();
        }
        else{
            fc.respond("age", age );
            fc.respond("weight", Integer.valueOf(et1.getText().toString()));
            fc.respond("height", Integer.valueOf(et2.getText().toString()));
            fc.respond("analyze", 0);
        }
    }
}
