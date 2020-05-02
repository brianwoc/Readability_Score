package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static List<String> import_(String fileFromCmd) {
        String regexWhitespace = "\\p{Z}";
//        String path = "C:\\dev\\Proj\\Readability Score\\Readability Score\\task\\src\\readability\\";
        File file = new File( fileFromCmd);
        List<String> inputTextList = new ArrayList<>();
        try (Scanner scanner1 = new Scanner(file)) {

            while (scanner1.hasNext()) {
                String newLine = scanner1.nextLine();
                String[] array = newLine.split(regexWhitespace);
                inputTextList = Arrays.asList(array);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return inputTextList;
    }

    public static int getSylabes(String inputWord) {
        String word = inputWord.replaceAll(".,?!", "");
        String regex = "[^aeiouAEIOU]";
        int numberOfSylabs = 0;
        if (word.replaceAll(regex, "").length() == 0) {
            return numberOfSylabs = 1;
        }
        if (word.length() == 1) {
            return numberOfSylabs = 1;
        }
        numberOfSylabs = word.replaceAll(regex, "").length();

        if (word.endsWith("e") && numberOfSylabs > 1) {
            numberOfSylabs--;
        }
        String regex2 = "(.*)[aeiouAEIOU][aeiouAEIOU](.*)";
        if (word.matches(regex2)) {
            numberOfSylabs--;
        }
        return numberOfSylabs;
    }

    public static int getAgeReadability(double Score) {
        switch ((int) Math.round(Score)) {
            case 1:
                return 6;
            case 2:
                return 7;
            case 3:
                return 9;
            case 4:
                return 10;
            case 5:
                return 11;
            case 6:
                return 12;
            case 7:
                return 13;
            case 8:
                return 14;
            case 9:
                return 15;
            case 10:
                return 16;
            case 11:
                return 17;
            case 12:
                return 18;
            case 13:
                return 24;
            default:
                return 25;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String textFromFile = args[0];
//        String textFromFile = "in.txt";
        List<String> inputTextList = new ArrayList<>();

        inputTextList = import_(textFromFile);
        String regex = "\\w+[.?!]";

        int counter = 0;
        double numberOfSentence = 0;
        double numberOfSylabes = 0;
        double numberOfCharacters = 0;
        double numberOfPolysylabes = -1;
        double numberOfWords = inputTextList.size();
        String lastWord = inputTextList.get(inputTextList.size() - 1);
        double score;

        if (lastWord.endsWith(".") || lastWord.endsWith("?") || lastWord.endsWith("!")) {

            for (int i = 0; i < inputTextList.size(); i++) {
                {
                    if (!inputTextList.get(i).matches(regex)) {
                        counter++;

                    } else {
                        counter++;
                        numberOfSentence++;
                    }
                    numberOfCharacters += inputTextList.get(i).length();
                    numberOfSylabes += getSylabes(inputTextList.get(i));
                    if (getSylabes(inputTextList.get(i)) > 2) {
                        numberOfPolysylabes++;
                    }
                }
            }
            System.out.println("Words: " + inputTextList.size());
            System.out.println("Sentences: " + numberOfSentence);
            System.out.println("Characters: " + numberOfCharacters);
            System.out.println("Syllables: " + numberOfSylabes);
            System.out.println("Polysyllables: " + numberOfPolysylabes);

        } else {
            numberOfSentence = 1;

            for (int i = 0; i < inputTextList.size(); i++) {
                {
                    if (!inputTextList.get(i).matches(regex)) {
                        counter++;
                    } else {
                        counter++;
                        numberOfSentence++;
                    }
                    numberOfCharacters += inputTextList.get(i).length();
                    numberOfSylabes += getSylabes(inputTextList.get(i));
                    if (getSylabes(inputTextList.get(i)) > 2) {
                        numberOfPolysylabes++;
                    }
                }
            }
            System.out.println("Words: " + inputTextList.size());
            System.out.println("Sentences: " + numberOfSentence);
            System.out.println("Characters: " + numberOfCharacters);
            System.out.println("Syllables: " + numberOfSylabes);
            System.out.println("Polysyllables: " + numberOfPolysylabes);

        }
        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all)");
        String userInput = scanner.nextLine();
        if (userInput.equals("ARI")) {
            score = ((4.71 * ((double) numberOfCharacters / inputTextList.size())) + (0.5 * ((double) inputTextList.size() / numberOfSentence)) - 21.43);
            System.out.printf("Automated Readability Index: %.2f", score);
            int score2 = getAgeReadability(score);
            System.out.printf(" (about %s year olds).\n", score2);


        }
        if (userInput.equals("FK")) {
            score = ((0.39 * ((double) inputTextList.size() / numberOfSentence)) + (11.8 * ((double) numberOfSylabes / inputTextList.size())) - 15.59);
            System.out.printf("Flesch–Kincaid readability tests: %.2f", score);
            int score2 = getAgeReadability(score);
            System.out.printf(" (about %s year olds).\n", score2);
        }
        if (userInput.equals("SMOG")) {
            score = ((1.043 * Math.sqrt(numberOfPolysylabes * (30.0 / numberOfSentence))) + 3.1291);
            System.out.printf("Simple Measure of Gobbledygook: %.2f", score);
            int score2 = getAgeReadability(score);
            System.out.printf(" (about %s year olds).\n", score2);
        }
        if (userInput.equals("CL")) {
            double L = (numberOfCharacters / numberOfWords) * 100.0;
            double S = (numberOfSentence / numberOfWords) * 100.0;

            score = (0.0588 * L) - (0.296 * S) - 15.8;
            System.out.printf("Coleman–Liau index: %.2f", score);
            int score2 = getAgeReadability(score);
            System.out.printf(" (about %s year olds).\n", score2);
        }
        if (userInput.equals("all")) {

            score = ((4.71 * ((double) numberOfCharacters / inputTextList.size())) + (0.5 * ((double) inputTextList.size() / numberOfSentence)) - 21.43);
            System.out.printf("Automated Readability Index: %.2f", score);
            int score2 = getAgeReadability(score);
            System.out.printf(" (about %s year olds).\n", score2);
            score = ((0.39 * ((double) inputTextList.size() / numberOfSentence)) + (11.8 * ((double) numberOfSylabes / inputTextList.size())) - 15.59);
            System.out.printf("Flesch–Kincaid readability tests: %.2f", score);
            int score3 = getAgeReadability(score);
            System.out.printf(" (about %s year olds).\n", score3);
            score = ((1.043 * Math.sqrt(numberOfPolysylabes * (30.0 / numberOfSentence))) + 3.1291);
            System.out.printf("Simple Measure of Gobbledygook: %.2f", score);
            int score4 = getAgeReadability(score);
            System.out.printf(" (about %s year olds).\n", score4);
            double L = (numberOfCharacters / numberOfWords) * 100.0;
            double S = (numberOfSentence / numberOfWords) * 100.0;
            score = (0.0588 * L) - (0.296 * S) - 15.8;
            System.out.printf("Coleman–Liau index: %.2f", score);
            int score5 = getAgeReadability(score);
            System.out.printf(" (about %s year olds).\n", score5);
            System.out.println();
            double average = (score2 + score3 + score4 +score5)/4.0;
            System.out.printf("This text should be understood in average by %.2f year olds.",average);

        }
    }
}
