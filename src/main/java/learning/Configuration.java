package learning;

public class Configuration {

    // Learning iterations count
    public static final int EPISODE_COUNT = 750000;

    // Learning rate:
    // - 0: agent do not learn
    // - 1: agent use only latest information
    public static final double ALPHA = 0.8;

    // Discount factor:
    // - 0: highest actual rewards
    // - 1: long term reward increase
    public static final double GAMMA = 0.8;
    // Exploration factor:
    // - the smaller value the greater knowledge usage
    // - the greater value the greater exploration
    public static final double EPSILON = 0.5;

    // Changing epsilon after every epsilon, factor must be < 1.0
    public static final boolean CHANGE_EPSILON = false;
    public static final double EPSILON_CHANGE_FACTOR = 0.99999;

}
