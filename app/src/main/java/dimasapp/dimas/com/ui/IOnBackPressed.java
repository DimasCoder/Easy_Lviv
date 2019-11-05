package dimasapp.dimas.com.ui;

/**
 * Created by Dima on 01.11.2019.
 */
public interface IOnBackPressed {
    /**
     * If you return true the back press will not be taken into account, otherwise the activity will act naturally
     * @return true if your processing has priority if not false
     */
    boolean onBackPressed();
}