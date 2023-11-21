package com.example.p7_daa_alexandre.model;

import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class CatFact {

    @SerializedName("fact")
    @Expose
    private final String fact;
    @SerializedName("length")
    @Expose
    private final Integer length;

    public CatFact(String fact, Integer length) {
        this.fact = fact;
        this.length = length;
    }

    public String getFact() {
        return fact;
    }

    public Integer getLength() {
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatFact catFact = (CatFact) o;
        return Objects.equals(fact, catFact.fact) &&
                Objects.equals(length, catFact.length);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fact, length);
    }

    @NonNull
    @Override
    public String toString() {
        return "CatFact{" +
                "fact='" + fact + '\'' +
                ", length=" + length +
                '}';
    }
}