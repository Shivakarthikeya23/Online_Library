package com.example.onlinelibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Library extends AppCompatActivity {

    Button btnSem;
    ImageButton mselectpdf;
    TextView mpdfname;
    Button muploadpdf;

    private final int REQ=1;
    private Uri pdfdata;
    private String pdfname;
    int sem = 1;
    TextView tvBooks;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_library);
        btnSem = findViewById(R.id.btnsem);
        mselectpdf=findViewById(R.id.selectpdf);
        mpdfname=findViewById(R.id.pdfname);
        muploadpdf=findViewById(R.id.uploadpdf);
        tvBooks = findViewById(R.id.tvBooks);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");


        muploadpdf.setVisibility(View.INVISIBLE);
        mselectpdf.setVisibility(View.INVISIBLE);
        mpdfname.setVisibility(View.INVISIBLE);
        tvBooks.setVisibility(View.INVISIBLE);

        btnSem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muploadpdf.setVisibility(View.VISIBLE);
                mselectpdf.setVisibility(View.VISIBLE);
                mpdfname.setVisibility(View.VISIBLE);
                tvBooks.setVisibility(View.VISIBLE);
            }
        });

        mselectpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });

        //  mpdfname.setText(pdfname);
        muploadpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pdfdata==null)
                {
                    Toast.makeText(getApplicationContext(),"Select Pdf First",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    uploadpdf();
                }
            }
        });
    }

    private void uploadpdf() {

        String name = mpdfname.getText().toString();
        Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
        intent.putExtra("pdfname", pdfname);
        startActivity(intent);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final long messagePushID = System.currentTimeMillis();
        final StorageReference filepath = storageReference.child(messagePushID + "." + "pdf");
        Toast.makeText(Library.this, filepath.getName(), Toast.LENGTH_SHORT).show();
        filepath.putFile(pdfdata).continueWithTask(new Continuation() {
            @Override
            public Object then(@NonNull Task task) throws Exception {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                return filepath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Toast.makeText(Library.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                if (task.isSuccessful()) {
                    Uri uri = task.getResult();
                    String myurl;
                    myurl = uri.toString();
                    Log.d("TAG",myurl);
                    Toast.makeText(Library.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    //  dialog.dismiss();
//                    Toast.makeText(Library.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }





    private void opengallery() {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pdf File"),REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pdfdata = data.getData();
        Cursor returnCursor = Library.this.getContentResolver().query(pdfdata, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String fileName = returnCursor.getString(nameIndex);
        returnCursor.close();

        mpdfname.setText(fileName);

    }

    public void add(View view){
        sem = sem + 1;
        display(sem);
    }
    public void sub(View view){
        sem = sem - 1;
        display(sem);
    }

    public void display(int sem) {
        TextView tvSem = (TextView) findViewById(R.id.tvSem);
        tvSem.setText(String.valueOf(sem));
    }
}