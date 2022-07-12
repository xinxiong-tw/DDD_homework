package com.ddd.domain.calculation.valueObject;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
@Builder
public class PromotionInfo {
    private String title;
    private String description;
    private List<String> tags;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromotionInfo that = (PromotionInfo) o;
        return title.equals(that.title) && description.equals(that.description) && tags.equals(that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, tags);
    }

    @Override
    public String toString() {
        return "PromotionInfo{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                '}';
    }
}
