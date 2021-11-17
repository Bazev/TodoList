package com.example.todo.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.todo.database.TodoDbHelper;
import com.example.todo.pojos.Todo;

import java.util.ArrayList;
import java.util.List;

public class TodoDAO extends DAO {

    public TodoDAO(Context context) {
        super(new TodoDbHelper(context));
    }

    public Todo find(int id) {
        Todo todo = null;
        open();
        Cursor cursor = db.rawQuery("select * from " + TodoDbHelper.TODO_TABLE_NAME +
                        " where " + TodoDbHelper.TODO_KEY + " = ? ",
                new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            todo = new Todo();
            todo.setId(cursor.getLong(TodoDbHelper.TODO_KEY_COLUMN_INDEX));
            todo.setName(cursor.getString(TodoDbHelper.TODO_NAME_COLUMN_INDEX));
            todo.setUrgency(cursor.getString(TodoDbHelper.TODO_URGENCY_COLUMN_INDEX));

            cursor.close();
        }
        close();
        return todo;
    }

    public List<Todo> list() {
        List<Todo> todos = new ArrayList<>();
        open();
        Cursor cursor = db.rawQuery("select * from " + TodoDbHelper.TODO_TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Todo todo = new Todo();
                todo.setId(cursor.getLong(TodoDbHelper.TODO_KEY_COLUMN_INDEX));
                todo.setName(cursor.getString(TodoDbHelper.TODO_NAME_COLUMN_INDEX));
                todo.setUrgency(cursor.getString(TodoDbHelper.TODO_URGENCY_COLUMN_INDEX));

                todos.add(todo);
                cursor.moveToNext();
            }
            cursor.close();
        }
        close();
        return todos;
    }

    public void add(Todo todo) {
        open();
        ContentValues values = new ContentValues();
        values.put(TodoDbHelper.TODO_NAME, todo.getName());
        values.put(TodoDbHelper.TODO_URGENCY, todo.getUrgency());
        long id = db.insert(TodoDbHelper.TODO_TABLE_NAME, null, values);
        todo.setId(id);
        close();
    }

    public void update(Todo todo) {
        open();
        ContentValues values = new ContentValues();
        values.put(TodoDbHelper.TODO_NAME, todo.getName());
        values.put(TodoDbHelper.TODO_URGENCY, todo.getUrgency());

        db.update(TodoDbHelper.TODO_TABLE_NAME, values, TodoDbHelper.TODO_KEY + " = ? ",
                new String[]{String.valueOf(todo.getId())});
        close();
    }

    public void delete(Todo todo) {
        open();
        db.delete(TodoDbHelper.TODO_TABLE_NAME, TodoDbHelper.TODO_KEY + " = ? ",
                new String[]{String.valueOf(todo.getId())});
        close();
    }
}
