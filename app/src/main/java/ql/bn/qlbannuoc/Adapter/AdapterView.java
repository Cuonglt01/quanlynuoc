package ql.bn.qlbannuoc.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import ql.bn.qlbannuoc.Sreeen.Add_Fragment;
import ql.bn.qlbannuoc.Sreeen.All_Fragment;

public class AdapterView extends FragmentStatePagerAdapter {
    //tạo adapter cho viewpager trong main activity để chuyển đổi giữa các fragment trong main activity (trang chủ)
    //chuyển đổi các tablayout trong main activity
    public AdapterView(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0 :
                //khởi tạo fragment add trips
                Add_Fragment add_Fragment = new Add_Fragment();
                return add_Fragment;
            case 1 :
                //khởi tạo fragment all trips
                All_Fragment all_Fragment = new All_Fragment();
                return all_Fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
                
                case 0:
                    return "Thêm hóa đơn";
                case 1:
                    return "Tất cả hóa đơn";
            default:
                return null;
        }
    }
}
