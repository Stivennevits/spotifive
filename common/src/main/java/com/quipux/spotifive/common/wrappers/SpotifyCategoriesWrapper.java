package com.quipux.spotifive.common.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.quipux.spotifive.common.response.CategoriesResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyCategoriesWrapper {

    private Categories categories;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Categories {
        private List<CategoriesResponse> items;
    }
}
