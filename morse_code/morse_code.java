/**
 * This program translates morse code into text, and text into morse code.
 * @author Ahmet Faruk Turhan
 * @since 13.04.2021
 */
import java.util.Scanner;

public class morse_code {
    public static void main(String []args) {

        Scanner input = new Scanner(System.in); //Scanner object
        while (true) {
            System.out.print("\n\nEnter 1 to convert a text to morse code.\nEnter 2 to convert a morse code into morse " +
                    "code\nEnter 0 to exit the program: ");
            int userChoice = input.nextInt();

            if (userChoice == 1)
                textToMorse();
            else if (userChoice == 2)
                morseToText();
            else {
                System.out.println("You chose to exit.");
                input.close(); //close Scanner object
                break;
            }
        }

        input.close(); //close Scanner object
    }

    /**
     * A function to convert text into morse code
     */
    public static void textToMorse() {
        Scanner input = new Scanner(System.in); //Scanner object

        System.out.print("\nEnter a text: ");
        String userText = input.nextLine(); //get input

        StringBuilder textToMorse = new StringBuilder(); //SB to append at the end of the string
        for (int i = 0; i < userText.length(); i++) { //traverse along the characters of text

            String temp = String.valueOf(userText.charAt(i)).toUpperCase(); //uppercase the current character

            if (temp.equals(" ")) { //if the current character is a whitespace,
                textToMorse.append(" / ");
                continue;
            }
            String toAdd = findMorse(temp); //find the corresponding morse code of the current character
            textToMorse.append(toAdd); //add the morse code at the end of the string
            textToMorse.append(" "); //whitespace to divide the characters
        }
        System.out.println("Morse code of the given text: "+textToMorse); //print the result

    }

    /**
     * A function to convert text to morse
     * @param character Character
     * @return corresponding morse code of the character
     */
    public static String findMorse(String character) {

        String morse = ""; //initalize
        //morse code array
        String [][]morseArr = new String[][]{{"A", ".-"}, {"B", "-..."}, {"C", "-.-."}, {"D", "-.."}, {"E", "."}, {"F", "..-."}, {"G", "--."}, {"H", "...."},
                {"I", ".."}, {"J", ".---"}, {"K", "-.-"}, {"L", ".-.."}, {"M", "--"}, {"N", "-."}, {"O", "---"}, {"P", ".--."}, {"Q", "--.-"},
                {"R", ".-."}, {"S", "..."}, {"T", "-"}, {"U", "..-"}, {"V", "...-"}, {"W", ".--"}, {"X", "-..-"}, {"Y", "-.--"},
                {"Z", "--.."}, {"Ö", "---."}};

        if (character.equals("İ")) //fix a bug with the character i,ı,I,İ
            return "..";

        for (int i = 0; i <morseArr.length; i++) { //row
            for (int j = 0; j < morseArr[0].length; j++) { //column

                if (character.equals(morseArr[i][0])) { //if the current character equals to the current array's index,
                    morse = morseArr[i][1]; //corresponding morse code value
                    break;
                }
            }
        }

        return morse;
    }

    /**
     * a function to convert morse code into text
     */
    public static void morseToText() {
        Scanner input = new Scanner(System.in); //Scanner object

        System.out.print("\nEnter a morse code: ");
        String morseCode = input.nextLine();//get input

        StringBuilder morseToText = new StringBuilder(); //morse to text string

        String[] arrOfStr = morseCode.split(" "); //split the morse codes by using the delimiter whitespace

        for (String a : arrOfStr) { //traverse along the
            morseToText.append(findText(a)); //find the corresponding character of the morse code

        }
        System.out.println("Text of the given morse code: " +morseToText); //print the result

    }

    /**
     * a function to convert morse code into text
     * @param character takes the current morse code
     * @return the corresponding character
     */
    public static String findText(String character) {
        String text = "";

        String [][]morseArr = new String[][]{{"A", ".-"}, {"B", "-..."}, {"C", "-.-."}, {"D", "-.."}, {"E", "."}, {"F", "..-."}, {"G", "--."}, {"H", "...."},
                {"I", ".."}, {"J", ".---"}, {"K", "-.-"}, {"L", ".-.."}, {"M", "--"}, {"N", "-."}, {"O", "---"}, {"P", ".--."}, {"Q", "--.-"},
                {"R", ".-."}, {"S", "..."}, {"T", "-"}, {"U", "..-"}, {"V", "...-"}, {"W", ".--"}, {"X", "-..-"}, {"Y", "-.--"},
                {"Z", "--.."}};

        if (character.equals("/")) //if the current morse code is a whitespace
            return " ";

        if (character.equals("---.")) //special case for letter Ö
            return "Ö";

        for (int i = 0; i <morseArr.length; i++) { //row
            for (int j = 0; j < morseArr[0].length; j++) { //column

                if (character.equals(morseArr[i][1])) { //if the current morse code equals to the current array's index,
                    text = morseArr[i][0]; //corresponding character
                    break;
                }
            }
        }
        return text;
    }

}
