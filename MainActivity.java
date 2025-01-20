package com.example.myzakat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText weightGoldEditText, currentGoldValueEditText;
    private Spinner goldTypeSpinner;
    private TextView totalGoldTextView, zakatPayableTextView, totalZakatTextView;
    private Button calculateButton, resetButton, backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        weightGoldEditText= findViewById(R.id.weightGold);
        currentGoldValueEditText = findViewById(R.id.cgold);
        goldTypeSpinner = findViewById(R.id.spinner);
        totalGoldTextView = findViewById(R.id.totalGold);
        zakatPayableTextView = findViewById(R.id.zakatPay);
        totalZakatTextView = findViewById(R.id.totalZakat);
        calculateButton = findViewById(R.id.calculateBtn);
        resetButton = findViewById(R.id.resetBtn);

        backButton = findViewById(R.id.backBtn);


        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateZakat();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void calculateZakat(){
        try {
            double weightGold = Double.parseDouble(weightGoldEditText.getText().toString());
            double currentGoldValue = Double.parseDouble(currentGoldValueEditText.getText().toString());
            String goldType = goldTypeSpinner.getSelectedItem().toString();


            double nisab = goldType.equalsIgnoreCase("keep") ? 85 : 200;


            double totalGoldValue = weightGold * currentGoldValue;
            double zakatPayableWeight = Math.max(0, weightGold - nisab);
            double zakatPayableValue = zakatPayableWeight * currentGoldValue;
            double totalZakat = zakatPayableValue * 0.025;

            totalGoldTextView.setText(String.format("Total Gold Value: RM %.2f", totalGoldValue));
            zakatPayableTextView.setText(String.format("Zakat Payable: RM %.2f", zakatPayableValue));
            totalZakatTextView.setText(String.format("Total Zakat: RM %.2f", totalZakat));

        }catch (NumberFormatException e) {
            totalGoldTextView.setText("Total Gold Value: RM");
            zakatPayableTextView.setText("Zakat Payable: RM");
            totalZakatTextView.setText("Total Zakat: RM");
        }
    }
    private void resetFields(){
        weightGoldEditText.setText("");
        currentGoldValueEditText.setText("");
        totalGoldTextView.setText("Total Gold Value: RM");
        zakatPayableTextView.setText("Zakat Payable: RM");
        totalZakatTextView.setText("Total Zakat: RM");
        goldTypeSpinner.setSelection(0);
    }
}