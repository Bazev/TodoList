package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.todo.pojos.Todo;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddTodoActivity extends AppCompatActivity {

    public static final String KEY_TODO = "todo";

    private Button btnAdd;
    private Button btnCancel;
    private Todo todo;
    private EditText etTodo;
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
        etTodo = findViewById(R.id.etTodo);
        context =getApplicationContext();


        Spinner spinner = (Spinner) findViewById(R.id.spTodo);

        String[] priority = new String[]{
                "Basse",
                "Normal",
                "Haute"
        };

        final List<String> priorityList = new ArrayList<>(Arrays.asList(priority));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, priorityList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etTodo.getText().length() < 3) {
                    Toast toast = Toast.makeText(context, "3 caractères minimum", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    todo = new Todo(etTodo.getText().toString(), spinner.getSelectedItem().toString());
                }
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_TODO, todo);
                setResult(RESULT_OK, resultIntent);
                finish();

                spinnerArrayAdapter.notifyDataSetChanged();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });


    }
}