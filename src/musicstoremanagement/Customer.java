package musicstoremanagement;

import java.util.ArrayList;

public class Customer {

    private int id;
    private String name;
    private String Address;
    private ArrayList<Integer> albumID;
    private ArrayList<String> dates;
    private ArrayList<String> albumTitles;
    private ArrayList<Integer> quantities;
    private ArrayList<Double> prices;

    public Customer() {
        dates = new ArrayList();
        albumID = new ArrayList();
        albumTitles = new ArrayList();
        quantities = new ArrayList();
        prices = new ArrayList();
    }

    public Customer(int id, String name, String Address, ArrayList<Integer> albumID, ArrayList<String> dates, ArrayList<String> albumTitles, ArrayList<Integer> quantities, ArrayList<Double> prices) {
        dates = new ArrayList();
        albumID = new ArrayList();
        albumTitles = new ArrayList();
        quantities = new ArrayList();
        prices = new ArrayList();
        this.id = id;
        this.name = name;
        this.Address = Address;
        this.albumID = albumID;
        this.dates = dates;
        this.albumTitles = albumTitles;
        this.quantities = quantities;
        this.prices = prices;
    }

    public Customer(int id, String name, String Address) {
        dates = new ArrayList();
        albumID = new ArrayList();
        albumTitles = new ArrayList();
        quantities = new ArrayList();
        prices = new ArrayList();
        this.id = id;
        this.name = name;
        this.Address = Address;
    }

    public void updateCustomerInfo(int aID, String aTitle, String date, int quantity, double price) {

            albumID.add(aID);
            dates.add(date);
            albumTitles.add(aTitle);
            quantities.add(quantity);
            prices.add(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getDates() {
        return dates;
    }

    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
    }

    public ArrayList<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(ArrayList<Integer> quantities) {
        this.quantities = quantities;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public ArrayList<Integer> getAlbumID() {
        return albumID;
    }

    public void setAlbumID(ArrayList<Integer> albumID) {
        this.albumID = albumID;
    }

    public ArrayList<String> getAlbumTitles() {
        return albumTitles;
    }

    public void setAlbumTitles(ArrayList<String> albumTitles) {
        this.albumTitles = albumTitles;
    }

    public ArrayList<Double> getPrices() {
        return prices;
    }

    public void setPrices(ArrayList<Double> prices) {
        this.prices = prices;
    }

}
