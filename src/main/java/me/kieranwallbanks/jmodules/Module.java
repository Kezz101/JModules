package me.kieranwallbanks.jmodules;

/**
 * Represents a module
 */
public interface Module {

    /**
     * Called when the module is enabled
     */
    void onEnable();

    /**
     * Called when the module is disabled
     */
    void onDisable();

    /**
     * Gets the name of the module. Defaults to {@link Class#getSimpleName()}.
     */
    default String getName() {
        return null;
    }
}
