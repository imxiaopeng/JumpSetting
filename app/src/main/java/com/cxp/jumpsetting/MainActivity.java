package com.cxp.jumpsetting;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<PackageInfo> installedPackages;
    private List<AppItem> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ProgressBar pb_bar = (ProgressBar) findViewById(R.id.pb_bar);
        final ListView lv = (ListView) findViewById(R.id.lv);
        new Thread(new Runnable() {
            @Override
            public void run() {
                installedPackages = getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
                GetAppInfo getAppInfo = new GetAppInfo(MainActivity.this, installedPackages);
                apps = getAppInfo.getApps();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb_bar.setVisibility(View.GONE);
                        lv.setVisibility(View.VISIBLE);
                        lv.setAdapter(new Adapter());
                    }
                });
            }
        }).start();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package", apps.get(position).getPackageName(), null));
                startActivity(intent);
            }
        });

    }

    class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return apps.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AppItem appItem = apps.get(position);
            VH vh = null;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.item_lv, null);
                vh = new VH(convertView);
                convertView.setTag(vh);
            } else {
                vh = (VH) convertView.getTag();
            }
            vh.iv.setImageDrawable(appItem.getIcon());
            vh.tv.setText(appItem.getAppName());
            return convertView;
        }
    }

    class VH {

        private final ImageView iv;
        private final TextView tv;

        public VH(View view) {
            iv = view.findViewById(R.id.iv);
            tv = view.findViewById(R.id.tv);
        }
    }
}
