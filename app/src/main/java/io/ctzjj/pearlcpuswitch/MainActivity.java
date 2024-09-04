package io.ctzjj.pearlcpuswitch;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity  extends Activity {

    private final Map<String, CheckBox> cpuCheckBoxMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cpuCheckBoxMap.put("cpu0", (CheckBox) findViewById(R.id.cpu0));
        cpuCheckBoxMap.put("cpu1", (CheckBox) findViewById(R.id.cpu1));
        cpuCheckBoxMap.put("cpu2", (CheckBox) findViewById(R.id.cpu2));
        cpuCheckBoxMap.put("cpu3", (CheckBox) findViewById(R.id.cpu3));
        cpuCheckBoxMap.put("cpu4", (CheckBox) findViewById(R.id.cpu4));
        cpuCheckBoxMap.put("cpu5", (CheckBox) findViewById(R.id.cpu5));
        cpuCheckBoxMap.put("cpu6", (CheckBox) findViewById(R.id.cpu6));
        cpuCheckBoxMap.put("cpu7", (CheckBox) findViewById(R.id.cpu7));

        cpuCheckBoxMap.keySet().forEach((key) -> {
            CheckBox checkBox = cpuCheckBoxMap.get(key);
            if (null == checkBox) {
                return;
            }
            checkBox.setOnCheckedChangeListener((compoundButton, checked) -> {
                Boolean aBoolean = CpuSwitch.switchCpu(key, checked);
                if (Boolean.TRUE.equals(aBoolean)) {
                    Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "操作失败", Toast.LENGTH_SHORT).show();
            });
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, Boolean> stringBooleanMap = CpuSwitch.CpuStatus(cpuCheckBoxMap.keySet());
        stringBooleanMap.keySet().forEach((cpu) -> {
            Boolean stat = stringBooleanMap.getOrDefault(cpu, true);
            CheckBox checkBox = cpuCheckBoxMap.get(cpu);
            if (null == checkBox) {
                return;
            }
            checkBox.setChecked(Boolean.TRUE.equals(stat));
        });
    }
}
