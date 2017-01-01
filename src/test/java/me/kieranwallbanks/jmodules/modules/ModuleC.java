package me.kieranwallbanks.jmodules.modules;

import me.kieranwallbanks.jmodules.Module;

public class ModuleC implements Module {

    @Override
    public void onEnable() {
        System.out.println("Enabled ModuleC");
    }

    @Override
    public void onDisable() {
        System.out.println("Disabled ModuleC");
    }

    @Override
    public String[] getDependencies() {
        return new String[]{"ModuleA", "ModuleB"};
    }
}
