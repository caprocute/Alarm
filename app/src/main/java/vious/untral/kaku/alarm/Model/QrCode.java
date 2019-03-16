package vious.untral.kaku.alarm.Model;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.ThreadLocalRandom;

public class QrCode {
    private String id;
    private String content;
    private String name;

    public QrCode() {
        this.id = randomID();
        this.content = "demo";
        this.name = "demo";
    }

    public QrCode(String id, String content, String name) {
        this.id = id;
        this.content = content;
        this.name = name;
    }

    private String randomID() {
        long number = ThreadLocalRandom.current().nextLong(100000, 1000000);
        String text = RandomStringUtils.randomAlphabetic(2);
        return ("QR" + number + text);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
