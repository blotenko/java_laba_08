package Main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static List<Country> countries;
    public static  String filepath = "/Users/blotenko/Downloads/OperationSystems-master/testingForMySql/src/Map.xml";
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, ParserConfigurationException, SAXException {

        ServerSocket sersock = new ServerSocket(5000);
        System.out.println("server is ready");  //  message to know the server is running
        Socket sock = sersock.accept();
        readXmlFile();
        makeDBFromXml();

        InputStream istream = sock.getInputStream();
        DataInputStream dstream = new DataInputStream(istream);
        String message2 = dstream.readLine();
        if(message2.charAt(0)=='Д' && message2.charAt(1)=='К' )
        {
            addCountry();
        }
        else if(message2.charAt(0)=='B' && message2.charAt(1)=='К' )
        {
            deleteCountry();
        }
        else if(message2.charAt(0)=='Д'&& message2.charAt(1)=='М')
        {
            addCity();
        }
        else if(message2.charAt(0)=='B' && message2.charAt(1)=='М')
        {
            deleteCity();
        }
        else if(message2.charAt(0)=='Р')
        {
            editCity();
        }
        else if(message2.charAt(0)=='П')
        {
            countOfCities();
        }
        else if(message2.charAt(0)=='О'&& message2.charAt(1)=='П' && message2.charAt(0)=='C'&& message2.charAt(1)=='М')
        {
            getAllCities();
        }
        else if(message2.charAt(0)=='О'&& message2.charAt(1)=='C')
        {
            citiesOfCountry();
        }
        else if(message2.charAt(0)=='О'&& message2.charAt(1)=='П' && message2.charAt(0)=='C'&& message2.charAt(1)=='K')
        {
            getAllCountries();
        }

        dstream .close(); istream.close(); sock.close(); sersock.close();
    }

    public static void addCountry() throws ClassNotFoundException, SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/";
        String username = "bill";
        String password = "passpass";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL,username,password);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Country name");
        String val = scanner.nextLine();
        System.out.println("Enter Country id");
        String val2 = scanner.nextLine();
        String sql = "INSERT INTO schema_name.Countries(country_name, country_id) VALUES ('"+val+"','"+ val2+"'); ";
        // String sql = "insert into Countries ('country_name', 'country_id') " + "c";
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.execute();
    }
    public static void addCity() throws ClassNotFoundException, SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/";
        String username = "bill";
        String password = "passpass";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL,username,password);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Country name");
        String val = scanner.nextLine();
        String sql = "select * from schema_name.Countries;";
        PreparedStatement p = connection.prepareStatement(sql);
        ResultSet  rs = p.executeQuery();
        boolean tmp = false;
        while(rs.next()){
            String name = rs.getString("country_name");
            if (name.equals(val)){
                tmp = true;
                break;
            }
        }
        if(tmp){
            System.out.println("Enter City name");
            String val1 = scanner.nextLine();
            System.out.println("Enter City id");
            int val2 = scanner.nextInt();
            System.out.println("Enter City count of people");
            int val3 = scanner.nextInt();
            System.out.println("Enter City is capital");
            int val4 = scanner.nextInt();
            String sql2 = "INSERT INTO schema_name.city(city_namE, city_ID,count_of_people,isStol,country_ref) VALUES ('"+val1+"','"+ val2+"', '"+val3+"','"+val4+"','"+val+"'); ";
            PreparedStatement preparedStmt = connection.prepareStatement(sql2);
            preparedStmt.execute();
        }
    }
    public static void editCity()throws ClassNotFoundException, SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/";
        String username = "bill";
        String password = "passpass";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL,username,password);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter City name");
        String val = scanner.nextLine();
        String sql = "select * from schema_name.Cities;";
        PreparedStatement p = connection.prepareStatement(sql);
        ResultSet  rs = p.executeQuery();
        boolean tmp = false;
        int id =0;
        while(rs.next()){
            String name = rs.getString("city_namE");
            if (name.equals(val)){
                id = rs.getInt("city_ID");
                tmp = true;
                break;
            }
        }
        if(tmp){
            System.out.println("Enter City name");
            String val1 = scanner.nextLine();
            System.out.println("Enter City id");
            int val2 = scanner.nextInt();
            System.out.println("Enter City count of people");
            int val3 = scanner.nextInt();
            System.out.println("Enter City is capital");
            int val4 = scanner.nextInt();
            String sql2 = "UPDATE schema_name.city SET city_namE = '" + val1+ "', city_ID = '"+val2+"', count_of_people = '"+val3+"',isStol = '"+val4+"',country_ref = '"+val+"' WHERE city_ID = '"+id+"';";
            PreparedStatement preparedStmt = connection.prepareStatement(sql2);
            preparedStmt.execute();
        }
    }
    public static int countOfCities()throws ClassNotFoundException, SQLException{
        String jdbcURL = "jdbc:mysql://localhost:3306/";
        String username = "bill";
        String password = "passpass";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL,username,password);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Country name");
        String val = scanner.nextLine();
        String sql = "select * from schema_name.Countries;";
        PreparedStatement p = connection.prepareStatement(sql);
        ResultSet  rs = p.executeQuery();
        boolean tmp = false;
        int count = 0;
        while(rs.next()){
            String name = rs.getString("country_name");
            if (name.equals(val)){
                tmp = true;
                break;
            }
        }
        if(tmp){
            String sql2 = "select * from schema_name.Cities where country_ref = '"+val+"';";
            PreparedStatement p2 = connection.prepareStatement(sql2);
            ResultSet  rs2 = p2.executeQuery();

            while(rs2.next()){
                String name = rs2.getString("country_name");
                if (name.equals(val)){
                    count++;
                }
            }
            System.out.println(count);
        }
        return count;
    }

    public static List<City> citiesOfCountry()throws ClassNotFoundException, SQLException{
        String jdbcURL = "jdbc:mysql://localhost:3306/";
        String username = "bill";
        String password = "passpass";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL,username,password);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Country name");
        String val = scanner.nextLine();
        String sql = "select * from schema_name.Countries;";
        PreparedStatement p = connection.prepareStatement(sql);
        ResultSet  rs = p.executeQuery();
        boolean tmp = false;
        while(rs.next()){
            String name = rs.getString("country_name");
            if (name.equals(val)){
                tmp = true;
                break;
            }
        }
        List<City> cities = new ArrayList<>();
        if(tmp){
            String sql2 = "select * from schema_name.Cities where country_ref = '"+val+"';";
            PreparedStatement p2 = connection.prepareStatement(sql2);
            ResultSet  rs2 = p2.executeQuery();
            while(rs2.next()){
                String name = rs2.getString("country_name");
                if (name.equals(val)){
                    cities.add(new City(rs2.getString("city_namE"),rs2.getString("city_ID"),rs2.getString("country_ref"),rs2.getString("count_of_people"),rs2.getString("isStol")));
                }
            }
            System.out.println(cities);
        }
        return cities;
    }

    public static List<City> getAllCities()throws ClassNotFoundException, SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/";
        String username = "bill";
        String password = "passpass";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL,username,password);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Country name");
        String val = scanner.nextLine();
        String sql = "select * from schema_name.Cities;";
        PreparedStatement p = connection.prepareStatement(sql);
        ResultSet  rs2 = p.executeQuery();
        boolean tmp = false;
        List<City> cities = new ArrayList<>();
        while(rs2.next()){
                cities.add(new City(rs2.getString("city_namE"),rs2.getString("city_ID"),rs2.getString("country_ref"),rs2.getString("count_of_people"),rs2.getString("isStol")));
        }

        System.out.println(cities);
        return cities;
    }

    public static List<Country> getAllCountries()throws ClassNotFoundException, SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/";
        String username = "bill";
        String password = "passpass";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL,username,password);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Country name");
        String val = scanner.nextLine();
        String sql = "select * from schema_name.Countries;";
        PreparedStatement p = connection.prepareStatement(sql);
        ResultSet  rs2 = p.executeQuery();
        boolean tmp = false;
        List<Country> countries = new ArrayList<>();
        while(rs2.next()){
            String sql2 = "select * from schema_name.Cities;";
            PreparedStatement p2 = connection.prepareStatement(sql);
            ResultSet  rs = p2.executeQuery();
            List<City> cities = new ArrayList<>();
            while(rs.next())
            {
                if(rs.getString("country_ref") == rs2.getString("country_name"))
                {
                    cities.add(new City(rs.getString("city_namE"),rs.getString("city_ID"),rs.getString("country_ref"),rs.getString("count_of_people"),rs.getString("isStol")));
                }
            }
            countries.add(new Country(rs2.getString("city_namE"),rs2.getString("city_namE"),cities));
        }

        System.out.println(countries);
        return countries;
    }
    public static void deleteCity()throws ClassNotFoundException, SQLException{
        String jdbcURL = "jdbc:mysql://localhost:3306/";
        String username = "bill";
        String password = "passpass";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL,username,password);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter City name");
        String val = scanner.nextLine();
        String sql = "delete from schema_name.city where city_namE = '"+val+"';";
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.execute();
    }
    public static void deleteCountry()throws ClassNotFoundException, SQLException{
        String jdbcURL = "jdbc:mysql://localhost:3306/";
        String username = "bill";
        String password = "passpass";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL,username,password);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Country name");
        String val = scanner.nextLine();
        String sql = "delete from schema_name.city where country_ref = '"+val+"';";
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.execute();
        String sql2 = "delete from schema_name.Countries where country_name = '"+val+"';";
        PreparedStatement preparedStmt2 = connection.prepareStatement(sql2);
        preparedStmt2.execute();
    }
    private static City getCity(Node node) {
        Element element = (Element) node;
        City lang = new City(getTagValue("cityName", element),getTagValue("cityId", element),
                getTagValue("countryRef", element),getTagValue("countOfPeople", element),getTagValue("isStol",element));
        return lang;
    }

    private static Country getCountry(Node node, List<City> cities) {
        Element element = (Element) node;
        Country  lang = new Country(getTagValue("title", element),getTagValue("id", element),
                (List<City>) cities);

        return lang;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    private static void readXmlFile() throws ParserConfigurationException, IOException, SAXException {
        File xmlFile = new File(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        document.getDocumentElement().normalize();
        System.out.println("Корневой элемент: " + document.getDocumentElement().getNodeName());

        NodeList nodeList = document.getElementsByTagName("Country");
        NodeList nodeList1 = document.getElementsByTagName("city");

        List<Country> langList = new ArrayList<Country>();

        List<City> langList1 = new ArrayList<City>();
        for(int j = 0; j<nodeList1.getLength();j++){
            langList1.add(getCity(nodeList1.item(j)));
        }
        List<List<City>> lists = new ArrayList<>();

        int countOfCities = 0;
        int temp = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            countOfCities = (nodeList.item(i).getChildNodes().getLength()-5)/2;
            if(lists.size() == 0){
                lists.add(i,langList1.subList(0,countOfCities));
            }else{
                lists.add(i,langList1.subList(temp,temp+countOfCities));
            }
            temp = temp+countOfCities;
            langList.add(getCountry(nodeList.item(i),lists.get(i)));
        }
        countries = langList;
        for (Country lang : langList) {
            lang.display();
            System.out.println(" ");
        }
    }
    private static void writeXmlFile(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();


            Document doc = builder.newDocument();

            Element rootElement = doc.createElement( "Map");

            doc.appendChild(rootElement);

            for(int i = 0; i<countries.size();i++){
                rootElement.appendChild(getCountryToFile(doc,countries.get(i)));
            }


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);


            //  StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(new File(filepath));

            //  transformer.transform(source, console);
            transformer.transform(source, file);
            //    System.out.println("Создание XML файла закончено");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Node getCountryToFile(Document doc, Country c){
        Element country = doc.createElement("Country");
        Node item = null;
        item = doc.createElement("title");
        item.appendChild(doc.createTextNode(c.name));
        country.appendChild(item);
        Node item2 = null;
        item2 = doc.createElement("id");
        item2.appendChild(doc.createTextNode(c.id));
        country.appendChild(item2);

        for(int i = 0;i<c.cities.size();i++){
            country.appendChild(getCities(doc, country,c.cities.get(i)));
        }
        return country;
    }


    private static Node getCities(Document doc, Element element, City c) {

        Node item = null;
        item = doc.createElement("city");


        Node item2 = null;
        item2 = doc.createElement("cityId");
        item2.appendChild(doc.createTextNode(c.id));
        item.appendChild(item2);

        Node item3 = null;
        item3 = doc.createElement("countryRef");
        item3.appendChild(doc.createTextNode(c.ref));
        item.appendChild(item3);

        Node item4 = null;
        item4 = doc.createElement("cityName");
        item4.appendChild(doc.createTextNode(c.name));
        item.appendChild(item4);


        Node item5 = null;
        item5 = doc.createElement("countOfPeople");
        item5.appendChild(doc.createTextNode(c.countOfPeople));
        item.appendChild(item5);

        Node item6 = null;
        item6 = doc.createElement("isStol");
        item6.appendChild(doc.createTextNode(c.isStol));
        item.appendChild(item6);
        return item;
    }

    private static Country makeNewCountryXML(){
        List<City> cities = new ArrayList<>();
        String countryName = "";
        String countryId = "";
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter country Name : ");
        countryName = keyboard.nextLine();
        System.out.println("Enter country id : ");
        countryId = keyboard.nextLine();
        System.out.println("Do you want add cities to " + countryName +"y/n ?");
        String tmp = keyboard.nextLine();
        if(tmp.equals("y")){
            System.out.println("Enter count of Cities ?");
            int n = keyboard.nextInt();
            for(int i = 0; i< n;i++){
                System.out.println("Enter city name ?");
                String name = keyboard.nextLine();
                System.out.println("Enter city id ?");
                String id = keyboard.nextLine();
                System.out.println("Enter city ref?");
                String ref = keyboard.nextLine();
                System.out.println("Enter city count of people ?");
                String count = keyboard.nextLine();
                System.out.println("Enter city is stoliza ?");
                String isStol = keyboard.nextLine();
                cities.add(i,new City(name,id,ref,count,isStol));
            }

        } else{
            return new Country(countryName, countryId, cities);
        }
        Country country = new Country(countryName, countryId, cities);

        return country;
    }

    private static void deleteCountryXML(){
        System.out.println("Enter which country delete :");
        for(int  i =0; i< countries.size();i++){
            System.out.println(i+" country");
        }
        Scanner keyboard = new Scanner(System.in);
        int choice = keyboard.nextInt();
        countries.remove(choice);
    }

    private static void makeDBFromXml() throws ClassNotFoundException, SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/";
        String username = "bill";
        String password = "passpass";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL,username,password);
        for(int i = 0;i<countries.size();i++){

            String val = countries.get(i).name;
            String v2 = countries.get(i).id;
            String sql = "INSERT INTO schema_name.Countries(country_name, country_id) VALUES ('"+val+"','"+ v2+"'); ";
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.execute();
            for(int j = 0; j<countries.get(i).cities.size();j++){

                String val1 = countries.get(i).cities.get(j).name;

                String val2 = countries.get(i).cities.get(j).id;

                String val3 = countries.get(i).cities.get(j).countOfPeople;

                String val4 = countries.get(i).cities.get(j).isStol;
                String sql2 = "INSERT INTO schema_name.city(city_namE, city_ID,count_of_people,isStol,country_ref) VALUES ('"+val1+"','"+ val2+"', '"+val3+"','"+val4+"','"+val+"'); ";
                PreparedStatement preparedStmt2 = connection.prepareStatement(sql2);
                preparedStmt2.execute();
            }
        }

    }
}