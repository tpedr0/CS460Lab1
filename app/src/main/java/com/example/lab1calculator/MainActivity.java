package com.example.lab1calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.time.format.TextStyle;

/**
 * MainActivity class that implements a calculator interface.
 * This class handles the UI and calculation logic for a basic calculator application
 * that supports arithmetic operations, decimal numbers, and bracket operations.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTV, solutionTV;

    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;

    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;

    MaterialButton buttonMul, buttonPlus, buttonSub, buttonDiv, buttonEquals;

    MaterialButton buttonAC, buttonDot;

    /**
     * Initializes the activity and sets up the calculator interface.
     * The method is called when the activity is first created. It initializes all UI elements
     * and sets up the display and assigns click listeners to all calculator buttons
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, this contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Otherwise, null.
     */
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

        resultTV = findViewById(R.id.result_tv);
        solutionTV = findViewById(R.id.solution_tv);

        assignID(buttonC, R.id.button_c);
        assignID(buttonBrackOpen, R.id.button_open_bracket);
        assignID(buttonBrackClose, R.id.button_close_bracket);

        assignID(button0, R.id.button_0);
        assignID(button1, R.id.button_1);
        assignID(button2, R.id.button_2);
        assignID(button3, R.id.button_3);
        assignID(button4, R.id.button_4);
        assignID(button5, R.id.button_5);
        assignID(button6, R.id.button_6);
        assignID(button7, R.id.button_7);
        assignID(button8, R.id.button_8);
        assignID(button9, R.id.button_9);

        assignID(buttonMul, R.id.button_mul);
        assignID(buttonPlus, R.id.button_plus);
        assignID(buttonDiv, R.id.button_division);
        assignID(buttonSub, R.id.button_sub);
        assignID(buttonEquals, R.id.button_equals);

        assignID(buttonAC, R.id.button_AC);
        assignID(buttonDot, R.id.button_dot);
    }

    /**
     * Helper method to find a button by its ID and set its click listener
     * Method finds a materialButton in the layout using the ID and
     * sets the activity as its click listener.
     *
     * @param btn the materialbutton reference to be initialzed
     * @param id the resource ID of the button in the layout
     */
    void assignID(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    /**
     * Handle click events for all calculator buttons
     * Processes the button clicks, updates the display, and does
     * calculations based on the button pressed. It handles special
     * cases for AC and C and equals operations
     *
     * @param view The view (button that was clicked)
     */
    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTV.getText().toString();

        if(buttonText.equals("AC")) {
            solutionTV.setText("");
            resultTV.setText("0");
            return;
        }

        if(buttonText.equals("=")){
            solutionTV.setText(resultTV.getText());
            return;
        }

        if(buttonText.equals("C")) {
            dataToCalculate = dataToCalculate.substring(0,dataToCalculate.length()-1);
        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }

        solutionTV.setText(dataToCalculate);

        String finalResult = getResults(dataToCalculate);

        if(!finalResult.equals("Err")) {
            resultTV.setText(finalResult);
        }
    }

    /**
     *
     * Evaluates a mathematical expression and returns the result
     * Uses the Rhino Javascript engine to evaluate the math expression
     * provided as a string. It handles basic arithmetic operations and returns "Err" if
     * the expression is invalid or cannot be evaluated
     *
     * @param data The math expression to evaluate as a string
     * @return The result of the eval as a string, or "Err" if eval fails
     *
     */
    String getResults(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            return context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
        } catch (Exception e){
            return "Err";
        }
    }
}