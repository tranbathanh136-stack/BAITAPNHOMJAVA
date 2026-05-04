package model;

public class Publisher {
    private int publisherId;
    private String publisherName;
    private String address;
    private String phone;

    public Publisher() {
    }

    public Publisher(int publisherId, String publisherName, String address, String phone) {
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.address = address;
        this.phone = phone;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return publisherName;
    }
}