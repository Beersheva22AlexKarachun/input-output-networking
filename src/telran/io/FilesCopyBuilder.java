package telran.io;

public class FilesCopyBuilder {
	private static final String FILES_COPY = "FilesCopy";
	private static final String TRANSFER_COPY = "TransferCopy";
	private static final String BUFFER_COPY = "BufferCopy";
	private static final String OVERWRITE = "overwrite";

	private static final int DEFAULT_BUFFER_SIZE = 1_000_000;

	public Copy build(String type, String[] args) {
		if (args.length < 2) {
			throw new IllegalArgumentException("Not enough arguments");
		}
		String scrFile = args[0];
		String destFile = args[1];
		boolean overwrite = getOverWrite(args);

		return switch (type) {
		case FILES_COPY -> new FilesCopy(scrFile, destFile, overwrite);
		case TRANSFER_COPY -> new TransferCopy(scrFile, destFile, overwrite);
		case BUFFER_COPY -> new BufferCopy(scrFile, destFile, overwrite, getBufferSizeValue(args));
		default -> throw new IllegalArgumentException(type + " isn't supported");
		};

	}

	private int getBufferSizeValue(String[] args) {
		int res = DEFAULT_BUFFER_SIZE;

		if (args.length > 2) {
			try {
				res = Integer.parseInt(args[3]);
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
			}
		}
		return res;
	}

	private boolean getOverWrite(String[] args) {
		return args.length > 2 && args[2].equalsIgnoreCase(OVERWRITE);
	}
}
