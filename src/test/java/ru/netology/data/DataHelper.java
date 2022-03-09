package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataHelper {

    private Faker faker = new Faker();

    public static String getPropertyOrDefValue(String key, String defValue) {
        return System.getProperty(key) == null|| System.getProperty(key).isEmpty()?
                defValue : System.getProperty(key);
    }

    private String getCardNumber(String card) {
        if (card.equalsIgnoreCase("approved")) {
            return "1111 2222 3333 4444";
        }
        else if (card.equalsIgnoreCase("declined")) {
            return "5555 6666 7777 8888";
        }
        else return card;
    }

    private String generateMonth() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM");
        String date = LocalDate.now().plusMonths(3).format(format);
        return date;
    }

    private String generateYear() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yy");
        String date = LocalDate.now().plusYears(2).format(format);
        return date;
    }

    private String generateOwnerName() {
        String ownerName = faker.name().lastName().toUpperCase() + " " + faker.name().firstName().toUpperCase();
        return ownerName;
    }

    private String generateCvc() {
        return Integer.toString(faker.number().numberBetween(100, 999));
    }

    @Value
    public static class CardInfo{
        String cardNumber;
        String month;
        String year;
        String ownerName;
        String cvc;
    }

    public CardInfo getValidCardInfo(String card) {
        return new CardInfo(getCardNumber(card), generateMonth(), generateYear(), generateOwnerName(), generateCvc());
    }

    public CardInfo getInvalidCardInfo(String card) {
        return new CardInfo(getCardNumber(card), "14", "19", "Иванов Иван", generateCvc());
    }

    public CardInfo getInvalidFormatCardInfo(String card) {
        return new CardInfo(getCardNumber(card), "15", "3", "1234 @!", "0");
    }
}
