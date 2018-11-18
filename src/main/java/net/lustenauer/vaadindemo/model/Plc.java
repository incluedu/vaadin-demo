package net.lustenauer.vaadindemo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.transaction.Transactional;

@Entity
public class Plc {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private String address;

    private int db;

    private int port;

    private int rack;

    private int slot;

    private boolean active;

    private boolean running;


    protected Plc() {
    }

    public Plc(String name, String description, String address, int db, int port, int rack, int slot, boolean active) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.db = db;
        this.port = port;
        this.rack = rack;
        this.slot = slot;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getRack() {
        return rack;
    }

    public void setRack(int rack) {
        this.rack = rack;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isRunning() {
        return running;
    }

    @Transactional
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public String toString() {
        return "Plc{" +
                "name='" + name + '\'' +
                '}';
    }
}
