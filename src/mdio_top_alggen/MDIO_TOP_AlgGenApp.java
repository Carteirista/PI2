/*
 * MDIO_TOP_AlgGenApp.java
 */

package mdio_top_alggen;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class MDIO_TOP_AlgGenApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new MDIO_TOP_AlgGenView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of MDIO_TOP_AlgGenApp
     */
    public static MDIO_TOP_AlgGenApp getApplication() {
        return Application.getInstance(MDIO_TOP_AlgGenApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(MDIO_TOP_AlgGenApp.class, args);
    }
}
