import java.io.FileReader;
import java.io.IOException;

/**
 * Created by nitzan on 05/04/17.
 */
public class Main {

    public static void main(String[] args) {

     /*   for (int i = 0; i < args.length; i++) {
            System.out.println(i + 1 + ". " + args[i]);

        }
        encryptionDecryption tmp1 = new encryptionDecryption
                (10,
                        "C:\\Users\\Liron\\Desktop\\שנה ג\\סמסטר ב\\אבטחה\\examples\\plainMsg_example.txt",
                        "C:\\Users\\Liron\\Desktop\\שנה ג\\סמסטר ב\\אבטחה\\examples\\key_example.txt ",
                        "C:\\Users\\Liron\\Desktop\\שנה ג\\סמסטר ב\\אבטחה\\examples\\IV_example.txt",
                        "C:\\Users\\Liron\\Desktop\\שנה ג\\סמסטר ב\\אבטחה\\examples");
        encryptionDecryption tmp2 = new encryptionDecryption
                (10,
                        "C:\\Users\\Liron\\Desktop\\שנה ג\\סמסטר ב\\אבטחה\\examples\\cipherMsg_example.txt",
                        "C:\\Users\\Liron\\Desktop\\שנה ג\\סמסטר ב\\אבטחה\\examples\\key_example.txt ",
                        "C:\\Users\\Liron\\Desktop\\שנה ג\\סמסטר ב\\אבטחה\\examples\\IV_example.txt",
                        "C:\\Users\\Liron\\Desktop\\שנה ג\\סמסטר ב\\אבטחה\\examples");

        //tmp1.encryption();
    //    tmp2.decryption();
        */
   COA coa=new COA("C:\\Users\\Liron\\Desktop\\שנה ג\\סמסטר ב\\אבטחה\\examples\\cipherMsg_example.txt",
           "C:\\Users\\Liron\\Desktop\\שנה ג\\סמסטר ב\\אבטחה\\examples\\IV_example.txt",
           "C:\\Users\\Liron\\Desktop\\שנה ג\\סמסטר ב\\אבטחה\\examples\\MYKEY.txt");
   coa.coAttack();
    }

}
