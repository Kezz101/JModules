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
     * @return the name of this module
     */
    default String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * Gets an array containing other modules this module depends on
     * @return an array of the modules
     */
    default String[] getDependencies() {
        return null;
    }
}
