package musicstoremanagement;

import java.util.ArrayList;
import java.sql.*;
import javax.swing.JOptionPane;

public final class Controller {

    private PreparedStatement ps;
    private ResultSet rs;
    private ArrayList<Album> albums;
    private ArrayList<Customer> customers;
    private Album album;
    private Track track;
    private Singer singer;
    private Customer c;

    public Controller() {
        albums = new ArrayList<>();
        customers = new ArrayList<>();
        getCustomersFromDb();
        getAlbumsFromDb();
    }

    public Boolean addAlbum(Album album) {
        boolean found = false;
        for (Album oldAlbums : albums) {
            if (oldAlbums.getTitle() == null ? album.getTitle() == null : oldAlbums.getTitle().equals(album.getTitle())) {
                found = true;
            }
        }
        if (!found) {
            albums.add(album);
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public int getLastRecord() {
        int last = 0;
        Connection conn = dbConnection();
        try {
            ps = conn.prepareStatement("SELECT ID FROM ALBUMS");
            rs = ps.executeQuery();
            while (rs.next()) {
                last = Integer.parseInt(rs.getString("ID"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            try {
                conn.close();
                ps.close();
                rs.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return last;
    }

    public boolean addAlbumsToDb(Album album) throws SQLException {
        boolean error = false;
        Connection conn = dbConnection();
        String sql1 = "INSERT INTO Tracks(Title,Duration,Language,TrackRating,SingerName,SingerRating,AlbumID) Values(? ,? ,? , ? , ? , ? , ?)";
        String sql2 = "INSERT INTO Albums(Title,ReleaseYear,Genre,Rating,Quantity,Price,NoofTracks) Values(? ,? ,? , ? , ? , ? , ?)";
        for (Track track : album.getTracks()) {
            try {
                ps = conn.prepareStatement(sql1);
                ps.setString(1, track.getTitle());
                ps.setString(2, track.getDuration());
                ps.setString(3, track.getLanguage());
                ps.setInt(4, track.getRating());
                ps.setString(5, track.getArtist().getName());
                ps.setInt(6, track.getArtist().getRating());
                ps.setInt(7, album.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                error = true;
            }
        }
        try {
            ps = conn.prepareStatement(sql2);
            ps.setString(1, album.getTitle());
            ps.setInt(2, album.getReleaseYear());
            ps.setString(3, album.getGenre());
            ps.setInt(4, album.getRating());
            ps.setInt(5, album.getQuantity());
            ps.setInt(6, (int) album.getPrice());
            ps.setInt(7, album.getTracks().size());
            ps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            error = true;
        } finally {
            try {
                conn.close();
                ps.close();
                rs.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return !error;
    }

    public void getAlbumsFromDb() {
        Connection conn = dbConnection();
        try {
            ps = conn.prepareStatement("SELECT * FROM Albums");
            rs = ps.executeQuery();
            while (rs.next()) {
                album = new Album(rs.getInt("ID"), rs.getString("Title"), rs.getInt("ReleaseYear"), rs.getInt("Rating"), rs.getInt("Quantity"), rs.getInt("price"), rs.getString("genre"));
                if (!addAlbum(album)) {
                    JOptionPane.showMessageDialog(null, "Error in getting Albums from database", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        try {
            ps = conn.prepareStatement("SELECT * FROM Tracks");
            rs = ps.executeQuery();
            while (rs.next()) {
                singer = new Singer(rs.getString("SingerName"), rs.getInt("SingerRating"));
                track = new Track(rs.getString("Title"), rs.getString("Duration"), singer, rs.getInt("TrackRating"), rs.getString("Language"));
                for (Album album : albums) {
                    if (album.getId() == rs.getInt("AlbumID")) {
                        album.addTrack(track);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                conn.close();
                ps.close();
                rs.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public boolean sellAlbum(int albumID, int quantity, int cID, String cName, String address, double price, String date) {
        boolean exception = false;
        boolean found = false;
        Connection con = dbConnection();
        String sql1 = "INSERT INTO ShowRecord (CustomerID,CustomerName,address,AlbumID,AlbumTitle,Price,Dates,Quantity) Values(? ,?,? ,? , ?,? , ? , ? )";
        String sql2 = "UPDATE Albums SET Quantity = ? WHERE ID = ? ";
        String sql3 = "DELETE FROM Albums WHERE ID=?";
        String sql4 = "DELETE FROM Tracks WHERE AlbumID=?";
        for (Album a : getAlbums()) {
            if (a.getId() == albumID) {
                try {
                    found = true;
                    ps = con.prepareStatement(sql1);
                    ps.setInt(1, cID);
                    ps.setString(2, cName);
                    ps.setString(3, address);
                    ps.setInt(4, a.getId());
                    ps.setString(5, a.getTitle());
                    ps.setDouble(6, price);
                    ps.setString(7, date);
                    ps.setInt(8, quantity);
                    ps.executeUpdate();
                    if (a.getQuantity() - quantity <= 0) {
                        ps = con.prepareStatement(sql3);
                        ps.setInt(1, a.getId());
                        ps.executeUpdate();
                        ps = con.prepareStatement(sql4);
                        ps.setInt(1, a.getId());
                        ps.executeUpdate();
                    }
                    ps = con.prepareStatement(sql2);
                    ps.setInt(1, a.getQuantity() - quantity);
                    ps.setInt(2, a.getId());
                    ps.executeUpdate();
                    break;
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error ", JOptionPane.ERROR_MESSAGE);
                    exception = true;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error ", JOptionPane.ERROR_MESSAGE);
                    exception = true;
                }
            }

        }
        return !exception && found;
    }

    public void getCustomersFromDb() {
        Connection con = dbConnection();
        boolean found = false;
        try {
            ps = con.prepareStatement("SELECT * FROM ShowRecord ");
            rs = ps.executeQuery();
            while (rs.next()) {
                for (Customer c : customers) {

                    if (rs.getInt("CustomerID") == c.getId()) {
                        c.updateCustomerInfo(rs.getInt("AlbumID"), rs.getString("AlbumTitle"), rs.getString("Dates"), rs.getInt("quantity"), rs.getInt("Price"));
                        found = true;
                    } else {
                        found = false;
                    }
                }
                if (!found) {
                    Customer c = new Customer(rs.getInt("CustomerID"), rs.getString("CustomerName"), rs.getString("Address"));
                    c.updateCustomerInfo(rs.getInt("AlbumID"), rs.getString("AlbumTitle"), rs.getString("Dates"),
                            rs.getInt("Quantity"), rs.getDouble("Price"));
                    customers.add(c);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    public Customer findCustomer(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }

    public Album findAlbum(int id) {
        for (Album album : albums) {
            if (album.getId() == id) {
                return album;
            }
        }
        return null;
    }

    public static Connection dbConnection() {
        Connection conn = null;
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            String url = "jdbc:ucanaccess://C:/Users/SAM/Documents\\NetBeansProjects/MusicStoreManagement/src/musicstoremanagement/MusicStore.accdb";
            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return conn;
    }
}
