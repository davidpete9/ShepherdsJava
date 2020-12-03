package company;

import java.util.UUID;

public class Company {

    public static final String NAME = "name";
    public static final String LEADER = "leader";
    public static final String ADDRESS = "address";
    public static final String ACCOUNT = "account";

    private String id;
    private String name;
    private String leader = null;
    private String address = null;
    private String account = null;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getLeader() {
        return leader;
    }

    public String getAddress() {
        return address;
    }

    public String getAccount() {
        return account;
    }

    public Company(String name, String leader, String address, String account) {
        this(UUID.randomUUID().toString().replace("-", ""), name, leader, address, account);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Company(String id, String name, String leader, String address, String account) {
        this.id = id;
        this.name = name;
        this.leader = leader;
        this.address = address;
        this.account = account;
    }

    public String toString() {
        return this.getName();
    }
}
