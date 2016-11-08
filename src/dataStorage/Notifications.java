package dataStorage;

import java.util.Observable;

/**
 * An observable class which is used, at this point, exclusively for letting the front end know when it
 * needs to perform a specific action. It can tell the front end to clear its turtle screen.
 */

public class Notifications extends Observable {
    private static final boolean TRUE = true;
    private static final boolean FALSE = false;

    private Boolean myClearScreenFlag;

    private void updateAndCallObserver () {
        setChanged();
        notifyObservers(myClearScreenFlag);
    }

    public void setClearScreenFlag () {
        ////System.out.println("set clear screene flag");
        myClearScreenFlag = TRUE;
        updateAndCallObserver();
        myClearScreenFlag = FALSE;
    }
}
