package org.smartinrubio.javafuturescompletablefutures;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class Main {

    private static Actions actions = new Actions();
    private static Hotel hotel = new Hotel("Best");
    private static CreditCard creditCard = new CreditCard(123456, "Sergio");

    public static void main(String[] args) {
        System.out.println("The confirmation number is " + completableFutureStartBooking());
    }

//    public static int startBooking() throws ExecutionException, InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(4);
//        Future<Boolean> bookHotel = executorService.submit(() -> actions.bookHotel());
//
//        bookHotel.get();
//
//        Future<String> checkAvailabilityFuture = executorService.submit(() -> actions.checkAvailability(hotel));
//        Future<String> checkCreditCardDetailsFuture = executorService.submit(() -> actions.checkCreditCardDetails(creditCard));
//
//
//        Future<Integer> generateConfirmationNumberFuture = executorService.submit(() -> actions.generateConfirmationNumber(checkAvailabilityFuture.get(), checkCreditCardDetailsFuture.get()));
//
//        executorService.shutdown();
//
//        return generateConfirmationNumberFuture.get();
//    }

    public static Integer completableFutureStartBooking() {

        return CompletableFuture
                .supplyAsync(actions::bookHotel) // It's like submit in the Future. Static methods runAsync and supplyAsync allow us to create a CompletableFuture instance out of Runnable and Supplier functional types correspondingly
                .thenCompose(isBooking -> CompletableFuture // thenCompose() chains futures sequentially and uses previous stage as argument, it will flatten and return a Future, like flatMap
                        .supplyAsync(() -> actions.checkAvailability(hotel))
                        .thenCombineAsync(CompletableFuture // thenCombine() is used to do something with two futures
                                .supplyAsync(() -> actions.checkCreditCardDetails(creditCard)),
                                actions::generateConfirmationNumber // same as (hotelName, ownersCreditCardName) -> actions.generateConfirmationNumber(hotelName, ownersCreditCardName)
                        )
                ).exceptionally(e -> { // exceptionally allows us to complete the Future with a value in case of an error
                    log.error("Something went wrong: {}", e.getMessage());
                    return 0;
                })
                .thenApply(Math::abs) // thenApply() is used to transform the result of a completableFuture, like Map
                .join(); // join() is similar to get(), but it throws an unchecked exception is case the future does not complete
    }
}
