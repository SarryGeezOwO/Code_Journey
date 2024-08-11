public class Patterns {

    // First Pattern
    public static class Keyboard implements Hardware {
        @Override
        public void onConnect() {
            System.out.println("Keyboard connected, typing functionality allowed");
        }

        @Override
        public void action() {
            System.out.println("Keyboard typed something!");
        }
    }

    // Second Pattern
    public static class Mouse implements Hardware {
        @Override
        public void onConnect() {
            System.out.println("Mouse connected, Cursor movement allowed");
        }

        @Override
        public void action() {
            System.out.println("Mouse moved to a location!");
        }
    }

}
