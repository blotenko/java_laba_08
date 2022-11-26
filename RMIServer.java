package Main;


import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

import static Main.Main.*;


public class RMIServer implements RMIInterface{

    private static final long serialVersionUID = 1L;

    protected RMIServer() throws RemoteException {

        super();

    }

    public static void main(String[] args){

        try {

            Naming.rebind("// localhost / MyServer ", new RMIServer ());
            System.err.println("Server ready");

        } catch (Exception e) {

            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();

        }

    }

    @Override
    public String doAction(String message2) throws RemoteException, SQLException, ClassNotFoundException {
        if(message2.charAt(0)=='Д' && message2.charAt(1)=='К' )
        {
             addCountry();
             return "";
        }
        else if(message2.charAt(0)=='B' && message2.charAt(1)=='К' )
        {
             deleteCountry();
            return "";
        }
        else if(message2.charAt(0)=='Д'&& message2.charAt(1)=='М')
        {
             addCity();
            return "";
        }
        else if(message2.charAt(0)=='B' && message2.charAt(1)=='М')
        {
             deleteCity();
            return "";
        }
        else if(message2.charAt(0)=='Р')
        {
             editCity();
            return "";
        }
        else if(message2.charAt(0)=='П')
        {
            return String.valueOf(countOfCities());
        }
        else if(message2.charAt(0)=='О'&& message2.charAt(1)=='П' && message2.charAt(0)=='C'&& message2.charAt(1)=='М')
        {
            return String.valueOf(getAllCities());
        }
        else if(message2.charAt(0)=='О'&& message2.charAt(1)=='C')
        {
            return  String.valueOf(citiesOfCountry());
        }
        else if(message2.charAt(0)=='О'&& message2.charAt(1)=='П' && message2.charAt(0)=='C'&& message2.charAt(1)=='K')
        {
            return String.valueOf((getAllCountries()));
        }
        return "";
    }
}