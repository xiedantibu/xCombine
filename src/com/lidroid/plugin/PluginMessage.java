package com.lidroid.plugin;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 13-6-17
 * Time: AM 11:53
 */
public class PluginMessage {
    private long id;
    public String content;
    public Object[] args;

    public PluginMessage(String content, Object... args) {

        if (content == null) {
            throw new IllegalArgumentException("\"content\" must not be null");
        }

        this.content = content;
        this.args = args;
        id = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + content.hashCode();
        return result;
    }
}
