package com.example.appdoposto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etPrecoGasolina;
    private TextInputLayout tilPrecoGasolina;
    private TextInputEditText etPrecoEtanol;
    private TextInputLayout tilPrecoEtanol;
    private TextInputEditText etQuantidadeLitros;
    private TextInputLayout tilQuantidadeLitros;
    private Button btnCalcular;
    private TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etPrecoGasolina = findViewById(R.id.etPrecoGasolina);
        tilPrecoGasolina = findViewById(R.id.tilPrecoGasolina);

        etPrecoEtanol = findViewById(R.id.etPrecoEtanol);
        tilPrecoEtanol = findViewById(R.id.tilPrecoEtanol);

        etQuantidadeLitros = findViewById(R.id.etQuantidadeLitros);
        tilQuantidadeLitros = findViewById(R.id.tilQuantidadeLitros);

        btnCalcular = findViewById(R.id.btnCalcular);
        tvResultado = findViewById(R.id.tvResultado);
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularMelhorOpcao();
            }
        });

    }


    private void calcularMelhorOpcao() {
        tilPrecoGasolina.setError(null);
        tilPrecoEtanol.setError(null);
        tilQuantidadeLitros.setError(null);

        String precoGasolinaStr = etPrecoGasolina.getText().toString();
        String precoEtanolStr = etPrecoEtanol.getText().toString();
        String quantidadeLitrosStr = etQuantidadeLitros.getText().toString();

        if (precoGasolinaStr.isEmpty()) {
            tilPrecoGasolina.setError("Digite o preço da gasolina");
            return;
        }
        if (precoEtanolStr.isEmpty()) {
            tilPrecoEtanol.setError("Digite o preço do etanol");
            return;
        }
        if (quantidadeLitrosStr.isEmpty()) {
            tilQuantidadeLitros.setError("Digite a quantidade de litros");
            return;
        }

        double precoGasolina = 0;
        double precoEtanol = 0;
        double quantidadeLitros = 0;

        try {
            precoGasolina = Double.parseDouble(precoGasolinaStr);
        } catch (NumberFormatException e) {
            tilPrecoGasolina.setError("Preço da gasolina inválido");
            return;
        }

        try {
            precoEtanol = Double.parseDouble(precoEtanolStr);
        } catch (NumberFormatException e) {
            tilPrecoEtanol.setError("Preço do etanol inválido");
            return;
        }

        try {
            quantidadeLitros = Double.parseDouble(quantidadeLitrosStr);
        } catch (NumberFormatException e) {
            tilQuantidadeLitros.setError("Quantidade de litros inválida");
            return;
        }

        if (precoGasolina <= 0) {
            tilPrecoGasolina.setError("Preço da gasolina inválido");
            return;
        }
        if (precoEtanol <= 0) {
            tilPrecoEtanol.setError("Preço do etanol inválido");
            return;
        }
        if (quantidadeLitros <= 0) {
            tilQuantidadeLitros.setError("Quantidade de litros inválida");
            return;
        }

        double razao = precoEtanol / precoGasolina;

        String recomendacao;
        double valorTotal;

        if (razao < 0.7) {
            recomendacao = "Etanol é a melhor opção";
            valorTotal = precoEtanol * quantidadeLitros;
        } else {
            recomendacao = "Gasolina é a melhor opção";
            valorTotal = precoGasolina * quantidadeLitros;
        }

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String valorTotalFormatado = nf.format(valorTotal);

        String resultadoFinal = recomendacao + "\n" + "Valor total: " + valorTotalFormatado;

        tvResultado.setText(resultadoFinal);
    }
}
