package com.example.ashish.firebasenotes.Model;

public class NotesData {

    String id;
    String title;
    String notes;
    String DATE;
   /* private boolean isSelected = false;
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public boolean isSelected() {
        return isSelected;
    }
   */

    public NotesData(){}
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNotes() {
        return notes;
    }

    public String getDATE() {
        return DATE;
    }

    public NotesData(String id, String title, String notes) {
        this.id = id;
        this.title = title;
        this.notes = notes;
    }
}
