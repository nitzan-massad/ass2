import java.io.FileReader;
import java.io.IOException;

/**
 * Created by nitzan on 05/04/17.
 */
public class Main {

    public static void main(String[] args) {

        for (int i = 0; i < args.length; i++) {
            System.out.println(i + 1 + ". " + args[i]);

        }
        encryptionDecryption tmp1 = new encryptionDecryption
                (10,
                        "C:\\Users\\nitzan\\Desktop\\לימודים\\שנה ג\\סמסטר ו\\אבטחת מחשבים\\עבודה 2\\examples\\plainMsg_example.txt",
                        "C:\\Users\\nitzan\\Desktop\\לימודים\\שנה ג\\סמסטר ו\\אבטחת מחשבים\\עבודה 2\\examples\\key_example.txt ",
                        "C:\\Users\\nitzan\\Desktop\\לימודים\\שנה ג\\סמסטר ו\\אבטחת מחשבים\\עבודה 2\\examples\\IV_example.txt",
                        "C:\\Users\\nitzan\\Desktop\\לימודים\\שנה ג\\סמסטר ו\\אבטחת מחשבים\\עבודה 2\\examples");
        encryptionDecryption tmp2 = new encryptionDecryption
                (10,
                        "C:\\Users\\nitzan\\Desktop\\לימודים\\שנה ג\\סמסטר ו\\אבטחת מחשבים\\עבודה 2\\examples\\cipherMsg_example.txt",
                        "C:\\Users\\nitzan\\Desktop\\לימודים\\שנה ג\\סמסטר ו\\אבטחת מחשבים\\עבודה 2\\examples\\key_example.txt ",
                        "C:\\Users\\nitzan\\Desktop\\לימודים\\שנה ג\\סמסטר ו\\אבטחת מחשבים\\עבודה 2\\examples\\IV_example.txt",
                        "C:\\Users\\nitzan\\Desktop\\לימודים\\שנה ג\\סמסטר ו\\אבטחת מחשבים\\עבודה 2\\examples");

        //tmp1.encryption();
        tmp2.decryption();
    }
}
