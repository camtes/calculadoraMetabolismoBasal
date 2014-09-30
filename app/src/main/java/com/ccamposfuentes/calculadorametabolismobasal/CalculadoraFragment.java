package com.ccamposfuentes.calculadorametabolismobasal;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by ccampos on 04/08/14.
 */

public class CalculadoraFragment extends Fragment {

    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.calculadora_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        final EditText editPeso = (EditText) getView().findViewById(R.id.editTextPeso);
        final EditText editAltura = (EditText) getView().findViewById(R.id.editTextAltura);
        final EditText editEdad = (EditText) getView().findViewById(R.id.editTextEdad);
        final RadioButton hombre = (RadioButton) getView().findViewById(R.id.radioButtonHombre);
        final RadioButton mujer = (RadioButton) getView().findViewById(R.id.radioButtonMujer);
        final RadioButton sedentario = (RadioButton) getView().findViewById(R.id.radioButtonSedentario);
        final RadioButton ligera = (RadioButton) getView().findViewById(R.id.radioButtonLigera);
        final RadioButton moderada = (RadioButton) getView().findViewById(R.id.radioButtonModerada);
        final RadioButton activa = (RadioButton) getView().findViewById(R.id.radioButtonActiva);
        final RadioButton muyActiva = (RadioButton) getView().findViewById(R.id.radioButtonMuyActiva);
        final Button botonCalcular = (Button) getView().findViewById(R.id.buttonCalcular);
        final Button botonVolver = (Button) getView().findViewById(R.id.buttonVolver);
        final LinearLayout formulario = (LinearLayout) getView().findViewById(R.id.layoutFormulario);
        final LinearLayout resultados = (LinearLayout) getView().findViewById(R.id.layoutResultados);
        final TextView resMB = (TextView) getView().findViewById(R.id.textViewMB);
        final TextView resMantener = (TextView) getView().findViewById(R.id.textViewMantener);
        final TextView resAdelgazar = (TextView) getView().findViewById(R.id.textViewAdelgazar);
        final TextView resEngordar = (TextView) getView().findViewById(R.id.textViewSubir);

        //Evento que se lanzar� al pulsar el bot�n
        botonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //V ariables auxiliares
                double mb = 0, mbManterner=0, peso, altura, edad;
                String textTMB;

                // Comprobamos si los campos no estan vacios
                if (!editAltura.getText().toString().equals("") && !editEdad.getText().toString().equals("") &&
                !editPeso.getText().toString().equals("")) {

                    //Obtenemos la estatura, peso y edad
                    altura = Float.valueOf(editAltura.getText().toString());
                    peso = Float.valueOf(editPeso.getText().toString());
                    edad = Float.valueOf(editEdad.getText().toString());

                    //Realizamos el calculo del IMC
                    if (hombre.isChecked()) {
                        mb = 66+(13.7*peso)+(5*altura)-(6.8*edad);
                    }
                    else if (mujer.isChecked()) {
                        mb = 655+(9.6*peso)+(1.8*altura)-(4.7*edad);
                    }

                    // Realizamos la comprobacion del nivel de actividad
                    if (sedentario.isChecked()) {
                        mbManterner = mb*1.2;
                    }
                    else if (ligera.isChecked()) {
                        mbManterner = mb*1.375;
                    }
                    else if (moderada.isChecked()) {
                        mbManterner = mb*1.55;
                    }
                    else if (activa.isChecked()) {
                        mbManterner = mb*1.725;
                    }
                    else if (muyActiva.isChecked()) {
                        mbManterner = mb*1.9;
                    }

                    //Establecemos el resultado en su TextView
                    //resultadoTMB = new Double(tmb).toString();

                    resMB.setText(df.format(mb));
                    resMantener.setText(df.format(mbManterner));
                    resAdelgazar.setText(df.format(mbManterner-500));
                    resEngordar.setText(df.format(mbManterner+500));
                    formulario.setVisibility(View.GONE);
                    resultados.setVisibility(View.VISIBLE);
                }
                else {
                    //Creamos un mensaje
                    Toast mensajeFallo = Toast.makeText(getActivity(), "debes de introducir todos los datos", Toast.LENGTH_SHORT);
                    //Mostramos el mensaje
                    mensajeFallo.show();
                }
            }
        });

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPeso.setText("");
                editAltura.setText("");
                editEdad.setText("");
                hombre.setChecked(true);
                sedentario.setChecked(true);
                resultados.setVisibility(View.GONE);
                formulario.setVisibility(View.VISIBLE);
            }
        });
    }


}
