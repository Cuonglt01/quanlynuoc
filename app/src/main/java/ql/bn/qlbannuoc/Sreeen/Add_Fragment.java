package ql.bn.qlbannuoc.Sreeen;

import static ql.bn.qlbannuoc.Sreeen.All_Fragment.mutableLiveData;
import static ql.bn.qlbannuoc.Sreeen.Login_Activity.id_user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ql.bn.qlbannuoc.Database.DatabaseWaterbottleHelper;
import ql.bn.qlbannuoc.Model.Waterbottle;
import ql.bn.qlbannuoc.databinding.FragmentAddBinding;

public class Add_Fragment extends Fragment {
    FragmentAddBinding binding;
    private byte[] image;
    public static ArrayList<String> type = new ArrayList<>();
    public static ArrayList<String> brand = new ArrayList<>();
    public Add_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnAdd.setOnClickListener(v->addProduct());
        binding.btnChoose.setOnClickListener(v->chooseImage());
        binding.edtDate.setOnClickListener(v->chooseDate());
        setSpiner();
    }

    //set spiner
    private void setSpiner(){
        type.add("6l");
        type.add("12l");
        type.add("18l");
        type.add("24l");
        type.add("30l");
        type.add("36l");
        type.add("42l");
        type.add("48l");
        brand.add("Vihawa");
        brand.add("Vina");
        brand.add("Nestle");
        brand.add("Lavie");
        brand.add("Aquafina");
        brand.add("Dasa");
        brand.add("Bidrico");
        binding.spnType.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, type));
        binding.spnType.setSelection(0);
        binding.spnBrand.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, brand));
        binding.spnBrand.setSelection(0);
    }


    private void chooseDate() {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();//khởi tạo builder để hiển thị date picker
        builder.setTitleText("Select a date");
        MaterialDatePicker materialDatePicker = builder.build();
        materialDatePicker.show(getChildFragmentManager(),"DATE_PICKER");
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            //convert date to string
            String myFormat = "dd/MM/yyyy";// định dạng ngày tháng năm
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);// định dạng ngày tháng năm
            binding.edtDate.setText(sdf.format(selection));
    });
    }
    private void addProduct() {
        //check null input
        if(binding.edtName.getText().toString().isEmpty()){
            binding.edtName.setError("Không được để trống");
            return;
        }
        if(binding.edtAddress.getText().toString().isEmpty()){
            binding.edtAddress.setError("Không được để trống");
            return;
        }
        if(binding.edtDate.getText().toString().isEmpty()){
            binding.edtDate.setError("Không được để trống");
            return;
        }
        if(binding.edtPrice.getText().toString().isEmpty()){
            binding.edtPrice.setError("Không được để trống");
            return;
        }
        //get data
        Waterbottle waterbottle = new Waterbottle();
        waterbottle.setName(binding.edtName.getText().toString());
        waterbottle.setAddress(binding.edtAddress.getText().toString());
        waterbottle.setType(binding.spnType.getSelectedItem().toString());
        waterbottle.setBrand(binding.spnBrand.getSelectedItem().toString());
        waterbottle.setDate(binding.edtDate.getText().toString());
        waterbottle.setImage(image);
        waterbottle.setPrice(binding.edtPrice.getText().toString());
        waterbottle.setId_user(id_user);
        //add to database
        DatabaseWaterbottleHelper databaseWaterbottleHelper = new DatabaseWaterbottleHelper(getContext());
        if(databaseWaterbottleHelper.insertData(waterbottle)){
            binding.edtName.setText("");
            binding.edtAddress.setText("");
            binding.edtDate.setText("");
            binding.edtPrice.setText("");
            binding.img.setImageResource(0);
            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Thành công")
                    .setContentText("Thêm sản phẩm thành công")
                    .show();
            mutableLiveData.setValue(databaseWaterbottleHelper.getAllData(id_user));
        }

    }

    public void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == getActivity().RESULT_OK){
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                binding.img.setImageBitmap(bitmap);
                image = imageViewToByte(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }




    //Convert bitmap to byte
    public byte[] imageViewToByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //Convert Byte To BitMap
    public static Bitmap convertCompressedByteArrayToBitmap(byte[] src){
        return BitmapFactory.decodeByteArray(src, 0, src.length);
    }

}