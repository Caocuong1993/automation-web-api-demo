package com.auto.api.common;

import org.json.JSONObject;

import java.util.Collection;

public class HashMapExtend<K, V> extends java.util.HashMap<K, V> {
    public HashMapExtend(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public HashMapExtend(int initialCapacity) {
        super(initialCapacity);
    }

    public HashMapExtend() {
        super();
    }

    //    @NotNull
    @Override
    public Collection<V> values() {
        return super.values();
    }

    public String getString(K key) {
        return super.get(key) == null ? null : super.get(key).toString();
    }

    public int getInt(K key) {
        return Integer.parseInt(super.get(key).toString());
    }

    public JSONObject toJson() {
        return new JSONObject(this);
    }
}
