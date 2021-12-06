package com.example.alphacar;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;
import com.example.alphacar.ATask.StoreRegister;
import com.lakue.lakuepopupactivity.PopupActivity;
import com.lakue.lakuepopupactivity.PopupGravity;
import com.lakue.lakuepopupactivity.PopupResult;
import com.lakue.lakuepopupactivity.PopupType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class RegisterUpdateActivity extends AppCompatActivity {


    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;



    LinearLayout layout;
    TextView textView;

    EditText et_store_name, et_store_master_name, et_store_registration_number, et_inventory, et_store_price,
            et_introduce, et_addr1, et_addr2, et_store_tel, et_store_time, et_store_dayoff;

    Button btnSearch_addr, btnRegister;

    ImageView iv_pic1, iv_pic2, iv_pic3;

    ImageButton btn_back;

    File imgFile = null;

    private int GALLEY_CODE = 10;
    private int CAMERA_CODE = 1004;

    private ArrayList<String> storePic = new ArrayList<>();
    private String profile;

    private ArrayList<String> storeInventory = new ArrayList<String>();


    //이미지 리얼패스
    private String getRealPathFromUri(Uri uri) {
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);
        Cursor cursor = cursorLoader.loadInBackground();
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String url = cursor.getString(columnIndex);
        cursor.close(); return url;
    }

    //팝업으로 받아온 프로필 사진 처리 방법
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        iv_pic1 = findViewById(R.id.register_iv_pic1);
        iv_pic2 = findViewById(R.id.register_iv_pic2);
        iv_pic3 = findViewById(R.id.register_iv_pic3);
        et_addr1 = findViewById(R.id.register_et_store_addr1);


        ArrayList<ImageView> list = new ArrayList<>();
        list.add(iv_pic1);
        list.add(iv_pic2);
        list.add(iv_pic3);

        if (resultCode == RESULT_OK) {
            PopupResult result = (PopupResult) data.getSerializableExtra("result");
            if(result == PopupResult.RIGHT){
                //closeOptionsMenu(); //테스트끝나면 쓸코드

                // 사진 찍기
                Intent picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(picIntent.resolveActivity(getPackageManager()) != null){
                    imgFile = null;
                    // creatFile 매소드를 이용하여 임시파일을 만듬
                    imgFile = creatFile();

                    if(imgFile != null){
                        // API24 이상부터는 FileProvider를 제공해야함
                        Uri imgUri = FileProvider.getUriForFile(getApplicationContext(),
                                getApplicationContext().getPackageName()+".fileprovider",
                                imgFile);
                        // 만약에 API24 이상이면
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){ // API24
                            picIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                        }else {
                            picIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
                        }

                        startActivityForResult(picIntent, CAMERA_CODE);
                    }

                }

            } else if(result == PopupResult.LEFT){
                // 사진첩 접근
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //데이터를 uri로 받는다.
                startActivityForResult(intent, GALLEY_CODE);

            }
        }

        if(requestCode == GALLEY_CODE){

            if (resultCode == RESULT_OK) {

                //기존 이미지 지우기
                iv_pic1.setImageResource(0);
                iv_pic2.setImageResource(0);
                iv_pic3.setImageResource(0);

                //ClipData 또는 Uri를 가져온다
                Uri uri = data.getData();
                ClipData clipData = data.getClipData();

                //이미지 URI 를 이용하여 이미지뷰에 순서대로 세팅한다.
                if(clipData!=null){
                    if (clipData.getItemCount()>3){
                        Toast.makeText(getApplicationContext(), "사진은 3장까지 선택가능합니다", Toast.LENGTH_SHORT).show();

                    }
                    for(int i = 0; i < list.size(); i++) {
                        if(i<clipData.getItemCount()){
                            Uri urione =  clipData.getItemAt(i).getUri();
                            switch (i){
                                case 0:
                                    storePic.add(getRealPathFromUri(urione));
                                    iv_pic1.setImageURI(urione);
                                    break;
                                case 1:
                                    storePic.add(getRealPathFromUri(urione));
                                    iv_pic2.setImageURI(urione);
                                    break;
                                case 2:
                                    storePic.add(getRealPathFromUri(urione));
                                    iv_pic3.setImageURI(urione);
                                    break;
                            }
                        }
                    }
                }else if(uri != null){
                    storePic.add(getRealPathFromUri(data.getData()));
                    iv_pic1.setImageURI(uri);
                }
            }
        }
        if(requestCode == CAMERA_CODE && resultCode == RESULT_OK){
            // 저장처리를 함
            //Toast.makeText(JoinPage.this, "사진이 잘 찍힘", Toast.LENGTH_SHORT).show();
            setPic();
        }

        switch (requestCode){
            case SEARCH_ADDRESS_ACTIVITY:
                if(resultCode == RESULT_OK) {
                    String addr = data.getExtras().getString("data");
                    if(data != null){
                        et_addr1.setText(addr);
                    }
                }
                break;
        }

    }
    //사진 파일을 이름을 만듦
    private File creatFile() {
        // 파일 이름을 만들기 위해 시간값을 생성함
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = "My" + timestamp;
        // 사진파일을 저장하기 위한 경로
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File curFile = null;
        try {
            // 임시파일을 생성함(전체경로),  2번째 suffix 확장자:파일확장자(jpg)
            curFile = File.createTempFile(imageFileName, ".jpg"
                    , storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 스트링타입으로 임시파일이 있는 곳의 절대경로를 저장함
        profile = curFile.getAbsolutePath();

        return curFile;
    }
    // 사진을 저장처리 하는 곳
    private void setPic() {
        // 이미지뷰의 크기 알아오기
        int targetW = iv_pic1.getWidth();
        int targetH = iv_pic1.getHeight();

        // 사진의 크기 가져오기
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        int photoW = options.outWidth;
        int photoH = options.outHeight;

        // 이미지 크기를 맟출비율을 결정
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // 이미지뷰의 크기에 맞게 이미지크기를 조절
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;
        options.inPurgeable = true;

        // 비트맵 이미지를 생성
        Bitmap bitmap = BitmapFactory.decodeFile(profile);
        // 이미지를 갤러리에 저장하기
        gelleryAddPic(bitmap);
        Glide.with(this).load(bitmap).into(iv_pic1);



    }
    // 이미지를 갤러리에 저장하기
    private void gelleryAddPic(Bitmap bitmap) {
        FileOutputStream fos;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){ // API29
            ContentResolver resolver = getContentResolver();

            // 맵구조를 가진 ContentValues : 파일정보를 저장함
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,
                    "Image_" + "jpg");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE,
                    "image/jpeg");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES + File.separator + "TestFolder");

            Uri imageUri = resolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues );
            try {
                fos = (FileOutputStream) resolver
                        .openOutputStream(Objects.requireNonNull(imageUri));
                //Toast.makeText(JoinPage.this, "fos 작업됨", Toast.LENGTH_SHORT).show();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Objects.requireNonNull(fos);

            }catch (Exception e){

            }

        }else {
            // 이미지 파일을 스캔해서 갤러리에 저장하기 위한 인텐트
            Intent msIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            // 처음에 CreateFile에서 생성해둔 이미지경로(imgFilePath)를 이용하여 파일객체를 만든다
            Uri contentUri = Uri.fromFile(imgFile);
            File f = new File(profile);
            msIntent.setData(contentUri);
            // sendBroadcast를 이용하여 저장
            this.sendBroadcast(msIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Intent intent = getIntent();
        String prev_store_name = intent.getStringExtra("store_name");
        String prev_customer_name = intent.getStringExtra("customer_name");
        String prev_store_registration_number = intent.getStringExtra("store_registration_number");
        String prev_inventory = String.valueOf(intent.getIntExtra("store_registration_number", 0));
        String prev_store_price = intent.getStringExtra("store_price");
        String prev_introduce = intent.getStringExtra("introduce");
        String prev_store_addr = intent.getStringExtra("store_addr");
        String prev_store_tel = intent.getStringExtra("store_tel");
        String prev_store_time = intent.getStringExtra("store_time");
        String prev_store_dayoff = intent.getStringExtra("store_dayoff");




        checkDangerousPermissions();

        btn_back = findViewById(R.id.btn_back);
        btnSearch_addr = findViewById(R.id.register_btn_search_addr);


        //상단 뒤로가기
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSearch_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterUpdateActivity.this, WebSearch.class);
                startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY );
            }
        });

        et_store_name = findViewById(R.id.register_et_store_name);
        et_store_master_name = findViewById(R.id.register_et_store_master_name);
        et_store_registration_number = findViewById(R.id.register_et_store_registration_number);
        et_inventory = findViewById(R.id.register_et_inventory);
        et_store_price = findViewById(R.id.register_et_store_price);
        et_introduce = findViewById(R.id.register_et_introduce);
        et_addr1 = findViewById(R.id.register_et_store_addr1);
        et_addr2 = findViewById(R.id.register_et_store_addr2);
        et_store_tel = findViewById(R.id.register_et_store_tel);
        et_store_time = findViewById(R.id.register_et_store_time);
        et_store_dayoff = findViewById(R.id.register_et_store_dayoff);

        et_store_name.setText(prev_store_name);
        et_store_master_name.setText(prev_customer_name);
        et_store_registration_number.setText(prev_store_registration_number);
        et_inventory.setText(prev_inventory);
        et_store_price.setText(prev_store_price);
        et_introduce.setText(prev_introduce);
        et_addr1.setText(prev_store_addr);
        et_store_tel.setText(prev_store_tel);
        et_store_time.setText(prev_store_time);
        et_store_dayoff.setText(prev_store_dayoff);

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String customer_email = "";
                String store_name = et_store_name.getText().toString();
                String store_master_name = et_store_master_name.getText().toString();
                String store_registration_number = et_store_registration_number.getText().toString();
                int inventory = Integer.parseInt(et_inventory.getText().toString());
                String store_price = et_store_price.getText().toString();
                String introduce = et_introduce.getText().toString();
                String store_addr = et_addr1.getText().toString()+et_addr2.getText().toString();
                String store_tel = et_store_tel.getText().toString();
                String store_time = et_store_time.getText().toString();
                String store_dayoff = et_store_dayoff.getText().toString();

                StoreRegister register = new StoreRegister(customer_email, store_name, store_addr,
                        store_tel, store_time, store_dayoff, introduce, inventory, store_price,
                        store_master_name, store_registration_number, storePic);
                String state = "";
                try {
                    state = register.execute().get().trim();
                    Log.d("main:joinact0 : ", state);
                    state = state.substring(11, 12);
                    Log.d("main:joinact1 : ", state);
                } catch (ExecutionException e) {
                    //e.printStackTrace();
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }

                if (state.equals("1")) {
                    Toast.makeText(RegisterUpdateActivity.this, "삽입성공 !!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:joinact", "삽입성공 !!!");
                    finish();
                } else {
                    Toast.makeText(RegisterUpdateActivity.this, "삽입실패 !!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:joinact", "삽입실패 !!!");
                    finish();
                }
            }
        });


//        textView = findViewById(R.id.register);

//        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //사진관련 처리-------------------------------------------------------------------

        iv_pic1 = findViewById(R.id.register_iv_pic1);
        iv_pic2 = findViewById(R.id.register_iv_pic2);
        iv_pic3 = findViewById(R.id.register_iv_pic3);

        ArrayList<ImageView> list = new ArrayList<>();
        list.add(iv_pic1);
        list.add(iv_pic2);
        list.add(iv_pic3);

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(getBaseContext(), PopupActivity.class);
                    intent.putExtra("type", PopupType.SELECT);
                    intent.putExtra("gravity", PopupGravity.LEFT);
                    intent.putExtra("title", "세차장 사진");
                    intent.putExtra("content", "새로운 사진을 찍거나 사진을 가져올 수 있습니다.");
                    intent.putExtra("buttonLeft", "불러오기");
                    intent.putExtra("buttonRight", "사진찍기");
                    startActivityForResult(intent, 2);

                }
            });
        }
    }

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_MEDIA_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}