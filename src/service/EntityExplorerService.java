package service;

import model.Entity;
import utils.Matrix;
import utils.Node;

import java.util.*;

public class EntityExplorerService {

    private MatrixService matrixService;
    private Random random;

    public EntityExplorerService() {
        matrixService = new MatrixService();
        random = new Random();
    }

    public void exploreMatrix(Entity entity, Matrix matrix) {

        while (!isMissionCompleted(entity, matrix)) {

            System.out.println("Generation : " + entity.getGenerationNumber());

            System.out.println("Matrix to explore: ");
            matrixService.printMatrix(matrix);


            System.out.println("Current entity explored matrix to explore: ");
            matrixService.printMatrix(entity.getLearnedMatrix());

            final Node randomStartLocation = entity.getLearnedLocation().get(random.nextInt(entity.getLearnedLocation().size()));
            final Node randomFinishLocation = new Node(random.nextInt(matrix.getRows()), random.nextInt(matrix.getColumns()));

            Explore(matrix, entity, randomStartLocation.getX(), randomStartLocation.getY(), randomFinishLocation.getX(), randomFinishLocation.getY());
        }

        System.out.println(entity.getName() + " has explore all map");


        System.out.println("Do you want to see the entity learned matrix and the map matrix? (Yes/No)");
        Scanner scanner = new Scanner(System.in);
        String wantToSeeMatrix = scanner.nextLine();

        if (wantToSeeMatrix.toUpperCase().equals("YES") || wantToSeeMatrix.toUpperCase().equals("Y")) {
            System.out.println("Map Matrix: ");
            matrixService.printMatrix(matrix);
            System.out.println();
            System.out.println("Entity Map Explored");
            matrixService.printMatrix(entity.getLearnedMatrix());
        }

        System.out.println("Find all location from map, after " + entity.getGenerationNumber() + " generation");

    }

    // I want to find all the map locations by setting the starting point with a random value from learned locations of entity
    // and setting the destination point randomly with a value that don't exceed from maximum of rows/columns
    //I have been inspired from Lee algorithm concept , but adapt it to my specific problem.
    private void Explore(Matrix matrix, Entity entity, int i, int j, int x, int y) {

        // Below arrays details all 4 possible movements from a cell
        final int rowPossibleMoves[] = {-1, 0, 0, 1};
        final int columnPossibleMoves[] = {0, -1, 1, 0};


        Queue<Node> q = new ArrayDeque<>();

        // always add in queue the random selected node from entity learned locations list
        q.add(new Node(i, j));

        while (!q.isEmpty()) {

            // entity can explore only if its energy is bigger > 0
            entity.setEnergy(entity.getEnergy() - 1);

            Node node = q.poll();

            // current cell
            i = node.getX();
            j = node.getY();

            // break when find location
            if (i == x && j == y) {
                break;
            }

            //break when entity has consumed its energy
            if (entity.getEnergy() == 0) {
                break;
            }


            // I initialize k with a random value to simulate a good explorer training
            // we have 4 potential locations that entity can explore from a current location (i+1 , j), (i , j+1), (i-1 , j), (i , j-1)
            final int k = random.nextInt(4);
            if (isValid(matrix, entity, i + rowPossibleMoves[k], j + columnPossibleMoves[k])) {
                // mark next cell as visited and enqueue it
                Node newLocation = new Node(i + rowPossibleMoves[k], j + columnPossibleMoves[k]);
                q.add(newLocation);
                entity.setLocation(newLocation);
                final Boolean existingLocationFlag = existLocation(entity, newLocation);
                if (existingLocationFlag.equals(Boolean.FALSE)) {
                    entity.getLearnedMatrix().getMatrix()[i + rowPossibleMoves[k]][j + columnPossibleMoves[k]] = matrix.getMatrix()[i + rowPossibleMoves[k]][j + columnPossibleMoves[k]];
                    entity.getLearnedLocation().add(newLocation);
                }
            }
        }
    }

    // store only distinct value
    //can't use Set from java because I compare the node values (x,y) ,that's why I'm using a verification method
    private Boolean existLocation(Entity entity, Node newLocation) {
        for (Node existingLocation : entity.getLearnedLocation()) {
            if (existingLocation.getX() == newLocation.getX() && existingLocation.getY() == newLocation.getY()) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }


    // entity can explore another location and save it as learned location only if
    // it is a valid one - it not exceed of limit and the near areas are learned
    // for example : you can  explore [2,2] if entity find [2,1]
    // ! entity move only on line/column
    private boolean isValid(Matrix matrix, Entity entity, int row, int col) {

        final int M = matrix.getRows();
        final int N = matrix.getColumns();

        return (row >= 0) && (row < M) && (col >= 0) && (col < N) && verifyIfCurrentLocationIsNearOfOneLearned(entity, row, col);
    }

    private boolean verifyIfCurrentLocationIsNearOfOneLearned(Entity entity, int row, int col) {
        for (Node learnedLocation : entity.getLearnedLocation()) {
            if ((learnedLocation.getX() == row - 1 && learnedLocation.getY() == col)
                    || (learnedLocation.getX() == row && learnedLocation.getY() == col - 1)
                    || (learnedLocation.getX() == row + 1 && learnedLocation.getY() == col)
                    || (learnedLocation.getX() == row && learnedLocation.getY() == col + 1)) {
                return true;
            }
        }
        return false;
    }

    // stop exploring condition
    private boolean isMissionCompleted(Entity entity, Matrix matrix) {
        entity.setGenerationNumber(entity.getGenerationNumber() + 1);
        return matrixService.areEquals(entity.getLearnedMatrix().getMatrix(), matrix.getMatrix());
    }
}
