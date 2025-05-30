/* Player behavior strategy for hitting or standing */
public class PlayerBehavior implements HandBehavior {

    @Override
    public boolean shouldHit(Hand hand) {
        // User input, so return false by default
        return false;
    }
}
