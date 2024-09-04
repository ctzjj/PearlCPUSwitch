package io.ctzjj.pearlcpuswitch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ScreenReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), Intent.ACTION_SCREEN_ON)) {
            // 亮屏时的处理逻辑
            String lightCpus = new SwitchConfig().setContext(context).getConfig("lightCpus");
            if (null == lightCpus) {
                return;
            }
            List<String> list = Arrays.asList(lightCpus.split(","));
            list.forEach((cpu) -> {
                Boolean aBoolean = CpuSwitch.switchCpu(cpu, Boolean.TRUE);
                Log.i("PearlCpusWitch", "Screen On Open " + cpu + "====>" + aBoolean);
            });
        } else if (Objects.equals(intent.getAction(), Intent.ACTION_SCREEN_OFF)) {
            // 熄屏时的处理逻辑
            String lightCpus = new SwitchConfig().setContext(context).getConfig("darkCpus");
            if (null == lightCpus) {
                return;
            }
            List<String> list = Arrays.asList(lightCpus.split(","));
            list.forEach((cpu) -> {
                Boolean aBoolean = CpuSwitch.switchCpu(cpu, Boolean.FALSE);
                Log.i("PearlCpusWitch", "Screen Off Open " + cpu + "====>" + aBoolean);
            });
        }
    }

}
