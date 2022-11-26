package Main;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JOptionPane;



public class RMIClient {

    private static RMIInterface look_up;

    public static void main(String[] args)
            throws MalformedURLException, RemoteException, NotBoundException, SQLException, ClassNotFoundException {

        look_up = (RMIInterface) Naming.lookup("// локальный / MyServer ");
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
        String response = look_up.doAction(val);

    }

}