package me.kieranwallbanks.jmodules;

/**
 * Represents a {@link Runnable} to be run when the {@link ModuleManager} finds a module that extends or implements a class
 */
public abstract class ModuleInterfaceRunnable {
    private Class<?> interfaceClass;

    /**
     * Constructs a new {@link ModuleInterfaceRunnable}
     *
     * @param interfaceClass the class to look for
     */
    public ModuleInterfaceRunnable(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    /**
     * Gets the class that the {@link ModuleManager} should look for
     *
     * @return the class
     */
    public Class<?> getSearchClass() {
        return interfaceClass;
    }

    /**
     * The runnable to be run when the {@link ModuleManager} finds a module that extends or implements the class
     *
     * @param module the module that the ModuleManager found
     */
    public abstract void run(Module module);

}
