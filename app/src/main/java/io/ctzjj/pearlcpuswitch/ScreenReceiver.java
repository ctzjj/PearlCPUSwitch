package io.ctzjj.pearlcpuswitch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Objects;

public class ScreenReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), Intent.ACTION_SCREEN_ON)) {
            // 亮屏时的处理逻辑
            for (int i = 0; i <= 5; i++) {
                Boolean aBoolean = CpuSwitch.switchCpu("cpu" + i, Boolean.TRUE);
                Log.i("PearlCpusWitch", "Screen On Open Cpu" + i + "====>" + aBoolean);
            }
        } else if (Objects.equals(intent.getAction(), Intent.ACTION_SCREEN_OFF)) {
            // 熄屏时的处理逻辑
            for (int i = 2; i <= 7; i++) {
                Boolean aBoolean = CpuSwitch.switchCpu("cpu" + i, Boolean.FALSE);
                Log.i("PearlCpusWitch", "Screen Off Open Cpu" + i + "====>" + aBoolean);
            }
        }
    }

}
