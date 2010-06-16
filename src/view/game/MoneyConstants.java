package view.game;

public class MoneyConstants {

    public static final String MONEY = "Money";
    public static final String MONEY_SYMBOL = "$";
    
    public static String getMoneyString(int value) {
    	return MONEY_SYMBOL + " " + value;
    }
    
}
