import core.Core;
import java.util.Scanner;
import java.util.Arrays;

public class CLI {
	public static void main(String[] args) {
		System.out.println("Welcome to the Neural Network");
		Core core = null;
		// TODO: Refazer esse console de um jeito mais apropriado
		while (true) {
			System.out.print("Network console: ");
			Scanner scanner = new Scanner(System.in);
			String[] consoleInput = scanner.nextLine().split(" ");
			switch(consoleInput[0]) {
				case("create"):
					int[] layers = Arrays.stream(consoleInput[1].split(",")).mapToInt(Integer::parseInt).toArray();
					try {
						core = new Core(layers);
					} catch (IllegalArgumentException e) {
						System.out.println(e);
					}
				break;
				case("think"):
					if (core == null) {
						System.out.println("Neural network not loaded");
						continue;
					}
					double[] networkInput = Arrays.stream(consoleInput[1].split(",")).mapToDouble(Double::parseDouble).toArray();
					double[] networkOutput;
					try {
						networkOutput = core.think(networkInput);
					} catch (IllegalArgumentException e) {
						System.out.println(e);
						continue;
					}
					//Print network output
					for(int i=0; i<networkOutput.length; ++i) {
						System.out.printf("Neuron %d: %f\n", i, networkOutput[i]);
					}
					System.out.println();
				break;
				case("setTrain"):
					if (core == null) {
						System.out.println("Neural network not loaded");
						continue;
					}
					//Create learnSet matrix
					double[][][] learnSet = new double[consoleInput.length-1][2][];
					for(int set=0; set<consoleInput.length-1; ++set){
						for(int j=0; j<2; ++j){
							learnSet[set][j] = new double[ consoleInput[set+1].split(";")[j].split(",").length ];
							for (int k=0; k<consoleInput[set+1].split(";")[j].split(",").length; ++k){
								learnSet[set][j][k] = Double.parseDouble(consoleInput[set+1].split(";")[j].split(",")[k]);
							}
						}
					}
					core.setTrainingSet(learnSet);
				break;
				case("train"):
					if (core == null) {
						System.out.println("Neural network not loaded");
						continue;
					}
					try {
						int iterations = Integer.parseInt(consoleInput[1]);
						core.train(iterations);
					} catch (NumberFormatException e) {
						System.out.println("The argument must be a number");
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
				break;
				case("exit"):
					System.out.println("Bye");
					System.exit(0);
				break;
				default:
					System.out.println("Comando não reconhecido\n");
				break;
			}
		}
	}
}
