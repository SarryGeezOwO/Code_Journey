public class Main {
    public static void main(String[] args) {

        /*
            The Strategy Pattern is a design approach where you can create a bunch of different algorithms,
             each in its own class, and easily swap them out whenever you need to.

             Default Strategy <- This is the interface class that will be template for all patterns
             Strategy Pattern <- A Class that implements the Default strategy to create a unique functionality
             Context Class    <- This Class handles the Type of Pattern to be used, and calls the actions of the pattern
         */

        // The context class can now change Hardware type on any time!
        HardwareDriver driver = new HardwareDriver();

        driver.setHardware(new Patterns.Keyboard());
        driver.performAction();

        System.out.println("--------------------------------------------------");

        driver.setHardware(new Patterns.Mouse());
        driver.performAction();
    }
}