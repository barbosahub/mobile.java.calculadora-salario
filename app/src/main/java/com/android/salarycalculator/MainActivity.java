package com.android.salarycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private EditText edtSalario;
    private EditText edtDependentes;
    private EditText edtDescontos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void ViewResult(View view){
        /*Components*/
        edtSalario = (EditText)findViewById(R.id.edtSalarioBruto);
        edtDependentes = (EditText)findViewById(R.id.edtDependentes);
        edtDescontos =(EditText)findViewById(R.id.edtDescontos);

        Double salario = Double.parseDouble(edtSalario.getText().toString());


            if (edtDescontos.getText().toString().isEmpty() || edtDependentes.getText().toString().isEmpty() || edtSalario.getText().toString().isEmpty()) {
                Toast.makeText(this, "Por favor, preencher todos campos!", Toast.LENGTH_SHORT).show();
            }else {

                Intent i = new Intent(this, Result.class);

                i.putExtra("salario", edtSalario.getText().toString());
                i.putExtra("dependentes", edtDependentes.getText().toString());
                i.putExtra("descontos", edtDescontos.getText().toString());

                startActivity(i);
                finish();
            }

    }
}