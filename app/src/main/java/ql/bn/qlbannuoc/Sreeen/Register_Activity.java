package ql.bn.qlbannuoc.Sreeen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ql.bn.qlbannuoc.Database.DatabaseUserHelper;
import ql.bn.qlbannuoc.Model.User;
import ql.bn.qlbannuoc.R;
import ql.bn.qlbannuoc.databinding.ActivityRegisterBinding;

public class Register_Activity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnLogin.setOnClickListener(v->{
            startActivity(new Intent(Register_Activity.this,Login_Activity.class));
        });
        binding.btnRegister.setOnClickListener(v->{
            String username = binding.enterUsnedt.getText().toString();
            String password = binding.enterPassedt.getText().toString();
            String repassword = binding.enterRepassedt.getText().toString();
            if(username.isEmpty()){
                binding.enterUsnedt.setError("Không được để trống");
            }
            else if(password.isEmpty()){
                binding.enterPassedt.setError("Không được để trống");
            }
            else if(repassword.isEmpty()){
                binding.enterRepassedt.setError("Không được để trống");
            }
            else if(!password.equals(repassword)){
                binding.enterRepassedt.setError("Mật khẩu không trùng khớp");
            }
            else{
                //check username
                DatabaseUserHelper databaseUserHelper = new DatabaseUserHelper(this);
                //check username
                User user = databaseUserHelper.getUser(username);
                if(user != null){
                    binding.enterUsnedt.setError("Tên đăng nhập đã tồn tại");
                }
                else{
                    databaseUserHelper.insertData(username,password);
                    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Đăng ký thành công")
                            .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    startActivity(new Intent(Register_Activity.this,Login_Activity.class));
                                }
                            })
                            .show();
                }
            }
        });
    }
}