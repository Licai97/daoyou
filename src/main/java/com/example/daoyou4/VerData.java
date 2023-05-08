package com.example.daoyou4;

import java.util.Objects;

public class VerData {
    int  position;
    String name;
    String introduction;

    public VerData(int position, String name, String introduction) {
        this.position = position;
        this.name = name;
        this.introduction = introduction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VerData)) return false;
        VerData verData = (VerData) o;
        return position == verData.position && Objects.equals(name, verData.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return position + ">" + name + ">"+introduction+"\n";
    }
}
