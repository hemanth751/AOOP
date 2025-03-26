import java.util.*;

interface Bidder {
    void update(String itemName, double newBid);
}

class AuctionBidder implements Bidder {
    private final String name;

    public AuctionBidder(String name) {
        this.name = name;
    }

    @Override
    public void update(String itemName, double newBid) {
        System.out.println(name + " has been notified: New bid of $" + newBid + " placed on " + itemName);
    }
}

class AuctionItem {
    private final String itemName;
    private double highestBid;
    private final List<Bidder> bidders = new ArrayList<>();

    public AuctionItem(String itemName) {
        this.itemName = itemName;
        this.highestBid = 0;
    }

    public void registerBidder(Bidder bidder) {
        bidders.add(bidder);
    }

    public void removeBidder(Bidder bidder) {
        bidders.remove(bidder);
    }

    public void placeBid(double bidAmount) {
        if (bidAmount > highestBid) {
            highestBid = bidAmount;
            notifyBidders();
        } else {
            System.out.println("Bid of $" + bidAmount + " is too low for " + itemName);
        }
    }

    private void notifyBidders() {
        for (Bidder bidder : bidders) {
            bidder.update(itemName, highestBid);
        }
    }
}

abstract class Auction {
    protected AuctionItem auctionItem;

    public Auction(AuctionItem auctionItem) {
        this.auctionItem = auctionItem;
    }

    public final void startAuction() {
        announceAuction();
        conductBidding();
        concludeAuction();
    }

    protected void announceAuction() {
        System.out.println("Auction for " + auctionItem + " has started!");
    }

    protected abstract void conductBidding();

    protected void concludeAuction() {
        System.out.println("Auction ended.");
    }
}

class StandardAuction extends Auction {
    private final Scanner scanner = new Scanner(System.in);

    public StandardAuction(AuctionItem auctionItem) {
        super(auctionItem);
    }

    @Override
    protected void conductBidding() {
        System.out.println("Enter bid amounts (type '0' to end bidding):");
        while (true) {
            System.out.print("Enter bid: ");
            double bid = scanner.nextDouble();
            if (bid == 0) break;
            auctionItem.placeBid(bid);
        }
    }
}

public class AuctionSystem {
    public static void main(String[] args) {
        AuctionItem item = new AuctionItem("Antique Vase");

        Bidder bidder1 = new AuctionBidder("Alice");
        Bidder bidder2 = new AuctionBidder("Bob");
        
        item.registerBidder(bidder1);
        item.registerBidder(bidder2);

        StandardAuction auction = new StandardAuction(item);
        auction.startAuction();
    }
}