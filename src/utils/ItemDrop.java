package utils;

import item.*;

public class ItemDrop {
    public static Item dropRandomItem() {
        // Define probabilities
        double[] probabilities = {0.05, 0.05, 0.05, 0.10, 0.12, 0.13};

        // Generate a random number between 0 and 1
        double rand = Math.random();

        // Accumulator for probabilities
        double cumulativeProbability = 0;

        // Iterate through item types
        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            // Check if the random number falls within this item's probability range
            if (rand <= cumulativeProbability) {
                if (i == 0) return new ImmortalItem();
                if (i == 1) return new GhostItem();
                if (i == 2) return new HealPotion();
                if (i == 3) return new FeatherBootsItem();
                if (i == 4) return new StrongerBombItem();
                return new MoreBombItem();
            }
        }

        // Return null if no item type is chosen (shouldn't happen)
        return null;
    }
}
