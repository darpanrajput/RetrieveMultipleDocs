package com.darpan.retrievemultipledocs;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import static com.google.firebase.firestore.DocumentChange.Type.ADDED;
import static com.google.firebase.firestore.DocumentChange.Type.MODIFIED;
import static com.google.firebase.firestore.DocumentChange.Type.REMOVED;

public class PaginationActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPriority;
    private TextView textViewData;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Notebook");

    private DocumentSnapshot lastResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagination);
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextPriority = findViewById(R.id.edit_text_priority);
        textViewData = findViewById(R.id.text_view_data);
        executeBatchedWrite();
    }

    public void addNote(View v) {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        if (editTextPriority.length() == 0) {
            editTextPriority.setText("0");
        }

        int priority = Integer.parseInt(editTextPriority.getText().toString());

        Note note = new Note(title, description, priority);

        notebookRef.add(note);
    }

    public void loadNotes(View v) {
        Query query;
        if (lastResult == null) {
            //at first out last result would be empty
            query = notebookRef.orderBy("priority")
                    .limit(3);
        } else {
            query = notebookRef.orderBy("priority")
                    .startAfter(lastResult)//start after the last documents that were fetched previously
                    //start fetching result where th priority is 3 upto first 3 results
                    //startAfter will not include 3 in result and start at 4 or whatever comes after 3
                    // by start at and start after we use multiple queries
                    //like to achieve results where doc are arranged by the order by title and order by priority and start with 3
                    //we can use
                    //.orderBY("priority").orderBy("title").startAt(3,"title") order first by priority and then by title
                    .limit(3);//this is will limit our result for thr first fetch
        }

        query.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            //fetching the data from the applied query
                            Note note = documentSnapshot.toObject(Note.class);
                            note.setDocumentId(documentSnapshot.getId());

                            String documentId = note.getDocumentId();
                            String title = note.getTitle();
                            String description = note.getDescription();
                            int priority = note.getPriority();

                            data += "ID: " + documentId
                                    + "\nTitle: " + title + "\nDescription: " + description
                                    + "\nPriority: " + priority + "\n\n";
                        }

                        if (queryDocumentSnapshots.size() > 0) {
                            //to check if we reached the end
                            data += "___________\n\n";
                            textViewData.append(data);

                            lastResult = queryDocumentSnapshots.getDocuments()
                                    .get(queryDocumentSnapshots.size() - 1);
                        }
                    }
                });

        //this is just to show how to work with pagination but if click the load button twice then it will retrieve the same result

    }


/*
    @Override
    protected void onStart() {
        // this the provide the efficient way of iterating the collection and finding the doc where the change occur
        //instead of iterating the whole the documents

        super.onStart();
        *//*in this onStart method we usually fetching the whole  list of documents when anything changes in collection
        * but it is not one of the most efficient way of doing it. the best a of doing is to only provide th update
        * version of that document that has been recently changed instead of fetching the whole list if documents*//*
        notebookRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    //here we are iterating through something called document change get documents changes to change the required object
                    //this means we dont need to go on whole dat snapshot every document the document changes at the
                    //there are three type of document changes that will provide the the doc object will is changes ADDED MODIFIED REMOVED
                    DocumentSnapshot documentSnapshot = dc.getDocument();
                    String id = documentSnapshot.getId();//by default the data is ordered by the id which is randomly generated
                    int oldIndex = dc.getOldIndex();// if the doc does not exist then it will hold -1 as position
                    //like if add the document in th data set and the new added documents will hold the position of curent document object
                    int newIndex = dc.getNewIndex();

                    switch (dc.getType()) {
                        case ADDED:
                            textViewData.append("\nAdded: " + id +
                                    "\nOld Index: " + oldIndex + "New Index: " + newIndex);
                            break;
                        case MODIFIED:
                            textViewData.append("\nModified: " + id +
                                    "\nOld Index: " + oldIndex + "New Index: " + newIndex);
                            break;
                        case REMOVED:
                            textViewData.append("\nRemoved: " + id +
                                    "\nOld Index: " + oldIndex + "New Index: " + newIndex);
                            break;
                    }
                }
            }
        });
    }*/

    private void executeBatchedWrite() {

        WriteBatch batch = db.batch();
        DocumentReference doc1 = notebookRef.document("New Note");
        batch.set(doc1, new Note("New Note", "New Note", 1));// crate new doc

        DocumentReference doc2 = notebookRef.document("3TAptygBMGWgMdh6f9wP");//second documents id from firestore
        batch.update(doc2, "title", "Updated Note"); // update the doc

        DocumentReference doc3 = notebookRef.document("1V9pFclqIYB714k3cg50");//first documents id from firestore this will be delted from database if see the data base
        batch.delete(doc3);// delete the doc

        DocumentReference doc4 = notebookRef.document();
        batch.set(doc4, new Note("Added Note", "Added Note", 1));

        /*here we are calling failure listener as performing operation on documents that does exist
        * will give an exception and if exception occur that means the all the
        * above operation will not be executed as the batch is atomic in nature so the note will not be
        * updated niether the new note will be created nor the the note will be modified
        * the series of operation will failed if exception occur in any of the batched write operation  */
        batch.commit().addOnFailureListener(new OnFailureListener() {
            // pefore the single batch write ind single on click
            @Override
            public void onFailure(@NonNull Exception e) {
                textViewData.setText(e.toString());
            }
        });
    }

}
