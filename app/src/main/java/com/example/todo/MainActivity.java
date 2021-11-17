package com.example.todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.todo.DAO.TodoDAO;
import com.example.todo.pojos.Todo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private TextView tvTodo;
    private final String KEY_TODOS = "todos";
    private TodoDAO todoDAO;
    private List<Todo> todos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        tvTodo = findViewById(R.id.tvTodo);
        todoDAO = new TodoDAO(context);

        if (savedInstanceState != null) {
            tvTodo.setText(savedInstanceState.getString(KEY_TODOS));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        TodoAsyncTasks todoAsyncTasks = new TodoAsyncTasks();
        todoAsyncTasks.execute();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TODOS, tvTodo.getText().toString());
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
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showTodos() {
        tvTodo.setText("");
        todos = todoDAO.list();
        for (Todo todo : todos) {
            tvTodo.append(todo.getName() + " : " + todo.getUrgency() + "\n");
        }

    }

    public class TodoAsyncTasks extends AsyncTask<Nullable, Nullable, List<Todo>> {

        @Override
        protected List<Todo> doInBackground(Nullable... nullables) {

            List<Todo> responseTodo = new ArrayList<>();
            try {
                responseTodo = todoDAO.list();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return responseTodo;
        }

        @Override
        protected void onPostExecute(List<Todo> todos) {
            StringBuilder stringBuilder = new StringBuilder();
            showTodos();
            tvTodo.setText(stringBuilder.toString());
        }
    }

}
