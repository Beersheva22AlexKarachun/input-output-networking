package telran.view;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.function.*;

import org.junit.jupiter.api.Test;

class CalculatorTest {

	Item calc = new Menu("Calculator", 
			NumericalCalculator.getNumericalCalculator(),
			DateCalculator.getNumericalCalculator(), 
			Item.exit());
	
	InputOutput io = new StandardInputOutput();

	@Test
	void test() {
		calc.perform(io);
	}
}
