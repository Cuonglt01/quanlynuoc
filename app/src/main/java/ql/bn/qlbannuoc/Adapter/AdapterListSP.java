package ql.bn.qlbannuoc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ql.bn.qlbannuoc.Database.DatabaseWaterbottleHelper;
import ql.bn.qlbannuoc.Model.Waterbottle;
import ql.bn.qlbannuoc.R;
import ql.bn.qlbannuoc.Sreeen.Detail_Activity;

public class AdapterListSP extends RecyclerView.Adapter<AdapterListSP.ViewHolder> {
    private List<Waterbottle> waterbottleList;
    private Context context;

    public AdapterListSP(List<Waterbottle> waterbottleList, Context context) {
        this.waterbottleList = waterbottleList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_one,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Waterbottle waterbottle = waterbottleList.get(position);
        holder.tvName.setText(waterbottle.getName());
        holder.tvAddress.setText(String.valueOf(waterbottle.getAddress()));
        holder.tvDate.setText(waterbottle.getDate());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Detail_Activity.class);
            intent.putExtra("ID",waterbottle.getId());
            context.startActivity(intent);
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //xác nhận xóa
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Bạn có chắc chắn muốn xóa?")
                        .setConfirmText("Xóa")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                //xóa
                                waterbottleList.remove(position);
                                new DatabaseWaterbottleHelper(context).deleteData(waterbottle.getId());
                                notifyDataSetChanged();
                            }
                        })
                        .setCancelButton("Hủy", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return waterbottleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvAddress,tvDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
