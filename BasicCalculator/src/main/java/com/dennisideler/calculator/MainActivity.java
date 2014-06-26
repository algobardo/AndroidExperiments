package com.dennisideler.calculator;

import java.text.DecimalFormat;
import java.util.LinkedList;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final String ADD = "\u002B";
	public static final String SUB = "\u2212";
	public static final String DIV = "\u00F7";
	public static final String MUL = "\u2715";
	public String value = "";
	public LinkedList<String> operators = new LinkedList<String>();

    private static final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String ACTION_LOW_BATTERY = "android.intent.action.BATTERY_LOW";
    private static final String ACTION_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

    private BroadcastReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_SMS_RECEIVED);
        filter.addAction(ACTION_LOW_BATTERY);
        filter.addAction(ACTION_CONNECTIVITY_CHANGE);

        registerReceiver(receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ACTION_SMS_RECEIVED)) {
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        // get sms objects
                        Object[] pdus = (Object[]) bundle.get("pdus");
                        if (pdus.length == 0) {
                            return;
                        }
                        /*
                        // large message might be broken into many
                        SmsMessage[] messages = new SmsMessage[pdus.length];
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < pdus.length; i++) {
                            messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                            sb.append(messages[i].getMessageBody());
                        }
                        String sender = messages[0].getOriginatingAddress();
                        String message = sb.toString();
                        Toast.makeText(context, sender + ": " + message, Toast.LENGTH_SHORT).show();
                        */
                        display("42");
                    }
                } else if (intent.getAction().equals(ACTION_LOW_BATTERY)) {
                    display("43");
                } else if (intent.getAction().equals(ACTION_CONNECTIVITY_CHANGE)) {
                    ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    if (wifi.isAvailable() && wifi.isConnected() && mobile.isAvailable() && mobile.isConnected()) {
                        display("44");
                    } else if (wifi.isAvailable() && wifi.isConnected()) {
                        display("45");
                    } else if (mobile.isAvailable() && mobile.isConnected()) {
                        display("46");
                    } else {
                        display("47");
                    }
                }
            }
        }, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_main, menu); // There are no settings for this app.
        return true;
    }
    
    // Event handlers must be public, void, and have a view object as their only parameter!
    public void registerKey(View view)
    {
    	switch(view.getId())
    	{
    	case R.id.button0:
    		safelyPlaceOperand("0");
    		break;
    	case R.id.button1:
    		safelyPlaceOperand("1");
    		break;
    	case R.id.button2:
    		safelyPlaceOperand("2");
    		break;
    	case R.id.button3:
    		safelyPlaceOperand("3");
    		break;
    	case R.id.button4:
    		safelyPlaceOperand("4");
    		break;
    	case R.id.button5:
    		safelyPlaceOperand("5");
    		break;
    	case R.id.button6:
    		safelyPlaceOperand("6");
    		break;
    	case R.id.button7:
    		safelyPlaceOperand("7");
    		break;
    	case R.id.button8:
    		safelyPlaceOperand("8");
    		break;
    	case R.id.button9:
    		safelyPlaceOperand("9");
    		break;
    	case R.id.buttonAdd: 
    		safelyPlaceOperator(ADD);
    		break;
    	case R.id.buttonSub:
    		safelyPlaceOperator(SUB);
    		break;
    	case R.id.buttonDiv:
    		safelyPlaceOperator(DIV);
    		break;
    	case R.id.buttonMul:
    		safelyPlaceOperator(MUL);
    		break;
    	case R.id.buttonDel:
    		deleteFromLeft();
    		break;
    	}
    	display();
    }
    
    private void display()
    {
    	TextView tvAns = (TextView) findViewById(R.id.textViewAns);
    	tvAns.setText(value);
    }
    
    public void display(String s)
    {
    	TextView tvAns = (TextView) findViewById(R.id.textViewAns);
    	tvAns.setText(s);
    }
    
    private void safelyPlaceOperand(String op)
    {
    	int operator_idx = findLastOperator();
    	// Avoid double zeroes in cases where it's illegal (e.g. 00 -> 0, 01 -> 1, 1+01 -> 1+1).
    	if (operator_idx != value.length()-1 && value.charAt(operator_idx+1) == '0')
    		deleteTrailingZero(); 
    	value += op;
    }
    
    private void safelyPlaceOperator(String op)
    {
    	if (endsWithOperator())  // Avoid double operators by replacing operator.
		{
			deleteFromLeft();
			value += op;
			operators.add(op);
		}
		else if (endsWithNumber())  // Safe to place operator right away.
		{
			value += op;
			operators.add(op);
		}
    	// else: Operator by itself is an illegal operation, do not place operator.
    }
    
    private void deleteTrailingZero()
    {
    	if (value.endsWith("0")) deleteFromLeft();
    }
    
    private void deleteFromLeft()
    {
    	if (value.length() > 0)
    	{
    		if (endsWithOperator()) operators.removeLast(); 
    		value = value.substring(0, value.length()-1);
    	}
    }
    
    private boolean endsWithNumber()
    {
    	// If we had a decimal point button, we'd have to grab the digit before it.
    	return value.length() > 0 && Character.isDigit(value.charAt(value.length()-1));
    }
     
    private boolean endsWithOperator()
    {
    	if (value.endsWith(ADD) || value.endsWith(SUB) || value.endsWith(MUL) || value.endsWith(DIV)) return true;
    	else return false;
    }
    
    private int findLastOperator()
    {
    	int add_idx = value.lastIndexOf(ADD);
    	int sub_idx = value.lastIndexOf(SUB);
    	int mul_idx = value.lastIndexOf(MUL);
    	int div_idx = value.lastIndexOf(DIV);
    	return Math.max(add_idx, Math.max(sub_idx, Math.max(mul_idx, div_idx)));
    }
    
    public void calculate(View view)  // Note: only called by the '=' button.
    {
    	if (operators.isEmpty()) return;  // There is no operation to perform yet.
    	if (endsWithOperator())
    	{
    		display("incorrect format");
    		resetCalculator();
    		return;
    	}
    		
    	// StringTokenizer is deprecated. Using String.split instead.
    	String[] operands = value.split("\\u002B|\\u2212|\\u00F7|\\u2715");

    	int i = 0;
    	double ans = Double.parseDouble(operands[i]); // TODO: catch NumberFormatException?
    	for (String operator : operators)
    		ans = applyOperation(operator, ans, Double.parseDouble(operands[++i]));

    	DecimalFormat df = new DecimalFormat("0.###");
    	display(df.format(ans));
    	resetCalculator();
    	// TODO: overwrite value with ans. reset value on next keypress if it's an operand (leave it if it's an operator)
    }
    
    private double applyOperation(String operator, double operand1, double operand2)
    {
    	// Not using the string with switch syntax because Android doesn't support JDK 7 (JRE 1.7) yet.
    	if (operator.equals(ADD)) return operand1 + operand2;
    	if (operator.equals(SUB)) return operand1 - operand2;
    	if (operator.equals(MUL)) return operand1 * operand2;
    	if (operator.equals(DIV)) return operand1 / operand2;  // Don't need to check for div by 0, Java already does.
    	return 0.0;
    }
    
    private void resetCalculator()
    {
    	value = "";
    	operators.clear();
    }
}
