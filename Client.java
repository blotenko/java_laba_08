package Main;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String args[]) throws Exception
    {
        Socket sock = new Socket("127.0.0.1", 5000);

        System.out.println("Enter your choise:");
        System.out.println("1.Додавання нової країни");
        System.out.println("2.Видалення країни");
        System.out.println("3.Додавання нового міста в задану країну");
        System.out.println("4.Видалення міста");
        System.out.println("5.Редагування міста");
        System.out.println("6.Підрахунок кількості міст в країні");
        System.out.println("7.Отримання повного списку міст із зазначенням назви країни");
        System.out.println("8.Отримання списку міст для заданої країни");
        System.out.println("9.Отримання повного списку країн");
        Scanner scanner = new Scanner(System.in);
        String val = scanner.nextLine();
        OutputStream ostream = sock.getOutputStream();
        DataOutputStream dos = new DataOutputStream(ostream);
        dos.writeBytes(val);
        dos.close();
        ostream.close();
        sock.close();
    }
}


