ActorValue(actorValues, className) ::= <<
package skyproc.genenums;

/**
 * A list of values commonly used by Skyrim for various purposes. UNKNOWN
 * entries are simply placeholders.
 * 
 * @author Justin Swanson
 * @author Martin Rudat
 */
public enum <className> {

    UNKNOWN;

    /**
     *
     * @param in
     * @return
     */
    static public int value(<className> in) {
        if (in == UNKNOWN) {
            return -1;
        } else {
            return in.ordinal();
        }
    }

    /**
     *
     * @param in
     * @return
     */
    static public <className> value(int in) {
        if (in \< <className>.values().length - 2 && in >= 0) {
            return <className>.values()[in];
        } else {
            return UNKNOWN;
        }
    }

}
>>