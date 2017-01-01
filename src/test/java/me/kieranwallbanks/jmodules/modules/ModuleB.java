package me.kieranwallbanks.jmodules.modules;

import me.kieranwallbanks.jmodules.Module;

public class ModuleB implements Module {

    @Override
    public void onEnable() {
        System.out.println("Enabled ModuleB");
    }

    @Override
    public void onDisable() {
        System.out.println("Disabled ModuleB");
    }

    @Override
    public String[] getDependencies() {
        return new String[]{"ModuleA"};
    }
}
