package com.example.kingoftokyoprobabilitycalculator;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    static final int OPTIONS_REQUEST = 101;
    protected SetOptions opt = new SetOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calculateButton = (Button) findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });

        Button optionsBut = (Button) findViewById(R.id.optionsButton);
        optionsBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchOptions();
            }
        });

        CheckBox setDiceCheck = (CheckBox) findViewById(R.id.checkBox2);
        setDiceCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ArrayList<Spinner> setRollList = new ArrayList<Spinner>();
                setRollList.add((Spinner) findViewById(R.id.spinner7));
                setRollList.add((Spinner) findViewById(R.id.spinner8));
                setRollList.add((Spinner) findViewById(R.id.spinner9));
                setRollList.add((Spinner) findViewById(R.id.spinner10));
                setRollList.add((Spinner) findViewById(R.id.spinner11));
                setRollList.add((Spinner) findViewById(R.id.spinner12));
                TextView label = (TextView) findViewById(R.id.setDiceLabel);

                for (int i = 0; i<setRollList.size(); i++){
                    if (isChecked) {
                        setRollList.get(i).setVisibility(View.VISIBLE);
                        if (i==0) {
                            label.setVisibility(View.VISIBLE);
                        }
                    }
                    else{
                        setRollList.get(i).setVisibility(View.INVISIBLE);
                        if (i==0) {
                            label.setVisibility(View.INVISIBLE);
                        }
                    }
                }


            }
        });

        setupSpinners();


    }


    public void setupSpinners(){
        ArrayList<Spinner> DesRolls = new ArrayList<Spinner>();
        DesRolls.add((Spinner) findViewById(R.id.spinner));
        DesRolls.add((Spinner) findViewById(R.id.spinner2));
        DesRolls.add((Spinner) findViewById(R.id.spinner3));
        DesRolls.add((Spinner) findViewById(R.id.spinner4));
        DesRolls.add((Spinner) findViewById(R.id.spinner5));
        DesRolls.add((Spinner) findViewById(R.id.spinner6));

        ArrayList<Spinner> setRollList = new ArrayList<Spinner>();
        setRollList.add((Spinner) findViewById(R.id.spinner7));
        setRollList.add((Spinner) findViewById(R.id.spinner8));
        setRollList.add((Spinner) findViewById(R.id.spinner9));
        setRollList.add((Spinner) findViewById(R.id.spinner10));
        setRollList.add((Spinner) findViewById(R.id.spinner11));
        setRollList.add((Spinner) findViewById(R.id.spinner12));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.diceOptions,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        for (int i = 0; i<6; i++){
            DesRolls.get(i).setAdapter(adapter);
            setRollList.get(i).setAdapter(adapter);
            //DesRolls.get(i).setOnItemSelectedListener(this);
        }
    }

    public void calculate(){
        //EditText sims = (EditText) findViewById(R.id.sims);
        //String simsS = sims.getText().toString();
        String simsS = "100000";
        EditText rolls = (EditText) findViewById(R.id.rolls);
        String rollsS = rolls.getText().toString();

        String desRollStr = "";
        String setRollStr = "";
        boolean setRoll = false;


        ArrayList<Spinner> DesRolls = new ArrayList<Spinner>();
        DesRolls.add((Spinner) findViewById(R.id.spinner));
        DesRolls.add((Spinner) findViewById(R.id.spinner2));
        DesRolls.add((Spinner) findViewById(R.id.spinner3));
        DesRolls.add((Spinner) findViewById(R.id.spinner4));
        DesRolls.add((Spinner) findViewById(R.id.spinner5));
        DesRolls.add((Spinner) findViewById(R.id.spinner6));



        CheckBox setRollCheck = (CheckBox) findViewById(R.id.checkBox2);
        setRoll = setRollCheck.isChecked();



        for (int i = 0; i< DesRolls.size(); i++){
            String n = DesRolls.get(i).getSelectedItem().toString();
            switch(n){
                case "attack": n = "a"; break;
                case "heal": n = "h"; break;
                case "energy": n = "e"; break;
                case "any": n = "w"; break;

            }
            desRollStr = desRollStr.concat(n);
        }

        if (setRoll){
            ArrayList<Spinner> setRollList = new ArrayList<Spinner>();
            setRollList.add((Spinner) findViewById(R.id.spinner7));
            setRollList.add((Spinner) findViewById(R.id.spinner8));
            setRollList.add((Spinner) findViewById(R.id.spinner9));
            setRollList.add((Spinner) findViewById(R.id.spinner10));
            setRollList.add((Spinner) findViewById(R.id.spinner11));
            setRollList.add((Spinner) findViewById(R.id.spinner12));
            for (int i = 0; i< setRollList.size(); i++){
                String n = setRollList.get(i).getSelectedItem().toString();
                switch(n){
                    case "attack": n = "a"; break;
                    case "heal": n = "h"; break;
                    case "energy": n = "e"; break;
                    case "any": n = "w"; break;

                }
                setRollStr = setRollStr.concat(n);
            }
        }

        float result;
        if (!setRoll) {
            Probability prob = new Probability(desRollStr, Integer.parseInt(rollsS));
            result = prob.Calculate(opt.getNumberSims());
        }
        else{
            Probability prob = new Probability(desRollStr,Integer.parseInt(rollsS),setRollStr);
            result = prob.Calculate(opt.getNumberSims());
        }
        TextView resultText = findViewById(R.id.resultW);
        resultText.setText("The result is: ".concat(new DecimalFormat("#.#").format(result*100)).concat("%"));


        /*
        EditText sims = findViewById(R.id.sims);
        EditText rolls = findViewById(R.id.rolls);
        System.out.print("Number of sims is: ".concat(sims.getText().toString()));
        rolls.setText(sims.getText());
        */
    }


    public void launchOptions(){
        //setContentView(R.layout.options);


        Intent intent = new Intent(this,OptionsActivity.class);
        startActivityForResult(intent,OPTIONS_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==OPTIONS_REQUEST){
            try {
                opt = (SetOptions) data.getSerializableExtra("optionSet");
                if(opt.isSumMode()){
                    setContentView(R.layout.activity_sum);
                }
                else{
                    setContentView(R.layout.activity_main);
                }
            }
            catch (Exception e){
                Toast.makeText(this, "Error: Couldn't get options.", Toast.LENGTH_SHORT).show();
            }
        }
        resume();
    }

   protected void resume(){
       Button calculateButton = (Button) findViewById(R.id.calculateButton);
       calculateButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               calculate();
           }
       });

       Button optionsBut = (Button) findViewById(R.id.optionsButton);
       optionsBut.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               launchOptions();
           }
       });

       CheckBox setDiceCheck = (CheckBox) findViewById(R.id.checkBox2);
       setDiceCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               ArrayList<Spinner> setRollList = new ArrayList<Spinner>();
               setRollList.add((Spinner) findViewById(R.id.spinner7));
               setRollList.add((Spinner) findViewById(R.id.spinner8));
               setRollList.add((Spinner) findViewById(R.id.spinner9));
               setRollList.add((Spinner) findViewById(R.id.spinner10));
               setRollList.add((Spinner) findViewById(R.id.spinner11));
               setRollList.add((Spinner) findViewById(R.id.spinner12));
               TextView label = (TextView) findViewById(R.id.setDiceLabel);

               for (int i = 0; i<setRollList.size(); i++){
                   if (isChecked) {
                       setRollList.get(i).setVisibility(View.VISIBLE);
                       if (i==0) {
                           label.setVisibility(View.VISIBLE);
                       }
                   }
                   else{
                       setRollList.get(i).setVisibility(View.INVISIBLE);
                       if (i==0) {
                           label.setVisibility(View.INVISIBLE);
                       }
                   }
               }


           }
       });

       setupSpinners();
   }
}
