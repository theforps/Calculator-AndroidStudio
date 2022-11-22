package com.example.calculator;

import static java.lang.String.format;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.ImmutableList;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.Lists;

import org.mariuszgromada.math.mxparser.*;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Click(View view) {
        Button button = (Button)view;
        Create(button);
    }

    public void Create(Button button)
    {
        String textOfBut = button.getText().toString();
        TextView label = findViewById(R.id.label);
        String textOfLabel = label.getText().toString();
        List<Character> signs = Lists.newArrayList('+','-','×','%','÷','.');
        int maxLenght = 16;

        {
            if (textOfLabel.charAt(0) == '0' && Character.isDigit(textOfBut.charAt(0))
                    && textOfLabel.length() == 1 || textOfLabel.charAt(0) == 'N') {
                textOfLabel = "";

            } else if (textOfBut.charAt(0) == 'C')
                textOfLabel = "0";

            else if (textOfBut.charAt(0) == 'A' && textOfLabel.length() >= 2)
                textOfLabel = textOfLabel.substring(0, textOfLabel.length() - 1);

            else if (textOfBut.charAt(0) == '=') {
                boolean check = false;
                for (int i = 0; i < textOfLabel.length(); i++) {
                    if (signs.contains(textOfLabel.charAt(i)) && i + 1 < textOfLabel.length()
                            && Character.isDigit(textOfLabel.charAt(i + 1)) || textOfLabel.charAt(i) == '%')
                        check = true;
                }

                if (check) {
                    Expression e = new Expression(textOfLabel);
                    double resualt = e.calculate();

                    if ((int) resualt == resualt)
                        textOfLabel = Integer.toString((int) resualt);
                    else
                        textOfLabel = Double.toString(resualt);
                } else
                    textOfLabel = "0";
            } else if (Character.isDigit(textOfBut.charAt(0)))
                textOfLabel += textOfBut;

            else {
                char temp = textOfLabel.charAt(textOfLabel.length() - 1);
                if (signs.contains(textOfBut.charAt(0)) && !signs.contains(temp))
                    textOfLabel += textOfBut;
            }

            if (textOfLabel.length() > maxLenght)
                textOfLabel = textOfLabel.substring(0, maxLenght);

            if (textOfLabel.contains(","))
                textOfLabel = textOfLabel.replace(",", "");
        }

        label.setText(textOfLabel);
    }

}