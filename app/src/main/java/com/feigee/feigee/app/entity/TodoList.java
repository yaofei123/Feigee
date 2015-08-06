package com.feigee.feigee.app.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by fei.yao on 7/29/15.
 */
@DatabaseTable(tableName = "TodoList")
public class TodoList implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String context;

    @DatabaseField(canBeNull = false)
    private String deadline;

    private boolean isSloved = false;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public boolean isSloved() {
        return isSloved;
    }

    public void setSloved(boolean isSloved) {
        this.isSloved = isSloved;
    }
}
