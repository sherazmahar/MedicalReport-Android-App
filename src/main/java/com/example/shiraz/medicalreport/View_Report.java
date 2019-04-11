package com.example.shiraz.medicalreport;

import android.*;
import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Header;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.kcode.permissionslib.main.OnRequestPermissionsCallBack;
import com.kcode.permissionslib.main.PermissionCompat;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.itextpdf.text.html.HtmlTags.FONT;


public class View_Report extends AppCompatActivity {

    FirebaseDatabase database;
    ArrayList<Link_Suggestion> link_suggestions;
    Document document;



 Button vr_report;


 LinearLayout vr_list;



    ListView lv;

    ImageListAdapter list_adapter;
    TextView suggestion;



    public void createPdf() throws IOException, DocumentException {




        Image img = Image.getInstance(Environment.getExternalStorageDirectory() + "/Android/"+Patient_Login.patient_info.getName()+"1.jpg");

        Document document = new Document(img);
        PdfWriter.getInstance(document, new FileOutputStream(Environment.getExternalStorageDirectory() + "/Android/Report.pdf"));
        document.open();




        for(int i=0;i<link_suggestions.size();i++)
        {
        //for (String image : IMAGES) {
          img = Image.getInstance(Environment.getExternalStorageDirectory()
                  + "/Android/"+Patient_Login.patient_info.getName()+""+(i+1)+".jpg");

        document.setPageSize(img);
        document.newPage();
        img.setAbsolutePosition(0, 0);
        document.add(img);

        }
        Toast.makeText(getApplicationContext(),"Pdf Successfully Created",Toast.LENGTH_SHORT).show();
        MainActivity.file_count1=0;
        MainActivity.file_count2=0;
        document.close();
    }




    class Download_Task extends AsyncTask<String,Void,Void>
    {




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        public Download_Task() {
        }

        @Override
        protected Void doInBackground(String... strings) {

            String path=strings[0];
            try {
                URL url=new URL(path);
                URLConnection urlConnection=url.openConnection();
                urlConnection.connect();
                File f=new File(  Environment.getExternalStorageDirectory()
                        + "/Android");

                if(!f.exists())
                {
                    f.createNewFile();
                }
                int size=urlConnection.getContentLength();

                MainActivity.file_count1+=1;
                File inputfile=new File(f,""+Patient_Login.patient_info.getName()+""+MainActivity.file_count1+".jpg");
                InputStream inputStream=new BufferedInputStream(url.openStream(),8192);
                byte[] data=new byte[1024];
                OutputStream outputStream=new FileOutputStream(inputfile);
                int count=0;
                while((count= inputStream.read(data))!=-1)
                {
                    outputStream.write(data,0,count);
                }

                inputStream.close();
                outputStream.close();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(),"Report "+MainActivity.file_count2+" Downloaded",Toast.LENGTH_SHORT).show();
            MainActivity.file_count2+=1;

            if(MainActivity.file_count1==MainActivity.file_count2 && MainActivity.file_count2==link_suggestions.size())
            {

                try {
                    createPdf();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }


            }


        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__report);
        vr_report=(Button)findViewById(R.id.vr_view);
       // vr_list=(LinearLayout)findViewById(R.id.vr_list);
        suggestion=(TextView)findViewById(R.id.view_suggestion);

        TextView tv=new TextView(View_Report.this);
        tv.setText("Patient Name : "+Patient_Login.patient_info.getName().toString()+"\n Age : "+Patient_Login.patient_info.getAge()+"");
        tv.setTextSize(20);

       // vr_list.addView(tv);

        link_suggestions=new ArrayList<>();

        lv=(ListView)findViewById(R.id.view_report_lv);

        new Handler().postDelayed(new Runnable() {
            public void run() {

                list_adapter=new ImageListAdapter(getApplicationContext(),link_suggestions);
                lv.setAdapter(list_adapter);


            }
        }, 1500);




        vr_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i=0;i<link_suggestions.size();i++)
                {
                    new Download_Task().execute(link_suggestions.get(i).getLink());
                }


            }
        });






        link_suggestions=new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(""+Patient_Login.patient_info.getName()+"");






        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String ls =dataSnapshot.getValue(String.class);

                link_suggestions.add(new Link_Suggestion(ls));


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
