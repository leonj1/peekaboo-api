package com.josemleon.models;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * Intended for high level stats of the secrets in queue.
 * Such as:
 * - how many secrets in current queue
 * - how many valid (not expired) secrets
 *
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class QueueSummary {
    @SerializedName("total_secrets")
    private int totalSecrets;
    @SerializedName("count_still_valid")
    private int countStillValid;

    public QueueSummary(int totalSecrets, int countStillValid) {
        this.totalSecrets = totalSecrets;
        this.countStillValid = countStillValid;
    }

    public int getTotalSecrets() {
        return totalSecrets;
    }

    public int getCountStillValid() {
        return countStillValid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueueSummary that = (QueueSummary) o;
        return totalSecrets == that.totalSecrets &&
                countStillValid == that.countStillValid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalSecrets, countStillValid);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("totalSecrets", totalSecrets)
                .append("countStillValid", countStillValid)
                .toString();
    }
}
