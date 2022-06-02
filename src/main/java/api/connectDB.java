package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.servlet.SparkApplication;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class connectDB {
    static Connection con;
    static Statement state;
    static Gson g = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    static String cDb = "TisIsAt4esing.fo!rsmStieng31";
    static Crypt cAp = new Crypt("Henlo4Soms.!eagIzarsaFing");

    public static void main(String[] args) {
        List<User> lu = new ArrayList<>();
        /*String keyStoreLocation = "keystore.jks";
        String keyStorePassword = "footest";
        secure(keyStoreLocation, keyStorePassword, null, null);*/

        try{
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
            Connection connection = DriverManager.getConnection("jdbc:postgresql://"+host1+":"+port1+"/"+db1, user1, pw1);
            con = connection;
            System.out.println("Connected");
            Statement statement = connection.createStatement();
            state = statement;

            port(getHerokuAssignedPort());

            userIn();
            vocOps();
            changeLog();

            System.out.println(cAp.encrypt("test"));

            get("/", (req,resp) -> {
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
                return temp;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void userIn() {
        get("/login", (req, resp) -> {
            String mail = req.headers("email");
            String pw = req.headers("password");
            PreparedStatement log = con.prepareStatement("SELECT * FROM vocab WHERE email=? and PGP_SYM_DECRYPT(CAST(password AS bytea), ?)=?");
            log.setString(1, mail);
            log.setString(2, cDb);
            log.setString(3, cAp.decrypt(pw));
            ResultSet resultSet = log.executeQuery();
            if(resultSet.next()) {
                resp.status(200);
                resp.type("text/xml");
                return "Login successful";
            } else {
                resp.status(401);
                resp.type("text/xml");
                return "Login failed";
            }
        });
        post("/register", (req, resp) -> {
            User user = g.fromJson(req.body(), User.class);
            PreparedStatement reg = con.prepareStatement("SELECT * FROM vocab");
            String tablename = user.getEmail().replaceAll("[^a-zA-Z0-9]","");
            ResultSet resultSet1 = reg.executeQuery();
            boolean ex=false;
            while(resultSet1.next()) {
                String rsForm = resultSet1.getString("email").replaceAll("[^a-zA-Z0-9]","");
                if(rsForm.equals(tablename)) {
                    ex = true;
                }
            }
            if(ex) {
                resp.status(400);
                resp.type("text/xml");
                return "User already exists";
            } else {
                PreparedStatement newUser = con.prepareStatement("INSERT INTO vocab (email,password,tablename) VALUES (?,PGP_SYM_ENCRYPT(?,?),?)");
                newUser.setString(1, user.getEmail());
                newUser.setString(2, cAp.decrypt(user.getPw()));
                newUser.setString(3, cDb);
                newUser.setString(4, tablename);
                newUser.executeUpdate();
                PreparedStatement newVocab = con.prepareStatement("CREATE TABLE "+tablename+" (id SERIAL PRIMARY KEY,answer varchar(255),question varchar(255),language varchar(255),phase int)");
                newVocab.executeUpdate();
                resp.status(200);
                resp.type("text/xml");
                return "New User registered";
            }
        });
    }
    public static void vocOps() {
        post("/voc", (req, resp) -> {
            PreparedStatement log = con.prepareStatement("SELECT * FROM vocab WHERE email=? and PGP_SYM_DECRYPT(CAST(password AS bytea), ?)=?");
            log.setString(1,req.headers("email"));
            log.setString(2, cDb);
            log.setString(3, cAp.decrypt(req.headers("password")));
            ResultSet resultSet = log.executeQuery();
            if(resultSet.next()) {
                Voc voc = g.fromJson(req.body(), Voc.class);
                String tablename = resultSet.getString("tablename");
                PreparedStatement newVoc = con.prepareStatement("INSERT INTO "+tablename+" (answer,question,language,phase) VALUES (?,?,?,0)");
                newVoc.setString(1,voc.getAnswer());
                newVoc.setString(2,voc.getQuestion());
                newVoc.setString(3,voc.getLanguage());
                newVoc.executeUpdate();
                resp.status(200);
                resp.type("text/xml");
                return "Inserted new voc";
            } else {
                resp.status(401);
                resp.type("text/xml");
                return "Wrong login";
            }
        });
        get("/voc", (req, resp) -> {
            PreparedStatement log = con.prepareStatement("SELECT * FROM vocab WHERE email=? and PGP_SYM_DECRYPT(CAST(password AS bytea), ?)=?");
            log.setString(1,req.headers("email"));
            log.setString(2, cDb);
            log.setString(3, cAp.decrypt(req.headers("password")));
            ResultSet r0 = log.executeQuery();
            if(r0.next()) {
                List<VocWhole> v = new ArrayList<>();
                String tablename = r0.getString("tablename");
                PreparedStatement getVoc = con.prepareStatement("SELECT * FROM "+tablename);
                ResultSet r = getVoc.executeQuery();
                while(r.next()) {
                    VocWhole vo = new VocWhole(r.getInt("id"),r.getString("answer"),r.getString("question"),r.getString("language"),r.getInt("phase"));
                    v.add(vo);
                }
                resp.status(200);
                resp.type("json");
                return g.toJson(v);
            } else {
                resp.status(401);
                resp.type("text/xml");
                return "Wrong login";
            }
        });
        delete("/voc/:id", (req,resp) -> {
            PreparedStatement log = con.prepareStatement("SELECT * FROM vocab WHERE email=? and PGP_SYM_DECRYPT(CAST(password AS bytea), ?)=?");
            log.setString(1,req.headers("email"));
            log.setString(2, cDb);
            log.setString(3, cAp.decrypt(req.headers("password")));
            ResultSet r = log.executeQuery();
            if(r.next()) {
                String tablename = r.getString("tablename");
                PreparedStatement delVoc = con.prepareStatement("DELETE FROM "+tablename+" WHERE id=?");
                delVoc.setInt(1,Integer.parseInt(req.params(":id")));
                delVoc.executeUpdate();
                resp.status(200);
                resp.type("text/xml");
                return "Voc with id="+req.params(":id")+" deleted";
            } else {
                resp.status(401);
                resp.type("text/xml");
                return "Wrong login";
            }
        });
        patch("/voc/:id", (req,resp) -> {
            PreparedStatement log = con.prepareStatement("SELECT * FROM vocab WHERE email=? and PGP_SYM_DECRYPT(CAST(password AS bytea), ?)=?");
            log.setString(1,req.headers("email"));
            log.setString(2, cDb);
            log.setString(3, cAp.decrypt(req.headers("password")));
            ResultSet r = log.executeQuery();
            if(r.next()) {
                VocWhole v = g.fromJson(req.body(), VocWhole.class);
                String tablename = r.getString("tablename");
                PreparedStatement updVoc = con.prepareStatement("UPDATE "+tablename+" SET answer=?, question=?, language=?, phase=? WHERE id=?");
                updVoc.setString(1,v.getAnswer());
                updVoc.setString(2,v.getQuestion());
                updVoc.setString(3,v.getLanguage());
                updVoc.setInt(4,v.getPhase());
                updVoc.setInt(5,Integer.parseInt(req.params(":id")));
                updVoc.executeUpdate();
                resp.status(200);
                resp.type("text/xml");
                return "Voc with id="+req.params(":id")+" has been updated";
            } else {
                resp.status(401);
                resp.type("text/xml");
                return "Wrong login";
            }
        });
    }
    public static void changeLog() {
        patch("/user", (req,resp) -> {
            PreparedStatement log = con.prepareStatement("SELECT * FROM vocab WHERE email=? and PGP_SYM_DECRYPT(CAST(password AS bytea), ?)=?");
            log.setString(1,req.headers("email"));
            log.setString(2, cDb);
            log.setString(3, cAp.decrypt(req.headers("password")));
            ResultSet r = log.executeQuery();
            if(r.next()) {
                PreparedStatement changeLog = con.prepareStatement("UPDATE vocab SET email=?,password=PGP_SYM_ENCRYPT(?,?) WHERE email=?");
                User u = g.fromJson(req.body(), User.class);
                changeLog.setString(1, u.getEmail());
                changeLog.setString(2, cAp.decrypt(u.getPw()));
                changeLog.setString(3, cDb);
                changeLog.setString(4, req.headers("email"));
                changeLog.executeUpdate();
                resp.status(200);
                resp.type("text/xml");
                return "User Data changed";
            } else {
                resp.status(401);
                resp.type("text/xml");
                return "Wrong login";
            }
        });
    }
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
