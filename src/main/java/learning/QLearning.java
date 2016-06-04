package learning;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QLearning {

    private double epsilon = Configuration.EPSILON;
    private Knowledge knowledge;

    public QLearning(Point mazeSize) {
        knowledge = new Knowledge(mazeSize.x, mazeSize.y);
    }

    public Action pickAction(Point location, List<Action> possibleActions) {
        if (new Random().nextDouble() < epsilon) {
            return pickRandomAction(possibleActions);
        }
        return pickBestPossibleAction(location, possibleActions);
    }

    public Action pickBestAction(Point location){
        return knowledge.get(location.x, location.y).findSortedAction().get(0);
    }

    private Action pickBestPossibleAction(Point location, List<Action> possibleActions) {
        List<Action> actions = knowledge.get(location.x, location.y).findSortedAction();
        for (Action action : actions){
            if (possibleActions.contains(action)){
                return action;
            }
        }
        throw new RuntimeException("Action not found - should not happened!");
    }

    public void learn(Point location, Action action, Point newLocation, double reward) {
        if (Configuration.CHANGE_EPSILON) {
            epsilon *= Configuration.EPSILON_CHANGE_FACTOR;
        }
        double qVal = knowledge.get(location.x, location.y).get(action);
        double maxQVal = knowledge.get(newLocation.x,newLocation.y).findBestValue();
        double newQVal = qVal + Configuration.ALPHA * (reward + Configuration.GAMMA * maxQVal - qVal);
        knowledge.get(location.x,location.y).update(action, newQVal);
    }

    private Action pickRandomAction(List<Action> possibleActions) {
        int index = new Random().nextInt(possibleActions.size());
        return possibleActions.get(index);
    }

    public void printKnowledge() {
//        knowledge.print();
    }

    private class Knowledge {

        private List<List<StateKnowledge>> knowledge;

        public Knowledge(int xSize, int ySize) {
            knowledge = new ArrayList<>();
            for (int x = 0; x < xSize; x++) {
                List<StateKnowledge> list = new ArrayList<>();
                for (int y = 0; y < ySize; y++) {
                    list.add(new StateKnowledge());
                }
                knowledge.add(list);
            }
        }

        public StateKnowledge get(int x, int y){
            return knowledge.get(x).get(y);
        }

        public void print(){
            for (int x = 1; x < knowledge.size() - 1; x++){
                for (int y = 1; y < knowledge.get(x).size() - 1; y++){
                    StateKnowledge sk = get(x, y);
                    System.out.println(String.format("X: %d, Y: %d, Action: %s, Q: %f",x, y, sk.findSortedAction().get(0).name(), sk.findBestValue()));
                }
            }
        }
    }

    private class StateKnowledge {
        private Map<Action, Double> qValues;

        public StateKnowledge() {
            qValues = new HashMap<>();
            for (Action action : Action.values()) {
                qValues.put(action, 0.0);
            }
        }

        public double get(Action action) {
            return qValues.get(action);
        }

        public void update(Action action, double qValue) {
            qValues.put(action, qValue);
        }

        public List<Action> findSortedAction() {
            return sort().map(Map.Entry::getKey).collect(Collectors.toList());
        }

        public double findBestValue() {
            return sort().collect(Collectors.toList()).get(0).getValue();
        }

        private Stream<Map.Entry<Action, Double>> sort() {
            return qValues.entrySet().stream().sorted(new Comparator<Map.Entry<Action, Double>>() {
                @Override
                public int compare(Map.Entry<Action, Double> e1, Map.Entry<Action, Double> e2) {
                    return -e1.getValue().compareTo(e2.getValue());
                }
            });
        }
    }
}
