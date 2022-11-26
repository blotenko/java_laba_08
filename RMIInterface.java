package Main;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface RMIInterface extends Remote {

    public String doAction(String name) throws RemoteException, SQLException, ClassNotFoundException;

}