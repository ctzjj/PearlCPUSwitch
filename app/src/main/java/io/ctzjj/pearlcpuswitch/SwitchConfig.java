package io.ctzjj.pearlcpuswitch;


import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;
import java.util.Set;

public class SwitchConfig {

    private Context context;

    public SwitchConfig setContext(Context context) {
        this.context = context;
        return this;
    }
    public void saveConfig(String key, List<String> cpus) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PearlCPUSwitch", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, String.join(",", cpus));
        editor.apply();
    }

    public String getConfig(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PearlCPUSwitch", MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

}
