package learning;

public class Configuration {

    // Learning iterations count
    public static int EPISODE_COUNT = 750000;

    // Learning rate:
    // - 0: agent do not learn
    // - 1: agent use only latest information
    public static double ALPHA = 0.8;

    // Discount factor:
    // - 0: highest actual rewards
    // - 1: long term reward increase
    public static double GAMMA = 0.8;
    // Exploration factor:
    // - the smaller value the greater knowledge usage
    // - the greater value the greater exploration
    public static double EPSILON = 0.5;

    // Changing epsilon after every epsilon, factor must be < 1.0
    public static boolean CHANGE_EPSILON = false;
    public static double EPSILON_CHANGE_FACTOR = 0.99999;

    public static void set(double alpha, double gamma, double epsilon, boolean epsilonChange, double changeFactor){
        ALPHA = alpha;
        GAMMA = gamma;
        EPSILON = epsilon;
        CHANGE_EPSILON = epsilonChange;
        EPSILON_CHANGE_FACTOR = changeFactor;
    }
}
