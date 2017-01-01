package me.kieranwallbanks.jmodules.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Reflection-based utilities
 */
public class ReflectionsUtilities {

    /**
     * Gets a list containing the fully qualified names of the all classes in a package
     *
     * @param packageName the name of the package
     * @param classLoader the {@link ClassLoader} to find the package in
     *
     * @return a list containing the names of all classes in a package
     *
     * @throws IOException if an error occurred whilst getting the package information
     */
    public static List<String> getClassNamesFromPackage(String packageName, ClassLoader classLoader) throws IOException {
        URL packageURL;
        ArrayList<String> names = new ArrayList<>();

        packageName = packageName.replace(".", "/");
        packageURL = classLoader.getResource(packageName);

        if(packageURL != null && packageURL.getProtocol().equals("jar")) { // Loop through all things in the jar
            String jarFileName;
            JarFile jf ;
            Enumeration<JarEntry> jarEntries;
            String entryName;

            jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
            jarFileName = jarFileName.substring(5,jarFileName.indexOf("!"));
            jf = new JarFile(jarFileName);
            jarEntries = jf.entries();
            while(jarEntries.hasMoreElements()) { // Get the jar file and loop through everything in it
                entryName = jarEntries.nextElement().getName();
                if(entryName.startsWith(packageName) && entryName.endsWith(".class") && entryName.length() > packageName.length() + 5){
                    names.add(entryName.replace(".class", "").replace('/', '.')); // Put the stuff back again
                }
            }
            jf.close();
        } else if(packageURL != null) { // Otherwise loop through all things in the classpath
            File folder = new File(packageURL.getFile());
            File[] content = folder.listFiles();
            String entryName;
            if (content != null) {
                for(File file : content) {
                    entryName = file.getName();
                    names.add(entryName.replace(".class", "").replace('/', '.'));
                }
            }
        }

        return names;
    }

    /**
     * Gets all subtypes of a specified class in a package
     *
     * @param <T> type of class to get subtypes of
     * @param clazz the class to find subtypes of
     * @param packagee the package to search in
     * @param classLoader the {@link ClassLoader} to find the package in
     * @param ignore classes to ignore
     * @return a list containing all subtypes of the specified class
     * @throws Exception if an error occurred during the lookup of these classes
     */
    public static <T> List<Class<? extends T>> getSubtypesOf(Class<T> clazz, String packagee, ClassLoader classLoader, Class<?>... ignore) throws Exception {
        List<Class<? extends T>> returnMe = new ArrayList<>();
        List<Class<?>> ignores = new ArrayList<>();
        ignores.addAll(Arrays.asList(ignore));

        for(String str : getClassNamesFromPackage(packagee, classLoader)) {
            Class<?> indi = Class.forName(str.replace(".class", ""));
            if(clazz.isAssignableFrom(indi) && !ignores.contains(indi)) {
                @SuppressWarnings("unchecked cast") Class<? extends T> extended = (Class<? extends T>) indi;
                returnMe.add(extended);
            }
        }
        return returnMe;
    }

    /**
     * Adds a file to a classpath
     *
     * @param classPath the classpath
     * @param file the file
     * @throws Exception if an error occurred while adding the class to the classpath
     */
    public static void addFileToClasspath(ClassLoader classPath, File file) throws Exception {
        Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        method.setAccessible(true);
        method.invoke(classPath, file.toURI().toURL());
    }

    /**
     * Adds a file to the system's classpath
     *
     * @param file the file
     * @throws Exception if an error occurred while adding the class to the classpath
     */
    public static void addFileToClasspath(File file) throws Exception {
        addFileToClasspath(ClassLoader.getSystemClassLoader(), file);
    }
}
