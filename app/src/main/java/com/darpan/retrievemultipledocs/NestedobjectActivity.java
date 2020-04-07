package com.darpan.retrievemultipledocs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.darpan.retrievemultipledocs.Model.nestedNoteObject;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class NestedobjectActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPriority;
    private TextView textViewData;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Nested Notebook");

    private DocumentSnapshot lastResult;
    private EditText editTextTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nestedobject);
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextPriority = findViewById(R.id.edit_text_priority);
        editTextTags = findViewById(R.id.edit_text_tags);
        textViewData = findViewById(R.id.text_view_data);
    }

    public void addNote(View v) {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        if (editTextPriority.length() == 0) {
            editTextPriority.setText("0");
        }

        int priority = Integer.parseInt(editTextPriority.getText().toString());

        String tagInput = editTextTags.getText().toString();
        String[] tagArray = tagInput.split("\\s*,\\s*");//add value by commas separated
        Map<String, Boolean> tags = new HashMap<>();

        for (String tag : tagArray) {
            tags.put(tag, true);
        }

        nestedNoteObject note = new nestedNoteObject(title, description, priority, tags);

        notebookRef.add(note);
    }

    public void loadNotes(View v) {
        notebookRef.whereEqualTo("tags.tag1", true).get()
//                if we want to access tag1 then we have to go from top to down by using dot notation
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            nestedNoteObject note = documentSnapshot.toObject(nestedNoteObject.class);
                            note.setDocumentId(documentSnapshot.getId());

                            String documentId = note.getDocumentId();

                            data += "ID: " + documentId;

                            for (String tag : note.getTags().keySet()) {
                                data += "\n-" + tag;
                            }

                            data += "\n\n";
                        }
                        textViewData.setText(data);
                    }
                });
    }

    private void updateNestedValue0() {
        //to update the doc of qunique id pass the id of documents here
        //but this will only run if we have nested java object in our datdbase
        //this is only fir demo s.through this we can create a nested java object hat contains others java objects
        notebookRef.document("aoSRcxTCxkLpFcCyldBw")
                .update("tags.tag1.nested1.nested2", true);
    }

    private void updateNestedValue1() {
        notebookRef.document("aoSRcxTCxkLpFcCyldBw")
                .update("tags.tag1", false);
    }
}
