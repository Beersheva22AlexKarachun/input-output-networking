package telran.view;

import java.util.function.BinaryOperator;
import java.util.function.Consumer;

public class NumericalCalculator {
	private static final String NAME = "Numerical calculator";
	private static final String ERROR_MSG = "Wrong input";
	private static final String RESULT = "Result = %f";

	public static Menu getNumericalCalculator() {
		return new Menu(NAME,
				new Item[] { 
						Item.of("Addition", NumericalCalculator.getOperation((x, y) -> x + y)),
						Item.of("Subtraction", NumericalCalculator.getOperation((x, y) -> x - y)),
						Item.of("Multiplication", NumericalCalculator.getOperation((x, y) -> x * y)),
						Item.of("Division", NumericalCalculator.getOperation((x, y) -> x / y)), 
						Item.exit() });
	}

	private static Consumer<InputOutput> getOperation(BinaryOperator<Double> func) {
		return io -> {
			double[] nums = getNumbers(io, 2);
			io.writeLine(String.format(RESULT, func.apply(nums[0], nums[1])));
		};
	}

	private static double[] getNumbers(InputOutput io, int amountOfNumbers) {
		double[] numbers = new double[amountOfNumbers];
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = io.readNumber("Enter a number #" + (i + 1), ERROR_MSG, -Double.MAX_VALUE, Double.MAX_VALUE);
		}
		return numbers;
	}
}
