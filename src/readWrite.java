import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by nitzan on 06/04/17.
 */
public class readWrite
{
    private int m_numberOfCharecther ;
    private String m_pathToRead ;
    private PrintWriter m_writer ;

    private FileReader m_inputStream;

    public readWrite (int numberOfCharecther ,String pathToRead  )  {
        m_numberOfCharecther = numberOfCharecther ;
        m_pathToRead=pathToRead ;

    }
    public char [] read()
    {
        char[] ans = new char[m_numberOfCharecther];

        try {
            if (m_inputStream == null)
                m_inputStream = new FileReader(m_pathToRead);

            int r;
            int i = 0;
            while (i < m_numberOfCharecther &&(r = m_inputStream.read()) != -1  ) {
                char ch = (char) r;
                //int ascii = (int) ch ;
                //System.out.println(i +". the char: " + ch+" the ascii code: "+ascii);
                ans[i] = ch;
                i++;
            }
            if (i == 0) {
                return null;
            }
            if (i!=m_numberOfCharecther){
                char [] newAns = new char[i];
                for (int j=0 ; j < newAns.length ; j ++ ){
                    newAns[j]= ans[j];
                }
                return newAns ;
            }
        }
        catch (IOException e){}
        return ans ;
    }

    public void write(char [] arrayToWrite , String pathToWriteAndFileName)
    {
        try{
            if (m_writer == null ) {
                m_writer = new PrintWriter(pathToWriteAndFileName, "UTF-8");
                m_writer.write(arrayToWrite);
                m_writer.close() ;

            }else {
                FileWriter fw = new FileWriter(pathToWriteAndFileName, true);
               fw.write(arrayToWrite);
              fw.close();
            }
        }
        catch (IOException e)
        {
            System.out.println("problem in write function, trhow excption");
        }
    }

}
