package com.kttss.counter.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.kttss.counter.R;
import com.kttss.counter.activities.CounterActivity;
import com.kttss.counter.activities.CounterEditor;
import com.kttss.counter.activities.MainActivity;
import com.kttss.counter.checker.Counter;
import com.kttss.counter.checker.ServiceFactory;

import java.util.List;


public class CounterAdapter extends BaseAdapter {
    List<Counter> items;
    Context context;

    public CounterAdapter(List<Counter> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).getId();
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View v= LayoutInflater.from(context).inflate(R.layout.counter_item,null,false);

        final ViewHolder viewHolder=new ViewHolder(v);

        final Counter item=items.get(position);

        viewHolder.txtName.setText(context.getString(R.string.counter_name)+": "+item.getName());
        viewHolder.txtLap.setText(context.getString(R.string.counter_lap)+": "+String.valueOf(((Double)item.getLap()).intValue()));
        viewHolder.txtValue.setText(String.valueOf(((Double)item.getValue()).intValue()));

        viewHolder.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(position).setValue(items.get(position).getValue()+items.get(position).getLap());
                viewHolder.txtValue.setText(String.valueOf(((Double)items.get(position).getValue()).intValue()));
                ServiceFactory.counterService.update(items.get(position));
            }
        });

        viewHolder.btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(position).setValue(items.get(position).getValue()-items.get(position).getLap());
                viewHolder.txtValue.setText(String.valueOf(((Double)items.get(position).getValue()).intValue()));
                ServiceFactory.counterService.update(items.get(position));
            }
        });

        viewHolder.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.get(position).setValue(0d);
                viewHolder.txtValue.setText(String.valueOf(((Double)items.get(position).getValue()).intValue()));
                ServiceFactory.counterService.update(items.get(position));
            }
        });

        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CounterEditor counterEditor=new CounterEditor();
                counterEditor.setContext(context);
                counterEditor.setCounter(item);
                counterEditor.show(((MainActivity)context).getFragmentManager(),"Edit counter");
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                ServiceFactory.counterService.delete(item);
                                ((MainActivity)context).fillListView();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                builder.setMessage(R.string.counter_delete_confirmation).setPositiveButton(R.string.alert_yes, dialogClickListener)
                        .setNegativeButton(R.string.alert_no, dialogClickListener).show();
            }
        });
        viewHolder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CounterActivity.class);
                Bundle b=new Bundle();
                b.putInt("ID",item.getId());
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });
        return v;
    }


    private class ViewHolder{
        public TextView txtName;
        public TextView txtLap;
        public TextView txtValue;
        public Button btnPrevious;
        public Button btnReset;
        public Button btnNext;
        public Button btnDetails;
        public Button btnEdit;
        public Button btnDelete;

        public ViewHolder(View itemView) {
            txtName=(TextView)itemView.findViewById(R.id.txt_name);
            txtLap=(TextView)itemView.findViewById(R.id.txt_lap);
            txtValue=(TextView)itemView.findViewById(R.id.txt_value);
            btnPrevious=(Button)itemView.findViewById(R.id.btn_previous);
            btnReset=(Button)itemView.findViewById(R.id.btn_reset);
            btnNext=(Button)itemView.findViewById(R.id.btn_next);
            btnDetails=(Button)itemView.findViewById(R.id.btn_details);
            btnEdit=(Button)itemView.findViewById(R.id.btn_edit);
            btnDelete=(Button)itemView.findViewById(R.id.btn_delete);
        }
    }
}


