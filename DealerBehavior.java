/* Dealer behavior strategy for hitting or standing */
public class DealerBehavior implements HandBehavior {

    @Override
    public boolean shouldHit(Hand hand) {
        return hand.getTotal() < 17;
    }
}
