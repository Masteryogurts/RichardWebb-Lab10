import java.util.regex.*;
import java.util.Scanner;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors

public class WordCounter {
    private String stopword;
    private StringBuffer text = new StringBuffer();
    private String pathString;

    public static int processText(StringBuffer text, String stopword) throws InvalidStopwordException, TooSmallText {
        int count = 0;
        boolean stopwordchecker = false;
        Pattern regex = Pattern.compile("[a-zA-Z0-9']+");

        Matcher regexMatcher = regex.matcher(text);
        while (regexMatcher.find()) {
            count++;
            // System.out.println("I just found the word: " + regexMatcher.group());
            String word = regexMatcher.group();
            if (stopword != null && word.equals(stopword)) {
                stopwordchecker = true;
                break; // stops counting now
            }
        }
        // had to fix this up, always sends this exception if !stopwordchecker so need
        // to add the null
        if (!stopwordchecker && stopword != null) {
            throw new InvalidStopwordException(stopword);
        }

        if (count < 3) {
            throw new TooSmallText(count);
        }

        return count;
    }

    public static StringBuffer processFile(String pathString) throws EmptyFileException {
        Scanner input = new Scanner(System.in);
        File f = new File(pathString);
        while (!f.exists() || !f.isFile()) {
            System.out.println("Did not find file, retry");
            if (input.hasNextLine()) {
                pathString = input.nextLine();
                f = new File(pathString);
            } else {
                break;
            }
        }

        StringBuffer stringBuffer = new StringBuffer();
        try (Scanner reader = new Scanner(f)) { // auto closes the scanner
            while (reader.hasNextLine()) {
                stringBuffer.append(reader.nextLine()).append(" ");// for readability and spacing

            }
        } catch (FileNotFoundException e) {
            System.out.println("didnt find");
        }
        // cant do null for some odd reason (says dead code)
        if (stringBuffer.length() > 0){
            stringBuffer.setLength(stringBuffer.length() - 1); //we get rid of the FINAL trailing space
        }
        if (stringBuffer.length() == 0) {
            throw new EmptyFileException(pathString);
        }
        return stringBuffer;
    }

    public static void main(String[] args) {
        Scanner question = new Scanner(System.in);
        System.out.println("Option 1 for processing files. Option 2 for processing texts. Pick.");
        String answer = question.nextLine();
        while (!answer.equals("1") && !answer.equals("2")) {
            System.out.println("Invalid option, choose again");
            answer = question.nextLine();
        }
        String stopword = (args.length > 1) ? args[1] : null; //fancy stuff learned from systems. Basically just another if else searching for if we already had an argument


        if (answer.equals("1")) {
            String path = (args.length > 0) ? args[0] : null;
            if (path == null || path.isEmpty()){
                System.out.println("enter path");
                path = question.nextLine();
            }
            StringBuffer text;

            try {
                text = WordCounter.processFile(path);

            } catch (EmptyFileException e) {
                System.out.println(e);
                text = new StringBuffer(); // continue with an empty one
            }
            try{
                int count = WordCounter.processText(text, stopword);
                System.out.println("Found " + count + " words.");
            }catch (InvalidStopwordException e){
                System.out.println(e);
            }catch(TooSmallText e){
                System.out.println(e);
            }
        }else{
            System.out.println("Enter text");
            StringBuffer text = new StringBuffer(question.nextLine());

            try {
                int count = WordCounter.processText(text, stopword);
                System.out.println("Found " + count + " words.");
            } catch (InvalidStopwordException e) {
                System.out.println(e);
            } catch (TooSmallText e) {
                System.out.println(e);
            }

        }
        question.close(); // close the scanner
    }
}
