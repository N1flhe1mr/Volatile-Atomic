package ru.netology;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger three = new AtomicInteger(0);
    public static AtomicInteger four = new AtomicInteger(0);
    public static AtomicInteger five = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];

        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread threadOne = new Thread(() -> {
            for (String text : texts) {
                switch (text.length()) {
                    case 3 -> {
                        if (isPalindrome(text)) {
                            three.set(three.get() + 1);
                        }
                    }
                    case 4 -> {
                        if (isPalindrome(text)) {
                            four.set(four.get() + 1);
                        }
                    }
                    case 5 -> {
                        if (isPalindrome(text)) {
                            five.set(five.get() + 1);
                        }
                    }
                }
            }
        });

        Thread threadTwo = new Thread(() -> {
            for (String text : texts) {
                switch (text.length()) {
                    case 3 -> {
                        if (allSame(text)) {
                            three.set(three.get() + 1);
                        }
                    }
                    case 4 -> {
                        if (allSame(text)) {
                            four.set(four.get() + 1);
                        }
                    }
                    case 5 -> {
                        if (allSame(text)) {
                            five.set(five.get() + 1);
                        }
                    }
                }
            }
        });

        Thread threadThree = new Thread(() -> {
            for (String text : texts) {
                switch (text.length()) {
                    case 3 -> {
                        if (isAbc(text)) {
                            three.set(three.get() + 1);
                        }
                    }
                    case 4 -> {
                        if (isAbc(text)) {
                            four.set(four.get() + 1);
                        }
                    }
                    case 5 -> {
                        if (isAbc(text)) {
                            five.set(five.get() + 1);
                        }
                    }
                }
            }
        });

        threadOne.start();
        threadTwo.start();
        threadThree.start();
        threadOne.join();
        threadTwo.join();
        threadThree.join();
        System.out.printf(
                """
                        Красивых слов с длиной 3: %d шт
                        Красивых слов с длиной 4: %d шт
                        Красивых слов с длиной 5: %d шт""", three.get(), four.get(), five.get());
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }

        return text.toString();
    }

    public static boolean isPalindrome(String str) {
        return str.contentEquals(new StringBuilder(str).reverse());
    }

    public static boolean allSame(String str) {
        return str != null && !str.isEmpty() && str.chars().allMatch(c -> str.charAt(0) == c);
    }

    public static boolean isAbc(String str) {
        String sorted = str.chars()
                .sorted()
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return sorted.equals(str);
    }
}