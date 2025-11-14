package com.example.PrintDownload;



public class AmountToWords {

    private static final String[] units = {
        "", "One", "Two", "Three", "Four", "Five", "Six", "Seven",
        "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen",
        "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"
    };

    private static final String[] tens = {
        "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty",
        "Seventy", "Eighty", "Ninety"
    };

    
    
    public static String convertAmountToWords(String amountStr) {
        double amount = Double.parseDouble(amountStr);
        long rupees = (long) amount;
        int paise = (int) Math.round((amount - rupees) * 100);

        String result = "";
        if (rupees > 0) {
            result += convert(rupees) + " Rupees";
        }

        if (paise > 0) {
            if (!result.isEmpty()) result += " and ";
            result += convert(paise) + " Paise";
        }

        if (result.isEmpty()) {
            result = "Zero Rupees";
        }

        return result + " Only";
    }

    
    
    
    private static String convert(long n) {
        if (n < 20)
            return units[(int) n];
        else if (n < 100)
            return tens[(int) n / 10] + (n % 10 != 0 ? " " + units[(int) n % 10] : "");
        else if (n < 1000)
            return units[(int) n / 100] + " Hundred" + (n % 100 != 0 ? " and " + convert(n % 100) : "");
        else if (n < 100000)
            return convert(n / 1000) + " Thousand" + (n % 1000 != 0 ? " " + convert(n % 1000) : "");
        else if (n < 10000000)
            return convert(n / 100000) + " Lakh" + (n % 100000 != 0 ? " " + convert(n % 100000) : "");
        else
            return convert(n / 10000000) + " Crore" + (n % 10000000 != 0 ? " " + convert(n % 10000000) : "");
    }

    public static void main(String[] args) {
        System.out.println(convertAmountToWords("689.90"));
        System.out.println(convertAmountToWords("1234.09"));
        System.out.println(convertAmountToWords("1.25"));
    }
}