package com.example.shiraz.medicalreport;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Upload_Img_Activity extends AppCompatActivity {


    FirebaseDatabase database;

    Button ar_submit;
    int PICK_IMAGE_REQUEST=001;
    StorageReference mStorageReference;
    Bitmap bitmap;
    private Uri filePath1122=null;
    ImageView img;

    public static final String DEST = "multiple_images.pdf";

    String IMAGES[]={
            ""
    };


    public void convertPDF(byte[] path)
    {
        /*
        String FILE = "mnt/sdcard/FirstPdf.pdf";
        Document document=new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();

            try {
                //image=Image.getInstance(path);
                document.add(new Paragraph("My Heading"));
              //  document.add(image);
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        */
    }

    private void createPdf1(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();


        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);



        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);


        // write the document content
        String targetPdf = "/Report.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream("http://www.avajava.com/images/avajavalogo.jpg"));
            //   btn_convert.setText("Check PDF");
            //   boolean_save=true;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath1122 = data.getData();

            ar_submit.setEnabled(true);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath1122);
                img.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }

    }


    public void downloadFile(String uRl) {
        File direct = new File(Environment.getExternalStorageDirectory()
                + "/AnhsirkDasarp");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager mgr = (DownloadManager) getApplication().getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle("Demo")
                .setDescription("Something useful. No, really.")
                .setDestinationInExternalPublicDir("/AnhsirkDasarp", "fileName.jpg");

        mgr.enqueue(request);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__img);



        ar_submit=(Button)findViewById(R.id.upload_img_submit);
        img=(ImageView)findViewById(R.id.upload_img_image);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.upload_img_progress);


        // createPdf();

        img.setImageResource(R.drawable.upload_img);
        ar_submit.setEnabled(false);

        progressBar.setVisibility(View.GONE);

        String imageUrl = "https://firebasestorage.googleapis.com/v0/b/fireapp-1e9a6.appspot.com/o/photos%2Fimage%3A25649?alt=media&token=6846b566-5bc2-4508-9f35-209a6137d122";
        String destinationFile = "/image.jpg";

        // downloadFile("https://firebasestorage.googleapis.com/v0/b/fireapp-1e9a6.appspot.com/o/photos%2F25579?alt=media&token=ba61b418-c818-4da7-a6d8-b46ae0db7a40");

        mStorageReference= FirebaseStorage.getInstance().getReference();


        if(Patient_Login.patient_info==null){return;}

        database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(""+Patient_Login.patient_info.getName()+"");


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


            }
        });
        ar_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(filePath1122!=null)
                {
                    progressBar.setVisibility(View.VISIBLE);

                    ar_submit.setEnabled(false);
                    img.setEnabled(false);

                    StorageReference filepath=mStorageReference.child("photos").child(filePath1122.getLastPathSegment());
                    filepath.putFile(filePath1122).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            myRef.push().setValue(new String(""+taskSnapshot.getDownloadUrl()+""));


                            Toast.makeText(getApplicationContext(),"Upload Sucees ",Toast.LENGTH_LONG).show();
                            img=(ImageView)findViewById(R.id.upload_img_image);
                            ar_submit.setEnabled(true);
                           img.setImageResource(R.drawable.upload_img);
                           img.setEnabled(true);
                            progressBar.setVisibility(View.GONE);





                        }
                    });

                }
                //  Toast.makeText(getApplicationContext(),"report Saved",Toast.LENGTH_SHORT).show();
                //     startActivity(new Intent(Add_Report.this,Patient_Page.class));



            }
        });







    }




}

