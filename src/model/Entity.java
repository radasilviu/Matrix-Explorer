package model;

import utils.Matrix;
import utils.Node;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    private String name;
    // Current Location
    private Node location;
    private int generationNumber = 1;
    private List<Node> learnedLocation = new ArrayList<>();

    // Matrix explored , used this as a condition for stop exploring
    private Matrix learnedMatrix;

    // If energy <= 0, entity can't explore any location
    private int energy = 3;

    public Entity() {
    }

    public Entity(String name) {
        this.name = name;
    }

    public int getGenerationNumber() {
        return generationNumber;
    }

    public void setGenerationNumber(int generationNumber) {
        this.generationNumber = generationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getLocation() {
        return location;
    }

    public void setLocation(Node location) {
        this.location = location;
    }

    public List<Node> getLearnedLocation() {
        return learnedLocation;
    }

    public void setLearnedLocation(List<Node> learnedLocation) {
        this.learnedLocation = learnedLocation;
    }

    public Matrix getLearnedMatrix() {
        return learnedMatrix;
    }

    public void setLearnedMatrix(Matrix learnedMatrix) {
        this.learnedMatrix = learnedMatrix;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
