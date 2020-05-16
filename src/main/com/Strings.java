package main.com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tasks connected with {@link String}
 */
public class Strings {

  private static final int A = Character.getNumericValue('a');
  private static final int Z = Character.getNumericValue('z');

  public static void main(String[] args) {
    //System.out.println(isUniqueCharsWithArrays("Hello!"));
    //System.out.println(isUniqueCharsWithArrays("ПриветЕ!"));
    //System.out.println(isUniqueCharsWithArrays("Приветик!"));
    //System.out.println(urlify("Mr John Smith    ", 13));
    //System.out.println(isPalindromeOfPermutation("ahnxs hxan"));

/*    System.out.println(oneEditAway("pale", "ple"));
    System.out.println(oneEditAway("pales", "pale"));
    System.out.println(oneEditAway("pale", "bale"));
    System.out.println(oneEditAway("pale", "bae"));*/

    //System.out.println(compressString("hhheelloo"));
    System.out.println(lengthOfLongestSubstring("dvdf"));
  }

  public static boolean isUniqueCharsWithArrays(String str) {
    if (str.length() > 1103) {
      return false;
    }

    boolean[] char_set = new boolean[1103];
    for (int i = 0; i < str.length(); i++) {
      int val = str.charAt(i);
      if (char_set[val]) {
        return false;
      }
      char_set[val] = true;
    }
    return true;
  }

  public static char[] urlify(String str, int trueLength) {
    char[] strChars = str.toCharArray();
    int spaceCount = 0, index = 0;
    for (int i = 0; i < trueLength; i++) {
      if (strChars[i] == ' ') {
        spaceCount++;
      }
    }
    index = trueLength + spaceCount * 2;
    if (trueLength < str.length()) {
      strChars[trueLength] = '\0';
    }
    for (int i = trueLength - 1; i >= 0; i--) {
      if (strChars[i] == ' ') {
        strChars[index - 1] = '0';
        strChars[index - 2] = '2';
        strChars[index - 3] = '%';
        index = index - 3;
      } else {
        strChars[index - 1] = strChars[i];
        index--;
      }
    }
    return strChars;
  }

  public static boolean isPalindromeOfPermutation(String phrase) {
    int[] table = buildCharFrequencyTable(phrase);
    return checkMaxOneOdd(table);
  }

  private static boolean checkMaxOneOdd(int[] table) {
    boolean foundOdd = false;
    for (int count : table) {
      if (count % 2 == 1) {
        if (foundOdd) {
          return false;
        }
        foundOdd = true;
      }
    }
    return true;
  }

  private static int[] buildCharFrequencyTable(String phrase) {
    int[] table = new int[Z - A];
    for (char c : phrase.toCharArray()) {
      int x = getCharNumber(c);
      if (x != -1) {
        table[x]++;
      }
    }
    return table;
  }

  private static int getCharNumber(Character c) {
    int val = Character.getNumericValue(c);
    if (A <= val && val <= Z) {
      return val - A;
    }
    return -1;
  }


  /*
  There are three types of edits that can be performed on strings: insert a character, remove a character,
  or replace a character. Given two strings, write a function to check if they are one edit (or zero edits) away.
   */

  public static boolean oneEditAway(String str1, String str2) {
    if (str1.length() == str2.length()) {
      return oneEditReplace(str1, str2);
    } else if (str1.length()+1 == str1.length()) {
      return oneEditInsert(str1, str2);
    } else if (str1.length()-1 == str2.length()) {
      return oneEditInsert(str2, str1);
    }
    return false;
  }

  private static boolean oneEditReplace(String str1, String str2) {
    boolean foundedDiff = false;
    for(int i = 0; i < str1.length(); i++) {
      if (str1.charAt(i) != str2.charAt(i)) {
        if (foundedDiff) {
          return false;
        }
        foundedDiff = true;
      }
    }
    return true;
  }

  private static boolean oneEditInsert(String str1, String str2) {
    int index1 = 0;
    int index2 = 0;
    while (index2 < str2.length() && index1 < str1.length()) {
      if (str1.charAt(index1) != str2.charAt(index2)) {
        if (index1 != index2) {
          return false;
        }
        index2++;
      } else {
        index1++;
        index2++;
      }
    }
    return true;
   }


   /*
   String Compression: Implement a method to perform basic string compression using the counts
   of repeated characters. For example, the string aabcccccaaa would become a2b1c5a3. If the "compressed"
    string would not become smaller than the original string, your method should return the original string.
    You can assume the string has only uppercase and lowercase letters (a -z).
    */
   public static String compressString(String s) {
    int repeatedCount = 0;
    StringBuilder compressed = new StringBuilder();

    for (int i = 0; i < s.length(); i++) {
      repeatedCount++;
      char c = s.charAt(i);
      if (i + 1 >=s.length() || s.charAt(i) != s.charAt(i+1)) {
        compressed.append(s.charAt(i));
        compressed.append(repeatedCount);
        repeatedCount = 0;
      }
    }

    if (compressed.length() < s.length()) {
      return compressed.toString();
    } else {
      return s;
    }
   }

  /**
   * Given a string, find the length of the longest substring without repeating characters.
   *
   * @param s
   * @return
   */

  public static int lengthOfLongestSubstring(String s) {
    List<String> substrings = new ArrayList<>();

    String substring = "";

    for (char c:s.toCharArray()) {
      if (substring.lastIndexOf(c) >= 0) {
        substrings.add(substring);
        substring = String.valueOf(c);
      } else {
        substring = substring.concat(String.valueOf(c));
      }
    }
    substrings.add(substring);
    substrings.sort((s1, s2) -> {
      if (s1.length() == s2.length()) {
        return 0;
      }

      if (s1.length() > s2.length()) {
        return -1;
      } else {
        return 1;
      }
    });
    return substrings.get(0).length();
  }
}
