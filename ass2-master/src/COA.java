import java.io.PrintWriter;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Liron on 12/04/2017.
 */
public class COA {
    private String m_pathCipher;
    private String m_pathWriteKey;
    private String m_pathIV;
    private HashMap <Character,Character> m_key;
    private static Set <String> m_wordsSet;
    private static char[]m_aTo_h={ 'a','b','c','d','e','f', 'g','h'};
    private String m_encryptedTxt;

    public COA(String pathCipher, String pathIV , String pathWriteKey)
    {
        m_pathCipher=pathCipher;
        m_pathWriteKey = pathWriteKey;
        m_pathIV =pathIV ;
        m_key= new HashMap <Character,Character>() ;
        m_encryptedTxt="";
        m_wordsSet=new HashSet<String>();
        initiateDictionary();
    }

    //read the dictionary file and enter it to local set
    private void initiateDictionary()
   {
        if (m_wordsSet.size()!=0)
            return;
        else{
            try{
                Path path = Paths.get("words.txt");
                byte[] readBytes = Files.readAllBytes(path);
                String wordListContents = new String(readBytes, "UTF-8");
                String[] words = wordListContents.split("\n");
                m_wordsSet = new HashSet<>();
                Collections.addAll(m_wordsSet, words);
            }
            catch (IOException e){}
        }
   }
   //function to check if a word exists in the dictionary
    private boolean isInDictionary(String word)
    {
            return m_wordsSet.contains(word);
    }

    private static boolean changeKey() {
        int k = 6;
        while (m_aTo_h[k] >= m_aTo_h[k + 1]) {
            k--;
            if (k < 0) {
                return false;
            }
        }
        int l = m_aTo_h.length - 1;
        while (m_aTo_h[k] >= m_aTo_h[l]) {
            l--;
        }
        swap(k, l);
        int length = m_aTo_h.length - (k + 1);
        for (int i = 0; i < length / 2; i++) {
            swap(k + 1 + i, m_aTo_h.length - i - 1);
        }
        return true;
    }
//aux function to changeKey
    private static void swap(int i , int j){
        char tmp=m_aTo_h[i];
        m_aTo_h[i]=m_aTo_h[j];
        m_aTo_h[j]=tmp;
    }

   //assign to hash set of key the new permutations to try
   private boolean setKeyChange(){
       if (changeKey()){
           m_key.put('a' , m_aTo_h[0]);
           m_key.put('b' , m_aTo_h[1]);
           m_key.put('c' , m_aTo_h[2]);
           m_key.put('d' , m_aTo_h[3]);
           m_key.put('e' , m_aTo_h[4]);
           m_key.put('f' , m_aTo_h[5]);
           m_key.put('g' , m_aTo_h[6]);
           m_key.put('h' , m_aTo_h[7]);
           try{
               if ( m_aTo_h[0]== 'd' && m_aTo_h[1]== 'h'
                       &&  m_aTo_h[2]== 'a' && m_aTo_h[3]== 'g'
                       &&  m_aTo_h[4]== 'c' && m_aTo_h[5]== 'f'
                       &&  m_aTo_h[6]== 'b' && m_aTo_h[7]== 'e')
                   System.in.read();
           }
           catch (IOException e){}

           return true;
       }
       else
           return false;
   }

//
    private  Object getKeyFromValue(Object value) {
        for (Object o : m_key.keySet()) {
            if (m_key.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    private void tryBreak () {

        readWrite rw_cTxt = new readWrite(10, m_pathCipher);
        readWrite rw_IV = new readWrite(10, m_pathIV);

        char[] cipherText = rw_cTxt.read();
        char[] cipherText2 ;
        char[] PlainText = new char[10];
        char[] InitialVector = rw_IV.read();

        // take the first set of cipher Text and take the encription out
        for (int i = 0; i < cipherText.length; i++) {
            if (m_key.containsValue(cipherText[i])) {
                PlainText[i] = (char)(getKeyFromValue(cipherText[i])) ;
            }
            else {
                PlainText[i] = cipherText[i] ;
            }
        }
        // take the first set of plain text and xor it with the Initial Vector
        for (int i = 0; i < InitialVector.length; i++)
        {
            PlainText[i] = (char) (PlainText[i] ^ InitialVector[i]);
        }
        m_encryptedTxt+=String.valueOf(PlainText);
       // rw_cTxt.write(PlainText,m_pathToWriteOut+"\\Plain text "+m_sizeOfEncryption+".txt");

        while ((cipherText2 = rw_cTxt.read()) != null) {

            for (int i = 0; i < cipherText.length; i++) {
                if (m_key.containsValue(cipherText2[i])) {
                    PlainText[i] = (char)(getKeyFromValue(cipherText2[i])) ;
                }
                else {
                    PlainText[i] = cipherText2[i] ;
                }
            }
            for (int i = 0; i < cipherText.length; i++)
            {
                PlainText[i] = (char) (PlainText[i] ^ cipherText[i]);
            }

            cipherText =(cipherText2) ;
            m_encryptedTxt+=String.valueOf(PlainText);
           // readCipherText.write(PlainText,m_pathToWriteOut+"\\Plain text "+m_sizeOfEncryption+".txt");
        }
    }

    private boolean checkDecryption(){
       //more than 5 words wrong? return False!
       int limit=5;
        String[] arr = m_encryptedTxt.split(" | \n | \r");
        for ( String word : arr) {
            if (limit==0)
                return false;
            //not in the dic?
            if (!isInDictionary(word.toLowerCase()))
                limit-=1;
        }
        // all the words in the dictionary!
        return true;
    }
    public void coAttack(){
        //create a key
        while(setKeyChange()){
            //create a full string out of the encrypted text
            tryBreak();
            // if the validation check has complete- write the solution,
            // else change the key again and try again
             if (checkDecryption())
                 writeKey();
        }
    }
    private void writeKey(){
        try{
            PrintWriter pWrite= new PrintWriter(m_pathWriteKey, "UTF-8");
            for (Map.Entry<Character, Character> entry : m_key.entrySet()) {
                pWrite.append(entry.getKey());
                pWrite.append(' ');
                pWrite.append(entry.getValue());
                pWrite.append('\n');
            }
            pWrite.close() ;
        }
        catch (IOException e)
        {
            System.out.println("problem in writing the key to a file");
        }
    }



       /* public void printKey(){
        do {
            System.err.println(Arrays.toString(m_aTo_h));
        } while(changeKey());
    }
    */



}
