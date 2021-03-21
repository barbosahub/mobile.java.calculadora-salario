package com.android.salarycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Result extends AppCompatActivity {

    private TextView txtSalario;
    private TextView txtInss;
    private TextView txtIrrf;
    private TextView txtDescontos;
    private TextView txtSalarioLiquido;
    private TextView txtDescontosTotal;
    private TextView txtOutros;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtSalario = (TextView) findViewById(R.id.txtSalarioBruto);
        txtInss = (TextView) findViewById(R.id.txtInss);
        txtIrrf = (TextView) findViewById(R.id.txtIrrf);
        txtOutros =(TextView) findViewById(R.id.txtOutros);

        txtDescontosTotal =(TextView) findViewById(R.id.txtDescontos);
        txtSalarioLiquido =(TextView) findViewById(R.id.txtSalarioLiquido);


    //GET INTENT
        Intent i = getIntent();
        String Isalario = i.getStringExtra("salario");
        String Idependentes = i.getStringExtra("dependentes");
        String Idescontos = i.getStringExtra("descontos");

        //region INSS

        //        O salário de um trabalhador CLT tem o desconto do INSS, conforme tabela a seguir:

        //        Salário de Contribuição Alíquota Dedução
        //        Até um salário mínimo (R$ 1.045,00) 7,5%
        //        De R$ 1.045,01 até 2.089,60 9%    R$ 15,67
        //        De R$ 2.089,61 até 3.134,40 12%   R$ 78,36
        //        De R$ 3.134,41 até 6.101,06 14%   R$ 141,05


        Double salarioInss = Double.parseDouble(Isalario);
        Double percentInss = 0.0;
        Double deducaoInss = 0.0;
        Double inss = 0.0;

        if (salarioInss <= 1045.01){
            percentInss = 7.5; deducaoInss = 0.0;
        }else if (salarioInss >= 1045.01 && salarioInss <= 2089.60 ){
            percentInss = 9.00; deducaoInss = 15.67;
        }else if (salarioInss >= 2089.61 && salarioInss <= 3134.40 ){
            percentInss = 12.00; deducaoInss = 78.36;
        }else if (salarioInss >= 3134.41 && salarioInss <= 6101.06 ){
            percentInss = 14.00; deducaoInss = 141.05;
        }else if (salarioInss >= 6101.07 && salarioInss <= 10448.01 ){
            percentInss = 14.05; deducaoInss = 171.56;
        }else if (salarioInss >= 10448.02 && salarioInss <= 20896.00 ){
            percentInss = 16.05; deducaoInss = 380.50;
        }else if (salarioInss >= 20896.01 && salarioInss <= 40747.20 ){
            percentInss = 19.00; deducaoInss = 902.92;
        }else{
            percentInss = 22.00; deducaoInss = 2121.33;
        }



        inss = ((salarioInss * percentInss) / 100) - deducaoInss ;
    //endregion

        //region IRRF

        //        O imposto de renda retido na fonte (IRRF) tem como base para cálculo o seguinte valor:
        //        Base de cálculo = salário bruto – contribuição para o INSS – número de dependentes x
        //        189,59
        //        A partir desta base de cálculo, a seguinte tabela é aplicada:
        //        Base de cálculo Alíquota Dedução
        //        Até R$ 1.903,98 0 -
        //        De R$ 1.903,99 até R$ 2.826,65 7,5% R$ 142,80
        //        De R$ 2.826,66 até R$ 3.751,05 15,0% R$ 354,80
        //        De R$ 3.751,06 até R$ 4.664,68 22,5% R$ 636,13
        //        Acima de R$ 4.664,68 27,5% R$ 869,36


        Double salarioIrrf = Double.parseDouble(Isalario);
        Integer dependentes = Integer.parseInt(Idependentes);
        Double dependentesdesconto = dependentes * 189.59;
        Double percentIrrf = 0.0;
        Double baseCalculo = 0.0;
        Double deducaoIrrf = 0.0;
        Double irrf = 0.0;

//        Veja como fica o exemplo de cálculo do desconto do IRRF para um salário bruto de R$3.000,00.
//        Base de cálculo é R$ 2.718,36 (R$3.000,00 – INSS de R$281,64),
//        assim é aplicada a alíquota de 7,5% (R$ 203,87) e descontada a dedução de R$ 142,80 (R$
//        203,87 – R$142,80), o que teria um desconto de R$ 61,08 de IRRF.



        salarioIrrf = salarioIrrf -dependentesdesconto - inss;

        if (salarioIrrf <= 1903.98){
            percentIrrf = 0.0; deducaoIrrf = 0.0;
        }else if (salarioIrrf >= 1903.99 && salarioIrrf <= 2826.65 ){
            percentIrrf = 7.5; deducaoIrrf = 142.80;
        }else if (salarioIrrf >= 2826.66 && salarioIrrf <= 3751.05 ){
            percentIrrf = 15.00; deducaoIrrf = 354.80;
        }else if (salarioIrrf >= 3751.06 && salarioIrrf <= 4664.68 ){
            percentIrrf = 22.5; deducaoIrrf = 636.13;
        }else{
            percentIrrf = 27.5; deducaoIrrf = 869.36;
        }

        baseCalculo = ((salarioIrrf * percentIrrf) / 100);
        irrf = (baseCalculo - deducaoIrrf);
        //endregion

        //region Outros
        Double outros = Double.parseDouble(Idescontos);
        Double descontos = outros + irrf + inss;


        Double salario = Double.parseDouble(Isalario);
        salario = salario - descontos;


        //endregion

        //region RESULTADO FINAL
        txtSalario.setText(Isalario);
        txtInss.setText(String.format("%.2f", inss));
        txtIrrf.setText(String.format("%.2f", irrf));
        txtOutros.setText(outros.toString());

        txtSalarioLiquido.setText(String.format("%.2f", salario));
        txtDescontosTotal.setText(String.format("%.2f", descontos));
       //endregion
    }

    public void ViewResult(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}