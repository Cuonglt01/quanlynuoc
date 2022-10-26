package ql.bn.qlbannuoc.Sreeen;

import static ql.bn.qlbannuoc.Sreeen.Add_Fragment.brand;
import static ql.bn.qlbannuoc.Sreeen.Add_Fragment.type;
import static ql.bn.qlbannuoc.Sreeen.All_Fragment.mutableLiveData;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ql.bn.qlbannuoc.Database.DatabaseWaterbottleHelper;
import ql.bn.qlbannuoc.Model.Waterbottle;
import ql.bn.qlbannuoc.databinding.ActivityDetailBinding;
import ql.bn.qlbannuoc.databinding.FragmentAddBinding;

public class Detail_Activity extends AppCompatActivity {
    FragmentAddBinding binding;
    private int id, id_user;
    private Waterbottle waterbottle;
    private byte[] image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id = getIntent().getIntExtra("ID", 0);
        waterbottle = new Waterbottle();
        waterbottle = new DatabaseWaterbottleHelper(this).getDataById(id);
        id_user = waterbottle.getId_user();
        binding.btnAdd.setText("Sửa");
        binding.spnType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, type));
        binding.spnBrand.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, brand));
        binding.btnChoose.setOnClickListener(v->chooseImage());
        binding.btnAdd.setOnClickListener(v->editProduct());
        fillData();

    }



    private void fillData() {
        binding.edtName.setText(waterbottle.getName());
        binding.edtAddress.setText(waterbottle.getAddress());
        binding.edtPrice.setText(waterbottle.getPrice());
        binding.edtDate.setText(waterbottle.getDate());
        binding.spnType.setSelection(type.indexOf(waterbottle.getType()));
        binding.spnBrand.setSelection(brand.indexOf(waterbottle.getBrand()));
        binding.img.setImageBitmap(convertCompressedByteArrayToBitmap(waterbottle.getImage()));
    }
    private void editProduct() {
        //check null
        if (binding.edtName.getText().toString().isEmpty() || binding.edtAddress.getText().toString().isEmpty() || binding.edtPrice.getText().toString().isEmpty() || binding.edtDate.getText().toString().isEmpty()) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Không được để trống!")
                    .show();
            return;
        }
        waterbottle = new Waterbottle();
        waterbottle.setId(id);
        waterbottle.setName(binding.edtName.getText().toString());
        waterbottle.setAddress(binding.edtAddress.getText().toString());
        waterbottle.setPrice(binding.edtPrice.getText().toString());
        waterbottle.setDate(binding.edtDate.getText().toString());
        waterbottle.setType(binding.spnType.getSelectedItem().toString());
        waterbottle.setBrand(binding.spnBrand.getSelectedItem().toString());
        waterbottle.setImage(image != null ? image : convertBitmapToCompressedByteArray(((BitmapDrawable) binding.img.getDrawable()).getBitmap()));
        waterbottle.setId_user(id_user);
        if(new DatabaseWaterbottleHelper(this).updateData(waterbottle))
        {
            mutableLiveData.setValue(new DatabaseWaterbottleHelper(this).getAllData(id_user));
            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Thành công!")
                    .setContentText("Sửa thành công!")
                    .setConfirmButton("OK", sweetAlertDialog -> {
                        sweetAlertDialog.dismissWithAnimation();
                        finish();
                    })
                    .show();
        }
        else
        {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Sửa thất bại!")
                    .show();
        }
    }


    //Convert Byte To BitMap
    public Bitmap convertCompressedByteArrayToBitmap(byte[] compressedByteArray) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(compressedByteArray, 0, compressedByteArray.length);
        return bitmap;
    }

    //Convert Bitmap To Byte
    public byte[] convertBitmapToCompressedByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                binding.img.setImageBitmap(bitmap);
                image = convertBitmapToCompressedByteArray(bitmap);
                Log.d("TAGIMAGE", "onActivityResult: "+convertBitmapToCompressedByteArray(bitmap));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}