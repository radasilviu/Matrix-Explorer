import model.Entity;
import service.EntityExplorerService;
import utils.Matrix;
import service.MatrixService;
import utils.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        final MatrixService matrixService = new MatrixService();
        final EntityExplorerService entityExplorerService = new EntityExplorerService();


        System.out.print("Enter the entity name : ");
        Scanner entityScanner = new Scanner(System.in);
        final String entityName = entityScanner.nextLine();

        final Entity entity = new Entity(entityName);

        System.out.println("Enter the number of rows");
        Scanner rowsScanner = new Scanner(System.in);
        final int rows = rowsScanner.nextInt();

        System.out.println("Enter the number of columns");
        Scanner columnsScanner = new Scanner(System.in);
        final int columns = columnsScanner.nextInt();


        final Matrix matrix = new Matrix(rows, columns);
        //populate map with some values
        matrix.setMatrix(matrixService.populateMatrix(matrix));

        //set the start location as [0,0]
        final Node startNode = new Node(0, 0);
        entity.setLocation(startNode);

        //add start node to learned location
        List<Node> initialLearnedLocation = new ArrayList<>();
        initialLearnedLocation.add(startNode);
        entity.setLearnedLocation(initialLearnedLocation);

        // instantiate learned matrix from entity with values that don't exist in map matrix  .
        // only for start node set the same value as for the [0,0] position from map matrix.
        Matrix entityInitialLearnedMatrix = new Matrix(rows, columns);
        int[][] initialMatrix = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                initialMatrix[i][j] = -1;
            }
        }
        initialMatrix[0][0] = 1;

        entityInitialLearnedMatrix.setMatrix(initialMatrix);
        entity.setLearnedMatrix(entityInitialLearnedMatrix);


        entityExplorerService.exploreMatrix(entity, matrix);

    }
}
