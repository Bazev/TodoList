package com.example.todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.todo.pojos.Todo;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private TextView tvTodo;
    private final String KEY_TODOS = "todos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        tvTodo = findViewById(R.id.tvTodo);

        if (savedInstanceState != null) {
            tvTodo.setText(savedInstanceState.getString(KEY_TODOS));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(KEY_TODOS,  tvTodo.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuTodo:
                Intent intent = new Intent(context, AddTodoActivity.class);
                startActivityForResult(intent, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Todo todo = (Todo) data.getSerializableExtra(AddTodoActivity.KEY_TODO);
        if (requestCode == 1) {
            if (resultCode ==  RESULT_OK) {
                tvTodo.append("A faire :" + todo.getName() + " Priorité :" + todo.getUrgency() +"\n");
            }
        }
    }
}