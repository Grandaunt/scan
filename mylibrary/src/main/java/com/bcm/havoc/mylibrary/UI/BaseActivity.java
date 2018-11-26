package com.bcm.havoc.mylibrary.UI;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bcm.havoc.mylibrary.Application.AppManager;
import com.bcm.havoc.mylibrary.Application.MyApplication;
import com.bcm.havoc.mylibrary.UI.Activity.TitleActivity;
import com.bcm.havoc.mylibrary.Utils.PermissionManager;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 文 件 名: BaseActivity
 * 创 建 人: Allen
 * 创建日期: 16/12/24 15:33
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
//     setHead_toolbar(true, "物料信息", false).setDarkTheme();
public class BaseActivity extends TitleActivity implements EasyPermissions.PermissionCallbacks{
    /**
     * 日志输出标志getSupportActionBar().
     **/
    private TextView title;
    private ImageView back;
    protected final String TAG = this.getClass().getSimpleName();
    private static int win_width;
    private static int win_height;
    //activity 的统一管理者
    public AppManager appManager;

    private LinearLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionManager permissionManager = new PermissionManager();
        permissionManager.checkBasePermission(this);
        //屏幕的尺寸
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        win_width = display.getWidth();
        win_height = display.getHeight();

        // 将当前Activity加入栈管理
        appManager = AppManager.getAppManager();
        appManager.addActivity(this);
        /*----------------------------------------------------------------------------------*/
        /*这段代码作用 见    http://blog.csdn.net/liangde123/article/details/70255520     */
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                appManager.finishActivity(this);
                return;
            }
        }
        /*强制竖屏*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        /*----------------------------------------------------------------------------------*/
        MyApplication.setContext(this);


//        // 经测试在代码里直接声明透明状态栏更有效
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//        // 这句很关键，注意是调用父类的方法
//        super.setContentView(R.layout.activity_base);
//        initToolbar();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MyApplication.setContext(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.setContext(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(LoadingDialogue.isShowing())
            LoadingDialogue.dismiss();
    }

    /**
     * 返回屏幕宽度
     *
     * @return
     */
    public static int getWin_width() {
        return win_width;
    }

    /**
     * 返回屏幕高度
     *
     * @return
     */
    public static int getWin_height() {
        return win_height;
    }
    /**
     * 重写onRequestPermissionsResult，用于接受请求结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将请求结果传递EasyPermission库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    /**
     * 请求权限成功
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        ToastUtils.showToast(getApplicationContext(), "用户授权成功");

    }
    /**
     * 请求权限失败
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        ToastUtils.showToast(getApplicationContext(), "用户授权失败");
        /**
         * 若是在权限弹窗中，用户勾选了'NEVER ASK AGAIN.'或者'不在提示'，且拒绝权限。
         * 这时候，需要跳转到设置界面去，让用户手动开启。
         */
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();

        }

    }

}


//import static com.bcm.havoc.wmapp.Base.DB.XDBManager.db;
//        import static com.bcm.havoc.wmapp.Base.DB.XDBManager.initDb;
//
//public class RkListActivity extends BaseActivity {
//    private RecyclerView mRecyclerView;
//    private BaseQuickAdapter adapter;
//    private List<Stock_IN>  mDataList;
//    private SwipeRefreshLayout mSwipeRefreshLayout;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_rk_list);
//        initDb();
//        initData();
//        initView();
//        initAdapter();
//    }
//
//    private void initData() {
//        try {
//            mDataList = db.selector(Stock_IN.class).where("State","<",5).findAll();
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void initView() {
//        setTitle("入库任务");
//        setBackBtn();
//        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
//        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refresh();
//            }
//        });
//
//    }
//
//    private void refresh() {
//        initData();
//        initView();
//        initAdapter();
//        mSwipeRefreshLayout.setRefreshing(false);
//
//    }
//
//    private void initAdapter() {
//        adapter= new MyAdapter(mDataList);
//        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN );//动画效果为缩放
////        动画默认只执行一次,如果想重复执行可设置
//        adapter.isFirstOnly(false);
////        adapter.addHeaderView(top);
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Intent intent = new Intent(RkListActivity.this, RkActivity.class);
//                intent.putExtra(RkActivity.intenttagrk, mDataList.get(position));
//                startActivity(intent);
//            }
//        });
//
//        mRecyclerView.setAdapter(adapter);
//    }
//
//    public class MyAdapter extends BaseQuickAdapter<Stock_IN, BaseViewHolder> {
//        public MyAdapter(List data) {
//            super(R.layout.item_text_view,data);
//        }
//
//        @Override
//        protected void convert(BaseViewHolder helper, Stock_IN item) {
////        helper.addOnClickListener(R.id.img).addOnClickListener(R.id.tweetName);
//
//            helper.setText(R.id.tv_supplier_name, item.getSupplierName());
//            helper.setText(R.id.tv_rk_no, item.getInNumber());
//            helper.setText(R.id.tv_ck_no, item.getCangKuId());
//            helper.setText(R.id.tv_rk_time, item.getStrInTime());
//
//
//            List<Stock_IN_Detail> stock_in_detail_list = new ArrayList<>();
//            try {
//                stock_in_detail_list = db.selector(Stock_IN_Detail.class).where("InNumber","=",item.getInNumber()).findAll();
//
//
//            } catch (DbException e) {
//                e.printStackTrace();
//            }
//            helper.setText(R.id.tv_item_num,stock_in_detail_list.size()+"");
//
//
//        }
//
//
//    }
//@Override
//public void onClick(View view) {
//    switch (view.getId()) {
//        case R.id.tv_new_start_time:
//
//            break;
//        case R.id.tv_new_end_time:
//
//            break;
//        case R.id.tv_new_furit_kind:
//
//            break;
////            case R.id.tv_new_area:
////                finish();
////                break;
////               default:
//                break;
//
//    }
//
//}
//    @Override
//    protected void onResume() {
//        refresh();
//        super.onResume();
//    }
//}
