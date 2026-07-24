package org.example.configuracon;

public interface IConfigReader {
    String getString(String key);
    int getInt(String key);
    boolean getBoolean(String key);
}
