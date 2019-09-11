package ua.hillel.java.elementary1.objects.api.commands;

import java.util.Arrays;

/**
 * Command responsible for type and arguments.
 */
// In OOP languages object is main structure. OOP languages operates with objects.
// Class is language structure defining data, properties and behaviour of objects.
// Objects are instances of the classes.
// Class can be considered as 'template' or 'baking form' for objects.
public class Command {
    // Each file in java (except package-info, module-info)  should contains main level class with same
    // name as file.
    // Classes should be (this is style limitation not language) started from capital letter and name should
    // not contains anything except alphanumerical letters.
    // Java support any unicode letter in class name, but only alphanumerical preferable.
    // Classes are organized in packages. You can threat them as directories (they actually are in OS).
    // Class with package name called - fully qualified class name.
    //
    // Class usually describes some real-world abstraction. For example Command in our case describe command
    // came for execution. class Point will describe 2D point on the surface.
    //
    //
    // Class is holder for two main characteristics:
    // 1. Data - variables which bounded to concrete objects and use memory.
    // 2. Methods (including constructors) - program interface for manipulation of the data.
    //
    // Together 1 and 2 called 'incapsulation'.
    // Lots of people threat incapsulation as 'hidding' which is not correct (or just partially correct).
    // More detailed explanation : incapsulation helps group all related data and properties under same class abstraction
    // and provide interface for manipulation of it.
    //
    // Incapsulation provided mechanisms for 'hiding' data inside the class called visibility levels
    // In java they are
    // 1. private - data (or method) is visible only within current class.
    // 2. protected - data (or method) is visible in current class and all inheritors.
    // 3. default - data (or method) is visible in current class and package
    // 4. public - data (or method) is visible everywhere.
    //
    // To define data in the class should write: '[visibility level] class variable name'. When visibility level is not set this means - default.
    private String name;
    private Object[] parameters;

    // In additional to data in class existed some interface for data manipulation
    // 1. Methods
    // 2. Constructor
    //
    // Method is code construction which can aggregate batch of code related to class and manipulate
    // data in (or out) the class.
    //
    // Specific methods are called constructors and called when object is created.
    // Constructor is called always (in normal flow). When there are no constructor in the class default constructor
    // called (constructor without parameters).
    //
    // Methods structure  is
    // [visibility-method] return-type method-name ([list of parameters with types separated by comma])
    // In java signature is : method-name ([list of parameters with types separated by comma])
    public Command(String name, Object[] parameters) {
        // Specific keyword 'this' in Java objects corresponding to reference to current instance of the class.
        this.name = name;
        this.parameters = parameters;
    }

    // it is possible to create method with same name, but different parameters. This is called overloading.
    // Same applied to constructors.
    public Command(String name) {
        this(name, new Object[]{});
    }

    public String getName() {
        return name;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public Command addParam(Object param) {
        if (param == null) {
            return this;
        }
        Object[] prms = Arrays.copyOf(parameters, parameters.length + 1);
        prms[parameters.length] = param;
        return new Command(name, prms);
    }

    public static Command createFromArgs(String[] args) {
        if (args == null || args.length == 0) {
            return null;
        }
        String name = args[0];
        Object[] params = new Object[args.length - 1];
        System.arraycopy(args, 1, params, 0, params.length);
        return new Command(name, params);
    }

}
