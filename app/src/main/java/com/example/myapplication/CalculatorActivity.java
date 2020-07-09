/*
package com.example.myapplication;

*/
/**
 * Thanks to
 *
 * @URL : http://www2.lawrence.edu/fast/GREGGJ/CMSC150/071Calculator/Calculator.html
 */
/*


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CalculatorActivity extends AppCompatActivity {

    ImageButton btn_themechange;
    Button btn_ce;
    Button btn_del;
    Button btn_per;
    Button btn_div;
    Button btn_1;
    Button btn_2;
    Button btn_3;
    Button btn_4;
    Button btn_5;
    Button btn_6;
    Button btn_7;
    Button btn_8;
    Button btn_9;
    Button btn_0;
    Button btn_eq;
    Button btn_sum;
    Button btn_sub;
    Button btn_mul;
    Button btn_dot;
    Button btn_ps;
    Button btn_pe;
    Button btn_mrc;
    Button btn_mp;

    TextView tv_operand;
    TextView tv_ans;

    SharedPreferences appSettings;
    SharedPreferences.Editor appSettingsEdit;
    boolean isDarkModeOn;


    FullCalculator cal;
    String operand;
    double ans;
    double memory;
    long backPressedTime;

    private void init() {
        btn_ce = findViewById(R.id.btn_ce);
        btn_del = findViewById(R.id.btn_del);
        btn_per = findViewById(R.id.btn_percentage);
        btn_div = findViewById(R.id.btn_divide);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_0 = findViewById(R.id.btn_0);
        btn_eq = findViewById(R.id.btn_eq);
        btn_sum = findViewById(R.id.btn_sum);
        btn_sub = findViewById(R.id.btn_sub);
        btn_mul = findViewById(R.id.btn_multiply);
        btn_dot = findViewById(R.id.btn_dot);
        btn_ps = findViewById(R.id.btn_ps);
        btn_pe = findViewById(R.id.btn_pe);
        btn_mrc = findViewById(R.id.btn_mrc);
        btn_mp = findViewById(R.id.btn_mp);
        tv_ans = findViewById(R.id.tv_ans);
        tv_operand = findViewById(R.id.tv_operands);
//        btn_themechange = findViewById(R.id.btn_changeTheme);
        ans = 0.0;
        memory = 0.0;
        operand = "";
        backPressedTime = 0;
        cal = new FullCalculator();
        appSettings = getSharedPreferences("AppSettings", 0);
        appSettingsEdit = appSettings.edit();
        isDarkModeOn = appSettings.getBoolean("nightMode", false);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        init();
//        checkForTheme();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis())
            finish();
        else
            Toast.makeText(this, "Press back again to exit from the app", Toast.LENGTH_SHORT).show();

        backPressedTime = System.currentTimeMillis();
    }

    private boolean isLastOperator(char s) {
        return s == '%' || s == '*' || s == '/' || s == '-' || s == '+' || s == '(' || s == ')';
    }

    private boolean isLastDot(String s) {
        return s.endsWith(".");
    }

    public void btnCE(View view) {
        tv_operand.setText("");
        tv_ans.setText("");
        ans = 0.0;
        operand = "";
    }

    public void btnDel(View view) {
        if (operand.length() == 0) {
            Toast.makeText(this, "Empty Operand Field", Toast.LENGTH_SHORT).show();
            return;
        }
        if (operand.charAt(operand.length() - 1) == ' ') {
            if (isLastOperator(operand.charAt(operand.length() - 2)))
                operand = operand.substring(0, operand.length() - 3);
        } else
            operand = operand.substring(0, operand.length() - 1);
        tv_operand.setText(operand);
        tv_ans.setText("");
        ans = 0;
    }

    public void btnEq(View view) {
        if (operand.length() == 0) {
            Toast.makeText(this, "Empty Operand Field", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            cal = new FullCalculator();
            if (operand.charAt(operand.length() - 1) == ' ') {
                if (operand.length() > 2 && operand.charAt(operand.length() - 2) == '%') {
                    String[] op = operand.trim().split(" ");
                    if (op.length % 2 != 0)
                        throw new InvalidDataException();
                    if (op.length >= 4) {
                        if (op[1].equals("+"))
                            ans = Double.parseDouble(op[0]) + Double.parseDouble(op[2]) / 100;
                        else
                            ans = Double.parseDouble(op[0]) - Double.parseDouble(op[2]) / 100;
                    } else if (op.length >= 2) {
                        ans = Double.parseDouble(op[0]) / 100;
                    }
                }else
                    ans = cal.processInput(operand.trim());
            } else {
                ans = cal.processInput(operand.trim());
            }
            operand = ans + "";
            tv_ans.setText(ans + "");
            tv_operand.setText(operand);
        } catch (InvalidDataException e) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnDiv(View view) {
        if (isLastDot(operand))
            operand += "0";
        tv_operand.setText(operand + " / ");
        operand += " / ";
    }

    public void btnPer(View view) {
        if (isLastDot(operand))
            operand += "0";
        operand += " % ";
        tv_operand.setText(operand);
    }

    public void btnMul(View view) {
        if (isLastDot(operand))
            operand += "0";
        tv_operand.setText(operand + " * ");
        operand += " * ";
    }

    public void btnSum(View view) {
        if (isLastDot(operand))
            operand += "0";
        operand += " + ";
        tv_operand.setText(operand);
    }

    public void btnSub(View view) {
        if (isLastDot(operand))
            operand += "0";
        operand += " - ";
        tv_operand.setText(operand);
    }

    public void btnDot(View view) {
        operand += ".";
        tv_operand.setText(operand);
    }

    public void btn7(View view) {
        operand += "7";
        tv_operand.setText(operand);
    }

    public void btn8(View view) {
        operand += "8";
        tv_operand.setText(operand);
    }

    public void btn9(View view) {
        operand += "9";
        tv_operand.setText(operand);
    }

    public void btn4(View view) {
        operand += "4";
        tv_operand.setText(operand);
    }

    public void btn5(View view) {
        operand += "5";
        tv_operand.setText(operand);
    }

    public void btn6(View view) {
        operand += "6";
        tv_operand.setText(operand);
    }

    public void btn1(View view) {
        operand += "1";
        tv_operand.setText(operand);
    }

    public void btn2(View view) {
        operand += "2";
        tv_operand.setText(operand);
    }

    public void btn3(View view) {
        operand += "3";
        tv_operand.setText(operand);
    }

    public void btn0(View view) {
        operand += "0";
        tv_operand.setText(operand);
    }

    public void btnPs(View view) {
        operand += " ( ";
        tv_operand.setText(operand);
    }

    public void btnPe(View view) {
        operand += " ) ";
        tv_operand.setText(operand);
    }

    public void btnMp(View view) {
        memory = ans;
        Toast.makeText(this, "Answer saved into memory", Toast.LENGTH_SHORT).show();
    }

    public void btnMrc(View view) {
        operand += memory + "";
        tv_operand.setText(operand);
    }
}*/

package com.example.myapplication;

/**
 * Thanks to
 *
 * @URL : http://www2.lawrence.edu/fast/GREGGJ/CMSC150/071Calculator/Calculator.html
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CalculatorActivity extends AppCompatActivity {

    ImageButton btn_themechange;
    Button btn_ce;
    Button btn_del;
    Button btn_per;
    Button btn_div;
    Button btn_1;
    Button btn_2;
    Button btn_3;
    Button btn_4;
    Button btn_5;
    Button btn_6;
    Button btn_7;
    Button btn_8;
    Button btn_9;
    Button btn_0;
    Button btn_eq;
    Button btn_sum;
    Button btn_sub;
    Button btn_mul;
    Button btn_dot;
    Button btn_ps;
    Button btn_pe;
    Button btn_mrc;
    Button btn_mp;

    TextView tv_operand;
    TextView tv_ans;

    SharedPreferences appSettings;
    SharedPreferences.Editor appSettingsEdit;
    boolean isDarkModeOn;


    FullCalculator cal;
    String operand;
    double ans;
    double memory;
    long backPressedTime;

    private void init() {
        btn_ce = findViewById(R.id.btn_ce);
        btn_del = findViewById(R.id.btn_del);
        btn_per = findViewById(R.id.btn_percentage);
        btn_div = findViewById(R.id.btn_divide);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_0 = findViewById(R.id.btn_0);
        btn_eq = findViewById(R.id.btn_eq);
        btn_sum = findViewById(R.id.btn_sum);
        btn_sub = findViewById(R.id.btn_sub);
        btn_mul = findViewById(R.id.btn_multiply);
        btn_dot = findViewById(R.id.btn_dot);
        btn_ps = findViewById(R.id.btn_ps);
        btn_pe = findViewById(R.id.btn_pe);
        btn_mrc = findViewById(R.id.btn_mrc);
        btn_mp = findViewById(R.id.btn_mp);
        tv_ans = findViewById(R.id.textView2);
        tv_operand = findViewById(R.id.tv_operands2);
//        btn_themechange = findViewById(R.id.btn_changeTheme);
        ans = 0.0;
        memory = 0.0;
        operand = "";
        backPressedTime = 0;
        cal = new FullCalculator();
        appSettings = getSharedPreferences("AppSettings", 0);
        appSettingsEdit = appSettings.edit();
        isDarkModeOn = appSettings.getBoolean("nightMode", false);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_constrained);
        init();
//        checkForTheme();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis())
            finish();
        else
            Toast.makeText(this, "Press back again to exit from the app", Toast.LENGTH_SHORT).show();

        backPressedTime = System.currentTimeMillis();
    }

    private boolean isLastOperator(char s) {
        return s == '%' || s == '*' || s == '/' || s == '-' || s == '+' || s == '(' || s == ')';
    }

    private boolean isLastDot(String s) {
        return s.endsWith(".");
    }

    public void btnCE(View view) {
        tv_operand.setText("");
        tv_ans.setText("");
        ans = 0.0;
        operand = "";
    }

    public void btnDel(View view) {
        if (operand.length() == 0) {
            Toast.makeText(this, "Empty Operand Field", Toast.LENGTH_SHORT).show();
            return;
        }
        if (operand.charAt(operand.length() - 1) == ' ') {
            if (isLastOperator(operand.charAt(operand.length() - 2)))
                operand = operand.substring(0, operand.length() - 3);
        } else
            operand = operand.substring(0, operand.length() - 1);
        tv_operand.setText(operand);
        tv_ans.setText("");
        ans = 0;
    }

    public void btnEq(View view) {
        if (operand.length() == 0) {
            Toast.makeText(this, "Empty Operand Field", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            cal = new FullCalculator();
            if (operand.charAt(operand.length() - 1) == ' ') {
                if (operand.length() > 2 && operand.charAt(operand.length() - 2) == '%') {
                    String[] op = operand.trim().split(" ");
                    if (op.length % 2 != 0)
                        throw new InvalidDataException();
                    if (op.length >= 4) {
                        if (op[1].equals("+"))
                            ans = Double.parseDouble(op[0]) + Double.parseDouble(op[2]) / 100;
                        else
                            ans = Double.parseDouble(op[0]) - Double.parseDouble(op[2]) / 100;
                    } else if (op.length >= 2) {
                        ans = Double.parseDouble(op[0]) / 100;
                    }
                }else
                    ans = cal.processInput(operand.trim());
            } else {
                ans = cal.processInput(operand.trim());
            }
            operand = ans + "";
            tv_ans.setText(ans + "");
            tv_operand.setText(operand);
        } catch (InvalidDataException e) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnDiv(View view) {
        if (isLastDot(operand))
            operand += "0";
        tv_operand.setText(operand + " / ");
        operand += " / ";
    }

    public void btnPer(View view) {
        if (isLastDot(operand))
            operand += "0";
        operand += " % ";
        tv_operand.setText(operand);
    }

    public void btnMul(View view) {
        if (isLastDot(operand))
            operand += "0";
        tv_operand.setText(operand + " * ");
        operand += " * ";
    }

    public void btnSum(View view) {
        if (isLastDot(operand))
            operand += "0";
        operand += " + ";
        tv_operand.setText(operand);
    }

    public void btnSub(View view) {
        if (isLastDot(operand))
            operand += "0";
        operand += " - ";
        tv_operand.setText(operand);
    }

    public void btnDot(View view) {
        operand += ".";
        tv_operand.setText(operand);
    }

    public void btn7(View view) {
        operand += "7";
        tv_operand.setText(operand);
    }

    public void btn8(View view) {
        operand += "8";
        tv_operand.setText(operand);
    }

    public void btn9(View view) {
        operand += "9";
        tv_operand.setText(operand);
    }

    public void btn4(View view) {
        operand += "4";
        tv_operand.setText(operand);
    }

    public void btn5(View view) {
        operand += "5";
        tv_operand.setText(operand);
    }

    public void btn6(View view) {
        operand += "6";
        tv_operand.setText(operand);
    }

    public void btn1(View view) {
        operand += "1";
        tv_operand.setText(operand);
    }

    public void btn2(View view) {
        operand += "2";
        tv_operand.setText(operand);
    }

    public void btn3(View view) {
        operand += "3";
        tv_operand.setText(operand);
    }

    public void btn0(View view) {
        operand += "0";
        tv_operand.setText(operand);
    }


    public void btnPi(View view) {
        operand += "3.14";
        tv_operand.setText(operand);
    }

    public void btnPs(View view) {
        operand += " ( ";
        tv_operand.setText(operand);
    }

    public void btnPe(View view) {
        operand += " ) ";
        tv_operand.setText(operand);
    }

    public void btnMp(View view) {
        memory = ans;
        Toast.makeText(this, "Answer saved into memory", Toast.LENGTH_SHORT).show();
    }

    public void btnMrc(View view) {
        operand += memory + "";
        tv_operand.setText(operand);
    }

}