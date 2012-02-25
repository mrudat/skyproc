package skyproc;

import levgui.ProgressBar;

/**
 * A boundary class that will eventually offer an interface to the the LevGUI library.
 * @author Justin Swanson
 */
class SPGuiPortal {

    static ProgressBar progress = new ProgressBarPlaceholder();

    static class ProgressBarPlaceholder implements ProgressBar {

        @Override
        public void setMax(int i) {
        }

        @Override
        public void incrementBar() {
        }

        @Override
        public void reset() {
        }

        @Override
        public void setBar(int i) {
        }

        @Override
        public void setMax(int i, String string) {
        }

        @Override
        public void setStatus(String string) {
        }
    }
}
