package telran.view;

import java.time.LocalDate;
import java.util.function.*;

public class DateCalculator {
	private static final String NAME = "Date calculator";
	private static final String ERROR_MSG = "Wrong input";
	private static final String RESULT = "Result = %s";

	public static Menu getNumericalCalculator() {
		return new Menu(NAME, new Item[] { 
				Item.of("Add days", DateCalculator.getDateOperation(LocalDate::plusDays)),
				Item.of("Subtract Days", DateCalculator.getDateOperation(LocalDate::minusDays)), 
				Item.exit() });
	}

	private static Consumer<InputOutput> getDateOperation(BiFunction<LocalDate, Long, LocalDate> func) {
		return io -> {
			LocalDate date = io.readDateISO("Enter a date in format yyyy-mm-dd", ERROR_MSG);
			long days = io.readLong("Enter a number of days", ERROR_MSG, Long.MIN_VALUE, Long.MAX_VALUE);
			io.writeLine(String.format(RESULT, func.apply(date, days)));
		};
	}
}
