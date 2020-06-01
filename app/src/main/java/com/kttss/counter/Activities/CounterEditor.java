package com.kttss.counter.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.kttss.counter.R;
import com.kttss.counter.checker.Counter;
import com.kttss.counter.checker.ServiceFactory;
import com.kttss.counter.util.Globals;
import com.kttss.counter.util.Utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;



public class CounterEditor extends DialogFragment {

    EditText txtDate;
    EditText txtName;
    EditText txtLap;
    Button btnSave;

    Counter counter;

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.counter_editor,container,false);

        txtDate=(EditText)view.findViewById(R.id.txt_date);
        txtName=(EditText)view.findViewById(R.id.txt_name);
        txtLap=(EditText)view.findViewById(R.id.txt_lap);
        btnSave=(Button)view.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(btnSaveClick);
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendarDialog();
            }
        });

        startFragment();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog=getDialog();
        if(dialog!=null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void startFragment(){
        if(counter==null){
            counter=new Counter();
            counter.setDate(new Date());
            txtName.setText("");
            txtLap.setText("1");
            txtDate.setText(Utils.convertDate2String(Calendar.getInstance().getTime(), Globals.DISPLAY_DATE_FORMAT));
        }else{
            txtDate.setText(Utils.convertDate2String(counter.getDate(), Globals.DISPLAY_DATE_FORMAT));
            txtName.setText(counter.getName());
            txtLap.setText(String.valueOf(((Double)(counter.getLap())).intValue()));
        }
    }

    //Open Calendar
    public void openCalendarDialog(){
        Calendar cal=Calendar.getInstance();
        cal.setTime(counter.getDate());
        DatePickerDialog datePicker = new DatePickerDialog(context, dateChangedEvent, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }
    //Selected date event
    private DatePickerDialog.OnDateSetListener dateChangedEvent = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int year,
                              int month, int day) {
            Calendar cal=Calendar.getInstance();
            cal.set(year,month,day);
            txtDate.setText(Utils.convertDate2String(cal.getTime(),Globals.DISPLAY_DATE_FORMAT));

        }
    };


    private View.OnClickListener btnSaveClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!validate())
                return;
            counter.setName(txtName.getText().toString().trim());
            try {
                counter.setDate(Utils.convertString2Date(txtDate.getText().toString().trim(),Globals.DISPLAY_DATE_FORMAT));
                counter.setLap(Integer.parseInt(txtLap.getText().toString().trim()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(counter.getId()>0){
                ServiceFactory.counterService.update(counter);
            }
            else {
                counter.setValue(0d);
                ServiceFactory.counterService.create(counter);
            }
            ((MainActivity)context).fillListView();
            dismiss();
        }
    };

    private boolean validate(){
        boolean isValid=true;
        txtLap.setError(null);
        txtName.setError(null);
        if(txtLap.getText().toString().trim().length()<=0) {
            isValid = false;
            txtLap.setError(getString(R.string.action_settings));
        }
        if(txtName.getText().toString().trim().length()<=0) {
            isValid = false;
        }
        return isValid;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }
}
