// Context Class
public class HardwareDriver {

    // This setter is used to change Pattern type anytime
    // You can also add a Getter() but im too lazy
    private Hardware hardware;
    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }

    // Calls the Functions of the Base/Default Strategy with a specified pattern
    public void performAction() {
        hardware.onConnect();
        hardware.action();
    }
}
