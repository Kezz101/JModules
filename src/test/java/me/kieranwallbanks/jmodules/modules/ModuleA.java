package me.kieranwallbanks.jmodules.modules;

import me.kieranwallbanks.jmodules.Module;

public class ModuleA implements Module {

    @Override
    public void onEnable() {
        System.out.println("Enabled ModuleA");
    }

    @Override
    public void onDisable() {
        System.out.println("Disabled ModuleA");
    }
}
