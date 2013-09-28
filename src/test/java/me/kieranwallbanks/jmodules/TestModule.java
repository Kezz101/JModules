package me.kieranwallbanks.jmodules;

public class TestModule implements Module {

    @Override
    public void onEnable() {
        ModuleTests.enabled = true;

        System.out.println("TestModule enabled");
    }

    @Override
    public void onDisable() {
        System.out.println("TestModule disabled");
    }

}
