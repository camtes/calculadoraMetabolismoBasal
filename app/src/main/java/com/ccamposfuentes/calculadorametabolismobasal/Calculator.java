package com.ccamposfuentes.calculadorametabolismobasal;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class Calculator extends Fragment implements AdapterView.OnItemSelectedListener {

    private RelativeLayout calculator;
    private RelativeLayout results;
    private Spinner sex;
    private Spinner activity;
    private Button submit;
    private ImageButton close, save;

    double mb = 0, mbManterner=0;

    // Calculate
    private TextInputLayout weightLayout, heightLayout, yearsLayout;
    private EditText weight, height, years;

    // Results
    private TextView mbs, calories, lostWeight, gainWeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_calculator, container, false);

        // Calculator
        calculator = (RelativeLayout) rootView.findViewById(R.id.calculatorCalc);

        weightLayout = (TextInputLayout) rootView.findViewById(R.id.calculatorWeight);
        heightLayout = (TextInputLayout) rootView.findViewById(R.id.calculatorHeight);
        yearsLayout = (TextInputLayout) rootView.findViewById(R.id.calculatorYears);
        weight = weightLayout.getEditText();
        height = heightLayout.getEditText();
        years = yearsLayout.getEditText();
        sex = (Spinner) rootView.findViewById(R.id.calculatorSpinnerSex);
        activity = (Spinner) rootView.findViewById(R.id.calculatorSpinnerActivity);

        submit = (Button) rootView.findViewById(R.id.calculatorSubmit);

        // Results
        results = (RelativeLayout) rootView.findViewById(R.id.calculatorResult);

        mbs = (TextView) rootView.findViewById(R.id.resultGenerateMBS);
        calories = (TextView) rootView.findViewById(R.id.resultCaloriesGenerated);
        lostWeight = (TextView) rootView.findViewById(R.id.resultCaloriesLostWeightGenerated);
        gainWeight = (TextView) rootView.findViewById(R.id.resultCaloriesGainWeightGenerated);

        close =  (ImageButton) rootView.findViewById(R.id.resultClose);
        save =  (ImageButton) rootView.findViewById(R.id.resultSave);


        //submit
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!weight.getText().toString().isEmpty()) {
                    if (!height.getText().toString().isEmpty()) {
                        if (!years.getText().toString().isEmpty()) {
                            calculator.setVisibility(View.GONE);
                            results.setVisibility(View.VISIBLE);
                            calculateMBS(Double.valueOf(weight.getText().toString()),
                                    Double.valueOf(height.getText().toString()), Integer.valueOf(years.getText().toString()),
                                    sex.getSelectedItem().toString(), activity.getSelectedItemPosition());
                        }
                        else {
                            Toast.makeText(getContext(), "Debe de introducir su edad.", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "Debe de introducir su altura.", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Debe de introducir su peso.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // close
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculator.setVisibility(View.VISIBLE);
                results.setVisibility(View.GONE);
                weight.setText("");
                height.setText("");
                years.setText("");
                sex.setSelection(0);
                activity.setSelection(0);
            }
        });

        //save
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String currentDateandTime = sdf.format(new Date());

                BMR mbs = new BMR(currentDateandTime, mb, mbManterner, mbManterner-500, mbManterner+500);

                BMRHelperAdapter helper = new BMRHelperAdapter(getContext());
                helper.insertBMR(mbs);

                //ToDo guardar mbs

                Toast.makeText(getContext(), currentDateandTime, Toast.LENGTH_LONG).show();
            }
        });

        // Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.calculatorSex, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        sex.setOnItemSelectedListener(this);
        sex.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.calculatorActivity, R.layout.spinner_item);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown);
        activity.setOnItemSelectedListener(this);
        activity.setAdapter(adapter2);


        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void calculateMBS(double weight, double height, int years, String sex, int activity) {

        //Realizamos el calculo del IMC
        if (sex.compareTo("Masculino") == 1) {
            mb = 66+(13.7*weight)+(5*height)-(6.8*years);
        }
        else {
            mb = 655+(9.6*weight)+(1.8*height)-(4.7*years);
        }

        // Realizamos la comprobacion del nivel de actividad
        switch (activity) {
            case 0:
                mbManterner = mb*1.2;
                break;
            case 1:
                mbManterner = mb*1.375;
                break;
            case 2:
                mbManterner = mb*1.55;
                break;
            case 3:
                mbManterner = mb*1.725;
                break;
            case 4:
                mbManterner = mb*1.9;
                break;
        }

        //Establecemos el resultado en su TextView
        //resultadoTMB = new Double(tmb).toString();

        mbs.setText(String.format("%.2f", mb));
        calories.setText(String.valueOf(mbManterner));
        lostWeight.setText(String.valueOf(mbManterner - 500));
        gainWeight.setText(String.valueOf(mbManterner + 500));
    }
}
