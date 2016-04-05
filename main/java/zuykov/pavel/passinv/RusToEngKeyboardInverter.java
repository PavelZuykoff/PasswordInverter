package zuykov.pavel.passinv;

import android.util.Log;

/**
 * Created by novem on 21.03.2016.
 */
public class RusToEngKeyboardInverter {
    //класс инвертирует текст написанный в раскладке ЙЦУКЕН в QWERTY (только буквы)
    private final String TAG = "Happy";

    private final char[] rusKeyboard1 = {'Ё', 'ё', 'Х', 'х', 'Ъ', 'ъ', 'Ж', 'ж', 'Э', 'э', 'Б', 'б', 'Ю', 'ю'};
    private final char[] engKeyboard1 = {'~', '`', '{', '[', '}', ']', ':', ';', '"', '\'', '<', ',', '>', '.'};
    private final char[] rusKeyboard2 = {'й', 'ц', 'у', 'к', 'е', 'н', 'г', 'ш', 'щ', 'з', 'ф', 'ы', 'в', 'а', 'п', 'р', 'о', 'л', 'д', 'я', 'ч', 'с', 'м', 'и', 'т', 'ь'};
    private final char[] engKeyboard2 = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm'};

    protected String invertRusToEng(String string) {


        char[] charArray = string.toCharArray();
        //сравниваем массивы№1 где русские буквы не имеют английских аналогов на клавиатуре, меняем на соответствующие им символы.

        for (int i = 0; i < charArray.length; i++) {
            if (Character.isDigit(charArray[i]) == false) {
                for (int j = 0; j < rusKeyboard1.length; j++) {
                    Character charFromInputedText = charArray[i];
                    Character charFromRusKeyboardArray = rusKeyboard1[j];

                    if (charFromInputedText.equals(charFromRusKeyboardArray)) {
                        charArray[i] = engKeyboard1[j];
                        Log.d(TAG, "Первый заход, поменял " + rusKeyboard1[j] + " " + charArray[i]);
                    }
                }
            }
        }
//        сравниваем массивы№2 где русские буквы имеют английские аналоги на клавиатуре, меняем согласно регистру.
        for (int i = 0; i < charArray.length; i++) {
            if (Character.isDigit(charArray[i]) == false) {
                for (int j = 0; j < rusKeyboard2.length; j++) {
                    Character charFromInputedText = charArray[i];
                    Character charFromRusKeyboardArray;
                    if (Character.isUpperCase(charArray[i])) {

                        charFromRusKeyboardArray = Character.toUpperCase(rusKeyboard2[j]);
                        if (charFromInputedText.equals(charFromRusKeyboardArray)) {
                            charArray[i] = Character.toUpperCase(engKeyboard2[j]);
                            Log.d(TAG, "Второй заход, поменял " + charFromInputedText + " на " + charArray[i]);

                        }
                    } else {
                        charFromRusKeyboardArray = rusKeyboard2[j];
                        if (charFromInputedText.equals(charFromRusKeyboardArray)) {
                            charArray[i] = engKeyboard2[j];
                            Log.d(TAG, "Второй заход, поменял " + charFromRusKeyboardArray + " на " + charArray[i]);
                        }
                    }
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            builder.append(charArray[i]);
            string = builder.toString();
        }
        return string;
    }


}
