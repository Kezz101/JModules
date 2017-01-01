package me.kieranwallbanks.jmodules;

import me.kieranwallbanks.jmodules.util.ReflectionsUtilities;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;

/**
 * The main point of entry into the JModules library
 */
public class ModuleManager {
    private Map<Class<?>, ModuleInterfaceRunnable> runnableMap = new HashMap<>();
    private Map<String, Module> modules = new HashMap<>();
    private boolean enabled = false;
    private List<Module> loadOrder;

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
        if (!enabled) {
            reloadOrderList();

            for (Module module : loadOrder) {
                for (Class<?> interfacee : module.getClass().getInterfaces()) {
                    if (runnableMap.get(interfacee) != null) {
                        runnableMap.get(interfacee).run(module);
                    }
                }

                if (runnableMap.get(module.getClass().getSuperclass()) != null) {
                    runnableMap.get(module.getClass().getSuperclass()).run(module);
                }

                module.onEnable();
            }

            enabled = true;
        }
    }

    /**
     * Calls {@link Module#onDisable()} for all modules
     */
    public void disableModules() {
        if (enabled) {
            Collections.reverse(loadOrder);

            for (Module module : loadOrder) {
                module.onDisable();
            }
        }
    }

    /**
     * Adds a module to the {@link ModuleManager}
     *
     * @param module the module
     */
    public void addModule(Module module) {
        if (!enabled) {
            modules.put(module.getName(), module);
        }
    }

    /**
     * Adds several modules to the {@link ModuleManager}
     *
     * @param modules a {@link Collection} of modules
     */
    public void addModules(Collection<Module> modules) {
        for (Module module : modules) {
            addModule(module);
        }
    }

    /**
     * Adds all modules from a package using {@link ReflectionsUtilities#getSubtypesOf(Class, String, ClassLoader, Class...)}
     *
     * @param packageName the name of the package
     * @param classLoader the {@link ClassLoader} to find the package in
     * @param ignores a list of classes to ignore if they are found
     *
     * @throws Exception if an error occurred during the lookup of these classes
     */
    public void addModulesFromPackage(String packageName, ClassLoader classLoader, Class<?>... ignores) throws Exception {
        for(Class<? extends Module> clazz : ReflectionsUtilities.getSubtypesOf(Module.class, packageName, classLoader, ignores)) {
            try {
                Module module = clazz.newInstance();
                addModule(module);
            } catch(InstantiationException ignored) { } // We'll just ignore this error
        }
    }

    /**
     * Gets an unmodifiable collection of all modules currently loaded
     * @return the collection of modules
     */
    public Collection<Module> getModules() {
        return Collections.unmodifiableCollection(modules.values());
    }

    /**
     * Checks if the modules are enabled yet
     * @return {@code true} if the modules are enabled
     */
    public boolean areModulesEnabled() {
        return enabled;
    }

    private void reloadOrderList() {
        DirectedAcyclicGraph<Module, DefaultEdge> dag = new DirectedAcyclicGraph<>(DefaultEdge.class);

        // add all the modules to the dag
        for (Module module : modules.values()) {
            dag.addVertex(module);
        }

        // add all the dependencies to the dag
        for (Module module : modules.values()) {
            if (module.getDependencies() != null) {
                for (String dependsOn : module.getDependencies()) {
                    if (modules.containsKey(dependsOn)) {
                        dag.addEdge(modules.get(dependsOn), module);
                    }
                }
            }
        }

        // setup the order list
        loadOrder = new ArrayList<>(dag.vertexSet().size());
        dag.forEach(loadOrder::add);
    }
}
