package io.ctzjj.pearlcpuswitch;

import android.widget.Toast;

import com.topjohnwu.superuser.Shell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CpuSwitch {

    private static final String HOTPLUG_PATH = "/sys/devices/system/cpu/{cpus}/hotplug/target";

    public static Boolean switchCpu(String cpu, Boolean turnOn) {
        String cpuHotplugPath = HOTPLUG_PATH.replace("{cpus}", cpu);
        String stat = turnOn ? "222" : "0";
        String cmd = "echo " + stat + " > " + cpuHotplugPath;
        Shell.Result result = Shell.cmd(cmd).exec();
        List<String> err = result.getErr();
        return err.size() == 0;
    }

    public static Map<String, Boolean> CpuStatus(Set<String> cpus) {
        Map<String, Boolean> cpuStatusMap = new HashMap<>();
        cpus.forEach((cpu) -> {
            String cpuHotplugPath = HOTPLUG_PATH.replace("{cpus}", cpu);
            Shell.Result result = Shell.cmd("cat " + cpuHotplugPath).exec();
            List<String> out = result.getOut();

            if (out.size() == 0) {
                cpuStatusMap.put(cpu, Boolean.TRUE);
                return;
            }

            String stat = out.get(0);
            cpuStatusMap.put(cpu, "222".equals(stat) ? Boolean.TRUE : Boolean.FALSE);
        });

        return cpuStatusMap;
    }


}
