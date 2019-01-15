package sample;

import java.util.Comparator;

public class StringComparator implements Comparator<String> {
    @Override
    public int compare (String s1, String s2) {
        //remove numbers from strings
        String ss1 = s1.replaceAll("\\d","");
        String ss2 = s2.replaceAll("\\d","");

        //compare string alphabetically
        int strcmp = ss1.compareTo(ss2);
        if(strcmp == 0) {
            //if same string compare by the numbers in string
            int i1 = Integer.parseInt(s1.replaceAll("[\\D]", ""));
            int i2 = Integer.parseInt(s2.replaceAll("[\\D]", ""));
            int cmp = Integer.compare(i1, i2);
            if (cmp != 0) {
                return cmp;
            }
            return s1.compareTo(s2);
        }
        return strcmp;
    }
}