package api_test.abdelfaical.com.apitest;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.List;

/**
 * Created by AbdelFaical on 22/11/2017.
 */

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.ItemHolder> {

    private Context context;
    private List<UserModel> userModels;
    private OnRequestToDeleteItem onRequestToDeleteItem;
    private Typeface typeface;

    public DataListAdapter(Context context, List<UserModel> userModels) {
        this.context = context;
        this.userModels = userModels;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        final UserModel userModel = userModels.get(position);
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Comfortaa-Regular.ttf");
        holder.tvName.setTypeface(typeface);
        holder.tvFunction.setTypeface(typeface);
        holder.btnDelete.setTypeface(typeface);

        holder.tvName.setText(userModel.getUserFirstname().concat(" ".concat(userModel.getUserSurname())));
        holder.tvFunction.setText(userModel.getUserFunction());

        holder.mrlDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRequestToDeleteItem != null){
                    onRequestToDeleteItem.onDeleteItem(userModel.getUserId());
                }
            }
        });
    }

    interface OnRequestToDeleteItem{
        void onDeleteItem(int itemId);
    }

    public void setOnRequestToDeleteItem(OnRequestToDeleteItem onRequestToDeleteItem) {
        this.onRequestToDeleteItem = onRequestToDeleteItem;
    }

    @Override
    public int getItemCount() {
        if (userModels.size() > 0)
            return userModels.size();
        return 0;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvFunction;
        private Button btnDelete;
        private MaterialRippleLayout mrlDelete;

        public ItemHolder(View itemView) {
            super(itemView);

            tvName = (TextView)itemView.findViewById(R.id.tvName);
            tvFunction = (TextView)itemView.findViewById(R.id.tvFunction);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            mrlDelete = (MaterialRippleLayout)itemView.findViewById(R.id.mrlDelete);
        }
    }
}
