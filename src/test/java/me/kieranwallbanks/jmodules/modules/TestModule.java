package me.kieranwallbanks.jmodules.modules;

import me.kieranwallbanks.jmodules.Module;
import me.kieranwallbanks.jmodules.ModuleTest;

public class TestModule implements Module {

    @Override
    public void onEnable() {
        ModuleTest.enabled = true;

        System.out.println("TestModule enabled");
    }

    @Override
    public void onDisable() {
        System.out.println("TestModule disabled");
    }

}
