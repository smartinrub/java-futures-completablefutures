package org.smartinrubio.javafuturescompletablefutures;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class Actions {

    public boolean bookHotel() {

        log.info("Booking hotel");
        delay(1000);
        log.info("Processing booking...");
        return true;
    }


    public String checkAvailability(Hotel hotel) {
        log.info("Checking hotel availability of " + hotel.getName());
        delay(2000);
        log.info("The hotel is available!");
        return hotel.getName();
    }

    public String checkCreditCardDetails(CreditCard creditCard) {
        log.info("Checking credit card " + creditCard.getNumber());
        delay(1500);
        log.info("Credit card validated!");
        return creditCard.getOwnersName();
    }

    public int generateConfirmationNumber(String hotelName, String creditCardOwnersName) {
        log.info("Generating Confirmation number for hotel " + hotelName + " and customer " + creditCardOwnersName);
        return ThreadLocalRandom.current().nextInt();
    }

    private void delay(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
