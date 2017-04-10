import java.util.HashMap;
import java.util.Map;

/**
 * Created by nitzan on 06/04/17.
 */
public class encryptionDecryption
{
    private int m_sizeOfEncryption;
    private String m_pathText ;
    private String m_pathKey;
    private String m_pathInitialVector ;
    private String m_pathToWriteOut;
    private HashMap <Character,Character> m_key ;

    public encryptionDecryption(int sizeOfEncryption, String pathText, String pathKey, String pathInitialVector , String pathToWriteOut)
    {
        m_sizeOfEncryption = sizeOfEncryption ;
        m_pathText=pathText;
        m_pathKey = pathKey;
        m_pathInitialVector =pathInitialVector ;
        m_pathToWriteOut = pathToWriteOut ;
        m_key= new HashMap<Character,Character>() ;
    }

    public void encryption () {
        loadKey();


        readWrite readPlainText = new readWrite(m_sizeOfEncryption, m_pathText);
        readWrite readInitialVector = new readWrite(m_sizeOfEncryption, m_pathInitialVector);



        char[] PlainText = readPlainText.read();
        char[] InitialVector = readInitialVector.read();
        char[] cipherText = new char[PlainText.length];
        // take the first set of plain text and xor it with the Initial Vector
        for (int i = 0; i < PlainText.length; i++) {
            cipherText[i] = (char) (PlainText[i] ^ InitialVector[i]);
        }
        // take the first set of (plain text xor Initial Vector) and shaffale with the key
        for (int i = 0; i < cipherText.length; i++) {
            if (m_key.containsKey(cipherText[i])) {
                cipherText[i] = m_key.get(cipherText[i]);
            }
        }
        // can use any of the readWrite object to write. its dosnt matter
        readPlainText.write(cipherText,m_pathToWriteOut+"\\Cipher text "+m_sizeOfEncryption+".txt");

        while ((PlainText = readPlainText.read()) != null) {


            for (int i = 0; i < PlainText.length; i++) {
                cipherText[i] = (char) (PlainText[i] ^ cipherText[i]);
            }
            for (int i = 0; i < cipherText.length; i++) {
                if (m_key.containsKey(cipherText[i])) {
                    cipherText[i] = m_key.get(cipherText[i]);
                }
            }
            if (PlainText.length<m_sizeOfEncryption){
                for (int i = PlainText.length ; i<cipherText.length ; i++){
                    cipherText[i]=0;
                }
            }
            // can use any of the readWrite object to write. its dosnt matter
            readPlainText.write(cipherText,m_pathToWriteOut+"\\Cipher text "+m_sizeOfEncryption+".txt");
        }
    }

    private void loadKey()
    {
        readWrite readKey = new readWrite(100 , m_pathKey);
        char [] keyInArray = readKey.read() ;

        for (int i = 0 ; i< keyInArray.length ; i=i+5)
        {
            m_key.put((keyInArray[i]) , (keyInArray[i+2]));
        }
    }

public void decryption () {

    loadKey();

    readWrite readCipherText = new readWrite(m_sizeOfEncryption, m_pathText);
    readWrite readInitialVector = new readWrite(m_sizeOfEncryption, m_pathInitialVector);

    char[] cipherText = readCipherText.read();
    char[] cipherText2 ;
    char[] PlainText = new char[m_sizeOfEncryption];
    char[] InitialVector = readInitialVector.read();


    // take the first set of cipher Text and take the increption out
    for (int i = 0; i < cipherText.length; i++) {
        if (m_key.containsValue(cipherText[i])) {
            PlainText[i] = (char)(getKeyFromValue(m_key , cipherText[i])) ;
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

    readCipherText.write(PlainText,m_pathToWriteOut+"\\Plain text "+m_sizeOfEncryption+".txt");

    while ((cipherText2 = readCipherText.read()) != null) {

        for (int i = 0; i < cipherText.length; i++) {
            if (m_key.containsValue(cipherText2[i])) {
                PlainText[i] = (char)(getKeyFromValue(m_key , cipherText2[i])) ;
            }
            else {
                PlainText[i] = cipherText2[i] ;
            }
        }
        for (int i = 0; i < cipherText.length; i++)
        {
            PlainText[i] = (char) (PlainText[i] ^ cipherText[i]);
        }

        // can use any of the readWrite object to write. its dosnt matter
    cipherText =(cipherText2) ;
    readCipherText.write(PlainText,m_pathToWriteOut+"\\Plain text "+m_sizeOfEncryption+".txt");
    }






}



    public  Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }










}
