package core;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mi5ho on 03.01.2018.
 */
public class DBmethods {

    private String connString = "jdbc:oracle:thin:@asterix.fri.uniza.sk:1521/orclpdb.fri.uniza.sk";
    private String meno       = "alfa_sp";
    private String heslo      = "vsetcimajua";

    public DBmethods() {
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void vytvorStanicu(String pNazov, float pGpsSirka, float pGpsDlzka) {
        
        Connection connection = null;
        Statement stmt;

        try {

            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.createStatement();

            String sql;      

            sql = "INSERT INTO stanica (nazov, gps_sirka, gps_dlzka) VALUES('"+pNazov+"',"+pGpsSirka+","+pGpsDlzka+")";
            stmt.executeUpdate(sql);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void vytvorVlak(int pZaciatok, int pCiel, int pTyp, String pDatVyp, String pDatDor) {

        Connection connection = null;
        Statement stmt;

        try {

            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.createStatement();

            String sql;

            if (!pDatDor.equals("")||!pDatDor.equals(" ")||!pDatDor.isEmpty()) {

                sql = "INSERT INTO vlak (zaciatok,ciel,typ,dat_vypravenia,dat_dorazenia) VALUES("+pZaciatok+","+pCiel+","+pTyp+",to_timestamp('"+pDatVyp+"','DD-MM-YY'),to_timestamp('"+pDatDor+"','DD-MM-YY'))";
                stmt.executeUpdate(sql);

            } else {

                sql = "INSERT INTO vlak (zaciatok,ciel,typ,dat_vypravenia) VALUES("+pZaciatok+","+pCiel+","+pTyp+",to_timestamp('"+pDatVyp+"','DD-MM-YY'))";
                stmt.executeUpdate(sql);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void vytvorPohybVoznaVlak(char pTypPohybu, int pIdSnimaca, int pIdVlaku) {

        Connection connection = null;
        Statement stmt;

        try {

            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.createStatement();

            String sql;

            sql = "INSERT INTO pohyb_vozna_vlak (typ_pohybu,id_snimaca,id_vlaku) VALUES("+pTypPohybu+","+pIdSnimaca+","+pIdVlaku+")";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String zobrazVlak() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.createStatement();
            
            sql          = "SELECT id_vlaku, zaciatok, ciel, typ, dat_vypravenia, dat_dorazenia, vozne FROM vlak";
            ResultSet rs = stmt.executeQuery(sql);
            
     
            while(rs.next()){
                
                result += "Vlak\n"
                        + " > id              = "+rs.getString("id_vlaku")+"\n"
                        + " > zaciatok        = "+rs.getString("zaciatok")+"\n"
                        + " > ciel            = "+rs.getString("ciel")+"\n"
                        + " > typ             = "+rs.getString("typ")+"\n"
                        + " > dat. vypravenia = "+rs.getString("dat_vypravenia")+"\n"
                        + " > dat. dorazenia  = "+rs.getString("dat_dorazenia")+"\n"
                        + " > vozne           = "+rs.getObject("vozne")+"\n\n";        
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazPohybVoznaVlak() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.createStatement();
            
            sql          = "SELECT id_zaradenia, typ_pohybu, id_snimaca, id_vlaku FROM pohyb_vozna_vlak";
            ResultSet rs = stmt.executeQuery(sql);
            
     
            while(rs.next()){
                
                result += "Pohyb vozna vlak\n"
                        + " > id zaradenia = "+rs.getString("id_zaradenia")+"\n"
                        + " > typ pohybu   = "+rs.getString("typ_pohybu")+"\n"
                        + " > id snimaca   = "+rs.getString("id_snimaca")+"\n"
                        + " > id vlaku     = "+rs.getString("id_vlaku")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazPohyb() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();
            sql          = "SELECT id_pohybu, id_presunu, id_zaradenia, datum_od, datum_do, id_vozna, kod, poznamka FROM pohyb";
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                
                result += "Pohyb\n"
                        + " > id pohybu     = "+rs.getString("id_pohybu")+"\n"
                        + " > id presunu    = "+rs.getString("id_presunu")+"\n"
                        + " > id zaradenia  = "+rs.getString("id_zaradenia")+"\n"
                        + " > datum od      = "+rs.getString("datum_od")+"\n"
                        + " > datum do      = "+rs.getString("datum_do")+"\n"
                        + " > id_vozna      = "+rs.getString("id_vozna")+"\n"
                        + " > kod           = "+rs.getString("kod")+"\n"
                        + " > poznamka      = "+rs.getString("poznamka")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazSnimac() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();
            sql          = "SELECT id_snimaca, gps_sirka, gps_dlzka, cislo, id_stanice FROM snimac";
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                
                result += "Snimac\n"
                        + " > id snimaca = "+rs.getString("id_snimaca")+"\n"
                        + " > gps sirka  = "+rs.getString("gps_sirka")+"\n"
                        + " > gps dlzka  = "+rs.getString("gps_dlzka")+"\n"
                        + " > cislo      = "+rs.getString("cislo")+"\n"
                        + " > id stanice = "+rs.getString("id_stanice")+"\n\n";
                    
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazKolaj() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.createStatement();
            
            sql          = "SELECT cislo, dlzka, id_stanice FROM kolaj";
            ResultSet rs = stmt.executeQuery(sql);
            
     
            while(rs.next()){
                
                result += "Kolaj\n"
                        + " > cislo      = "+rs.getString("cislo")+"\n"
                        + " > dlzka      = "+rs.getString("dlzka")+"\n"
                        + " > id stanice = "+rs.getString("id_stanice")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazStanice() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.createStatement();
            
            sql          = "SELECT id_stanice, nazov, gps_sirka, gps_dlzka FROM stanica";
            ResultSet rs = stmt.executeQuery(sql);
            
     
            while(rs.next()){
                
                result += "Stanica\n"
                        + " > id stanice = "+rs.getString("id_stanice")+"\n"
                        + " > nazov      = "+rs.getString("nazov")+"\n"
                        + " > gps sirka  = "+rs.getString("gps_sirka")+"\n"
                        + " > gps dlzka  = "+rs.getString("gps_dlzka")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazVozen() {
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "SELECT id_vozna, kod, v_prevadzke, id_spolocnosti FROM vozen";
            ResultSet rs = stmt.executeQuery(sql);
     
            while(rs.next()){
                
                result += "Vozen\n"
                        + " > id vozna       = "+rs.getString("id_vozna")+"\n"
                        + " > kod            = "+rs.getString("kod")+"\n"
                        + " > v prevadzke    = "+rs.getString("v_prevadzke")+"\n"
                        + " > id spolocnosti = "+rs.getString("id_spolocnosti")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazPresun() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "SELECT id_presunu, id_snimaca_z, id_snimaca_na FROM presun";
            ResultSet rs = stmt.executeQuery(sql);
     
            while(rs.next()){
                
                result += "Presun\n"
                        + " > id presunu    = "+rs.getString("id_presunu")+"\n"
                        + " > id snimaca z  = "+rs.getString("id_snimaca_z")+"\n"
                        + " > id snimaca na = "+rs.getString("id_snimaca_na")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazSpolocnost() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "SELECT id_spolocnosti, nazov FROM spolocnost";
            ResultSet rs = stmt.executeQuery(sql);
     
            while(rs.next()){
                
                result += "Spolocnost\n"
                        + " > id spolocnosti = "+rs.getString("id_spolocnosti")+"\n"
                        + " > nazov          = "+rs.getString("nazov")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazTypVozna() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "SELECT rad, "
                                + "kod, "
                                + "interabilita, "
                                + "dlzka, "
                                + "hmotnost, "
                                + "loz_hmotnost, "
                                + "loz_dlzka, "
                                + "loz_sirka, "
                                + "loz_plocha, "
                                + "loz_vyska, "
                                + "loz_objem, "
                                + "poznamka, "
                                + "obrazok "
                            + "FROM typ_vozna";
            ResultSet rs = stmt.executeQuery(sql);
     
            while(rs.next()){
                
                result += "Typ vozna\n"
                        + " > rad           = "+rs.getString("rad")+"\n"
                        + " > kod           = "+rs.getString("kod")+"\n"
                        + " > interabilita  = "+rs.getString("interabilita")+"\n"
                        + " > dlzka         = "+rs.getString("dlzka")+"\n"
                        + " > hmotnost      = "+rs.getString("hmotnost")+"\n"
                        + " > loz. hmotnost = "+rs.getString("loz_hmotnost")+"\n"
                        + " > loz. dlzka    = "+rs.getString("loz_dlzka")+"\n"
                        + " > loz. sirka    = "+rs.getString("loz_sirka")+"\n"
                        + " > loz. plocha   = "+rs.getString("loz_plocha")+"\n"
                        + " > loz. vyska    = "+rs.getString("loz_vyska")+"\n"
                        + " > loz. objem    = "+rs.getString("loz_objem")+"\n"
                        + " > poznamka      = "+rs.getString("poznamka")+"\n"
                        + " > obrazok       = "+rs.getBlob("obrazok")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazPouzivatel() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "SELECT id_pouzivatela, rod_cislo, meno, priezvisko FROM pozivatel";
            ResultSet rs = stmt.executeQuery(sql);
     
            while(rs.next()){
                
                result += "Pouzivatel\n"
                        + " > id pouzivatela = "+rs.getString("id_pouzivatela")+"\n"
                        + " > rodne cislo    = "+rs.getString("rod_cislo")+"\n"
                        + " > meno           = "+rs.getString("meno")+"\n"
                        + " > priezvisko     = "+rs.getString("priezvisko")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public String zobrazZaznam() {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        try {
                
            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "SELECT datum, tabulka, id_pouzivatela FROM zaznam";
            ResultSet rs = stmt.executeQuery(sql);
     
            while(rs.next()){
                
                result += "Zaznam\n"
                        + " > datum          = "+rs.getString("datum")+"\n"
                        + " > tabulka        = "+rs.getString("tabulka")+"\n"
                        + " > id pouzivatela = "+rs.getString("id_pouzivatela")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public void pridajVozen(int pIdSpolocnosti, int pKod, int pIdVozna, int pIdSnimaca) {
        
        Connection connection = null;
        CallableStatement stmt;

        try {

            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.prepareCall("call pridaj_vozen(?,?,?,?)");

            stmt.setInt(1, pIdSpolocnosti);
            stmt.setInt(2, pKod);
            stmt.setInt(3, pIdVozna);
            stmt.setInt(4, pIdSnimaca);
            
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void vyradVozen(int pKod, int pIdVozna) {
        
        Connection connection = null;
        CallableStatement stmt;

        try {

            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.prepareCall("call vyrad_vozen_z_prevadzky(?,?)");

            stmt.setInt(1, pKod);
            stmt.setInt(2, pIdVozna);
            
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void zaradVozenDoVlaku(int pIdVlaku, int pIdVozna, int pKod, int pIdSnimaca) {
        
        Connection connection = null;
        CallableStatement stmt;

        try {

            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.prepareCall("call zarad_vozen_do_vlaku(?,?,?,?)");

            stmt.setInt(1, pIdVlaku);
            stmt.setInt(2, pIdVozna);
            stmt.setInt(3, pKod);
            stmt.setInt(4, pIdSnimaca);
            
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void vyradVozenZVlaku(int pIdVlaku, int pIdVozna, int pKod, int pIdSnimaca) {
        
        Connection connection = null;
        CallableStatement stmt;

        try {

            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.prepareCall("call vyrad_vozen_z_vlaku(?,?,?,?)");

            stmt.setInt(1, pIdVlaku);
            stmt.setInt(2, pIdVozna);
            stmt.setInt(3, pKod);
            stmt.setInt(4, pIdSnimaca);
            
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void zmenPoholuVoznaVStanici(int pIdVozna, int pKod, int pIdSnimaca, String pPoznamka) {
        
        Connection connection = null;
        CallableStatement stmt;

        try {

            connection = DriverManager.getConnection(connString,meno,heslo);
            stmt       = connection.prepareCall("call presun_vozen(?,?,?,?)");

            stmt.setInt(1, pIdVozna);
            stmt.setInt(2, pKod);
            stmt.setInt(3, pIdSnimaca);
            stmt.setString(4, pPoznamka);
            
            
            
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***************************************************************************
    * Vystupy
    */
    
    public String zoznamVoznovVStanici(boolean pVPrevadzke, int pIdStanice, Timestamp pOd, Timestamp pDo) {
        
        Connection connection = null;
        Statement  stmt;
        String     sql;
        String     result = "";
        
        String vPrevadzke;
        if(pVPrevadzke) {
            vPrevadzke = "A";
        } else {
            vPrevadzke = "N";
        }
        
        try {
                
            connection   = DriverManager.getConnection(connString,meno,heslo);
            stmt         = connection.createStatement();          
            sql          = "SELECT id_vozna, kod, nazov, datum_od, datum_do from ZOZNAM_VOZNOV_V_STANICI"
                         + " where "
                         + " v_prevadzke = '"+vPrevadzke+"' and "
                         + " id_stanice = "+pIdStanice+" and "
                         + " datum_od >= to_timestamp('"+pOd.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF') and "
                         + " datum_do <= to_timestamp('"+pDo.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
              
            System.out.println(sql);
                    
            ResultSet rs = stmt.executeQuery(sql);
                     
     
            while(rs.next()){
                
                System.out.println("row");
                result += "Vozen\n"
                        + " > ID vozna      = "+rs.getString("id_vozna")+"\n"
                        + " > Kod           = "+rs.getString("kod")+"\n"
                        + " > Nazov stanice = "+rs.getString("nazov")+"\n"
                        + " > Cas od        = "+rs.getString("datum_od")+"\n"
                        + " > Cas do        = "+rs.getString("datum_do")+"\n\n";
                                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBmethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
        
    }
    

}