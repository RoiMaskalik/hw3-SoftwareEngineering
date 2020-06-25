
import jdk.jshell.execution.Util;
import jdk.nashorn.api.tree.Tree;

import java.io.File;
import java.util.*;

public abstract class AbstractInvertedIndex{
    private HashMap<String, TreeSet<String>> invertedIndexMap;
    /**
     * Constructor that initializes an Inverted index, each unique word in the stock is
     * a key and a set of file names that contain the word is a value
     */
    protected AbstractInvertedIndex(){
        invertedIndexMap = new HashMap<>();
    }
    public HashMap<String, TreeSet<String>> getInvertedIndexMap() {
        return invertedIndexMap;
    }

    /**
     * Maps Each word in the stock to the set of files that contain the word
     * @param files - An array that contains all the files that are in the stock
     */
    public void buildInvertedIndex(File[] files) {
        String fileText;
        for(File file: files){
            fileText = getTextFromFile(file);
            if(fileText != null)
                updateInvertedIndexMap(file, fileText);
        }
    }

    /**
     * Checks for all the files in the stock that meet the requirements of the query
     *
     * the queryResult stack should have 1 element at the end of the method
     * @param query - is written in a structure called "Reverse Polish Notation"
     * @return a set of file names
     */
    public TreeSet<String> runQuery(String query){
        String[] queryArray = Utils.splitBySpace(query);
        Stack<TreeSet<String>> queryResult = new Stack<>();
        for(String word: queryArray){
                updateResult(word, queryResult);
            }
        return queryResult.pop();
    }
    /**
     * Adds the file's name to the sets of all the words that are in the text of
     * the file(not including the file name).
     * @param file - An example of file creation: new File ("path to file on disk")
     * @param text - the text that was extracted from a file
     */
    private void updateInvertedIndexMap(File file, String text){
       String [] textList = Utils.splitBySpace(text);
       String fileName = getFileName(file);
       TreeSet<String> filesForCurrentWords;
       for(String word : textList){
           filesForCurrentWords = invertedIndexMap.computeIfAbsent
                   (word, k -> new TreeSet<>());
           filesForCurrentWords.add(fileName.trim());
       }
    }

    /**
     * Changes the result stack according to the "word" of the query
     *
     * there are 3 special "words" which are the boolean operators, they have special cases
     * @param word - the query is splitted to "words" by space
     * @param queryResult - a stack that will contain the set of files that matches the query
     */
    private void updateResult(String word,  Stack<TreeSet<String>> queryResult) {
        TreeSet<String> set1;
        switch (word) {
            case "AND":
                set1 = queryResult.pop();
                queryResult.peek().retainAll(set1);
                break;
            case "OR":
                set1 = queryResult.pop();
                queryResult.peek().addAll(set1);
                break;
            case "NOT":
                set1 = queryResult.pop();
                queryResult.peek().removeAll(set1);
                break;
            default:
                queryResult.add(new TreeSet<>(
                        invertedIndexMap.get(sensitivity(word))));
        }
    }
    /**
     * retrieve a file name that is located between 2 unique tags
     * @param file
     * @return - the file's name, null if the file is empty or does not have a name
     */
    private String getFileName(File file){
        List<String> fileRows = Utils.readLines(file);
        if (fileRows != null)
            return Utils.substringBetween
                    (fileRows.get(1),"<DOCNO>", "</DOCNO>");
        return null;
    }
    /**
     * retrieve a files text that is located between 2 unique tags: <Text> and </Text>
     * @param file
     * @return a string that contains the text, null if the file is empty or doesn't have a text
     */
    private String getTextFromFile(File file){
        List<String> fileRows = Utils.readLines(file);
        StringBuffer text = new StringBuffer();
        //check if we are between the text tags
        boolean isBetweenTags = false;
        for (String fileRow : fileRows) {
            if (fileRow.equals("</TEXT>"))
                isBetweenTags = false;

            if (isBetweenTags)
                text.append(sensitivity(fileRow));

            if (fileRow.equals("<TEXT>"))
                isBetweenTags = true;
        }
        if (text.toString().equals(""))
            return null;
        return text.toString();
    }
    protected abstract String sensitivity(String row);
}
