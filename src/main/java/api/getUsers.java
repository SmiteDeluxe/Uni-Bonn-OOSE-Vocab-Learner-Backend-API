package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class getUsers extends HttpServlet {
    static Gson g = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    static String cDb = "TisIsAt4esing.fo!rsmStieng31";
    static Crypt cAp = new Crypt("Henlo4Soms.!eagIzarsaFing");
    List<User> lu = new ArrayList<>();
    Statement statement;

    public void init(ServletConfig config) throws ServletException {
        g = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        cDb = "TisIsAt4esing.fo!rsmStieng31";
        cAp = new Crypt("Henlo4Soms.!eagIzarsaFing");
        lu = new ArrayList<>();
        super.init(config);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletOutputStream out = resp.getOutputStream();
        out.println("<html>\n<body>");

        String host1 = "192.168.178.44";
        String port1 = "5432";
        String db1 = "vocab";
        String user1 = "postgres";
        String pw1 = "foobar";

        String host2 = "ec2-52-31-94-195.eu-west-1.compute.amazonaws.com";
        String port2 = "5432";
        String db2 = "d1s53eksbi3k99?sslmode=require";
        String user2 = "nxxbxlcrttfdql";
        String pw2 = "ca84445ae334cf562942cc25d92b6bf89dcfae8407dc507be737bf98e6468ab0";
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://"+host1+":"+port1+"/"+db1, user1, pw1);
            out.println("Connected");
            statement = con.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println(cAp.encrypt("test"));

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM vocab");
            while (resultSet.next()) {
                User u = new User(resultSet.getString("email"),resultSet.getString("password"));
                lu.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String temp = g.toJson(lu);
        lu.clear();
        out.println(temp);

        out.println("</html>\n</body>");
    }
}
