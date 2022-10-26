package ql.bn.qlbannuoc.Sreeen;

import static ql.bn.qlbannuoc.Sreeen.All_Fragment.mutableLiveData;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ql.bn.qlbannuoc.Database.DatabaseUserHelper;
import ql.bn.qlbannuoc.Database.DatabaseWaterbottleHelper;
import ql.bn.qlbannuoc.MainActivity;
import ql.bn.qlbannuoc.Model.User;
import ql.bn.qlbannuoc.R;
import ql.bn.qlbannuoc.databinding.ActivityLoginBinding;

public class Login_Activity extends AppCompatActivity {
    ActivityLoginBinding binding;
    SharedPreferences sharedPreferences;
    public static int id_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        binding.enterUsnedt.setText(sharedPreferences.getString("username",""));
        binding.enterPassedt.setText(sharedPreferences.getString("password",""));
        binding.rememberMe.setChecked(sharedPreferences.getBoolean("checked",false));
        binding.signinCraete.setOnClickListener(v->{
            startActivity(new Intent(Login_Activity.this,Register_Activity.class));
        });
        binding.loginBtn.setOnClickListener(v->{
            String username = binding.enterUsnedt.getText().toString();
            String password = binding.enterPassedt.getText().toString();
            if(username.isEmpty()){
                binding.enterUsnedt.setError("Không được để trống");
            }
            else if(password.isEmpty()){
                binding.enterPassedt.setError("Không được để trống");
            }
            else{
                //check username
                DatabaseUserHelper databaseUserHelper = new DatabaseUserHelper(this);
                //check username
                User user = databaseUserHelper.getUser(username);
                if(user == null){
                    binding.enterUsnedt.setError("Tên đăng nhập không tồn tại");
                }
                else{
                    if(user.getPassword().equals(password)){
                        //login success
                        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Đăng nhập thành công")
                                .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        if(binding.rememberMe.isChecked()){
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("username",username);
                                            editor.putString("password",password);
                                            editor.putBoolean("checked",true);
                                            editor.apply();
                                        }
                                        else{
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("username","");
                                            editor.putString("password","");
                                            editor.putBoolean("checked",false);
                                            editor.apply();
                                        }
                                        Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                                        id_user = user.getId();
                                        mutableLiveData.setValue(new DatabaseWaterbottleHelper(Login_Activity.this).getAllData(id_user));
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .show();
                    }
                    else{
                        binding.enterPassedt.setError("Mật khẩu không đúng");
                    }
                }
            }
        });
    }
}