package io.ctzjj.pearlcpuswitch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity  extends Activity {

    private final Map<String, CheckBox> lightCpuCheckBoxMap = new HashMap<>();

    private final Map<String, CheckBox> darkCpuCheckBoxMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        String packageName = getPackageName();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (!pm.isIgnoringBatteryOptimizations(packageName)) {
            intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + packageName));
            startActivity(intent);
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);

        ScreenReceiver receiver = new ScreenReceiver();
        registerReceiver(receiver, filter);

        initLightCpuCheckBox();

        initDarkCpuCheckBox();



    }

    @Override
    protected void onResume() {
        super.onResume();

        restoreCheckbox(lightCpuCheckBoxMap, "lightCpus");

        restoreCheckbox(darkCpuCheckBoxMap, "darkCpus");
    }

    private void restoreCheckbox(Map<String, CheckBox> dataMap, String key) {
        SwitchConfig switchConfig = new SwitchConfig().setContext(this);
        String lightCpus = switchConfig.getConfig(key);
        if (null == lightCpus) {
            return;
        }
        List<String> list = Arrays.asList(lightCpus.split(","));
        dataMap.keySet().forEach((cpu) -> {
            CheckBox checkBox = dataMap.get(cpu);
            if (null == checkBox) {
                return;
            }

            if ("darkCpus".equals(key)) {
                if (list.contains(cpu)) {
                    checkBox.setChecked(Boolean.FALSE);
                } else {
                    checkBox.setChecked(Boolean.TRUE);
                }
            } else {
                if (list.contains(cpu)) {
                    checkBox.setChecked(Boolean.TRUE);
                } else {
                    checkBox.setChecked(Boolean.FALSE);
                }
            }


        });
    }

    private void initLightCpuCheckBox() {
        lightCpuCheckBoxMap.put("cpu0", (CheckBox) findViewById(R.id.cpu0l));
        lightCpuCheckBoxMap.put("cpu1", (CheckBox) findViewById(R.id.cpu1l));
        lightCpuCheckBoxMap.put("cpu2", (CheckBox) findViewById(R.id.cpu2l));
        lightCpuCheckBoxMap.put("cpu3", (CheckBox) findViewById(R.id.cpu3l));
        lightCpuCheckBoxMap.put("cpu4", (CheckBox) findViewById(R.id.cpu4l));
        lightCpuCheckBoxMap.put("cpu5", (CheckBox) findViewById(R.id.cpu5l));
        lightCpuCheckBoxMap.put("cpu6", (CheckBox) findViewById(R.id.cpu6l));
        lightCpuCheckBoxMap.put("cpu7", (CheckBox) findViewById(R.id.cpu7l));

        lightCpuCheckBoxMap.keySet().forEach((key) -> {
            CheckBox checkBox = lightCpuCheckBoxMap.get(key);
            if (null == checkBox) {
                return;
            }
            checkBox.setOnCheckedChangeListener((compoundButton, checked) -> {
                saveConfig(lightCpuCheckBoxMap, "lightCpus");
                Boolean aBoolean = CpuSwitch.switchCpu(key, checked);
                if (Boolean.TRUE.equals(aBoolean)) {
                    Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "操作失败", Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void initDarkCpuCheckBox() {
        darkCpuCheckBoxMap.put("cpu0", (CheckBox) findViewById(R.id.cpu0d));
        darkCpuCheckBoxMap.put("cpu1", (CheckBox) findViewById(R.id.cpu1d));
        darkCpuCheckBoxMap.put("cpu2", (CheckBox) findViewById(R.id.cpu2d));
        darkCpuCheckBoxMap.put("cpu3", (CheckBox) findViewById(R.id.cpu3d));
        darkCpuCheckBoxMap.put("cpu4", (CheckBox) findViewById(R.id.cpu4d));
        darkCpuCheckBoxMap.put("cpu5", (CheckBox) findViewById(R.id.cpu5d));
        darkCpuCheckBoxMap.put("cpu6", (CheckBox) findViewById(R.id.cpu6d));
        darkCpuCheckBoxMap.put("cpu7", (CheckBox) findViewById(R.id.cpu7d));

        darkCpuCheckBoxMap.keySet().forEach((key) -> {
            CheckBox checkBox = darkCpuCheckBoxMap.get(key);
            if (null == checkBox) {
                return;
            }
            checkBox.setOnCheckedChangeListener((compoundButton, checked) -> {
                saveConfig(darkCpuCheckBoxMap, "darkCpus");
            });
        });
    }

    private void saveConfig(Map<String, CheckBox> dataMap, String key) {
        List<String> cpus = new ArrayList<>();
        dataMap.keySet().forEach((k) -> {
            CheckBox checkBox = dataMap.get(k);
            if (null == checkBox) {
                return;
            }
            boolean checked = checkBox.isChecked();
            if ("lightCpus".equals(key) && checked) {
                cpus.add(k);
            }
            if ("darkCpus".equals(key) && !checked) {
                cpus.add(k);
            }
        });
        new SwitchConfig().setContext(this).saveConfig(key, cpus);
    }
}
