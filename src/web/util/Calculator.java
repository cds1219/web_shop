package web.util;

public class Calculator {
	private static float USD_RATE = 1124.70F;
	private static float JPY_RATE = 10.113F;
	private static float CNY_RATE = 163.30F;
	private static float GBP_RATE = 1444.35F;
	private static float EUR_RATE = 1295.97F;

	public static String calculate(float won, String operator) {
		String result = null;
		if (operator.equals("dollar")) {
			result = String.format("%.6f", won / USD_RATE);
		} else if (operator.equals("en")) {
			result = String.format("%.6f", won / JPY_RATE);
		} else if (operator.equals("wian")) {
			result = String.format("%.6f", won / CNY_RATE);
		} else if (operator.equals("pound")) {
			result = String.format("%.6f", won / GBP_RATE);
		} else if (operator.equals("euro")) {
			result = String.format("%.6f", won / EUR_RATE);
		}
		return result;
	}

}
