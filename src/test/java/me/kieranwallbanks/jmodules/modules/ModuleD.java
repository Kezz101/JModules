package me.kieranwallbanks.jmodules.modules;

import me.kieranwallbanks.jmodules.Module;

public class ModuleD implements Module {

    @Override
    public void onEnable() {
        System.out.println("Enabled ModuleD");
    }

    @Override
    public void onDisable() {
        System.out.println("Disabled ModuleD");
    }

    @Override
    public String[] getDependencies() {
        return new String[]{"ModuleB"};
    }
}
