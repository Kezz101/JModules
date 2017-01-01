# JModules

[![Current Version](https://img.shields.io/github/release/Kezz101/JModules.svg)](https://github.com/Kezz101/JModules/releases)
[![Build Status](https://img.shields.io/travis/Kezz101/JModules/master.svg)](https://travis-ci.org/Kezz101/JModules)
[![Total Downloads](https://img.shields.io/github/downloads/Kezz101/JModules/total.svg)](https://github.com/Kezz101/JModules/releases)
[![Issues](https://img.shields.io/github/issues/Kezz101/JModules.svg)](https://github.com/Kezz101/JModules/issues)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE.md)


A simple module framework for Java

## Introduction
JModules is a simple Java framework that makes creating additional modules easy. All you have to do is implement a class
and let JModules do the rest.

## How to use JModules
Using JModules in your project is as easy as 1, 2, 3.

1. Implement the `Module` class
2. Create a `ModuleManager`
3. Add some packages

Then you can access, enable or disable modules to your hearts content!

### Implementing the `Module` class
Each module is represented by a simple interface, meaning implementing it is super simple. 

````java
public class MyModule implements Module {
    
    @Override
    public void onEnable() {
        // do some enabling
    }
    
    @Override
    public void onDisable() {
        // do some disabling
    }
}
````

### Creating a `ModuleManager`
The `ModuleManager` class is the main class used in JModules. Firstly, you need to create your own instance of the `ModuleManager` like so:

````java
ModuleManager myManager = new ModuleManager();
````

You can also add any amount of runnables to be executed whenever a module is added. This can be used to store each module
or register them in some other external system.

Each `ModuleInterfaceRunnable` is added to the `ModuleManager` during instantiation.

````java
ModuleManager myManager = new ModuleManager(new ModuleInterfaceRunnable(MyInterface.class) {
    
    @Override
    public void run(Module module) {
        // do some more things with the module
    }
});
````

### Adding the modules
JModules works by searching a package and adding any `Module`s found to the `ModuleManager`. This is done by supplying
the `ModuleManager` with a package to search, a `ClassLoader` to search through. You can also ask the `ModuleManager` to
ignore a set of classes if you like.

````java
myManager.addModulesFromPackage("com.mypackage", System.classLoader(), IgnoreMe.class);
````

You can also add modules by yourself using the `addModule` method.

### Finishing up
Once you are done you can enable and disable modules using the methods supplied in the `ModuleManager`.

## Final bits
Thanks for using JModules! I originally created this because I am lazy and couldn't be bothered to implement something
like this for each project I would use it in, but I decided to upload it to the world in the hopes someone else would
find it useful!

If you need any help or have any issues or suggestions, please let me know!
