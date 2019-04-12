package com.pagatodo.apolo.activity.account;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.splash._presenter.SplashPresenter;
import com.pagatodo.apolo.activity.splash._presenter._interfaces.ISplashPresenter;
import com.pagatodo.apolo.activity.splash._presenter._interfaces.ISplashView;
import com.pagatodo.apolo.data.local.Preferences;
import com.pagatodo.apolo.data.model.factory.RequestImage;
import com.pagatodo.apolo.data.room.pojo.Bancos;
import com.pagatodo.apolo.data.room.pojo.Bines;
import com.pagatodo.apolo.ui.base.factoryactivities.BasePresenterActivity;
import com.pagatodo.apolo.utils.Base64Utils;
import com.pagatodo.apolo.utils.ValidateForm;
import com.pagatodo.apolo.utils.customviews.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.apolo.utils.Constants.DTOCIF;
import static com.pagatodo.apolo.utils.Constants.IDP;
import static com.pagatodo.apolo.utils.Constants.ID_BANCO;
import static com.pagatodo.apolo.utils.Constants.NUM_TARJETA;
import static com.pagatodo.apolo.utils.Constants.SOLICITUD_ADULTO_MAYOR;
import static com.pagatodo.apolo.utils.Constants.SOLICITUD_IMPRESA;

public class TakePicturesActivity extends BasePresenterActivity<ISplashPresenter> implements ISplashView {

    private static final int CAMERA_REQUEST_FRONT = 1888;
    private static final int CAMERA_REQUEST_BACK = 2888;
    private static final int CAMERA_REQUEST_REQUIREMENT = 3888;

    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private ContentValues values;
    private Uri imageUri;
    @BindView(R.id.btnAceptar)
    MaterialButton btnContinuar;
    @BindView(R.id.img_ine_frente)
    ImageView img_ine_frente;
    @BindView(R.id.img_ine_reverso)
    ImageView img_ine_reverso;
    @BindView(R.id.img_solicitud)
    ImageView img_solicitud;
    @BindView(R.id.imageView)
    ImageView imageView;
    String base64_front,base64_back, base64_requeriment;
    private Bitmap thumbnail;
    String imageurl;
    Bitmap scaled;
    String bittosendfront="",bittosendback="",bittosendrequiriment="";
    boolean isValid;
    int imagen =0;
    boolean btnTerminado;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_pictures);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected int setIdContainerFragments() {
        return 0;
    }

    @Override
    protected int setIdMainView() {
        return 0;
    }

    @Override
    protected void initializePresenter() {
        presenter = new SplashPresenter(this);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "your subdirectory for Picture directory");
        if(!directory.exists() && !directory.mkdir())
            return null;
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                directory      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }

    private void initViews() {




        img_ine_frente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    }
                    else
                    {
                        values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "Identificacion_Frente");
                        values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                        imageUri = getContentResolver().insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, CAMERA_REQUEST_FRONT);

                    }
                }
            }
        });
        img_ine_reverso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    }
                    else
                    {
                        values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "Identificacion_Reverso");
                        values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                        imageUri = getContentResolver().insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, CAMERA_REQUEST_BACK);

                    }
                }
            }
        });    img_solicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    }
                    else
                    {
                        values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "Solicitud_Domiciliacion");
                        values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                        imageUri = getContentResolver().insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, CAMERA_REQUEST_REQUIREMENT);

                    }
                }
            }
        });

    }

    private void getData() {


    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_FRONT);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }



    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        /**Primer imagen Foto_INE_Frente **/
        if (requestCode == CAMERA_REQUEST_FRONT && resultCode == Activity.RESULT_OK)
        {
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Obtiene la ruta donde se encuentra guardada la imagen.

            if (thumbnail.getWidth() > 1280 || thumbnail.getHeight()> 720) {
                /* Reducir el tamaño de las imagenes un 30% */
                int newHeight = (int) Math.round(thumbnail.getHeight() * .40);
                int newWidth = (int) Math.round(thumbnail.getWidth() * .40);
                scaled = Bitmap.createScaledBitmap( thumbnail, newWidth, newHeight, false);
                // saveBmpImgUser(scaled, new Base64Utils(scaled, imageurl));
                bittosendfront = Base64Utils.getEncodedString(scaled);
                // presenter.loadFrontCard("Imagen_front",bittosendfront, );

            }
            thumbnail.getHeight();
            thumbnail.getWidth();

            imageurl = getRealPathFromURI(imageUri);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG,20,stream);

            byte[] byteArray = stream.toByteArray();
            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
            imageView.setImageBitmap(compressedBitmap);
            compressedBitmap.getHeight();
            compressedBitmap.getWidth();

            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            OutputStream outStream = null;
            File file = new File(extStorageDirectory, "er.PNG");
            ByteArrayOutputStream mOutputStream = new ByteArrayOutputStream();
            try {
                outStream = new FileOutputStream(file);
                compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, mOutputStream);
                byte [] mByteArray = mOutputStream.toByteArray();
                bittosendfront = Base64.encodeToString(mByteArray, Base64.DEFAULT);
                outStream.flush();
                outStream.close();
                img_ine_frente.setImageResource(R.drawable.done);
            } catch(Exception e) {

            }

        /**Segunda Imagen Foto_INE_Reverso*/
        }if (requestCode == CAMERA_REQUEST_BACK && resultCode == Activity.RESULT_OK)
        {
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Obtiene la ruta donde se encuentra guardada la imagen.

            if (thumbnail.getWidth() > 1280 || thumbnail.getHeight()> 720) {
                /* Reducir el tamaño de las imagenes un 30% */
                int newHeight = (int) Math.round(thumbnail.getHeight() * .40);
                int newWidth = (int) Math.round(thumbnail.getWidth() * .40);
                scaled = Bitmap.createScaledBitmap( thumbnail, newWidth, newHeight, false);
                // saveBmpImgUser(scaled, new Base64Utils(scaled, imageurl));
                bittosendback = Base64Utils.getEncodedString(scaled);


                // presenter.loadFrontCard("Imagen_front",bittosendfront, );

            }

            thumbnail.getHeight();
            thumbnail.getWidth();

            imageurl = getRealPathFromURI(imageUri);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG,20,stream);

            byte[] byteArray = stream.toByteArray();
            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
            imageView.setImageBitmap(compressedBitmap);
            compressedBitmap.getHeight();
            compressedBitmap.getWidth();


            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            OutputStream outStream = null;
            File file = new File(extStorageDirectory, "er.PNG");
            ByteArrayOutputStream mOutputStream = new ByteArrayOutputStream();
            try {
                outStream = new FileOutputStream(file);
                compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, mOutputStream);
                byte [] mByteArray = mOutputStream.toByteArray();
                bittosendback = Base64.encodeToString(mByteArray, Base64.DEFAULT);
                outStream.flush();
                outStream.close();
                img_ine_reverso.setImageResource(R.drawable.done);
            } catch(Exception e) {

            }


            /**Tercer imagen Solicitud**/
        }if (requestCode == CAMERA_REQUEST_REQUIREMENT && resultCode == Activity.RESULT_OK)
    {

        try {
            thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Obtiene la ruta donde se encuentra guardada la imagen.

        if (thumbnail.getWidth() > 1280 || thumbnail.getHeight()> 720) {
            /* Reducir el tamaño de las imagenes un 30% */
            int newHeight = (int) Math.round(thumbnail.getHeight() * .40);
            int newWidth = (int) Math.round(thumbnail.getWidth() * .40);
            scaled = Bitmap.createScaledBitmap( thumbnail, newWidth, newHeight, false);
            // saveBmpImgUser(scaled, new Base64Utils(scaled, imageurl));
            bittosendrequiriment = Base64Utils.getEncodedString(scaled);


            // presenter.loadFrontCard("Imagen_front",bittosendfront, );

        }

        thumbnail.getHeight();
        thumbnail.getWidth();

        imageurl = getRealPathFromURI(imageUri);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG,20,stream);

        byte[] byteArray = stream.toByteArray();
        Bitmap compressedBitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        imageView.setImageBitmap(compressedBitmap);
        compressedBitmap.getHeight();
        compressedBitmap.getWidth();


        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        File file = new File(extStorageDirectory, "er.PNG");
        ByteArrayOutputStream mOutputStream = new ByteArrayOutputStream();
        try {
            outStream = new FileOutputStream(file);
            compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, mOutputStream);
            byte [] mByteArray = mOutputStream.toByteArray();
            bittosendrequiriment = Base64.encodeToString(mByteArray, Base64.DEFAULT);
            outStream.flush();
            outStream.close();
            img_solicitud.setImageResource(R.drawable.done);
        } catch(Exception e) {

        }

        }
    }

    @OnClick(R.id.btnAceptar)
    public void continuar(){
    if (btnTerminado)
        finish();
    else
        validate();
    }


    public  void validate(){
        isValid=true;
        if (bittosendfront.isEmpty()){
            isValid =false;
        }
        if (bittosendback.isEmpty()){
            isValid =false;
        }
        if (bittosendrequiriment.isEmpty()){
            isValid =false;
        }

            if (isValid) {
                ValidateForm.enableBtn(true, btnContinuar);
                onSuccess();
            }
    }

    private void onSuccess() {
        Preferences preferences = App.getInstance().getPrefs();
        presenter.loadFrontCard("Identificacion_Frente.jpg",bittosendfront,bittosendfront.length(),preferences.loadString(SOLICITUD_IMPRESA),preferences.loadInt(IDP));
    }


    @Override
    public void updatePromotorsSuccess() {

    }

    @Override
    public void updateIniciativasSuccess() {

    }

    @Override
    public void updateTiendasSuccess() {

    }

    @Override
    public void getBanksSuccess(List<Bancos> bancosList) {

    }

    @Override
    public void getBinesSuccess(List<Bines> binesList) {

    }

    @Override
    public void setInsertSuccess() {

    }

    @Override
    public void successLoadImagen() {

        if (imagen ==0) {
            Preferences preferences = App.getInstance().getPrefs();
            presenter.loadFrontCard("Identificacion_Reverso.jpg", bittosendback, bittosendback.length(), preferences.loadString(SOLICITUD_IMPRESA), preferences.loadInt(IDP));
        imagen ++;

        }
        if (imagen ==1) {
            Preferences preferences = App.getInstance().getPrefs();
            presenter.loadFrontCard("Solicitud_Domiciliacion.jpg", bittosendrequiriment, bittosendrequiriment.length(), preferences.loadString(SOLICITUD_IMPRESA), preferences.loadInt(IDP));
            imagen ++;

        }if (imagen ==2) {
            btnTerminado =true;
            btnContinuar.setText("Finalizar");
            showMessage("La Domiciliación de Pago se ha guardado correctamente");
        }

    }

    @Override
    public void updateFailed(String title, String message) {

    }
}
