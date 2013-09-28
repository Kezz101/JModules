package me.kieranwallbanks.jmodules;

import me.kieranwallbanks.jmodules.util.ReflectionsUtilities;

import java.util.*;

/**
 * The main point of entry into the JModules library
 */
public class ModuleManager {
    private Map<Class<?>, ModuleInterfaceRunnable> runnableMap = new HashMap<>();
    private Set<Module> modules = new HashSet<>();

    /**
     * Constructs a new {@link ModuleManager}
     *
     * @param runnables any amount of {@link ModuleInterfaceRunnable}s
     */
    public ModuleManager(ModuleInterfaceRunnable... runnables) {
        for(ModuleInterfaceRunnable runnable : runnables) {
            runnableMap.put(runnable.getSearchClass(), runnable);
        }
    }

    /**
     * Calls {@link Module#onEnable()} for all modules and runs any {@link ModuleInterfaceRunnable}s if needed
     */
    public void enableModules() {
        for(Module module : modules) {
            for(Class<?> interfacee : module.getClass().getInterfaces()) {
                if(runnableMap.get(interfacee) != null) {
                    runnableMap.get(interfacee).run(module);
                }
            }

            if(runnableMap.get(module.getClass().getSuperclass()) != null) {
                runnableMap.get(module.getClass().getSuperclass()).run(module);
            }

            module.onEnable();
        }
    }

    /**
     * Calls {@link Module#onDisable()()} for all modules
     */
    public void disableModules() {
        for(Module module : modules) {
            module.onDisable();
        }
    }

    /**
     * Adds a module to the {@link ModuleManager}
     *
     * @param module the module
     */
    public void addModule(Module module) {
        modules.add(module);
    }

    /**
     * Adds several modules to the {@link ModuleManager}
     *
     * @param modules a {@link Collection} of modules
     */
    public void addModules(Collection<Module> modules) {
        this.modules.addAll(modules);
    }

    /**
     * Adds all modules from a package using {@link ReflectionsUtilities#getSubtypesOf(Class, String, ClassLoader, Class...)}
     *
     * @param packageName the name of the package
     * @param classLoader the {@link ClassLoader} to find the package in
     *
     * @throws Exception if an error occurred during the lookup of these classes
     */
    public void addModulesFromPackage(String packageName, ClassLoader classLoader, Class<?>... ignores) throws Exception {
        for(Class<? extends Module> clazz : ReflectionsUtilities.getSubtypesOf(Module.class, packageName, classLoader, ignores)) {
            try {
                modules.add(clazz.newInstance());
            } catch(InstantiationException ignored) { } // We'll just ignore this error
        }
    }

}
