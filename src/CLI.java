import core.Core;
import java.io.Console;
import java.util.Arrays;

public class CLI {
	public static void main(String[] args) {
		System.out.println("Welcome to the Neural Network");
		Core core = new Core();
		// TODO: Refazer esse console de um jeito mais apropriado
		while (true) {
			System.out.print("Network console: ");
			Console console = System.console();
			String[] consoleInput = console.readLine().split(" ");
			switch(consoleInput[0]) {
				case("create"):
					int[] layers = Arrays.stream(consoleInput[1].split(",")).mapToInt(Integer::parseInt).toArray();
					try {
						core.createNetwork(layers);
					} catch (IllegalArgumentException e ) {
						System.out.println(e);
					}
				break;
				case("think"):
					double[] networkInput = Arrays.stream(consoleInput[1].split(",")).mapToDouble(Double::parseDouble).toArray();
					double[] networkOutput;
					try {
						networkOutput = core.networkThink(networkInput);
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
				case("learn"):
					//Create learnSet matrix
					double[][][] learnSet = new double[consoleInput.length][2][];
					for(int set=1; set<consoleInput.length; ++set){
						for(int j=0; j<2; ++j){
							learnSet[set][j] = new double[ consoleInput[set].split(";")[j].split(",").length ];
							for (int k=0; k<consoleInput[set].split(";")[j].split(",").length; ++k){
								learnSet[set][j][k] = Double.parseDouble(consoleInput[set].split(";")[j].split(",")[k]);
							}
						}
					}
					//Train the nn with each learnSet recursively
					double error = 0.0;
					for (int set=1; set<learnSet.length; set++) {
						try{
							error += core.learn(learnSet[set]);
						} catch (IllegalArgumentException e) {
							System.out.println(e);
							break;
						}
					}
					core.updateWeights();
					System.out.printf("Error for iteration 1: %f\n", error/learnSet.length);
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
